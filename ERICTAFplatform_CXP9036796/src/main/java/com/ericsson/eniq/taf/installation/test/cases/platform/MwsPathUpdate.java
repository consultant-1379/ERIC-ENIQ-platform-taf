package com.ericsson.eniq.taf.installation.test.cases.platform;

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
import com.ericsson.eniq.taf.installation.test.flows.mwsFlow;

/**
 * MWS Path Update
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class MwsPathUpdate extends TorTestCaseHelper {

	public static final String MWS_SCENARIO = "MWS_Path_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private mwsFlow mwsFlow;

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
	 * MWS Path Update
	 */
	@Test
	@TestId(id = "EQEV-50682_Parsing_01", title = "Update MWS path at run time")
	public void mwsPathRuntimeUpdate() {
		final TestScenario scenario = scenario(MWS_SCENARIO).addFlow(mwsFlow.mwsPathUpdateFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}