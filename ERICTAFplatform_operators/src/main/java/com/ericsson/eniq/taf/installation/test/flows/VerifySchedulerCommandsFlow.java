package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifySchedulerSteps;

/**
 * Flows to test scheduler Installation, Commands and Service
 */
public class VerifySchedulerCommandsFlow {

	private static final String SCHEDULER_INSTALLATION_FLOW = "schedulerCommandsExecution";
	private static final String SCHEDULER_COMMANDS_FLOW = "schedulerCommandsExecution";
	private static final String SCHEDULER_SERVICE_FLOW = "schedulerServicesExecution";
	private static final String SCHEDULER_LOG_FLOW = "schedulerServicesExecution";
	@Inject
	private VerifySchedulerSteps schedulerTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow schedulerInstallationFlow() {
		return flow(SCHEDULER_INSTALLATION_FLOW)
				.addTestStep(
						annotatedMethod(schedulerTestSteps, VerifySchedulerSteps.StepIds.VERIFY_SCHEDULER_IS_INSTALLED))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow schedulerCommandsFlow() {
		return flow(SCHEDULER_COMMANDS_FLOW)
				.addTestStep(
						annotatedMethod(schedulerTestSteps, VerifySchedulerSteps.StepIds.VERIFY_SCHEDULER_COMMANDS))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow schedulerServicesFlow() {
		return flow(SCHEDULER_SERVICE_FLOW)
				.addTestStep(
						annotatedMethod(schedulerTestSteps, VerifySchedulerSteps.StepIds.VERIFY_SCHEDULER_SERVICES))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlows
	 */
	public TestStepFlow schedulerLogsFlow() {
		return flow(SCHEDULER_LOG_FLOW)
				.addTestStep(
						annotatedMethod(schedulerTestSteps, VerifySchedulerSteps.StepIds.VERIFY_SCHEDULER_LOG_EXISTS))
				.addTestStep(
						annotatedMethod(schedulerTestSteps, VerifySchedulerSteps.StepIds.VERIFY_SCHEDULER_LOG_ERRORS))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlows
	 */
	public TestStepFlow schedulerTpAndInterfaceFlow() {
		return flow(SCHEDULER_LOG_FLOW)
				.addTestStep(annotatedMethod(schedulerTestSteps,
						VerifySchedulerSteps.StepIds.VERIFY_ACTIVE_INTERFACE_SCHEDULER_LOG_ERRORS))
				.build();
	}
}
