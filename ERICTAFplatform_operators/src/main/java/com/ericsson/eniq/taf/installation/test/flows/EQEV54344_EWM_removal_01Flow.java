package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV54344_EWM_removal_01Steps;

/**
 * Verification of  EWM details as part of manage_connection integration script execution
 *
 * @author ZJSOLEA
 */
public class EQEV54344_EWM_removal_01Flow {

	private static final String EQEV54344_EWM_REMOVAL_01_FLOW = "EQEV54344_EWM_removal_01Flow";

	@Inject
	private EQEV54344_EWM_removal_01Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV54344_EWM_REMOVAL_01_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV54344_EWM_removal_01Steps.StepIds.EQEV54344_EWM_REMOVAL_01_STEP)).build();
	}
}
