package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RstateChecksSteps;

/**
 * 
 * @author zjsolea
 *
 */
public class RstateChecksFlow {
	private static final String RSTAE_CHECKS_FLOW = "RSTAE_CHECKS_FLOW";

	@Inject
	private RstateChecksSteps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(RSTAE_CHECKS_FLOW)
				.addTestStep(annotatedMethod(steps, RstateChecksSteps.StepIds.RSTATE_CHECKS_STEP))
				.build();
	}
}
