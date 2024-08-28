package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.CertificatesRetentionSteps;

/**
 * Flows to test Parsing
 * 
 * @param <CertificatesRetentionSteps>
 */
public class CertificatesRetentionFlow {

	private static final String CERTIFICATE_CER_FLOW = "CertificateVerificationForCER";
	private static final String CERTIFICATE_PEM_FLOW = "CertificateVerificationForPEM";
	private static final String CERTIFICATE_JKS_FLOW = "CertificateVerificationForJKS";
	private static final String CERTIFICATE_CSR_FLOW = "CertificateVerificationForCSR";
	private static final String CERTIFICATE_TS_FLOW = "CertificateVerificationForTS";
	private static final String CERTIFICATE_ENM_FLOW = "CertificateVerificationForENM";
	
	@Inject
	private CertificatesRetentionSteps certificatesRetentionStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificatesRetentionCER() {
		return flow(CERTIFICATE_CER_FLOW).addTestStep(
				annotatedMethod(certificatesRetentionStesps, CertificatesRetentionSteps.StepIds.VERIFY_CERTIFICATE_CER))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificatesRetentionPEM() {
		return flow(CERTIFICATE_PEM_FLOW).addTestStep(
				annotatedMethod(certificatesRetentionStesps, CertificatesRetentionSteps.StepIds.VERIFY_CERTIFICATE_PEM))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificatesRetentionJKS() {
		return flow(CERTIFICATE_JKS_FLOW).addTestStep(
				annotatedMethod(certificatesRetentionStesps, CertificatesRetentionSteps.StepIds.VERIFY_CERTIFICATE_JKS))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificatesRetentionCSR() {
		return flow(CERTIFICATE_CSR_FLOW).addTestStep(
				annotatedMethod(certificatesRetentionStesps, CertificatesRetentionSteps.StepIds.VERIFY_CERTIFICATE_CSR))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificatesRetentionTS() {
		return flow(CERTIFICATE_TS_FLOW).addTestStep(
				annotatedMethod(certificatesRetentionStesps, CertificatesRetentionSteps.StepIds.VERIFY_CERTIFICATE_TS))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifycertificatesRetentionENM() {
		return flow(CERTIFICATE_ENM_FLOW).addTestStep(
				annotatedMethod(certificatesRetentionStesps, CertificatesRetentionSteps.StepIds.VERIFY_CERTIFICATE_ENM))
				.build();
	}
}
