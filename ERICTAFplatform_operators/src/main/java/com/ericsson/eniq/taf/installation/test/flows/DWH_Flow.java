package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.DWH_Steps;

/**
 * Flows to test DWH_BASE_Functionality
 */
public class DWH_Flow {

	private static final String DWH_SET_FLOW = "DWH_set_Flow";
	private static final String EXECUTE_INSTALLER_LOG_FLOW = "DWH_cleanup_transer_batches_Flow";
	private static final String DWH_BASE_DISK_MANAGER_FLOW = "DWH_DiskManager_Flow";
	private static final String DWH_BASE_PARTITION_FLOW = "DWH_Partition_Flow";
	private static final String DWH_BASE_UPDATE_FLOW = "DWH_Partition_Flow";
	private static final String DWH_BASE_EXECUTION_PROFILER_FLOW = "DWH_Partition_Flow";
	private static final String DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_FLOW = "DWH_Partition_Flow";
	private static final String DWH_BASE_UPDATE_DATES_FLOW = "DWH_updateDates_Flow";

	@Inject
	private DWH_Steps dwhTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhSetFlow() {
		return flow(DWH_SET_FLOW).addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_SET)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhFlow() {
		return flow(EXECUTE_INSTALLER_LOG_FLOW).addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhBaseDiskManagerFlow() {
		return flow(DWH_BASE_DISK_MANAGER_FLOW)
				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_DISK_MANAGER)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhBasePartitionFlow() {
		return flow(DWH_BASE_PARTITION_FLOW)
				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_PARTITION)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow update_DWH_BaseFlow() {
		return flow(DWH_BASE_UPDATE_FLOW)
				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_UPDATE)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhBaseExecutionProfilerFlow() {
		return flow(DWH_BASE_EXECUTION_PROFILER_FLOW)
				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_EXECUTION_PROFILER))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhBaseSchedulerActivateAndTriggerFlow() {
		return flow(DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_FLOW)

				.addTestStep(
						annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER))
				.addTestStep(annotatedMethod(dwhTestSteps,
						DWH_Steps.StepIds.VERIFY_DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_STATUS))
				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_TRIGGER_PARTITIONING))

				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_TRIGGER_SERVICE)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhBaseupdateDatesFlow() {
		return flow(DWH_BASE_UPDATE_DATES_FLOW)
				.addTestStep(annotatedMethod(dwhTestSteps, DWH_Steps.StepIds.VERIFY_DWH_BASE_UPDATE_DATES)).build();
	}
}