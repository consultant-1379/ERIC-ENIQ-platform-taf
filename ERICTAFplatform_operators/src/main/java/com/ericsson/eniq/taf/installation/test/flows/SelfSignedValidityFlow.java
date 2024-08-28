package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.SelfSignedValiditySteps;

/**
 * 
 * @author xsounpk
 *
 */
public class SelfSignedValidityFlow {

	private static final String CERTIFICATE_BACKUP_FLOW = "CertificateBackupFlow";
	private static final String CA_EXPIRY_CHECK = "CAExpiryCheckFlow";
	private static final String WEBSERVER_RESTART = "WebserverRestartFlow";
	private static final String WEAK_CIPHERS = "WeakCiphersFlow";

	@Inject
	private SelfSignedValiditySteps selfSignedValiditySteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificateBackup() {
		return flow(CERTIFICATE_BACKUP_FLOW).addTestStep(
				annotatedMethod(selfSignedValiditySteps, SelfSignedValiditySteps.StepIds.VERIFY_CERTIFICATE_BACKUP))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyCAExpiryCheck() {
		return flow(CA_EXPIRY_CHECK)
				.addTestStep(annotatedMethod(selfSignedValiditySteps, SelfSignedValiditySteps.StepIds.VERIFY_CA_EXPIRY))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifywebRestart() {
		return flow(WEBSERVER_RESTART).addTestStep(
				annotatedMethod(selfSignedValiditySteps, SelfSignedValiditySteps.StepIds.VERIFY_WEBSERVER_RESTART))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyweakCiphers() {
		return flow(WEAK_CIPHERS).addTestStep(
				annotatedMethod(selfSignedValiditySteps, SelfSignedValiditySteps.StepIds.VERIFY_WEAK_CIPHERS_REMOVED))
				.build();
	}
}
