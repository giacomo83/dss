/**
 * DSS - Digital Signature Services
 * Copyright (C) 2015 European Commission, provided under the CEF programme
 * 
 * This file is part of the "DSS - Digital Signature Services" project.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package eu.europa.esig.dss.x509.revocation.ocsp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Test;

import eu.europa.esig.dss.DSSUtils;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.x509.CertificateToken;
import eu.europa.esig.dss.x509.revocation.ocsp.ExternalResourcesOCSPSource;
import eu.europa.esig.dss.x509.revocation.ocsp.OCSPSource;
import eu.europa.esig.dss.x509.revocation.ocsp.OCSPToken;

public class OfflineOCSPSourceTest {

	@Test
	public void testOCSPUniversign() {

		String ocspResponse = "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";

		ExternalResourcesOCSPSource ocspSource = new ExternalResourcesOCSPSource(new ByteArrayInputStream(Utils.fromBase64(ocspResponse)));

		CertificateToken userUniversign = DSSUtils.loadCertificateFromBase64EncodedString(
				"MIIEjjCCA3agAwIBAgIQfCokxd5pp8RnZZOUHNbq0zANBgkqhkiG9w0BAQsFADBuMQswCQYDVQQGEwJGUjEgMB4GA1UEChMXQ3J5cHRvbG9nIEludGVybmF0aW9uYWwxHDAaBgNVBAsTEzAwMDIgNDM5MTI5MTY0MDAwMjYxHzAdBgNVBAMTFlVuaXZlcnNpZ24gQ0EgaGFyZHdhcmUwHhcNMTIwNzA2MTkzNzI0WhcNMTcwNzA2MTkzNzI0WjBrMQswCQYDVQQGEwJGUjERMA8GA1UEBxMITkFOVEVSUkUxFzAVBgNVBAoTDkFYQSBGUkFOQ0UgVklFMRcwFQYDVQQLEw4wMDAyIDMxMDQ5OTk1OTEXMBUGA1UEAxMOQVhBIEZSQU5DRSBWSUUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDPbheD3Lk34Rv+SBwS3duDqzKNEHoNen48pzVbt6wFQf9bqpE9YPZh+jb+bnRUXLbv77vaWBfCZGOfrybDv/mqpmTkdYk6F+D7ddwQuF7OVmE+VqeOgoBc+Vo1LDzjowDPOmyH938DB1ZUzI8r2mw60rR9laqLI7kO6OT/9fodJMz1eE2G6LEzXvOj+V0PhDg3FX+vF+pLFUmI53x3L+zM8AZofUCY0oaUpY1hgWR4on5K1Oy0me63UHkslEjFS5jYff4EE+z/eZY7+e3FMqwzFm5al60w1djzsHUVod+CkIASs71VgAYSYOQyckwmKaFs6325JHAu5DH5a62WVqLnAgMBAAGjggEpMIIBJTA7BggrBgEFBQcBAQQvMC0wKwYIKwYBBQUHMAGGH2h0dHA6Ly9zY2FoLm9jc3AudW5pdmVyc2lnbi5ldS8wQgYDVR0gBDswOTA3BgsrBgEEAftLBQEDAjAoMCYGCCsGAQUFBwIBFhpodHRwOi8vZG9jcy51bml2ZXJzaWduLmV1LzBEBgNVHR8EPTA7MDmgN6A1hjNodHRwOi8vY3JsLnVuaXZlcnNpZ24uZXUvdW5pdmVyc2lnbl9jYV9oYXJkd2FyZS5jcmwwDAYDVR0TAQH/BAIwADAOBgNVHQ8BAf8EBAMCBkAwHQYDVR0OBBYEFK13mc0NTbq7YVWmGWisLz2JJu57MB8GA1UdIwQYMBaAFGDkMN3uetTQfl0l2f07eyFkT927MA0GCSqGSIb3DQEBCwUAA4IBAQA7DWIeC3qTgW+OOsoYnzuwZxdu+F9eiqiVhq2UXx1vxjJQ6hthfq1Thzj5050fn5GQ/HeSNl05+hfoDpAK0JVWLssq1rrvBAx2lfgNWTG+LF581DVtH1I3NLi+A9YslvCPt51NVGAERhye6BZyugDlQCVhy6dRFhqSDSbi+S7RRqpIl/QDR/pBOwnBePO6qSpwSrDsCJT+N9nFBHmXpRsyFJyPEFZMIAVoluuJq4mCEGLVtqiC4DzvAwCsFBKlnwQ7pSFHO9ztXMYHpYhD/0wDSegwcvAVm7p8/N0PQDaAZQlWXs7McCBHeQPjxVD2xAkk7s9joicJ6kKttfxfEc6w");
		CertificateToken caUniversign = DSSUtils.loadCertificateFromBase64EncodedString(
				"MIIEYTCCA0mgAwIBAgIQIVWN4tmvyrr2CIjMBjGC1zANBgkqhkiG9w0BAQsFADB2MQswCQYDVQQGEwJGUjEgMB4GA1UEChMXQ3J5cHRvbG9nIEludGVybmF0aW9uYWwxHDAaBgNVBAsTEzAwMDIgNDM5MTI5MTY0MDAwMjYxJzAlBgNVBAMTHlVuaXZlcnNpZ24gUHJpbWFyeSBDQSBoYXJkd2FyZTAeFw0xMjA2MTUxMjU2MjVaFw0yMjA2MTUxMjU2MjVaMG4xCzAJBgNVBAYTAkZSMSAwHgYDVQQKExdDcnlwdG9sb2cgSW50ZXJuYXRpb25hbDEcMBoGA1UECxMTMDAwMiA0MzkxMjkxNjQwMDAyNjEfMB0GA1UEAxMWVW5pdmVyc2lnbiBDQSBoYXJkd2FyZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKHfv76grtwK9PRFTBq4BDtmmLaaHDAqkj1wd0lHIN2QH1jM6hKWeS4U/wy8QuYtvw0aFxYxiMWzS/vrj0Sczv5hAt8reE1Eg1uQcx6+aSUBqJ6a+1TbM7PtkHg1ozgbmVXGuiOLyviLhhUo8XmeeGEhux+cyRNiYCs37VPN5OVrKks29dspMkvllkexfrxiPfc+gB58EU+iNEcip/YrZLu4ZqErlCePIVBLyX9skb7QwKXDW8XBIgAzpoBj0U9/4Vxaiyj209xT1Uuz2vKsuT8Hq7I1vt7miYviHg/ovsWXH6yGcNomxX55l0qWQ4z+mGlVhCLDPMKmspY5D+1kSqsCAwEAAaOB8jCB7zA7BgNVHSAENDAyMDAGBFUdIAAwKDAmBggrBgEFBQcCARYaaHR0cDovL2RvY3MudW5pdmVyc2lnbi5ldS8wEgYDVR0TAQH/BAgwBgEB/wIBADBMBgNVHR8ERTBDMEGgP6A9hjtodHRwOi8vY3JsLnVuaXZlcnNpZ24uZXUvdW5pdmVyc2lnbl9wcmltYXJ5X2NhX2hhcmR3YXJlLmNybDAOBgNVHQ8BAf8EBAMCAQYwHQYDVR0OBBYEFGDkMN3uetTQfl0l2f07eyFkT927MB8GA1UdIwQYMBaAFE3Z/Kgtx8hapK1fSa5opNyeihIiMA0GCSqGSIb3DQEBCwUAA4IBAQAQrQJxwn+DBwN+KTt75IuOkaPOnZ+FfmF/1V7zDt3YNz7n1hRXlflbx9wBJn30TwyuTuZ/Cq1gEiA+TJMrsdZPKvagY8a/q7oCm6jYw6cBhopErwV/sZ9R3Ic+fphKSxoEnygpZs4uKMU2bB7nbul+sdJkP/OrIHKfHydMk3ayeAxnnieOj8EU+Z5w3fpekOGOtb4VUTESWU/xQfDZcNaauNRU2DYFi1eDypfVnyD8tORDoFVaAqzdIJuMJ06jJB5fnmNBXU7GOQFLcdK7hy3ZDmPNh5FNGnaQRrlY2st7lXfV3mu9AgHmjPjxrbAwgo1GzLRY0byI9bfitN0sLT+d");
		OCSPToken ocspToken = ocspSource.getRevocationToken(userUniversign, caUniversign);
		ocspToken.initInfo();

		assertNotNull(ocspToken);
		assertNotNull(ocspToken.getArchiveCutOff());
		assertNotNull(ocspToken.getThisUpdate());
		assertNotNull(ocspToken.getNextUpdate());
		assertNotNull(ocspToken.getProductionDate());
		assertNotNull(ocspToken.getBasicOCSPResp());
		assertNotNull(ocspToken.getCertId());
		assertNull(ocspToken.getExpiredCertsOnCRL());
		assertTrue(ocspToken.getStatus());
	}

	@Test
	public void testOCSP() {

		String ocspResponse = "MIIFmgoBAKCCBZMwggWPBgkrBgEFBQcwAQEEggWAMIIFfDCBk6IWBBRG9FLh93YFoTCS3jTUkgpprd/GtRgPMjAxODAyMTMwNjMyNDhaMFMwUTA8MAkGBSsOAwIaBQAEFMzG72J5OVL0/31Lq6L3ZXYqftwTBBQ0Fhvx02RnYkyjNLwNs1OkfKHxFwIDC0wwggAYDzIwMTgwMjEzMDYzMjQ4WqETMBEwDwYJKwYBBQUHMAEJBAIFADANBgkqhkiG9w0BAQsFAAOCAQEAduyhdmJ1pxB6Y9wavgysICD2UjkusDLDNnj2xlNuYHK+c5OVdQ3XKxyUgdIVqNxxudVAmwtuX+vKFk7xjE16A27agZU2KO4llwpAVKrbYsrWMBj3FS/WJzTBk0G+SXKuSgDt5UkwfcZiUxmoMQBntWJTmVon0Ji2VJMALd0HzzEaVp47qqYGYtJX6L5HjvZIC7r/G5C3lEddBgmfNbSPei3EuBVRlLn8LZNLMk+49P7jNMHmlYt3InfSKwEiU18R1WGgQ5yrkyYxyMfhhBrjYZpptT0zs8AuwdAZb7xA4P7rrHgc6IMhQWXmRaKUWoJdOInKt1N59Hyl+mjk36kFQ6CCA84wggPKMIIDxjCCAq6gAwIBAgICHR0wDQYJKoZIhvcNAQELBQAwRDELMAkGA1UEBhMCTFUxFjAUBgNVBAoTDUx1eFRydXN0IHMuYS4xHTAbBgNVBAMTFEx1eFRydXN0IEdsb2JhbCBSb290MB4XDTE3MDYyNzEyMzMxNloXDTE4MDYyNzEyMzMxNlowXjELMAkGA1UEBhMCTFUxFjAUBgNVBAoTDUx1eFRydXN0IFMuQS4xEzARBgNVBAsTClBraSBlbnRpdHkxIjAgBgNVBAMTGUx1eFRydXN0IFMuQS4gT0NTUCBTZXJ2ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDSFDnPz4sWi+w+xMVUWq74fDelftPUqXt+bofmeIgKKC9pZ4FNrT6FCd/N7kpRjRbtKkfBUMiv88jwUFcHMC75EESf0OPeqBlubRkeSwTj60FqB8bBK2aIauGQMFSSLF2emfVvBkpw/qjR9/0bxd1R4FQ7rcl4CyrRGd/jfca6vTAi+rqFI5bOaxKT0MUHVtegJtshmj8zHlOBpm+BG2udKcpx79KIHVVKEb2AyFYdZefXV65FkRLri7l7ew08UYHV90Dq1hI/uAc8IHauSKkGE0arCRwNcLrsfVUPoh41HK3pqs7bTFJ2qmZu2mobAdBqDJBnfTxN+oHZ4whVNh5RAgMBAAGjgacwgaQwCQYDVR0TBAIwADAOBgNVHQ8BAf8EBAMCB4AwEwYDVR0lBAwwCgYIKwYBBQUHAwkwHwYDVR0jBBgwFoAUFxWFiQkvJIdvPx0b5PKWeYNIE84wMgYDVR0fBCswKTAnoCWgI4YhaHR0cDovL2NybC5sdXh0cnVzdC5sdS9MVEdSQ0EuY3JsMB0GA1UdDgQWBBRG9FLh93YFoTCS3jTUkgpprd/GtTANBgkqhkiG9w0BAQsFAAOCAQEANR/sq+gfaAtWqUXTbNFkisEan6abNbv9C8Pgt79rlDBkhAOOksyi/9tcfEZd5XqsMobIdaRZzAOlyN5SP5MgyZxSWmNArcqImofpg5RiIzbwewZzTIGaKh/xRrl8LkABKEhJ+tvTwrw0de2ad5nMUy2t2hCdO7Y5dMGzQRdJVnu10LcziEgZSW98C2SfdV3JxkW0rTsSi1vpRmSicpVxeEfbMvnlDKaMF17uM/eeTjqq7hsQOUbXPPbDOM8LcH0UmnkXkW6FVN4QDKjuKDlaL9bsRHJCuikebzwG+FOmKXhpHxMaUWLrRMvbeBIJvmd64LURIdhdjOt+Iho1lI4Oaw==";

		ExternalResourcesOCSPSource ocspSource = new ExternalResourcesOCSPSource(new ByteArrayInputStream(Utils.fromBase64(ocspResponse)));

		CertificateToken userUniversign = DSSUtils.loadCertificateFromBase64EncodedString(
				"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");
		CertificateToken caUniversign = DSSUtils.loadCertificateFromBase64EncodedString(
				"MIID5zCCAs+gAwIBAgICDJcwDQYJKoZIhvcNAQELBQAwRDELMAkGA1UEBhMCTFUxFjAUBgNVBAoTDUx1eFRydXN0IHMuYS4xHTAbBgNVBAMTFEx1eFRydXN0IEdsb2JhbCBSb290MB4XDTExMTEyMjA5MzQyNFoXDTE3MTEyMjA5MzQyNFowTDELMAkGA1UEBhMCTFUxFjAUBgNVBAoTDUx1eFRydXN0IFMuQS4xJTAjBgNVBAMTHEx1eFRydXN0IEdsb2JhbCBRdWFsaWZpZWQgQ0EwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCo0Cl9yek22xZb2PNZN0gIgx26qgQChKJ3az/r+qhAv/d/jVBLZy5mQvKRQFrvnLZp5gY5RzrgIcMyZxCwCitQ+MyLPiUYZ4mg/lUrHfOfhWRjYaJY4Dz+M69icdzAsKuVrfsurJ/UEmaIkGvgjBzeCd0qd8BXYnnt6GAT9bAsJ7Vh8lDyTho4D1mbude6jmupBTRNqW1TqnuzeJcooI8JAWkWZ0X4gGXzG0iZlC4irIlB/aaQiJoo+8QVr511T+zAJSJv2CpPS68dpL7AZBTw7/fhsyF84HcBf4tCH7Qhl9zFrdaWGdqrZiebSM4SeTBAnuuKUdYuuZyjoAHFnjj3AgMBAAGjgdowgdcwDwYDVR0TBAgwBgEB/wIBADBDBgNVHSAEPDA6MDgGCCuBKwEBAQoDMCwwKgYIKwYBBQUHAgEWHmh0dHBzOi8vcmVwb3NpdG9yeS5sdXh0cnVzdC5sdTALBgNVHQ8EBAMCAQYwHwYDVR0jBBgwFoAUFxWFiQkvJIdvPx0b5PKWeYNIE84wMgYDVR0fBCswKTAnoCWgI4YhaHR0cDovL2NybC5sdXh0cnVzdC5sdS9MVEdSQ0EuY3JsMB0GA1UdDgQWBBQ0Fhvx02RnYkyjNLwNs1OkfKHxFzANBgkqhkiG9w0BAQsFAAOCAQEAGPCWTo1SQy6Kmpwin+GaWVW9h2JO3Qwr6qVwZAQHXPhZGQTD+ghIxN949SGyoMmkGTvJjOSaIs8qJ5lGtDWJuy056rN5FCc2P+0qYofpGdNgSkyRY3xtuKteEPcoi+eFBphY+dlilSPXm3j6Vp/6BRb70Zd35O1Zic95ZUNCxDDVkaDbuN9tjGIBKhnq/ExtpheEdZJPjppVXpsBrCUUpJ0A9oSSFOQewwKSMD/vJKF32M9A2TlRd62XdHeNJrahdU/tO55f9tz3ekl/aKoS2khJxMfBvdvlw2Yc2g02g5rYsBxrSphph6IjqIkALM95jbm9T2Xo2CSROTAtprdhKw==");
		OCSPToken ocspToken = ocspSource.getRevocationToken(userUniversign, caUniversign);
		ocspToken.initInfo();

		assertNotNull(ocspToken);
		assertNull(ocspToken.getArchiveCutOff());
		assertNotNull(ocspToken.getThisUpdate());
		assertNotNull(ocspToken.getProductionDate());
		assertNotNull(ocspToken.getBasicOCSPResp());
		assertNotNull(ocspToken.getCertId());
		assertNull(ocspToken.getExpiredCertsOnCRL());
		assertFalse(ocspToken.isCertHashPresent());
		assertNotNull(ocspToken.getReason());
	}

	@Test
	public void testOCSPCertHash() {

		CertificateToken user = DSSUtils.loadCertificate(new File("src/test/resources/sk_user.cer"));
		CertificateToken caToken = DSSUtils.loadCertificate(new File("src/test/resources/sk_ca.cer"));
		assertTrue(user.isSignedBy(caToken));

		OCSPSource ocspSource = new ExternalResourcesOCSPSource("/sk_ocsp.bin");

		OCSPToken ocspToken = ocspSource.getRevocationToken(user, caToken);
		ocspToken.initInfo();

		assertNotNull(ocspToken);
		assertNotNull(ocspToken.getRevocationDate());

		assertTrue(ocspToken.isCertHashPresent());
		assertTrue(ocspToken.isCertHashMatch());
	}

}
