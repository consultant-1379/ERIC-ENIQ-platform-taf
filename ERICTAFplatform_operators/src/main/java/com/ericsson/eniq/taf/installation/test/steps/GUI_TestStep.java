package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;
import com.ericsson.eniq.taf.installation.test.operators.ClickAllLinks;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * Created by xarunha
 */
public class GUI_TestStep {

	private static final Logger LOGGER = LoggerFactory.getLogger(GUI_TestStep.class);
	public static final String LOGIN_ALARMCFG_UI = "Login to alarm cfg";

	boolean check = false;
	boolean closes = false;
	@Inject
	private ClickAllLinks test;

	@Inject
	private Provider<ClickAllLinks> webAppOperator;

	@Inject
	private GeneralOperator genOperator;

	@Inject
	private Provider<AdminUiSeleniumOperator> provider;

	// final ClickAllLinks test = webAppOperator.get();

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Enter search term in search box, receive correct search
	 *              results and follow link
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */

	@TestStep(id = StepIds.OPEN_BROWSER)
	public void openBrowser() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.openBrowser();
		LOGGER.info("\nBrowser Opened");
	}

	@TestStep(id = StepIds.CUSTOMIZED_DB_USERS_DISPLAYED)
	public void isCustomizedDBuserDisplayed() throws InterruptedException {
		boolean loggedIn = test.isAdminUisLoggedIn();
		if (loggedIn == true) {
			boolean dbUsers = test.isDbUsersAvailable();
			assertTrue(dbUsers,
					"Expected  : 'Customized Database User Details' but found 'Database users information not available'");
		}
	}

	@TestStep(id = StepIds.CREATE_CUSTOMIZED_DB_USERS)
	public void createCustomizedDBusers() throws InterruptedException {
		// create Customized DB users
		boolean dbUsers = genOperator.createDbUsers("DBusers");
		assertTrue(dbUsers, "Expected  : 'Successfully created user DBusers' but found " + dbUsers);
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.OPEN_FIREFOX)
	public void open() throws InterruptedException {
		test.openBrowser();
		test.login();

	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.CLOSE_FIREFOX)
	public void close() throws InterruptedException {
		// test.logoutAdminUI();
		test.quitBrowsers();

	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.BASIC_TEST)
	public void basicTestcase() throws InterruptedException {

		final String engineStatus = "engine status | grep 'Status'| grep 'active'";
		final String webserverRestart = "webserver restart";
		final String engineProfileStatus = "engine status | grep 'Current Profile' | grep 'Normal'";
		final String webserverStatus = "webserver status";
		final String licenseManagerStatus = "licmgr -status | grep 'License manager is running OK'";
		final String dwhdbStatus = "dwhdb status";
		final String repdbStatus = "repdb status";

		final String engineOnline = "engine start";
		final String engineProfileNormal = "engine -e changeProfile 'Normal'";
		final String webserverOnline = "webserver start";
		final String licenseManagerOnline = "licmgr -start";
		final String dwhdbOnline = "dwhdb start";
		final String repdbOnline = "repdb start";

		final String dwhdbStatusOutput = genOperator.executeCommandDcuser(dwhdbStatus);
		if (!dwhdbStatusOutput.contains("dwhdb is running OK")) {
			genOperator.executeCommandDcuser(dwhdbOnline);
			LOGGER.info(genOperator.executeCommandDcuser(dwhdbStatus));
		}

		final String engineStatusOutput = genOperator.executeCommandDcuser(engineStatus);
		if (!engineStatusOutput.contains("Status: active")) {
			genOperator.executeCommandDcuser(engineOnline);
			LOGGER.info(genOperator.executeCommandDcuser(engineStatus));
		}

		final String webserverStatusRestartOutput = genOperator.executeCommandDcuser(webserverRestart);
		if (!webserverStatusRestartOutput.contains("Service enabling eniq-webserver")) {
			genOperator.executeCommandDcuser(webserverOnline);
			LOGGER.info(genOperator.executeCommandDcuser(webserverStatus));
		}

		final String webserverStatusOutput = genOperator.executeCommandDcuser(webserverStatus);
		if (!webserverStatusOutput.contains("webserver is running OK")) {
			genOperator.executeCommandDcuser(webserverOnline);
			LOGGER.info(genOperator.executeCommandDcuser(webserverStatus));
		}

		final String licenseManagerOutput = genOperator.executeCommandDcuser(licenseManagerStatus);
		if (!licenseManagerOutput.contains("License manager is running OK")) {
			genOperator.executeCommandDcuser(licenseManagerOnline);
			LOGGER.info(genOperator.executeCommandDcuser(licenseManagerStatus));
		}

		final String repdbStatusOutput = genOperator.executeCommandDcuser(repdbStatus);
		if (!repdbStatusOutput.contains("repdb is running OK")) {
			genOperator.executeCommandDcuser(repdbOnline);
			LOGGER.info(genOperator.executeCommandDcuser(repdbStatus));
		}

		final String engineProfileStatusOutput = genOperator.executeCommandDcuser(engineProfileStatus);
		if (!engineProfileStatusOutput.contains("Current Profile: Normal")) {
			genOperator.executeCommandDcuser(engineProfileNormal);
			LOGGER.info(genOperator.executeCommandDcuser(engineProfileStatus));
		}
	}

	/**
	 * 
	 * @author xarunha
	 *
	 */
	public static class StepIds {

		public static final String VERIFY_ADMIN_UI_LOGIN = "launching of AdminUI";
		public static final String VERIFY_ADMIN_UI_LINKS = "Verify Admin UI Links";
		public static final String OPEN_BROWSER = "Open Browser";
		public static final String LOGIN = "AdminUITeckPackIdeLogin";
		public static final String EXECUTE_ADMINUI_TECKPACK_IDE_LOGIN = "AdminUITeckPackIdeTest";
		public static final String LOGOUT = "AdminUITeckPackIdeLogout";
		public static final String CLOSE_BROWSER = "Close Browser";
		public static final String CUSTOMIZED_DB_USERS_DISPLAYED = "Verify that Database User section is showing on AdminUI System Status Home Page";
		public static final String CREATE_CUSTOMIZED_DB_USERS = "Verify that Database User section is showing on AdminUI System Status Home Page";
		public static final String OPEN_FIREFOX = "Open Firefox browser";
		public static final String CLOSE_FIREFOX = "Closing Firefox browser";
		public static final String CLOSE_FIREFOX_UNIQ = "close firefox for uniqtestcase";
		public static final String BASIC_TEST = "Basic Test cases";
		public static final String LOGIN_ALARMCFG_UI = "Login to alarm cfg";

		private StepIds() {
		}
	}

	public static class Parameters {
		public static final String LINKS = "links";

		private Parameters() {
		}

	}
}
