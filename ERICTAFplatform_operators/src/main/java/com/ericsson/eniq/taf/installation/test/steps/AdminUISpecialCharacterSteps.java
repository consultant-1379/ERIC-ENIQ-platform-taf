package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.AdminUISpecialCharacterOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class AdminUISpecialCharacterSteps {
	private static Logger logger = LoggerFactory.getLogger(AdminUISpecialCharacterSteps.class);
	private static final String NEW_PASSWORD = AdminUISpecialCharacterOperator.generatePassword();
	private static final String NEW_VALID_PASSWORD = AdminUISpecialCharacterOperator.generateValidPassword();
	private static final String NEW_USER = AdminUISpecialCharacterOperator.generateNewUser();
	private static final String CHANGE_ADMINUI_PASSWORD = "echo -e \"eniq\\\\n" + NEW_PASSWORD
			+ "\" | /eniq/sw/installer/manage_tomcat_user.bsh -A CHANGE_PASSWORD";
	private static final String ADD_ADMINUI_USER = "echo -e \"" + NEW_USER + "\\\\n" + NEW_PASSWORD
			+ "\" | /eniq/sw/installer/manage_tomcat_user.bsh -A ADD_USER";
	private static final String CHANGE_VALID_ADMINUI_PASSWORD = "echo -e \"eniq\\\\n" + NEW_VALID_PASSWORD
			+ "\" | /eniq/sw/installer/manage_tomcat_user.bsh -A CHANGE_PASSWORD";

	@Inject
	private Provider<AdminUISpecialCharacterOperator> provider;

	/**
	 * @DESCRIPTION Verify restricted special characters are not accepted in
	 *              Password while changing password of an existing adminui user
	 */
	@TestStep(id = StepIds.Manage_tomcat_user_TC07_Step)
	public void changeAdminUIPassword() {
		final AdminUISpecialCharacterOperator operator = provider.get();
		String changePassword = operator.executeCommandsDCuser(CHANGE_ADMINUI_PASSWORD);
		if (changePassword.contains("Password should not contain ' \" < > & / \\ characters")) {
			assertTrue(changePassword.contains("Password should not contain ' \" < > & / \\ characters"),
					"Restricted special characters are not accepted in Password");
		} else {
			assertTrue(changePassword.contains("unexpected EOF while looking for matching `\"'"),
					"Restricted special characters ('\") are not accepted in Password");
			assertFalse(changePassword.contains("Password updates successfully for user eniq"),
					"Password should not get updates with the restricted special characters");
		}
	}

	/**
	 * @DESCRIPTION Verify restricted special characters are not accepted in
	 *              Password while adding a new adminui user
	 */
	@TestStep(id = StepIds.Manage_tomcat_user_TC06_Step)
	public void changeNewAdminUIPassword() {
		final AdminUISpecialCharacterOperator operator = provider.get();
		String addNewUser = operator.executeCommandsDCuser(ADD_ADMINUI_USER);
		assertFalse(addNewUser.contains("please provide a new user to add"), "User already exists");
		if (addNewUser.contains("Password should not contain ' \" < > & / \\ characters")) {
			assertTrue(addNewUser.contains("Password should not contain ' \" < > & / \\ characters"),
					"Restricted special characters are not accepted in Password");
		} else {
			assertTrue(addNewUser.contains("unexpected EOF while looking for matching `\"'"),
					"Restricted special characters ('\") are not accepted in Password");
			assertFalse(addNewUser.contains("Password updates successfully for user eniq"),
					"Password should not get updates with the restricted special characters");
		}
	}

	/**
	 * @throws ConfigurationException 
	 * @DESCRIPTION Changing AdminUI User Password
	 */
	@TestStep(id = StepIds.Manage_tomcat_user_TC04_Step)
	public void changeValidAdminUIPassword() throws ConfigurationException {
		final AdminUISpecialCharacterOperator operator = provider.get();
		String changePassword = operator.executeCommandsDCuser(CHANGE_VALID_ADMINUI_PASSWORD);
		assertTrue(changePassword.contains("Password updates successfully for user eniq"),
				"Password updated successfully for existing user");
		operator.updateAdminuiPasswordInProperties(NEW_VALID_PASSWORD);
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify Users able to login to AdminUI after changing AdminUI
	 *              User Password
	 */
	@TestStep(id = StepIds.Manage_tomcat_user_TC05_Step)
	public void loginAdminUIWithNewPassword() throws InterruptedException {
		final AdminUISpecialCharacterOperator operator = provider.get();
		String changePassword = operator.executeCommandsDCuser(CHANGE_VALID_ADMINUI_PASSWORD);
		logger.info("new passowrd....." + NEW_VALID_PASSWORD);
		assertTrue(changePassword.contains("Password updates successfully for user eniq"),
				"Password updated successfully for existing user");
		operator.openBrowser();
		logger.info("\nBrowser Opened");
		boolean result = operator.login(NEW_VALID_PASSWORD);
		logger.info("new passowrd " + NEW_VALID_PASSWORD);
		//operator.logoutUI();
		//logger.info("\nLogged out successfully");
		//operator.closeBrowser();
		//logger.info("\nBrowser Closed");
		assertTrue(result, "\nAdminUI new password verified successfully.\n");
	}

	public static class StepIds {
		public static final String Manage_tomcat_user_TC07_Step = "Verify restricted special characters are not accepted in Password while changing password of an existing adminui user";
		public static final String Manage_tomcat_user_TC06_Step = "Verify restricted special characters are not accepted in Password while adding a new adminui user";
		public static final String Manage_tomcat_user_TC05_Step = "Verify Users able to login to AdminUI after changing AdminUI User Password";
		public static final String Manage_tomcat_user_TC04_Step = "Changing AdminUI User Password";

		private StepIds() {
		}
	}

}
