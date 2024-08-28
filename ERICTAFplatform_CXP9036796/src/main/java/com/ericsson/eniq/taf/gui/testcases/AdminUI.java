package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_10_Flow;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_4_Flow;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_5_Flow;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_6_Flow;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;

/**
 * Test Campaign for adminui Test cases
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class AdminUI extends TorTestCaseHelper {

	public static final String ADMINUI_SCENARIO_01 = "adminui_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	private AdminUI_4_Flow adminuiflow01;
	@Inject
	private AdminUI_5_Flow adminuiflow02;
	@Inject
	private AdminUI_6_Flow adminuiflow03;
	@Inject
	private AdminUI_10_Flow adminuiflow04;

	@Inject
	AdminUI_Flows wepAppTestFlow;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * AdminUI_4
	 */
	@Test
	@TestId(id = "AdminUI_4", title = "Launching of adminui of same server in one more browser")
	public void verifyAdminUI_4() {
		final TestScenario scenario = scenario(ADMINUI_SCENARIO_01).addFlow(adminuiflow01.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * AdminUI_5
	 */
	@Test
	@TestId(id = "AdminUI_5", title = "Launching of adminui after creating the new user")
	public void verifyAdminUI_5() {
		final TestScenario scenario = scenario(ADMINUI_SCENARIO_01).addFlow(adminuiflow02.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * AdminUI_6
	 */
	@Test
	@TestId(id = "AdminUI_6", title = "Launching of adminui after changing the password")
	public void verifyAdminUI_6() {
		final TestScenario scenario = scenario(ADMINUI_SCENARIO_01).addFlow(adminuiflow03.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * AdminUI_10
	 */
	@Test
	@TestId(id = "AdminUI_10", title = "Verification of launching of adminui in same browser in multiple tabs")
	public void verifyAdminUI_10() {
		final TestScenario scenario = scenario(ADMINUI_SCENARIO_01).addFlow(adminuiflow04.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}