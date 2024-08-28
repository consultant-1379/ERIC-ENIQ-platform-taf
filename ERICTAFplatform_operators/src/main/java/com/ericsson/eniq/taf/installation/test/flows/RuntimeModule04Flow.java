package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RuntimeModule04Steps;

/**
 * Flow to test runtime module java and tomcat versions are latest
 */
public class RuntimeModule04Flow {

	private static final String RUNTIME_MODULE_04_FLOW = "RuntimeModule04Flow";

	@Inject
	private RuntimeModule04Steps runtimeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow runtimeModuleVerification() {
		return flow(RUNTIME_MODULE_04_FLOW)
				.addTestStep(annotatedMethod(runtimeSteps, RuntimeModule04Steps.StepIds.VERIFY_RUNTIME_MODULE_04)).build();
	}
}
