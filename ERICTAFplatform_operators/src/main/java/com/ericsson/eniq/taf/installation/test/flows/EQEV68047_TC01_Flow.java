package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV68047_TC01_Steps;;

/**
 * Verify the script to retrieve password of the user dc
 *
 * @author ZJSOLEA
 */
public class EQEV68047_TC01_Flow {

	private static final String EQEV68047_TC01_FLOW = "EQEV68047_TC01_FLOW";

	@Inject
	private EQEV68047_TC01_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV68047_TC01_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV68047_TC01_Steps.StepIds.EQEV68047_TC01_STEP_01)).build();
	}
}
