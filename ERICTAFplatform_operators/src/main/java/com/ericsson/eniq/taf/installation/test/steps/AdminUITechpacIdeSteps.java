package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.AdminUITechpackIdeOperator;

/**
 * Created by xarunha
 */
public class AdminUITechpacIdeSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminUITechpacIdeSteps.class);

	public static final String OPEN_BROWSER = "Open Browser";
	public static final String LOGIN = "AdminUITeckPackIdeLogin";
	public static final String EXECUTE_ADMINUI_TECKPACK_IDE_LOGIN = "AdminUITeckPackIdeTest";
	public static final String LOGOUT = "AdminUITeckPackIdeLogout";
	public static final String CLOSE_BROWSER = "Close Browser";

	@Inject
	AdminUITechpackIdeOperator webAppOperator;

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Enter search term in search box, receive correct search
	 *              results and follow link
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = AdminUITechpacIdeSteps.OPEN_BROWSER)
	public void openBrowser() throws InterruptedException {
		webAppOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
	}

	@TestStep(id = AdminUITechpacIdeSteps.LOGIN)
	public void loginAdminUITechpacIde() throws InterruptedException {
		webAppOperator.login();

	}

	@TestStep(id = AdminUITechpacIdeSteps.EXECUTE_ADMINUI_TECKPACK_IDE_LOGIN)
	public void verifyAdminUITeckpackIdeLaunching() throws InterruptedException {

		boolean result = webAppOperator.login();
		assertTrue(result, "\nFailed to Login AdminUI as 'eniq' user\n");

	}

	@TestStep(id = AdminUITechpacIdeSteps.LOGOUT)
	public void AdminUILogout() throws InterruptedException {

	}

	@TestStep(id = AdminUITechpacIdeSteps.CLOSE_BROWSER)
	public void closeBrowser() throws InterruptedException {
		webAppOperator.closeBrowser();
		LOGGER.info("\nBrowser Closed");
	}
}
