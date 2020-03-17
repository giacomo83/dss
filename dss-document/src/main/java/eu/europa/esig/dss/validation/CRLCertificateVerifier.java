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
package eu.europa.esig.dss.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.revocation.RevocationSource;
import eu.europa.esig.dss.spi.x509.revocation.RevocationToken;
import eu.europa.esig.dss.spi.x509.revocation.crl.CRLToken;

/**
 * Verifier based on CRL
 *
 */
public class CRLCertificateVerifier implements CertificateStatusVerifier {

	private static final Logger LOG = LoggerFactory.getLogger(CRLCertificateVerifier.class);

	private final RevocationSource<CRLToken> crlSource;

	/**
	 * Main constructor.
	 *
	 * @param crlSource
	 *                           the CRL repository used by this CRL trust linker.
	 */
	public CRLCertificateVerifier(final RevocationSource<CRLToken> crlSource) {
		this.crlSource = crlSource;
	}

	@Override
	public RevocationToken check(final CertificateToken certificateToken, final CertificateToken issuerToken) {
		try {
			if (crlSource == null) {
				LOG.debug("CRLSource is null");
				return null;
			}

			final CRLToken crlToken = crlSource.getRevocationToken(certificateToken, issuerToken);
			if (crlToken == null) {
				LOG.debug("{} : No CRL found for: {}", crlSource.getClass().getSimpleName(), certificateToken.getDSSIdAsString());
				return null;
			}
			crlToken.setRelatedCertificate(certificateToken);
			if (!crlToken.isValid()) {
				LOG.warn("{} : The CRL {} is not valid !", crlSource.getClass().getSimpleName(), crlToken.getDSSIdAsString());
				return null;
			}
			return crlToken;
		} catch (final Exception e) {
			LOG.error("Exception when accessing CRL for " + certificateToken.getDSSIdAsString(), e);
			return null;
		}
	}
}
