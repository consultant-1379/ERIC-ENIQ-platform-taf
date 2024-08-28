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
import com.ericsson.eniq.taf.installation.test.flows.EQEV57493Flow;

/**
 * Test Campaign for runtime modules - 15664
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class BugJiraEQEV57943 extends TorTestCaseHelper {

	public static final String EQEV57943_SCENARIO = "EQEV57943Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	private EQEV57493Flow flow;

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
	 * EQEV57943 HX45566 :: Engine is not setting to NoLoads after executing
	 * change profile command
	 */
	@Test
	@TestId(id = "EQEV57943", title = "HX45566: Engine is not setting to NoLoads after executing change profile command")
	public void bugJiraEQEV57943_01() {
		final TestScenario scenario = scenario(EQEV57943_SCENARIO).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}