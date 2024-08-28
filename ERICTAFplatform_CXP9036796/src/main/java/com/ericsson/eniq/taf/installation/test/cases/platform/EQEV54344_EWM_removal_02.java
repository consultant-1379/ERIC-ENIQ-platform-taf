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
import com.ericsson.eniq.taf.installation.test.flows.EQEV54344_EWM_removal_02Flow;

/**
 * pmdata_wifi directory should not be created under /eniq/data/
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV54344_EWM_removal_02 extends TorTestCaseHelper {

	public static final String EQEV54344_EWM_REMOVAL_02_SCENARIO_ = "EQEV54344_EWM_removal_02_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private EQEV54344_EWM_removal_02Flow flow;

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
	 * EQEV-54344_EWM removal_02
	 */
	@Test
	@TestId(id = "EQEV-54344_EWM removal_02", title = "pmdata_wifi directory should not be created under /eniq/data/")
	public void verification() {
		final TestScenario scenario = scenario(EQEV54344_EWM_REMOVAL_02_SCENARIO_).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}