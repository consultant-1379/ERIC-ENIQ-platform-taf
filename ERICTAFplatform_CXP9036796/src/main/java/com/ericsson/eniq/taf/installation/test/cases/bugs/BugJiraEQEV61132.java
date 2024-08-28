package com.ericsson.eniq.taf.installation.test.cases.bugs;

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
import com.ericsson.eniq.taf.installation.test.flows.BugJiraEQEV61132Flow01;

/**
 * HX60414 :: 3GPP32435DYNParser Parser failed to parse MINI LINK node type file
 * of ethsoam format.
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class BugJiraEQEV61132 extends TorTestCaseHelper {

	public static final String BUGJIRAEQEV61132_SCENARIO_01 = "bugjiraeqev61132_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private BugJiraEQEV61132Flow01 bugjiraeqev61132flow01;

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
	 * verify_bug_jira_EQEV61132
	 */
	@Test
	@TestId(id = "EQEV-61132", title = "HX60414 :: 3GPP32435DYNParser Parser failed to parse MINI LINK node type file of ethsoam format.")
	public void bugJiraEQEV61132_01() {
		final TestScenario scenario = scenario(BUGJIRAEQEV61132_SCENARIO_01)
				.addFlow(bugjiraeqev61132flow01.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}