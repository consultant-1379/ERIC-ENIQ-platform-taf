package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV57943Steps;;

/**
 * Flow to EQEV57943
 */
public class EQEV57493Flow {

	private static final String EQEV57943_FLOW = "EQEV57943Flow";

	@Inject
	private EQEV57943Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV57943_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV57943Steps.StepIds.VERIFY_EQEV57943_1))
				.addTestStep(annotatedMethod(steps, EQEV57943Steps.StepIds.VERIFY_EQEV57943_2))
				.build();
	}
}
