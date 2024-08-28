package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.InterfaceActivationSteps;

/**
 * Flows to test Interface Activation
 */
public class InterfaceActivationFlow {

	private static final String INTERFACE_ACTIVATION_FLOW = "InterfaceActivation";

	@Inject
	private InterfaceActivationSteps interfaceStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow ActiveInterfaceFlow() {
		return flow(INTERFACE_ACTIVATION_FLOW)
				.addTestStep(
						annotatedMethod(interfaceStesps, InterfaceActivationSteps.StepIds.VERIFY_ACTIVE_INTERFACES))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow interfaceActivationOneAliasFlow() {
		return flow(INTERFACE_ACTIVATION_FLOW)
				.addTestStep(
						annotatedMethod(interfaceStesps, InterfaceActivationSteps.StepIds.VERIFY_INTERFACE_ACTIVATION))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow interfaceActivationOssAliasFlow() {
		return flow(INTERFACE_ACTIVATION_FLOW).addTestStep(
				annotatedMethod(interfaceStesps, InterfaceActivationSteps.StepIds.VERIFY_INTERFACE_ACTIVATION_WITH_OSS))
				.build();
	}
}
