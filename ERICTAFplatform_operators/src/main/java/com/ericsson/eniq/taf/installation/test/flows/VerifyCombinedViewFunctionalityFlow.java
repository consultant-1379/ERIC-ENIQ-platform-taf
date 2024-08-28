package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyCombinedViewFunctionalityTestSteps;

/**
 * Flows to test the cli calculator
 */
public class VerifyCombinedViewFunctionalityFlow {

	private static final String EXECUTE_CV_INSTALLER_FLOW = "CV installation of installer";
	private static final String EXECUTE_CV_INSTALLER_LOG_FLOW = "CV installation of installer log flow";
	private static final String CV_LTE_WCDMA_SCRIPTS_FLOW = "To Verify the LTE and WCDMA Scripts flow";
	private static final String CV_G1_AND_G2_SCRIPTS_FLOW = "To Verify the G1 and G2 Scripts flow";
	private static final String EXECUTE_CV_LTEINSTALLER_LOG_FLOW = "CV installation of installer log flow";
	@Inject
	private VerifyCombinedViewFunctionalityTestSteps cvTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow installerFlow() {
		return flow(EXECUTE_CV_INSTALLER_FLOW)
				/*.addTestStep(
						annotatedMethod(cvTestSteps, VerifyCombinedViewFunctionalityTestSteps.StepIds.CLI_INITIALIZE))*/
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_INSTALLER_INSTALLATION))
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_VERSION_DB_PROPERTIES))
				.build();
	}

	public TestStepFlow installerLogFlow() {
		return flow(EXECUTE_CV_INSTALLER_LOG_FLOW)
				.addTestStep(
						annotatedMethod(cvTestSteps, VerifyCombinedViewFunctionalityTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_INSTALLER_LOGS))
				.build();
	}

	public TestStepFlow lteAndWcdmaScriptsFlow() {
		return flow(CV_LTE_WCDMA_SCRIPTS_FLOW)
				/*.addTestStep(
						annotatedMethod(cvTestSteps, VerifyCombinedViewFunctionalityTestSteps.StepIds.CLI_INITIALIZE))*/
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_LTE_WCDMA_SCRIPTS))
				.build();
	}

	public TestStepFlow g1AndG2ScriptFlow() {
		return flow(CV_G1_AND_G2_SCRIPTS_FLOW)
				.addTestStep(
						annotatedMethod(cvTestSteps, VerifyCombinedViewFunctionalityTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_ACCESS_VERIFICATION_EXECUTION))
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_ACCESS_LOGS))
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_ACCESS_ERROR_LOGS))
				.build();
	}

	public TestStepFlow ltewcdmaLogFlow() {
		return flow(EXECUTE_CV_LTEINSTALLER_LOG_FLOW)
				/*.addTestStep(
		
						annotatedMethod(cvTestSteps, VerifyCombinedViewFunctionalityTestSteps.StepIds.CLI_INITIALIZE))*/
				.addTestStep(annotatedMethod(cvTestSteps,
						VerifyCombinedViewFunctionalityTestSteps.StepIds.VERIFY_LTEWCDMAINSTALLER_LOGS))
				.build();
	}
}
