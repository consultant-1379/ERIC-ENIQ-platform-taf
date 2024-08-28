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
import com.ericsson.eniq.taf.installation.test.flows.EQEV_50504_AdminUI_02_Flow;

/**
 * Verify session logs not displaying error "Row count exceeded $toomany -rows"
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV_50504_AdminUI extends TorTestCaseHelper {

	public static final String EQEV_50504_AdminUI_SCENARIO_01 = "EQEV_50504_AdminUI_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	private EQEV_50504_AdminUI_02_Flow flow;

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
	 * EQEV_50504_AdminUI
	 */
	@Test
	@TestId(id = "EQEV_50504_AdminUI", title = "Verify session logs not displaying error 'Row count exceeded $toomany -rows'")
	public void verifyEQEV_50504_AdminUI() {
		final TestScenario scenario = scenario(EQEV_50504_AdminUI_SCENARIO_01).addFlow(wepAppTestFlow.basicTest()).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}