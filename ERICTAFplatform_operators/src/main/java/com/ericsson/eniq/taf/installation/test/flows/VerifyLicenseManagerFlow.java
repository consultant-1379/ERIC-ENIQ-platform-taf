package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifySchedulerSteps;

/**
 * Flows to test the cli calculator
 */
public class VerifyLicenseManagerFlow {

	private static final String EXECUTE_LICENSE_MGR_LOG_FLOW = "Verify LicenseManager Logs Flow";
	private static final String EXECUTE_LICENSE_SERVER_STATUS_FLOW = "Verify License server status Flow";
	private static final String EXECUTE_LICENSE_MGR_STATUS_FLOW = "Verify LicenseManager status Flow";

	@Inject
	private VerifySchedulerSteps lmTestSteps;

	/**
	 * license manager log flow
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow lmLogFlow() {
		return flow(EXECUTE_LICENSE_MGR_LOG_FLOW)
				.addTestStep(annotatedMethod(lmTestSteps, VerifySchedulerSteps.StepIds.VERIFY_LM_LOG)).build();
	}

	/**
	 * license manager server status
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow licenseServerStatusFlow() {
		return flow(EXECUTE_LICENSE_SERVER_STATUS_FLOW).addTestStep(
				annotatedMethod(lmTestSteps, VerifySchedulerSteps.StepIds.VERIFY_LICENSE_SERVER_STATUS))
				.build();
	}

	/**
	 * license manager status flow
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow licenseManagerStatusFlow() {
		return flow(EXECUTE_LICENSE_MGR_STATUS_FLOW).addTestStep(
				annotatedMethod(lmTestSteps, VerifySchedulerSteps.StepIds.VERIFY_LICENSE_MANAGER_STATUS))
				.build();
	}

	/**
	 * license manager status flow
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow featureMappingInfo() {
		return flow(EXECUTE_LICENSE_MGR_STATUS_FLOW)
				.addTestStep(
						annotatedMethod(lmTestSteps, VerifySchedulerSteps.StepIds.VERIFY_FEATURE_MAPPING_INFO))
				.build();
	}

	/**
	 * license manager status flow
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow featureIsValidOrNot() {
		return flow(EXECUTE_LICENSE_MGR_STATUS_FLOW)
				.addTestStep(
						annotatedMethod(lmTestSteps, VerifySchedulerSteps.StepIds.VERIFY_LICENSE_VALID_OR_NOT))
				.build();
	}

	/**
	 * feature status flow
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow licenseManagerStatus() {
		return flow(EXECUTE_LICENSE_MGR_STATUS_FLOW).addTestStep(
				annotatedMethod(lmTestSteps, VerifySchedulerSteps.StepIds.VERIFY_LICENSE_OPERATIONS_STATUS))
				.build();
	}
}
