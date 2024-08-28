package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TafTestBase;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.AlarmPwdFlow;

/**
 * Created by XARUNHA
 */
// @Test(enabled = false)
public class AlarmPwdVerification extends TafTestBase {

	public static final String BEFORE = "basicTest";
	public static final String ADMIN_UI_LOGIN = "AdminUILoginScenario";
	public static final String ADMIN_UI_LINKS = "AdminUIlinksScenario";
	public static final String AFTER = "AdminUILoginScenario";
	public static final String ALARM_PWD_ALLOWED_SCENARIO = "Verify changing the pwd with allowed characters";
	public static final String ALARM_PWD_NOT_ALLOWED_SCENARIO = "Verify changing the pwd with allowed characters";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private AlarmPwdFlow flow;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {

		final TestScenario scenario = scenario(ADMIN_UI_LOGIN).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
	
	//TAF GIT check.

	/**
	 * 
	 */
	@Test
	@TestId(id = "Alarm pwd verification_1", title = "Verify changing the pwd with allowed characters")
	public void verifyAlarmPwdWithAllowed() {
		final TestScenario scenario = scenario(ALARM_PWD_ALLOWED_SCENARIO).addFlow(flow.allowedPwd()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);

	}

	/**
	 * 
	 */
	@TestSuite
	@Test
	@TestId(id = "Alarm pwd verification_2", title = "Verify changing the pwd with the characters which are not allowed.")
	public void verifyAlarmPwdWithNotAllowed() {
		final TestScenario scenario = dataDrivenScenario(ALARM_PWD_NOT_ALLOWED_SCENARIO).addFlow(flow.notAllowedPwd())
				.withScenarioDataSources(dataSource("alarmPasswords")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
