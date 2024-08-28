package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RuntimeModule12Steps;

/**
 * Flow to check whether JAVA execute permissions are provided without any issues
 */
public class RuntimeModule12Flow {

	private static final String RUNTIME_MODULE_12_FLOW = "RuntimeModule12Flow";

	@Inject
	private RuntimeModule12Steps runtimeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow runtimeModuleVerification() {
		return flow(RUNTIME_MODULE_12_FLOW)
				.addTestStep(annotatedMethod(runtimeSteps, RuntimeModule12Steps.StepIds.VERIFY_RUNTIME_MODULE_12)).build();
	}
}
