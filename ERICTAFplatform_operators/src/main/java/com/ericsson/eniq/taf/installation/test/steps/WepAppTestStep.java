package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;
import com.ericsson.eniq.taf.installation.test.operators.ClickAllLinks;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPlatformInstallationOperator;

/**
 * Created by xarunha
 */
public class WepAppTestStep {

	private static final Logger LOGGER = LoggerFactory.getLogger(WepAppTestStep.class);
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
	@TestStep(id = StepIds.VERIFY_ADMIN_UI_LOGIN)
	public void loginAdminUI() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.openBrowser();
		boolean result = operator.login();
		operator.closeBrowser();
		assertTrue(result, "\n Failed to Login AdminUI as 'eniq' user\n");

	}

	@TestStep(id = StepIds.OPEN_BROWSER)
	public void openBrowser() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.openBrowser();
		LOGGER.info("\nBrowser Opened");
	}

	@TestStep(id = StepIds.LOGIN)
	public void loginAdminUITechpacIde() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.login();

	}

	@TestStep(id = StepIds.EXECUTE_ADMINUI_TECKPACK_IDE_LOGIN)
	public void verifyAdminUITeckpackIdeLaunching() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		boolean result = operator.login();
		assertTrue(result, "\nFailed to Login AdminUI as 'eniq' user\n");

	}

	@TestStep(id = StepIds.LOGOUT)
	public void AdminUILogout() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		boolean result = operator.logoutAdminUI();
		assertTrue(result, "\nFailed to Logout AdminUI\n");

	}

	@TestStep(id = StepIds.CLOSE_BROWSER)
	public void closeBrowser() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.closeBrowser();
		LOGGER.info("\nBrowser Closed");
	}

	@TestStep(id = StepIds.VERIFY_TRIGGER_SETS_IN_ADMIN_UI)
	public void triggerSetsInAdminUI() throws InterruptedException {
		// final ClickAllLinks operator = webAppOperator.get();
		// operator.openBrowser();
		boolean result = test.verifyTriggerSetsInAdminUI();
		// operator.logoutAdminUI();
		assertTrue(result,
				"\nUnable to trigger 'DeltaViewCreation' in 'ETLC Set Scheduling' with SetType:Maintainace Package:DWH_MONITOR\n");

	}

	@TestStep(id = StepIds.VERIFY_TRIGGER_SETS_ERRORS_IN_CLI)
	public void triggerSetsVlidationViaCLI() throws InterruptedException {
		String DWH_MONITORerrorLogFile = genOperator
				.executeCommandDcuser("cd /eniq/log/sw_log/engine/DWH_MONITOR;ls | grep engine |tail -1");
		String result = genOperator.DWH_MONITORerrorLogContent(DWH_MONITORerrorLogFile);
		assertTrue(result.isEmpty(), "\nFound Errors/warnings in DWH_MONITOR engine Log\n" + result);

	}

	@TestStep(id = StepIds.VERIFY_ADMIN_UI_LICENSE)
	public void verifyLicenseIsOnlineAdminUiTest() throws InterruptedException {
		boolean licenseInfoResult = test.verifyLicenseAdminUI();

		assertTrue(licenseInfoResult, "\nError: Expected 'is online' in AdminUI 'System Status' -> 'License info' \n");
		/*
		 * if (licenseInfoResult == true) { boolean validateLicenseLogInAdminUI
		 * = test.validateLicenseLogInAdminUI();
		 * assertTrue(validateLicenseLogInAdminUI,
		 * "\nErrors/Warnings/Servre present in Show installed licenses log in AdminUI: \n"
		 * +
		 * "Goto 'System Status' -> 'Show installed licenses' and Select date of test case execution then click on 'Serach'\n"
		 * ); } else { assertTrue(licenseInfoResult,
		 * "\nError: Expected 'is online' in AdminUI 'System Status' -> 'License info' \n"
		 * ); }
		 */
	}

	@TestStep(id = StepIds.VERIFY_LICENSE_GREEN)
	public void verifyLicenseIsGreenOrNotInAdminUi() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.openBrowser();
		boolean licenseInfoResult = operator.verifyLicenseStatusAdminUI();
		operator.closeBrowser();
		assertTrue(licenseInfoResult, "\n Status is not GREEN in main page \n");
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.VERIFY_ADMIN_UI_LINKS)
	public void verify(@Input("links") String linkName, @Input("search") String search) throws InterruptedException {
		/*
		 * if (check == false) { test.openBrowser(); test.login(); } check =
		 * true;
		 */
		boolean result = test.verifyAllAdminUIlinks(linkName, search);
		LOGGER.info("\n Verifying link : " + linkName + " Validation String :" + search);
		assertTrue(result, "\nExpected : " + search + "\nlLink validation failed for : " + linkName);
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
	@TestStep(id = StepIds.CLOSE_FIREFOX_UNIQ)
	public void close_test_uniqly() throws InterruptedException {
		if (closes == true) {
			test.logoutAdminUI();
			test.quitBrowsers();
		} else {
			closes = true;
		}

	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.CLOSE_FIREFOX)
	public void close() throws InterruptedException {
		//test.logoutAdminUI();
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

	@TestStep(id = StepIds.LOGIN_ALARMCFG_UI)
	public void loginToalarmCfg() throws InterruptedException {
		final ClickAllLinks operator = webAppOperator.get();
		operator.openBrowser();
		boolean result = operator.loginAlarmCFG();
		operator.closeBrowser();
		assertTrue(result, "\n Unable to open alarmcfg URL\n");

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
		public static final String VERIFY_TRIGGER_SETS_IN_ADMIN_UI = "Verify whether user is able to trigger the set manually from the AdminUI";
		public static final String VERIFY_TRIGGER_SETS_ERRORS_IN_CLI = "Validating errors/warnings in DWH_MONITOR engine log";
		public static final String VERIFY_ADMIN_UI_LICENSE = "Verify the licensing is online or not using AdminUI";
		public static final String VERIFY_LICENSE_GREEN = "Verify the status of License Manager in AdminUI";
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
