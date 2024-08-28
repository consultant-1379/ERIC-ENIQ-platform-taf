package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;
import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.AdminUILinkOperator;
import com.ericsson.eniq.taf.installation.test.operators.GUIOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class WebAppLoginLinksTestStep {

	private static final Logger LOGGER = LoggerFactory.getLogger(WepAppTestStep.class);

	public static final String SESSION_LIMIT = DataHandler.getAttribute("eniq.platform.adminui.session.limit")
			.toString();
	public static final String EXECUTE_ADMINUI_SERVERIP_LOGIN = "AdminUIServerIpLogin";
	public static final String EXECUTE_ADMINUI_INCORRECT_LOGIN = "AdminUIIncorrectLogin";
	public static final String EXECUTE_PORT_REDIRECT_LOGIN = "AdminUIPortRedirectLogin";
	public static final String EXECUTE_SESSION_SCRIPT_LOGIN = "AdmiUISessionScriptLogin";
	public static final String VERIFY_ADMINUI_LINKS = "AdmiUILinksVerification";
	public static final String LAUNCH_ALL_UI = "AllUILinksLogin";
	public static final String LOGIN_ALARMCFG_UI = "AlarmCfgUILogin";
	public static final String INCORRECT_LOGIN_ALARMCFG_UI = "AlarmCfgUIIncorrectLogin";
	public static final String SESSION_COOKIES_OF_ALL_UI = "SessionCookiesOfAllUILinksLogin";
	public static final String VERIFY_ENIQ_MONITORING_SERVICES_DWHDB = "AdminUIEniqMonitoringServicesDWHDB";
	public static final String VERIFY_ENIQ_MONITORING_SERVICES_REPDB = "AdminUIEniqMonitoringServicesRepDB";
	public static final String VERIFY_ENIQ_MONITORING_SERVICES_SCHEDULER = "AdminUIEniqMonitoringServicesScheduler";
	public static final String VERIFY_ENIQ_MONITORING_SERVICES_ENGINE = "AdminUIEniqMonitoringServicesEngine";
	private static final String EXECUTE_SESSION_SCRIPT = "cd /eniq/sw/installer/; bash change_session_properties.sh -s "
			+ SESSION_LIMIT;
	private static final String WEBSERVER = "webserver restart";

	@Inject
	private Provider<GUIOperator> provider;

	@Inject
	private Provider<AdminUILinkOperator> uiProvider;

	@Inject
	private Provider<GeneralOperator> generalProvider;

	/**
	 * @DESCRIPTION Login via serverIp address
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.EXECUTE_ADMINUI_SERVERIP_LOGIN)
	public void serverIpAdminUI() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		boolean result = guiOperator.serverIpLogin();
		guiOperator.logoutUI();
		LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser Closed");
		assertTrue(result, "\nFailed to Login AdminUI with serverip address as 'eniq' user\n");
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Launching of adminui by giving wrong username & password
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.EXECUTE_ADMINUI_INCORRECT_LOGIN)
	public void incorrectAdminUILogin() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		boolean result = guiOperator.incorrectLogin();
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser Closed");
		assertTrue(result, "\nNot able to open the adminui with wrong username & password\n");
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Launching of adminui with port 8080
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.EXECUTE_PORT_REDIRECT_LOGIN)
	public void portRedirectLogin() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		boolean result = guiOperator.portLogin();
		guiOperator.logoutUI();
		LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser Closed");
		assertTrue(result, "\n8080 port has been redirected to 8443 port successfully\n");
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Launching of adminui after running 'bash
	 *              change_session_properties.sh -s' script
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.EXECUTE_SESSION_SCRIPT_LOGIN)
	public void executeScriptLogin() throws InterruptedException {

		final AdminUILinkOperator adminUILinkOperator = uiProvider.get();
		GeneralOperator generalOperator = generalProvider.get();
		String executeSessionScript = generalOperator.executeCommandDcuser(EXECUTE_SESSION_SCRIPT);
		assertTrue(executeSessionScript.contains("Service enabling eniq-webserver"),
				"Successfully executed change_session_properties.sh script");
		adminUILinkOperator.loginAdminUI();
		adminUILinkOperator.verifySessionLimitFromSecondBrowser();
		adminUILinkOperator.logoutAdminUI();
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of adminui links
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.VERIFY_ADMINUI_LINKS)
	public void verifyLink() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		boolean result = guiOperator.linkDataVerification();
		guiOperator.logoutUI();
		LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser Closed");
		assertTrue(result, "\nVerified the links successfully.\n");
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of launching all
	 *              URL's(alarmCfg,adminUi,busyHour,EBS) after webServer restart
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.LAUNCH_ALL_UI)
	public void launchAllUI() throws InterruptedException {

		final AdminUILinkOperator adminUILinkOperator = uiProvider.get();
		GeneralOperator generalOperator = generalProvider.get();
		String webserver = generalOperator.executeCommandDcuser(WEBSERVER);
		assertTrue(webserver.contains("Service enabling eniq-webserver"), "webserver restarted successfully");
		adminUILinkOperator.loginAllUrl();
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Login of alarmCfg UI
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.LOGIN_ALARMCFG_UI)
	public void loginAlarmCfgUI() throws InterruptedException {

		final AdminUILinkOperator adminUILinkOperator = uiProvider.get();
		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		adminUILinkOperator.loginAlarmCfg();
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser closed");
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION incorrect Login of alarmCfg UI
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.INCORRECT_LOGIN_ALARMCFG_UI)
	public void incorrectLoginAlarmCfgUI() throws InterruptedException {

		final AdminUILinkOperator adminUILinkOperator = uiProvider.get();
		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		adminUILinkOperator.incorrectLoginAlarmCfg();
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser closed");
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of session cookies secured connection for all
	 *              URL's(alarmCfg,adminUi,busyHour,EBS)
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.SESSION_COOKIES_OF_ALL_UI)
	public void sessionCookiesOfAllUI() throws InterruptedException {

		final AdminUILinkOperator adminUILinkOperator = uiProvider.get();
		adminUILinkOperator.sessionCookiesAllUrl();
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of Eniq Monitoring Services details for DWHDB
	 *              Connection
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_DWHDB)
	public void eniqMonitoringDWHDB() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		guiOperator.eniqMonitorServicesDWHDB();
		//guiOperator.logoutUI();
		//LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser closed");
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of Eniq Monitoring Services details for RepDB
	 *              Connection
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_REPDB)
	public void eniqMonitoringRepDB() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		guiOperator.eniqMonitorServicesRepDB();
		guiOperator.logoutUI();
		LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser closed");
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of Eniq Monitoring Services details for
	 *              SchedulerHeap Connection
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_SCHEDULER)
	public void eniqMonitoringSchedulerHeap() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		guiOperator.eniqMonitorServicesSchedulerHeap();
		guiOperator.logoutUI();
		LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser closed");
		return;
	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verification of Eniq Monitoring Services details for EngineHeap
	 *              Connection
	 * 
	 * @PRE Verify Connection to Internet
	 * @PRIORITY MEDIUM
	 */
	@TestStep(id = WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_ENGINE)
	public void eniqMonitoringEngineHeap() throws InterruptedException {

		GUIOperator guiOperator = provider.get();
		guiOperator.openBrowser();
		LOGGER.info("\nBrowser Opened");
		guiOperator.eniqMonitorServicesEngineHeap();
		guiOperator.logoutUI();
		LOGGER.info("\nLogged out successfully");
		guiOperator.closeBrowser();
		LOGGER.info("\nBrowser closed");
		return;
	}

}