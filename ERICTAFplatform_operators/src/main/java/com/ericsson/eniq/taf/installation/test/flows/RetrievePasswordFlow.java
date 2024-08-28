package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RetrievePasswordSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class RetrievePasswordFlow {
	private static final String VERIFY_PASSWORD_FLOW = "VerifyPasswordFlow";

	@Inject
	private RetrievePasswordSteps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyPassword() {
		return flow(VERIFY_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, RetrievePasswordSteps.StepIds.VERIFY_PASSWORD_RETRIEVE)).build();
	}

}
