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
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.AdminUiRunningServicesTest_Flow;

/**
 * Verify that all running services are green
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class AdminUiRunningServicesTest extends TorTestCaseHelper {

	public static final String AdminUiRunningServicesTest_SCENARIO_01 = "AdminUiRunningServicesTest_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	private AdminUiRunningServicesTest_Flow flow;

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
	 * AdminUiRunningServicesTest
	 */
	@Test
	@TestId(id = "AdminUiRunningServicesTest", title = "Verify that all running services are green")
	public void verifyAdminUiRunningServicesTest() {
		final TestScenario scenario = scenario(AdminUiRunningServicesTest_SCENARIO_01).addFlow(flow.verification())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}