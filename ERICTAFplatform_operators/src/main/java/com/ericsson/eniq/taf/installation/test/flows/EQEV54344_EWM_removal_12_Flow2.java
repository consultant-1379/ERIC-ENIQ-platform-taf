package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV54344_EWM_removal_12_Steps;

/**
 * Verify whether all the references related to WIfi is removed from Admin UI
 *
 * @author ZJSOLEA
 */
public class EQEV54344_EWM_removal_12_Flow2 {

	private static final String EQEV54344_EWM_removal_12_FLOW_01 = "EQEV54344_EWM_removal_12_Flow_01";

	@Inject
	private EQEV54344_EWM_removal_12_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV54344_EWM_removal_12_FLOW_01)
				.addTestStep(annotatedMethod(steps, EQEV54344_EWM_removal_12_Steps.StepIds.EQEV54344_EWM_removal_12_STEP_01)).build();
	}
}
