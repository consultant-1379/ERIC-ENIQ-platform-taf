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
import com.ericsson.eniq.taf.installation.test.flows.BugJiraEQEV66467_TC02Flow;

/**
 * Verify that keystore password encrypted in server.xml
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class BugJiraEQEV66467_TC02 extends TorTestCaseHelper {

	public static final String BUGJIRAEQEV66467_TC02_SCENARIO_01 = "bugjiraeqev66467_tc02_scenario_01";
	public static final String BUGJIRAEQEV66468_TC03_SCENARIO_01 = "bugjiraeqev66468_tc03_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private BugJiraEQEV66467_TC02Flow bugjiraeqev66467_tc02flow01;

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
	 * verifyEQEV66467_TC02
	 */
	// @TestSuite
	@Test
	@TestId(id = "EQEV-66467_TC02", title = "Verify that keystore password encrypted in server.xml")
	public void verifyBugJiraEQEV66467_TC02_01() {
		final TestScenario scenario = scenario(BUGJIRAEQEV66467_TC02_SCENARIO_01)
				.addFlow(bugjiraeqev66467_tc02flow01.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * verifyEQEV66467_TC03
	 */
	@Test
	@TestId(id = "EQEV-66467_TC03", title = "Verify that keystore password changed in niq.ini")
	public void verifyBugJiraEQEV66468_TC03_01() {
		final TestScenario scenario = scenario(BUGJIRAEQEV66468_TC03_SCENARIO_01)
				.addFlow(bugjiraeqev66467_tc02flow01.verification_tc03()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}