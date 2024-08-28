package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyProcessManagementTestSteps;

/**
 * Flows to test ProcessManagement start/stop services
 */
public class VerifyProcessManagementFlow {

	private static final String EXECUTE_ENABLE_SERVICE_FLOW = "enable service flow";
	private static final String EXECUTE_DISABLE_FLOW = "disable service flow";
	private static final String EXECUTE_START_SERVICE_FLOW = "start service flow";
	private static final String EXECUTE_STOP_SERVICE_FLOW = "stop service flow";
	private static final String EXECUTE_DEPENDENT_SERVICES_FLOW = "dependent services flow";
	private static final String EXECUTE_SERVICES_LOGS_FLOW = "services logs";
	private static final String EXECUTE_RESTART_SERVICES_ORDER_FLOW = "restart services and order";

	@Inject
	private VerifyProcessManagementTestSteps processTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceEnableFlow() {
		return flow(EXECUTE_ENABLE_SERVICE_FLOW).addTestStep(
				annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_ENABLE))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceStartFlow() {
		return flow(EXECUTE_START_SERVICE_FLOW).addTestStep(
				annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_START))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceDisableFlow() {
		return flow(EXECUTE_DISABLE_FLOW).addTestStep(
				annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_DISABLE))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceStopFlow() {
		return flow(EXECUTE_STOP_SERVICE_FLOW)
				.addTestStep(
						annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_STOP))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceDependenciesFlow() {
		return flow(EXECUTE_DEPENDENT_SERVICES_FLOW).addTestStep(
				annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_DEPENDENT_SERVICES))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceReStartAllFlow() {
		return flow(EXECUTE_RESTART_SERVICES_ORDER_FLOW)
				.addTestStep(annotatedMethod(processTestSteps,
						VerifyProcessManagementTestSteps.StepIds.VERIFY_RESTART_ALL_SERVICES_ORDER))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow serviceLogFlow() {
		return flow(EXECUTE_SERVICES_LOGS_FLOW)
				.addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_LOGS))
				.build();
	}
}
