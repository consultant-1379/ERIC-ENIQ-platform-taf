package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.CertificateOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class CertificatesRetentionSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(CertificatesRetentionSteps.class);

	private final String INSTALLED_TOMCAT_VERSION_CMD = "ls /eniq/sw/runtime/| grep apache-tomcat";
	private final String INSTALLED_JDK_VERSION_CMD = "ls /eniq/sw/runtime/| grep jdk1.";
	private final String BACKUP_VERSION = "ls /enmcertificate/| grep backup_";
	private final String ENM_INTEGRATION = "cat /eniq/sw/conf/enmserverdetail";
	private final String LS_CMD = "ls";
	private final String SPACE = " ";
	private final String HOSTNAME = "hostname";
	private final String RUNTIME_PATH = "/eniq/sw/runtime/";
	private final String BACKUP_PATH = "/enmcertificate/";
	private final String SSL_DIR = "/ssl/";
	private final String SSL_PRIVATE_DIR = "/ssl/private/";
	private final String KEYSTORE = " | grep -i keystore";
	private final String CSR_FILE = " | grep -i csr";
	private final String TS_FILE = "/jre/lib/security/ | grep -i truststore.ts";
	Set<String> enmCertificate = new HashSet<String>();

	@Inject
	private Provider<CertificateOperator> provider;

	/**
	 * @DESCRIPTION CA and SS certificates need to be retained during Migration or
	 *              PF upgrade (Full or Only PF upgrade).
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_CERTIFICATE_CER)
	public void verifyCertificateForCER() {
		final CertificateOperator certificateOperator = provider.get();
		String tomcatVersion = certificateOperator.executeCommands(INSTALLED_TOMCAT_VERSION_CMD);
		LOGGER.info("\nInstalled apache-tomcat version is : " + tomcatVersion);
		String hostName = certificateOperator.executeCommands(HOSTNAME);
		LOGGER.info("\nServer Host Name is : " + hostName);
		String certificateCER = certificateOperator
				.executeCommands(LS_CMD + SPACE + RUNTIME_PATH + tomcatVersion + SSL_DIR);
		LOGGER.info("\nCertificate Retained : " + certificateCER);
		assertTrue(certificateCER.contains(hostName + ".cer"), "Certificate is not retained : '" + hostName + ".cer'"
				+ " under " + RUNTIME_PATH + tomcatVersion + SSL_DIR);
	}

	@TestStep(id = StepIds.VERIFY_CERTIFICATE_PEM)
	public void verifyCertificateForPEM() {
		final CertificateOperator certificateOperator = provider.get();
		String tomcatVersion = certificateOperator.executeCommands(INSTALLED_TOMCAT_VERSION_CMD);
		LOGGER.info("\nInstalled apache-tomcat version is : " + tomcatVersion);
		String hostName = certificateOperator.executeCommands(HOSTNAME);
		LOGGER.info("\nServer Host Name is : " + hostName);
		String certificatePEM = certificateOperator
				.executeCommands(LS_CMD + SPACE + RUNTIME_PATH + tomcatVersion + SSL_PRIVATE_DIR);
		LOGGER.info("\nCertificate Retained : " + certificatePEM);
		assertTrue(certificatePEM.contains(hostName + "-private.pem"), "Certificate is not retained : '" + hostName
				+ ".cer'" + " under " + RUNTIME_PATH + tomcatVersion + SSL_PRIVATE_DIR);
	}
	
	@TestStep(id = StepIds.VERIFY_CERTIFICATE_CSR)
	public void verifyCertificateForCSR() {
		final CertificateOperator certificateOperator = provider.get();
		String tomcatVersion = certificateOperator.executeCommands(INSTALLED_TOMCAT_VERSION_CMD);
		LOGGER.info("\nInstalled apache-tomcat version is : " + tomcatVersion);
		String hostName = certificateOperator.executeCommands(HOSTNAME);
		LOGGER.info("\nServer Host Name is : " + hostName);
		String certificateCER = certificateOperator
				.executeCommands(LS_CMD + SPACE + RUNTIME_PATH + tomcatVersion + SSL_DIR + CSR_FILE);
		LOGGER.info("\nCertificate Retained : " + certificateCER);
		assertTrue(certificateCER.contains(hostName + ".csr"), "Certificate is not retained : '" + hostName + ".csr'"
				+ " under " + RUNTIME_PATH + tomcatVersion + SSL_DIR + CSR_FILE);
	}
	
	@TestStep(id = StepIds.VERIFY_CERTIFICATE_JKS)
	public void verifyCertificateForJKS() {
		final CertificateOperator certificateOperator = provider.get();
		String tomcatVersion = certificateOperator.executeCommands(INSTALLED_TOMCAT_VERSION_CMD);
		LOGGER.info("\nInstalled apache-tomcat version is : " + tomcatVersion);
		String hostName = certificateOperator.executeCommands(HOSTNAME);
		LOGGER.info("\nServer Host Name is : " + hostName);
		String certificateJKS = certificateOperator
				.executeCommands(LS_CMD + SPACE + RUNTIME_PATH + tomcatVersion + SSL_DIR + KEYSTORE);
		LOGGER.info("\nCertificate Retained : " + certificateJKS);
		assertTrue(certificateJKS.contains("keystore.jks"),
				"Certificate is not retained : '" + " under " + RUNTIME_PATH + tomcatVersion + SSL_DIR + KEYSTORE);
	}
	
	@TestStep(id = StepIds.VERIFY_CERTIFICATE_TS)
	public void verifyCertificateForTS() {
		final CertificateOperator certificateOperator = provider.get();
		String enmCheck = certificateOperator.executeCommand(ENM_INTEGRATION);
		if (enmCheck.contains("ENM")) {
			String jdkVersion = certificateOperator.executeCommands(INSTALLED_JDK_VERSION_CMD);
			LOGGER.info("\nInstalled jdk version is : " + jdkVersion);
			String hostName = certificateOperator.executeCommands(HOSTNAME);
			LOGGER.info("\nServer Host Name is : " + hostName);
			String certificateTS = certificateOperator
					.executeCommands(LS_CMD + SPACE + RUNTIME_PATH + jdkVersion + TS_FILE);
			LOGGER.info("\nCertificate Retained : " + certificateTS);
			assertTrue(certificateTS.contains("truststore.ts"),
					"Certificate is not retained : '" + " under " + RUNTIME_PATH + jdkVersion + TS_FILE);
		} else {
			LOGGER.info("Not a Integrated ENM server");
			assertTrue(!enmCheck.contains("ENM"), "Not a Integrated ENM server");
		}
	}

	@TestStep(id = StepIds.VERIFY_CERTIFICATE_ENM)
	public void verifyCertificateForENM() {
		final CertificateOperator certificateOperator = provider.get();
		String enmCheck = certificateOperator.executeCommand(ENM_INTEGRATION);
		if (enmCheck.contains("ENM")) {
			String backupVersion = certificateOperator.executeCommands(BACKUP_VERSION);
			LOGGER.info("\nbackupVersion is : " + backupVersion);
			String hostName = certificateOperator.executeCommands(HOSTNAME);
			LOGGER.info("\nServer Host Name is : " + hostName);
			String certificateENM = certificateOperator.executeCommands(LS_CMD + SPACE + BACKUP_PATH + backupVersion);
			String[] enm = certificateENM.trim().split("\\r?\\n");
			for (int i = 0; i < enm.length; i++) {
				enmCertificate.add(enm[i]);
			}
			LOGGER.info("\nCertificate Retained : " + enmCertificate.toString());
			assertTrue(enmCertificate.contains("ENM_Infrastructure_CA.pem"),
					"Certificate is not retained : '" + " under " + BACKUP_PATH + backupVersion);
			assertTrue(enmCertificate.contains("ENM_PKI_Root_CA.pem"),
					"Certificate is not retained : '" + " under " + BACKUP_PATH + backupVersion);
			assertTrue(enmCertificate.contains("ENM_UI_CA.pem"),
					"Certificate is not retained : '" + " under " + BACKUP_PATH + backupVersion);
			assertTrue(enmCertificate.size() == 3,
					"Three enm certificates with extension .pem are available under" + BACKUP_PATH + backupVersion);
		} else {
			LOGGER.info("Not a Integrated ENM server");
			assertTrue(!enmCheck.contains("ENM"), "Not a Integrated ENM server");
		}
	}

	public static class StepIds {

		public static final String VERIFY_CERTIFICATE_CER = "Verify CA signed and Self signed certificates are retained after PF upgrade";
		public static final String VERIFY_CERTIFICATE_PEM = "Verify .pem certificate is retained after PF upgrade";
		public static final String VERIFY_CERTIFICATE_JKS = "Verify keystore.jks certificate is retained after migration";
		public static final String VERIFY_CERTIFICATE_CSR = "Verify .csr signed certificates are retained after PF upgrade";
		public static final String VERIFY_CERTIFICATE_TS = "Verify truststore.ts file is retained after PF upgrade which is integrated with ENM";
		public static final String VERIFY_CERTIFICATE_ENM = "Verify enm certificates are retained after migration which is integrated with ENM"; 

		private StepIds() {
		}
	}
}
