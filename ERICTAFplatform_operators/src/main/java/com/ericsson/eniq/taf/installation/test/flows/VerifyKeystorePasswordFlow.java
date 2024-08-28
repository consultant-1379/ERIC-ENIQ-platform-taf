package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyKeystorePasswordSteps;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;

/**
 * Flows to test Parsing
 */
public class VerifyKeystorePasswordFlow {

	private static final String KEY_PASSWORD_ENCRYPTION_FLOW = "VerifyKeystorePasswordFlow";

	@Inject
	private VerifyKeystorePasswordSteps keystorePasswordStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow keystorePasswordEncryptionFlow() {
		return flow(KEY_PASSWORD_ENCRYPTION_FLOW).addTestStep(annotatedMethod(keystorePasswordStesps, VerifyKeystorePasswordSteps.StepIds.KEYSTORE))
				.build();
	}
}