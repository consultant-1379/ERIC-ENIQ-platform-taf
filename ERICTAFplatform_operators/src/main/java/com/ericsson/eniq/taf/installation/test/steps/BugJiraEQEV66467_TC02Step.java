package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @DESCRIPTION Verify that keystore password encrypted in server.xml
 * @author ZJSOLEA
 */
public class BugJiraEQEV66467_TC02Step {

	private static Logger logger = LoggerFactory.getLogger(BugJiraEQEV66467_TC02Step.class);

	// Constants
	//private static final String GET_KEYSTORE_PASSWORD = "cat /eniq/sw/conf/niq.ini | grep -i keystorepassvalue | cut -d '=' -f2";
	private static final String GET_KEYSTORE_PASSWORD = ". /eniq/admin/lib/common_functions.lib ; inigetpassword KEYSTOREPASS -v keyStorePassValue -f /eniq/sw/conf/niq.ini";
	private static final String CHANGE_KEYSTORE_PASSWORD = "echo -e \"EniqOnSSL\\nNewEniqPswd\\nNewEniqPswd\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh";
	private static final String RESET_KEYSTOREE_PASSWORD = "echo -e \"NewEniqPswd\\nEniqOnSSL\\nEniqOnSSL\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh";
	private static final String CHECK_KEYSTORE_PASSWORD_IN_FILE = "cat /eniq/sw/runtime/tomcat/conf/server.xml | grep -i \"keystorepass=\"";
	private static final String CHECK_KEYSTORE_PASSWORD_IN_NIQ_FILE = "cat /eniq/sw/conf/niq.ini | grep keyStorePassValue=";

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION Verify that keystore password encrypted in server.xml
	 */
	@TestStep(id = StepIds.BUGJIRAEQEV66467_TC02_STEP_01)
	public void verify() throws InterruptedException {
		// get operators from providers
		final GeneralOperator operator = provider.get();

		// Make sure that the web server is up and running
		if (!operator.executeCommandDcuser("webserver status").contains("webserver is running OK")) {
			operator.executeCommandDcuser("webserver start");
			Thread.sleep(10000);
		}
		
		// Get old keystore password entry from server.xml
		// This will be used later to check if the password is changing in the file.
		String oldKeyStorePswdFromXml = operator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_FILE).trim();

		// Change password to NewEniqPswd
		logger.info("Getting keystore password");
		String get_old_password = operator.executeCommand(GET_KEYSTORE_PASSWORD);
		logger.info("Changing keystore password to NewEniqPswd");
		String console_logs_old = operator
				.executeCommand("echo -e \"" + get_old_password
						+ "\\nNewEniqPswd\\nNewEniqPswd\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh")
				.toLowerCase();
		assertTrue(console_logs_old.indexOf("error") < 0, "Error found when changing the keystore password");
		assertTrue(console_logs_old.indexOf("could not find") < 0, "Error found when changing the keystore password");

		// Get new keystore password entry from server.xml after changing the password
		logger.info("checking whether the password is encrypted in server.xml file");
		String newKeyStorePswdFromXml = operator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_FILE).trim();
		
		// Compare the current entry from server.xml with the old one to check if the password got changed in the file
		// If password didnt change then print an error log and proceed to revert back the password
		// The test should fail only after changing the password back to old value.
		if (oldKeyStorePswdFromXml.equals(newKeyStorePswdFromXml))
			logger.error("Password didnt change in server.xml file");

		// Revert the password back to old value (EniqOnSSL) so that other test cases
		// are not affected
		logger.info("Changing back keystore password to old value");
		String console_logs_2 = operator.executeCommand("echo -e \"NewEniqPswd\\n" 
															+ get_old_password + "\\n" 
															+ get_old_password 
															+ "\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh").toLowerCase();
		assertTrue(console_logs_2.indexOf("error") < 0,
				"Error found when changing back the keystore password to old value");
		assertTrue(console_logs_2.indexOf("could not find") < 0,
				"Error found when changing back the keystore password to old value");

		// Ensure that the password in server.xml changed
		assertNotEquals(oldKeyStorePswdFromXml, newKeyStorePswdFromXml, "Password is not changing in server.xml file");
		
		// Ensure that password is not visible as plain text in the file 
		assertTrue(newKeyStorePswdFromXml.indexOf("NewEniqPswd") < 0, "password is visible in the server.xml file");
			
		return;
	}

	@TestStep(id = StepIds.BUGJIRAEQEV66468_TC03_STEP_01)
	public void verifyNewConfigScript() throws InterruptedException {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		// Make sure that the web server is up and running
		if (!operator.executeCommandDcuser("webserver status").contains("webserver is running OK")) {
			operator.executeCommandDcuser("webserver start");
			Thread.sleep(10000);
		}
  
		// Get old keystore password entry from niq.ini file
		// This will be used later to check if the password is changing in the file.
		String oldKeyStorePswdFromNiq = operator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_NIQ_FILE).trim();

		// Change password to NewEniqPswd
		logger.info("Getting keystore password");
		String get_old_password = operator.executeCommand(GET_KEYSTORE_PASSWORD);
		logger.info("Changing keystore password to NewEniqPswd");
		String console_logs_old = operator
				.executeCommand("echo -e \"" + get_old_password
						+ "\\nNewEniqPswd\\nNewEniqPswd\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh")
				.toLowerCase();
		assertTrue(console_logs_old.indexOf("error") < 0, "Error found when changing the keystore password");
		assertTrue(console_logs_old.indexOf("could not find") < 0, "Error found when changing the keystore password");

		// Get new keystore password entry from niq.ini after changing the password
		logger.info("checking whether the password is changed in niq.ini file");
		String newKeyStorePswdFromNiq = operator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_NIQ_FILE).trim();

		// Compare the current entry from niq.ini with the old one to check if the
		// password got changed in the file
		// If password didnt change then print an error log and proceed to revert back
		// the password
		// The test should fail only after changing the password back to old value.
		if (oldKeyStorePswdFromNiq.equals(newKeyStorePswdFromNiq))
			logger.error("Password didnt change in niq.ini file");

		// Revert the password back to old value (EniqOnSSL) so that other test cases
		// are not affected
		logger.info("Changing back keystore password from NewEniqPswd to EniqOnSSL");
		String console_logs_2 = operator.executeCommand("echo -e \"NewEniqPswd\\n" 
															+ get_old_password + "\\n" 
															+ get_old_password 
															+ "\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh").toLowerCase();
		assertTrue(console_logs_2.indexOf("error") < 0,
				"Error found when changing back the keystore password to old value");
		assertTrue(console_logs_2.indexOf("could not find") < 0,
				"Error found when changing back the keystore password to old value");

		// Ensure that the password in niq.ini changed
		assertNotEquals(oldKeyStorePswdFromNiq, newKeyStorePswdFromNiq, "Password is not changing in niq.ini file");

		return;
	}

	public static class StepIds {
		public static final String BUGJIRAEQEV66467_TC02_STEP_01 = "Verify that keystore password encrypted in server.xml";
		public static final String BUGJIRAEQEV66468_TC03_STEP_01 = "Verify that keystore password changed in niq.ini";

		private StepIds() {
		}
	}
}