package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.HwUtilScriptsRemovedSteps;
import com.ericsson.eniq.taf.installation.test.steps.InitialInstallationSteps;

/**
 * Flows to test ENIQ Initial Install
 */
public class InitialInstallationFlow {

	private static final String INITIAL_INSTALL_FLOW = "verifyInitialInstallation";
	private static final String ENIQ_SERVICES_FLOW = "verifyInitialInstallation";
	private static final String ENIQ_SHIPMENT_FLOW = "verifyENIQshipment";

	@Inject
	private InitialInstallationSteps initialInstallationStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyInitialInstallation() {
		return flow(INITIAL_INSTALL_FLOW).addTestStep(annotatedMethod(initialInstallationStesps,
				InitialInstallationSteps.StepIds.VERIFY_ENIQ_INITIAL_INSTALL)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyEniqServicesStatus() {
		return flow(ENIQ_SERVICES_FLOW).addTestStep(
				annotatedMethod(initialInstallationStesps, InitialInstallationSteps.StepIds.VERIFY_ENIQ_SERVICES_FLOW))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyEniqShipmentStatus() {
		return flow(ENIQ_SHIPMENT_FLOW).addTestStep(
				annotatedMethod(initialInstallationStesps, InitialInstallationSteps.StepIds.VERIFY_ENIQ_SHIPMENT_FLOW))
				.build();
	}
}
