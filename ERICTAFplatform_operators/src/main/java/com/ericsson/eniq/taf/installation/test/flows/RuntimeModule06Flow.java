package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RuntimeModule06Steps;

/**
 * Flow to check the runtime package is extracted under /eniq/sw/runtime
 */
public class RuntimeModule06Flow {

	private static final String RUNTIME_MODULE_06_FLOW = "RuntimeModule06Flow";

	@Inject
	private RuntimeModule06Steps runtimeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow runtimeModuleVerification() {
		return flow(RUNTIME_MODULE_06_FLOW)
				.addTestStep(annotatedMethod(runtimeSteps, RuntimeModule06Steps.StepIds.VERIFY_RUNTIME_MODULE_06)).build();
	}
}
