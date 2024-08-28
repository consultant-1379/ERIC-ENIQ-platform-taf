package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUISpecialCharacterSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class AdminUISpecialCharacterFlow {

	private static final String ADMINUI_CHANGE_PASSWORD_FLOW = "AdminUIChangePassword_Flow";

	@Inject
	private AdminUISpecialCharacterSteps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyPasswordSpecialCharacterFlow() {
		return flow(ADMINUI_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, AdminUISpecialCharacterSteps.StepIds.Manage_tomcat_user_TC07_Step))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyNewUserPasswordFlow() {
		return flow(ADMINUI_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, AdminUISpecialCharacterSteps.StepIds.Manage_tomcat_user_TC06_Step))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyValidPasswordFlow() {
		return flow(ADMINUI_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, AdminUISpecialCharacterSteps.StepIds.Manage_tomcat_user_TC04_Step))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyValidPasswordLoginFlow() {
		return flow(ADMINUI_CHANGE_PASSWORD_FLOW)
				.addTestStep(annotatedMethod(steps, AdminUISpecialCharacterSteps.StepIds.Manage_tomcat_user_TC05_Step))
				.build();
	}

}
