package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV67891_SpecialCharacters01_Steps;

/**
 * Adding New AdminUI Users with special characters in password
 *
 * @author ZJSOLEA
 */
public class EQEV67891_SpecialCharacters01_Flow {

	private static final String EQEV67891_SpecialCharacters01_FLOW = "EQEV67891_SpecialCharacters01_FLOW";

	@Inject
	private EQEV67891_SpecialCharacters01_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV67891_SpecialCharacters01_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV67891_SpecialCharacters01_Steps.StepIds.ADD_USER_WITH_PASSWORD)).build();
	}
}
