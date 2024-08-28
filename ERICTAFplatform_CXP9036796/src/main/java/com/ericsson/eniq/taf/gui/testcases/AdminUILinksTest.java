package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TafTestBase;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.steps.WebAppLoginLinksTestStep;
import com.ericsson.eniq.taf.installation.test.steps.WepAppTestStep;

/**
 * 
 * @author xsounpk
 *
 */
public class AdminUILinksTest extends TafTestBase {

	public static final String ADMIN_UI_LOGIN_LINK_STEP = "AdminUILoginLinkScenario";
	public static final String BEFORE = "AdminUILoginScenario";
	
	@Inject
	WebAppLoginLinksTestStep webAppLoginLinksTestStep;

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	WepAppTestStep webApp;
	
	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	
	/*
	@Test
	@TestId(id = "Adminui_3", title = "Launching of adminui with server ip address")
	public void verifyAdminUIServerIpLogin() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
				annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.EXECUTE_ADMINUI_SERVERIP_LOGIN)))
				.build();
		runner().build().start(scenario);
	}*/

	@Test
	@TestId(id = "Adminui_2", title = "Launching of adminui by giving wrong username & password")
	public void verifyLogin() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest()).addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
				annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.EXECUTE_ADMINUI_INCORRECT_LOGIN)))
				.build();
		runner().build().start(scenario);
	}
/*
	@Test
	@TestId(id = "Adminui_1", title = "Launching of adminui with port 8080")
	public void verifyPortLogin() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
				annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.EXECUTE_PORT_REDIRECT_LOGIN)))
				.build();
		runner().build().start(scenario);
	}*/

/*	@Test
	@TestId(id = "Adminui_9", title = "Launching of adminui after running 'bash change_session_properties.sh -s' script")
	public void verifyExecuteScriptLogin() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
				annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.EXECUTE_SESSION_SCRIPT_LOGIN)))
				.build();
		runner().build().start(scenario);
	}*/
/*
	@Test
	@TestId(id = "Adminui_7", title = "Verification of adminui links")
	public void verifyLinksData() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP)
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
						annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.VERIFY_ADMINUI_LINKS)))
				.build();
		runner().build().start(scenario);
	}*/
/*
	@Test
	@TestId(id = "UI_Launch", title = "Verify Launching of all UI(AdminUI,AlarmCfg,BusyHour,EBS)")
	public void verifyAllUrlLogin() {

		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP)
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP)
						.addTestStep(annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.LAUNCH_ALL_UI)))
				.build();
		runner().build().start(scenario);
	}*/

	@Test
	@TestId(id = "EQEV-50689_Verify ENIQ alarm functionality_03", title = "Alarm Validation")
	public void verifyAlarmCfgLogin() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest()).addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP)
				.addTestStep(annotatedMethod(webApp, WepAppTestStep.LOGIN_ALARMCFG_UI)))
				.build();
		runner().build().start(scenario);
	}

	@Test
	@TestId(id = "EQEV-56786_01", title = "Verify the session cookie in different browsers")
	public void veifySessionCookies() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest())
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
						annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.SESSION_COOKIES_OF_ALL_UI)))
				.build();
		runner().build().start(scenario);
	}

	@Test
	@TestId(id = "EQEV-50689 Alarms", title = "Alarm CFG Functional Impacts on RHEL")
	public void verifyAlarmCfgIncorrectLogin() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest()).addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(
				annotatedMethod(webAppLoginLinksTestStep, WebAppLoginLinksTestStep.INCORRECT_LOGIN_ALARMCFG_UI)))
				.build();
		runner().build().start(scenario);
	}

	@Test
	@TestId(id = "Eniq Monitoring Services_DWHDB", title = "DWHDB Conn- Data validation ")
	public void verifyEniqMonitorServicesDWHDB() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest())
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(annotatedMethod(webAppLoginLinksTestStep,
						WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_DWHDB)))
				.build();
		runner().build().start(scenario);
	}

	@Test
	@TestId(id = "Eniq Monitoring Services_RepDB", title = "RepDBConn- Data validation")
	public void verifyEniqMonitorServicesRepDB() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest())
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(annotatedMethod(webAppLoginLinksTestStep,
						WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_REPDB)))
				.build();
		runner().build().start(scenario);
	}

	@Test
	@TestId(id = "Eniq Monitoring Services_SchedulerHeap", title = "SchedulerHeap- Data validation")
	public void verifyEniqMonitorServicesScheduler() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest())
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(annotatedMethod(webAppLoginLinksTestStep,
						WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_SCHEDULER)))
				.build();
		runner().build().start(scenario);
	}

	@Test
	@TestId(id = "Eniq Monitoring Services_EngineHeap", title = "EngineHeap- Data validation")
	public void verifyEniqMonitorServicesEngine() {
		TestScenario scenario = scenario(ADMIN_UI_LOGIN_LINK_STEP).addFlow(wepAppTestFlow.basicTest())
				.addFlow(flow(ADMIN_UI_LOGIN_LINK_STEP).addTestStep(annotatedMethod(webAppLoginLinksTestStep,
						WebAppLoginLinksTestStep.VERIFY_ENIQ_MONITORING_SERVICES_ENGINE)))
				.build();
		runner().build().start(scenario);
	}
}