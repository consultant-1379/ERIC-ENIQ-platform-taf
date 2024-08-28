package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyDeltaViewCreationTestSteps;

/**
 * Flows to test the DeltaView Re-Creation
 */
public class VerifyDeltaViewCreationFlow {

	private static final String DV_DELTACREATION_FLOW = "Verify DeltaViewRecreation Log Flow";
	private static final String DV_ACCESS_VERIY_SCRIPTLOG_FLOW = "Verify Access Verify Script Log Flow";
	private static final String DV_LOGS_VERIFICATION = "Verify Complete Script and Execution Log Flow";
	private static final String DV_SCRIPT_EXECUTION_FLOW = "Verify Script Executed Timing";
	private static final String DV_ACCESS_SCRIPT_FLOW = "Verify Access Verification Script Log Flow";

	@Inject
	private VerifyDeltaViewCreationTestSteps dvTestSteps;

	/**
	 * 
	 * @return TestStepFlow (For Test Case 5)
	 */
	public TestStepFlow DV_ACCESS_VERIFICATION_FLOW() {
		return flow(DV_ACCESS_SCRIPT_FLOW)
				.addTestStep(
						annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.DELTA_VIEW_PERMISSON))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyDeltaViewCreationTestSteps.StepIds.DELTA_VIEW_PERMISSON_ACCESS_LOG_ERRORS))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyDeltaViewCreationTestSteps.StepIds.DELTA_VIEW_PERMISSON_ERROR_LOG_ERRORS))
				.build();
	}

	public TestStepFlow DV_DELTACREATION_FLOW() {
		return flow(DV_DELTACREATION_FLOW)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyDeltaViewCreationTestSteps.StepIds.VERIFY_DELTAVIEW_EXECUTION))
				.addTestStep(
						annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.VERIFY_DELTAVIEW_LOGS))
				.build();
	}

	public TestStepFlow DV_ACCESSIBLE_FLOW() {
		return flow(DV_ACCESS_VERIY_SCRIPTLOG_FLOW)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(
						annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.VERIFY_DELTAVIEW_LOGS))
				.build();
	}

	public TestStepFlow DV_LOGS_VERIICATION() {
		return flow(DV_LOGS_VERIFICATION)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyDeltaViewCreationTestSteps.StepIds.VERIFY_SCRIPT_LOG_VERIFICATION))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyDeltaViewCreationTestSteps.StepIds.VERIFY_EXECUTION_LOG_VERIFICATION))
				.build();

	}

	public TestStepFlow DV_SCRIPT_EXECUTION_FLOW() {
		return flow(DV_SCRIPT_EXECUTION_FLOW)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyDeltaViewCreationTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyDeltaViewCreationTestSteps.StepIds.VERIFY_SCRIPTEXECUTION_TIMING))
				.build();

	}
}
