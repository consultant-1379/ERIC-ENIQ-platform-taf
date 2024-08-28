package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.RstateChecksFlow;

/**
 * R-state checks
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class RstateCheck extends TorTestCaseHelper {

	public static final String RSTATE_CHECKS_SCENARIO = "RSTATE_CHECKS_SCENARIO";


	@Inject
	private RstateChecksFlow flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {

	}

	/**
	 * RstateCheck
	 */
	@Test
	@TestId(id = "RSTAE_CHECK", title = "Rstate checks")
	public void verification() {
		final TestScenario scenario = scenario(RSTATE_CHECKS_SCENARIO).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}