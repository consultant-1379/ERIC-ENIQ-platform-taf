package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class VerifyKeystorePasswordSteps {

	private static Logger logger = LoggerFactory.getLogger(VerifyKeystorePasswordSteps.class);

	private final String INSTALLER_PATH = DataHandler.getAttribute("platform.install.DeltaViewCreation").toString();

	private static final String CHANGE_KEYSTORE_PASSWORD = "echo -e \"EniqOnSSL\\nNewEniqPswd\\nNewEniqPswd\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh";
	private static final String RESET_KEYSTOREE_PASSWORD = "echo -e \"NewEniqPswd\\nEniqOnSSL\\nEniqOnSSL\\n\" | timeout 300 /eniq/sw/installer/configure_newkeystore.sh";
	private static final String CHECK_KEYSTORE_PASSWORD_IN_FILE = "cat /eniq/sw/conf/niq.ini | grep keyStorePassValue=";
	private static final String CHECK_KEYSTORE_PASSWORD_IN_SERVER_XML_FILE = "cat /eniq/sw/runtime/tomcat/conf/server.xml | grep -i keystorepass";

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @DESCRIPTION This test case covers verification of Parsing
	 */
	@TestStep(id = StepIds.KEYSTORE)
	public void epfgFileGenerator() {
		final GeneralOperator generalOperator = generalOperatorProvider.get();

		String defaultPass = generalOperator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_FILE);
		String oldPasswordInServerXmlFile = generalOperator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_SERVER_XML_FILE);

		// This test case assumes that the default password is EniqOnSSL (encrypted: "RW5pcU9uU1NMCg==")
		if (defaultPass.contains("RW5pcU9uU1NMCg==")) {
			// Change password from default value (EniqOnSSL) to NewEniqPswd
			String scriptOutput = generalOperator.executeCommand(CHANGE_KEYSTORE_PASSWORD);
			assertFalse(scriptOutput.contains("Error") | scriptOutput.contains("failed"),
					"Error/failed found when changing the keystore password \nERROR : " + scriptOutput);
			String newKeyStorePswdFromXml = generalOperator.executeCommand(CHECK_KEYSTORE_PASSWORD_IN_SERVER_XML_FILE);
			if (oldPasswordInServerXmlFile.equals(newKeyStorePswdFromXml)) {
				logger.error("Password didnt change in server.xml file");
				// Ensure that the password in server.xml changed
				assertNotEquals(oldPasswordInServerXmlFile, newKeyStorePswdFromXml,
						"Password is not changing in server.xml file");
				// Revert the default password
				String reset = generalOperator.executeCommand(RESET_KEYSTOREE_PASSWORD);
				assertFalse(reset.contains("Error") | reset.contains("failed"),
						"Error/failed found while restoring to default keystore password \nERROR : " + reset);
			} else {
				assertTrue(newKeyStorePswdFromXml.indexOf("NewEniqPswd") < 0,
						"password is visible in the server.xml file");
				// Revert the default password
				String reset = generalOperator.executeCommand(RESET_KEYSTOREE_PASSWORD);
				assertFalse(reset.contains("Error") | reset.contains("failed"),
						"Error/failed found while restoring to default keystore password \nERROR : " + reset);
			}
		} else {
			assertTrue(false, "Failed because default keystore password not 'EniqOnSSL'\nERROR:");
		}
	}

	public static class StepIds {
		public static final String KEYSTORE = "Verify that keystore password can be changed using configure_newkeystore.sh script";

		private StepIds() {
		}
	}
}
