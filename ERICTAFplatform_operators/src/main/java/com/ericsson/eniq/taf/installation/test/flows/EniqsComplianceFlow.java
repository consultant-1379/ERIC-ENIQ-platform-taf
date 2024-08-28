package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyEWMTestSteps;

/**
 * Flows to test the DeltaView Re-Creation
 */
public class EniqsComplianceFlow {

	private static final String EXMAPLE_DIR_VERIFICATION_FLOW = "Example_Dir_Verification";

	@Inject
	private VerifyEWMTestSteps dvTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */

	public TestStepFlow exmapleDirVerification() {
		return flow(EXMAPLE_DIR_VERIFICATION_FLOW)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.EXMAPLE_DIR_VERIFICATION))
				.build();
	}
}
