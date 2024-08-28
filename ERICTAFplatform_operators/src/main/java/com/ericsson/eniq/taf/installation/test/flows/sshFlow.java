package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.sshSessionSteps;

/**
 * Flows to test Parsing
 */
public class sshFlow {

	private static final String SSH_FLOW = "sshSessionClose";

	@Inject
	private sshSessionSteps sshStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sshSessionCloseFlow() {
		return flow(SSH_FLOW).addTestStep(annotatedMethod(sshStesps, sshSessionSteps.StepIds.SSH_SESSION))
				.build();
	}
}