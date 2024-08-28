package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.SecurityFeaturesSteps;

/**
 * Flows to test Parsing
 */
public class SecurityFeaturesFlow {

	private static final String Security_FLOW = "SecurityFeaturesSteps";

	@Inject
	private SecurityFeaturesSteps securityStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow securityFlow() {
		return flow(Security_FLOW)
				.addTestStep(annotatedMethod(securityStesps, SecurityFeaturesSteps.StepIds.SECURITY_FEATURES)).build();
	}
}