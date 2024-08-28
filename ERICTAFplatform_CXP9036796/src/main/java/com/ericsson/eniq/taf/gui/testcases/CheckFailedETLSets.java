package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.CheckFailedETLSets_Flow;

/**
 * Verify that there are no failed ETL Sets
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class CheckFailedETLSets extends TorTestCaseHelper {

	public static final String CheckFailedETLSets_SCENARIO_01 = "CheckFailedETLSets_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	private CheckFailedETLSets_Flow flow;

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	/**
	 * CheckFailedETLSets
	 */
	@Test
	@TestId(id = "CheckFailedETLSets", title = "Verify that there are no failed ETL Sets")
	public void verifyFailedETLSets() {
		final TestScenario scenario = scenario(CheckFailedETLSets_SCENARIO_01).addFlow(wepAppTestFlow.basicTest()).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}