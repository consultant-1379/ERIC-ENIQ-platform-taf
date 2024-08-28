package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RuntimeModule15Steps;

/**
 * Flow to verify the web server status
 */
public class RuntimeModule15Flow {

	private static final String RUNTIME_MODULE_15_FLOW = "RuntimeModule15Flow";

	@Inject
	private RuntimeModule15Steps runtimeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow runtimeModuleVerification() {
		return flow(RUNTIME_MODULE_15_FLOW)
				.addTestStep(annotatedMethod(runtimeSteps, RuntimeModule15Steps.StepIds.VERIFY_RUNTIME_MODULE_15)).build();
	}
}
