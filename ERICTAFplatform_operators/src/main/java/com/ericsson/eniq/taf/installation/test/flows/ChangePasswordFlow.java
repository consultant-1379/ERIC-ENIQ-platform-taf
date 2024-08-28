package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.ChangePasswordSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class ChangePasswordFlow {
	private static final String DWHREP_CHANGE_PASSWORD_FLOW = "DwhrepChangePassword_Flow";
	private static final String ETLREP_CHANGE_PASSWORD_FLOW = "EtlrepChangePassword_Flow";
	private static final String DCPUBLIC_CHANGE_PASSWORD_FLOW = "DCPUBLIChangePassword_Flow";
	private static final String DCUSER_CHANGE_PASSWORD_FLOW = "DCUSERChangePassword_Flow";

	@Inject
	private ChangePasswordSteps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	/*public TestStepFlow changeDcpublicDBpasswordFlow() {
		return flow(DCPUBLIC_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, ChangePasswordSteps.StepIds.EQEV_68105_Negative_TC05_Step)).build();
	}*/

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhrepChangePassword() {
		return flow(DWHREP_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, ChangePasswordSteps.StepIds.EQEV_68105_Negative_TC05_Step)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow etlrepChangePassword() {
		return flow(ETLREP_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, ChangePasswordSteps.StepIds.EQEV_68105_Negative_TC06_Step)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dcUserChangePassword() {
		return flow(DCUSER_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, ChangePasswordSteps.StepIds.EQEV_68105_TC07_Step)).build();
	}

}
