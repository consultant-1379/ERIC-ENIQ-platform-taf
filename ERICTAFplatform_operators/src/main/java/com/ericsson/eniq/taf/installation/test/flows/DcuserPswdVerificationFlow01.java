package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.DcuserPswdVerificationStep01;
import com.ericsson.eniq.taf.installation.test.steps.DcuserPswdVerificationStep02;
import com.ericsson.eniq.taf.installation.test.steps.DcuserPswdVerificationStep03;

/**
 * Verify dcuser pswd by providing the pwd length less than 8 and greater than 30 characters.
 *
 * @author ZJSOLEA
 */
public class DcuserPswdVerificationFlow01 {

	private static final String DCUSER_PSWD_VERIFICATION_FLOW01 = "DcuserPswdVerificationFlow01";

	@Inject
	private DcuserPswdVerificationStep01 steps01;
	@Inject
	private DcuserPswdVerificationStep02 steps02;
	@Inject
	private DcuserPswdVerificationStep03 steps03;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(DCUSER_PSWD_VERIFICATION_FLOW01)
				.addTestStep(annotatedMethod(steps01, DcuserPswdVerificationStep01.StepIds.DCUSER_PSWD_VERIFICATION_STEP_01))
				.addTestStep(annotatedMethod(steps02, DcuserPswdVerificationStep02.StepIds.DCUSER_PSWD_VERIFICATION_STEP_02))
				.addTestStep(annotatedMethod(steps03, DcuserPswdVerificationStep03.StepIds.DCUSER_PSWD_VERIFICATION_STEP_03)).build();
	}
}
