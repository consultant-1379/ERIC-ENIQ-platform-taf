package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RuntimeModule10Steps;

/**
 * Flow to check the runtime package is extracted under /eniq/sw/runtime
 */
public class RuntimeModule10Flow {

	private static final String RUNTIME_MODULE_10_FLOW = "RuntimeModule10Flow";

	@Inject
	private RuntimeModule10Steps runtimeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow runtimeModuleVerification() {
		return flow(RUNTIME_MODULE_10_FLOW)
				.addTestStep(annotatedMethod(runtimeSteps, RuntimeModule10Steps.StepIds.VERIFY_RUNTIME_MODULE_10)).build();
	}
}
