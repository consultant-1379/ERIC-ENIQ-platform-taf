package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.InterfaceReactivationSteps;

/**
 * Flows to test Interface Re-Activation
 */
public class InterfaceReactivationFlow {

	private static final String INTERFACE_REACTIVATION_LOG_FLOW = "InterfaceRe-Activation_Log_Flow";
	private static final String INTERFACE_REACTIVATION_FLOW = "InterfaceRe-Activation_Log_Flow";
	private static final String FEATURE_INTERFACE_REACTIVATION_FLOW = "FeatureInterfaceRe-Activation_Log_Flow";

	@Inject
	private InterfaceReactivationSteps interfaceStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow interfaceReactivationFlow() {
		return flow(INTERFACE_REACTIVATION_FLOW).addTestStep(annotatedMethod(interfaceStesps,
				InterfaceReactivationSteps.StepIds.VERIFY_SINGLE_INTERFACE_REACTIVATION)).build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow featureInterfaceReactivationFlow() {
		return flow(FEATURE_INTERFACE_REACTIVATION_FLOW).addTestStep(annotatedMethod(interfaceStesps,
				InterfaceReactivationSteps.StepIds.VERIFY_FEATURE_INTERFACE_REACTIVATION)).build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow interfaceReactivationLogFlow() {
		return flow(INTERFACE_REACTIVATION_LOG_FLOW).addTestStep(
				annotatedMethod(interfaceStesps, InterfaceReactivationSteps.StepIds.VERIFY_INTERFACE_REACTIVATION_LOGS))
				.build();
	}
}
