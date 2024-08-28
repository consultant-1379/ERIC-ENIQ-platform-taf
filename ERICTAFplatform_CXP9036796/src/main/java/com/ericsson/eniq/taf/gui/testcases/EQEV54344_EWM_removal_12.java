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
import com.ericsson.eniq.taf.installation.test.flows.EQEV54344_EWM_removal_12_Flow2;;

/**
 * Verify whether all the references related to WIfi is removed from Admin UI
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV54344_EWM_removal_12 extends TorTestCaseHelper {

	public static final String EQEV54344_EWM_removal_12_SCENARIO_01 = "EQEV54344_EWM_removal_12_scenario_01";
	public static final String BEFORE = "basicTest";

	@Inject
	private EQEV54344_EWM_removal_12_Flow2 flow;

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
	 * EQEV_54344_EWM_removal_12
	 */
	@Test
	@TestId(id = "EQEV_54344_EWM_removal_12", title = "Verify whether all the references related to WIfi is removed from Admin UI")
	public void verify_EQEV_54344_EWM_removal_12() {
		final TestScenario scenario = scenario(EQEV54344_EWM_removal_12_SCENARIO_01).addFlow(wepAppTestFlow.basicTest()).addFlow(flow.verification())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}