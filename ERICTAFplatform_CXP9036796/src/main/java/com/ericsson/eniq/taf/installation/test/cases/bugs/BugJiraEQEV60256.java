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
import com.ericsson.eniq.taf.installation.test.flows.BugJiraEQEV60256Flow01;

/**
 * HX53556 3GPP32435DYN:ZERO ROWS LOADED warning throwing while loading NULL
 * values to counters
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class BugJiraEQEV60256 extends TorTestCaseHelper {

	public static final String BUGJIRAEQEV60256_SCENARIO_01 = "bugjiraeqev60256_scenario_01";

	@Inject
	private BugJiraEQEV60256Flow01 bugjiraeqev60256flow01;
	public static final String BEFORE = "basicTest";

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
	 * verify_bug_jira_EQEV60256
	 */
	@Test
	@TestId(id = "EQEV-60256", title = "HX53556 3GPP32435DYN:ZERO ROWS LOADED warning throwing while loading NULL values to counters")
	public void verifyBugJiraEQEV60256_01() {
		final TestScenario scenario = scenario(BUGJIRAEQEV60256_SCENARIO_01)
				.addFlow(bugjiraeqev60256flow01.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}