package eu.europa.esig.dss.diagnostic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import eu.europa.esig.dss.diagnostic.jaxb.XmlCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlCertificateRef;
import eu.europa.esig.dss.diagnostic.jaxb.XmlDiagnosticData;
import eu.europa.esig.dss.diagnostic.jaxb.XmlDigestAlgoAndValue;
import eu.europa.esig.dss.diagnostic.jaxb.XmlFoundCertificates;
import eu.europa.esig.dss.diagnostic.jaxb.XmlOrphanCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlOrphanCertificateToken;
import eu.europa.esig.dss.diagnostic.jaxb.XmlRelatedCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.CertificateRefOrigin;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;

public class FoundCertificateProxyTest {
	
	@Test
	public void getRelatedCertificatesByRefOriginTest() {
		XmlDiagnosticData xmlDiagnosticData = new XmlDiagnosticData();
		XmlSignature xmlSignature = new XmlSignature();
		XmlFoundCertificates xmlFoundCertificates = new XmlFoundCertificates();
		XmlRelatedCertificate xmlRelatedCertificate = new XmlRelatedCertificate();
		xmlRelatedCertificate.setCertificate(new XmlCertificate());
		
		XmlCertificateRef xmlCertificateRefOne = new XmlCertificateRef();
		xmlCertificateRefOne.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		XmlDigestAlgoAndValue xmlDigestAlgoAndValue = new XmlDigestAlgoAndValue();
		xmlDigestAlgoAndValue.setDigestMethod(DigestAlgorithm.SHA1);
		xmlDigestAlgoAndValue.setDigestValue(Base64.getDecoder().decode("PbxFIvJ7jIvMfax49ZehCt66gXg="));
		xmlCertificateRefOne.setDigestAlgoAndValue(xmlDigestAlgoAndValue);
		xmlRelatedCertificate.getCertificateRefs().add(xmlCertificateRefOne);
		
		XmlCertificateRef xmlCertificateRefTwo = new XmlCertificateRef();
		xmlCertificateRefOne.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		xmlDigestAlgoAndValue = new XmlDigestAlgoAndValue();
		xmlDigestAlgoAndValue.setDigestMethod(DigestAlgorithm.SHA256);
		xmlDigestAlgoAndValue.setDigestValue(Base64.getDecoder().decode("EGNLBWrSFV9p1en6aiwLPEatctJ+3/Wzc0N7H9IUn8c="));
		xmlCertificateRefTwo.setDigestAlgoAndValue(xmlDigestAlgoAndValue);
		xmlRelatedCertificate.getCertificateRefs().add(xmlCertificateRefTwo);
		
		assertEquals(2, xmlRelatedCertificate.getCertificateRefs().size());
		
		xmlFoundCertificates.getRelatedCertificates().add(xmlRelatedCertificate);
		xmlSignature.setFoundCertificates(xmlFoundCertificates);
		xmlDiagnosticData.setSignatures(Arrays.asList(xmlSignature));
		
		DiagnosticData diagnosticData = new DiagnosticData(xmlDiagnosticData);
		SignatureWrapper signatureWrapper = diagnosticData.getSignatures().get(0);
		FoundCertificatesProxy foundCertificates = signatureWrapper.foundCertificates();
		assertEquals(1, foundCertificates.getRelatedCertificatesByRefOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE).size());
		assertEquals(0, foundCertificates.getRelatedCertificatesByRefOrigin(CertificateRefOrigin.COMPLETE_CERTIFICATE_REFS).size());
		assertEquals(0, foundCertificates.getOrphanCertificatesByRefOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE).size());
		assertEquals(0, foundCertificates.getOrphanCertificatesByRefOrigin(CertificateRefOrigin.COMPLETE_CERTIFICATE_REFS).size());
	}
	
	@Test
	public void getOrphanCertificatesByRefOriginTest() {
		XmlDiagnosticData xmlDiagnosticData = new XmlDiagnosticData();
		XmlSignature xmlSignature = new XmlSignature();
		XmlFoundCertificates xmlFoundCertificates = new XmlFoundCertificates();
		XmlOrphanCertificate xmlOrphanCertificate = new XmlOrphanCertificate();
		xmlOrphanCertificate.setToken(new XmlOrphanCertificateToken());
		
		XmlCertificateRef xmlCertificateRefOne = new XmlCertificateRef();
		xmlCertificateRefOne.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		XmlDigestAlgoAndValue xmlDigestAlgoAndValue = new XmlDigestAlgoAndValue();
		xmlDigestAlgoAndValue.setDigestMethod(DigestAlgorithm.SHA1);
		xmlDigestAlgoAndValue.setDigestValue(Base64.getDecoder().decode("PbxFIvJ7jIvMfax49ZehCt66gXg="));
		xmlCertificateRefOne.setDigestAlgoAndValue(xmlDigestAlgoAndValue);
		xmlOrphanCertificate.getCertificateRefs().add(xmlCertificateRefOne);
		
		XmlCertificateRef xmlCertificateRefTwo = new XmlCertificateRef();
		xmlCertificateRefOne.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		xmlDigestAlgoAndValue = new XmlDigestAlgoAndValue();
		xmlDigestAlgoAndValue.setDigestMethod(DigestAlgorithm.SHA256);
		xmlDigestAlgoAndValue.setDigestValue(Base64.getDecoder().decode("EGNLBWrSFV9p1en6aiwLPEatctJ+3/Wzc0N7H9IUn8c="));
		xmlCertificateRefTwo.setDigestAlgoAndValue(xmlDigestAlgoAndValue);
		xmlOrphanCertificate.getCertificateRefs().add(xmlCertificateRefTwo);
		
		assertEquals(2, xmlOrphanCertificate.getCertificateRefs().size());
		
		xmlFoundCertificates.getOrphanCertificates().add(xmlOrphanCertificate);
		xmlSignature.setFoundCertificates(xmlFoundCertificates);
		xmlDiagnosticData.setSignatures(Arrays.asList(xmlSignature));
		
		DiagnosticData diagnosticData = new DiagnosticData(xmlDiagnosticData);
		SignatureWrapper signatureWrapper = diagnosticData.getSignatures().get(0);
		FoundCertificatesProxy foundCertificates = signatureWrapper.foundCertificates();
		assertEquals(0, foundCertificates.getRelatedCertificatesByRefOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE).size());
		assertEquals(0, foundCertificates.getRelatedCertificatesByRefOrigin(CertificateRefOrigin.COMPLETE_CERTIFICATE_REFS).size());
		assertEquals(1, foundCertificates.getOrphanCertificatesByRefOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE).size());
		assertEquals(0, foundCertificates.getOrphanCertificatesByRefOrigin(CertificateRefOrigin.COMPLETE_CERTIFICATE_REFS).size());
	}

}
