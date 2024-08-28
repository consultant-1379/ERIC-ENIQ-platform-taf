package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.SelfSignedValidityOperators;

/**
 * 
 * @author xsounpk
 *
 */
public class SelfSignedValiditySteps {

	private static Logger Logger = LoggerFactory.getLogger(SelfSignedValiditySteps.class);

	private final String RUN_BASH_SCRIPT = "cd /eniq/admin/bin/;ls | grep renew";
	private final String EXECUTE_SCRIPT = "cd /eniq/admin/bin/; bash ./renewSelfSignedCerts.bsh";
	private final String BACKUP_FOLDER = "cd /eniq/backup/;ls | grep certificateBackUp";
	private final String ZIP_FILE = "cd /eniq/backup/certificateBackUp/;ls | grep .zip | tail -1";
	private final String EXPIRY = "/eniq/sw/runtime/jdk/bin/keytool -list -v -keystore /eniq/sw/runtime/tomcat/ssl/keystore.jks -storepass EniqOnSSL | grep -i valid > caexpiry.txt";
	private final String GET_EXPIRY = "cd /eniq/home/dcuser/;cat caexpiry.txt";
	private final String REMOVE_EXPIRY = "cd /eniq/home/dcuser/;ls | rm -rf caexpiry.txt";
	private final String RESTART = "systemctl list-unit-files | grep eniq-webserver";
	private final String WEAK_CIPHERS = "cd /eniq/sw/runtime/tomcat/conf/;cat server.xml | grep ciphers=\"*\" > ciphers.txt";
	private final String GET_CIPHERS = "cd /eniq/sw/runtime/tomcat/conf/;cat ciphers.txt | grep -w -i 'TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA\\|TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA\\|TLS_RSA_WITH_AES_256_CBC_SHA\\|TLS_RSA_WITH_AES_128_CBC_SHA'";
	private final String REMOVE_CIPHERS = "cd /eniq/sw/runtime/tomcat/conf/;ls | rm -rf ciphers.txt";
	private final String LIST_FILE = "cd /eniq/sw/runtime/tomcat/conf/;ls | grep ciphers.txt";

	@Inject
	private Provider<SelfSignedValidityOperators> provider;

	/**
	 * @DESCRIPTION Self signed validity and renewal
	 * 
	 */

	@TestStep(id = StepIds.VERIFY_CERTIFICATE_BACKUP)
	public void VerifyCertificateBackup() {
		final SelfSignedValidityOperators selfSignedValidityOperators = provider.get();
		String backUp = selfSignedValidityOperators.executeCommands(RUN_BASH_SCRIPT);
		assertTrue(backUp.contains("renewSelfSignedCerts.bsh"), "renewSelfSignedCerts.bsh backup script exists");
		String executeBackupScript = selfSignedValidityOperators.executeCommands(EXECUTE_SCRIPT);
		Logger.info("Script Success Message" + executeBackupScript);
		assertFalse(executeBackupScript.contains("No such file or directory"),
				"failed to execute renewSelfSignedCerts.bsh backup script");
		String backUpFolder = selfSignedValidityOperators.executeCommands(BACKUP_FOLDER);
		assertTrue(backUpFolder.contains("certificateBackUp"), "certificateBackUp backup folder exists");
		String zipFile = selfSignedValidityOperators.executeCommands(ZIP_FILE);
		assertTrue(zipFile.contains("certificateBackUp") && zipFile.contains(".zip"),
				"certificateBackUp zip file exists");
		Logger.info("size of zip file" + zipFile);
	}

	@TestStep(id = StepIds.VERIFY_CA_EXPIRY)
	public void VerifyCAExpiry() {
		final SelfSignedValidityOperators selfSignedValidityOperators = provider.get();
		String expiry = selfSignedValidityOperators.executeCommands(EXPIRY);
		String caexpiry = selfSignedValidityOperators.executeCommands(GET_EXPIRY);
		assertTrue(caexpiry.contains("Valid"), "Contains valid from date and until date");
		if (caexpiry.contains("Valid") && !(caexpiry.isEmpty())) {
			String ex[] = caexpiry.split("\\r?\\n");
			String date1 = ex[0];
			String ex2[] = date1.split("until:");
			String spl = ex2[0];
			String date3 = ex2[1];
			String spl1[] = spl.split("from:");
			String date2 = spl1[1];
			String DAYS_CHECK = "echo $(($(($(date -d \"" + date3 + "\" \"+%s\") - $(date -d \"" + date2
					+ "\" \"+%s\"))) / 86400))";
			String validity = selfSignedValidityOperators.executeCommands(DAYS_CHECK);
			Logger.info("Validity date : " + validity);
			assertTrue(validity.equals("825"), "validity is for 825 days");
		}
		String removeExpiry = selfSignedValidityOperators.executeCommands(REMOVE_EXPIRY);
	}

	@TestStep(id = StepIds.VERIFY_WEBSERVER_RESTART)
	public void VerifyWebserverRestart() {
		final SelfSignedValidityOperators selfSignedValidityOperators = provider.get();
		String restart = selfSignedValidityOperators.executeCommand(RESTART);
		if ((restart.trim().contains("eniq-webserver.service")) && (restart.trim().contains("enabled"))) {
			Logger.info("eniq-webserver.service webserver restarted properly.");
			assertTrue((restart.trim().contains("eniq-webserver.service")) && (restart.trim().contains("enabled")),
					"eniq-webserver.service webserver restarted properly.");
		} else {
			assertFalse((restart.trim().contains("eniq-webserver.service")) && (restart.trim().contains("disabled")),
					"webserver is in disabled state.");
			assertFalse(!(restart.trim().contains("eniq-webserver.service")), "webserver service doesn't exists.");
			Logger.info("webserver doesn't restart properly.");
		}
	}

	@TestStep(id = StepIds.VERIFY_WEAK_CIPHERS_REMOVED)
	public void VerifyWeakCiphersRemoved() {
		final SelfSignedValidityOperators selfSignedValidityOperators = provider.get();
		String weakCiphers = selfSignedValidityOperators.executeCommands(WEAK_CIPHERS);
		String ciphersFileCheck = selfSignedValidityOperators.executeCommands(LIST_FILE);
		String getCiphers = selfSignedValidityOperators.executeCommand(GET_CIPHERS);
		String ciphersFileCheck1 = selfSignedValidityOperators.executeCommands(LIST_FILE);
		String removeCiphers = selfSignedValidityOperators.executeCommands(REMOVE_CIPHERS);
		if (!(weakCiphers.isEmpty())) {
			assertTrue(ciphersFileCheck.contains("ciphers.txt"), "ciphers.txt file created successfully");
			assertTrue(getCiphers.isEmpty(), "No weak ciphers identified from server.xml");
			if (ciphersFileCheck1.contains("ciphers.txt")) {
				assertFalse(removeCiphers.contains("No such file or directory"), "Failed to remove the ciphers.txt");
			}
		}
	}

	public static class StepIds {

		public static final String VERIFY_CERTIFICATE_BACKUP = "Verify standalone script to renew the self signed certificates.";
		public static final String VERIFY_CA_EXPIRY = "Verify standalone script to renew the self signed certificates and expiry check.";
		public static final String VERIFY_WEBSERVER_RESTART = "Verify the webserver restart";
		public static final String VERIFY_WEAK_CIPHERS_REMOVED = "Verify whether weak ciphers removed from server.xml";

		private StepIds() {
		}
	}
}
