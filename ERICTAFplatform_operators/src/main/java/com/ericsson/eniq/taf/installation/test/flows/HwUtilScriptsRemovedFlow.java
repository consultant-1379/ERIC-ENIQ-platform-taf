package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.HwUtilScriptsRemovedSteps;

/**
 * Flows to test Parsing
 * 
 * @param <CertificatesRetentionSteps>
 */
public class HwUtilScriptsRemovedFlow {

	private static final String HW_FLOW = "CertificateVerificationForCER";

	@Inject
	private HwUtilScriptsRemovedSteps hwUtilScriptsRemovedStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyHwUtilScriptsRemoved() {
		return flow(HW_FLOW).addTestStep(annotatedMethod(hwUtilScriptsRemovedStesps,
				HwUtilScriptsRemovedSteps.StepIds.VERIFY_HW_SCRIPTS_REMOVED)).build();
	}
}
