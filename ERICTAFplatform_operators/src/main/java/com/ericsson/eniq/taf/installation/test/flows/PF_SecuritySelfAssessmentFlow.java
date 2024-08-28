package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.PF_SecuritySelfAssessmentSteps;

/**
 * Flows to test scheduler Installation, Commands and Service
 */
public class PF_SecuritySelfAssessmentFlow {

	private static final String SPF_SECURITY_FLOW = "schedulerCommandsExecution";

	@Inject
	private PF_SecuritySelfAssessmentSteps pfSecuritySteps;

	/**
	 *
	 * @return TestStepFlow
	 */
	public TestStepFlow pfSecurityFlow() {
		return flow(SPF_SECURITY_FLOW)
				.addTestStep(annotatedMethod(pfSecuritySteps,
						PF_SecuritySelfAssessmentSteps.StepIds.VERIFY_PF_SECURITY_OPTION_D))
				.addTestStep(annotatedMethod(pfSecuritySteps,
						PF_SecuritySelfAssessmentSteps.StepIds.VERIFY_PF_SECURITY_OPTION_S))
				.addTestStep(annotatedMethod(pfSecuritySteps,
						PF_SecuritySelfAssessmentSteps.StepIds.VERIFY_PF_SECURITY_OPTION_P))
				.addTestStep(annotatedMethod(pfSecuritySteps,
						PF_SecuritySelfAssessmentSteps.StepIds.VERIFY_PF_SECURITY_OPTION_V))
				.addTestStep(annotatedMethod(pfSecuritySteps,
						PF_SecuritySelfAssessmentSteps.StepIds.VERIFY_PF_SECURITY_OPTION_F))

				.build();
	}
}
