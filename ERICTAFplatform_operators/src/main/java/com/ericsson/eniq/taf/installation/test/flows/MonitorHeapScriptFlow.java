package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.MonitorHeapScriptSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class MonitorHeapScriptFlow {
	private static final String MONITOR_HEAP_SCRIPT_EXECUTION = "MonitorHeapScriptExecutionFlow";
	private static final String FILE_SYSTEM_SCRIPT_EXECUTION = "FileSystemScriptExecutionFlow";

	@Inject
	private MonitorHeapScriptSteps monitorHeapScriptSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyHeapScriptFlow() {
		return flow(MONITOR_HEAP_SCRIPT_EXECUTION).addTestStep(
				annotatedMethod(monitorHeapScriptSteps, MonitorHeapScriptSteps.StepIds.VERIFY_HEAP_SCRIPT_EXECUTION))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyFileSystemScriptFlow() {
		return flow(FILE_SYSTEM_SCRIPT_EXECUTION).addTestStep(
				annotatedMethod(monitorHeapScriptSteps, MonitorHeapScriptSteps.StepIds.VERIFY_LOG_FILES_LIMIT_EXCEEDS))
				.build();
	}
}
