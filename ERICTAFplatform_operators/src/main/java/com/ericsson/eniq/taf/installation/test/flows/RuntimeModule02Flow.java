package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RuntimeModule02Steps;

/**
 * Flow to test runtime module installation logs
 */
public class RuntimeModule02Flow {

	private static final String RUNTIME_MODULE_02_FLOW = "RuntimeModule02Flow";

	@Inject
	private RuntimeModule02Steps runtimeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow runtimeModuleVerification() {
		return flow(RUNTIME_MODULE_02_FLOW)
				.addTestStep(annotatedMethod(runtimeSteps, RuntimeModule02Steps.StepIds.VERIFY_RUNTIME_MODULE_02)).build();
	}
}
