package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;

/**
 * Flows to test Parsing
 */
public class epfgFlow {

	private static final String EPFG_FLOW = "epfgFileGenerator";
	private static final String CLOSE_FIREFOX_FLOW = "closeFirefoxBrowserFlow";

	@Inject
	private epfgFileGeneratorSteps epfgStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow epfgFileGeneratorFlow() {
		return flow(EPFG_FLOW).addTestStep(annotatedMethod(epfgStesps, epfgFileGeneratorSteps.StepIds.EPFG_TOOL))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow closeFirefoxBrowserFlow() {
		return flow(CLOSE_FIREFOX_FLOW).addTestStep(annotatedMethod(epfgStesps, epfgFileGeneratorSteps.StepIds.CLOSE_FIREFOX_PROCESS))
				.build();
	}
}