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
import com.ericsson.eniq.taf.installation.test.flows.EQEV65501_UserManual_Flow;

/**
 * launch User manual from AdminUi
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV65501_UserManual extends TorTestCaseHelper {

	public static final String EQEV65501_UserManual_SCENARIO_01 = "EQEV65501_UserManual_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	private EQEV65501_UserManual_Flow flow;

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
	 * EQEV65501_UserManual
	 */
	@Test
	@TestId(id = "EQEV65501_UserManual", title = "launch User manual from AdminUi")
	public void verifyEQEV_65501_UserManual() {
		final TestScenario scenario = scenario(EQEV65501_UserManual_SCENARIO_01).addFlow(wepAppTestFlow.basicTest()).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}