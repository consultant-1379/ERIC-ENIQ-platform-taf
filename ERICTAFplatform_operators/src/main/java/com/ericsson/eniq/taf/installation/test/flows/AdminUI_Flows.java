package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.WepAppTestStep;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;

/**
 * Admin UI Test Cases
 *
 * @author xarunha
 */
public class AdminUI_Flows {

	private static final String ADMINUI_LOGIN = "AdminUILogin";
	private static final String TRIGGER_SETS_IN_ADMIN_UI = "TriggerSetInAdminUIFlow";
	private static final String VERIFY_LICENSE_GREEN="StatusOfLicenseManagerFlow";

	@Inject
	WepAppTestStep wepAppTestStep;

	@Inject
	private epfgFileGeneratorSteps epfgStesps;
	
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow openBrowser() {
		return flow(ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.OPEN_FIREFOX))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow basicTest() {
		return flow(ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.BASIC_TEST))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow adminUiLogin() {
		return flow(ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.VERIFY_ADMIN_UI_LOGIN)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyadminUiLinks() {
		return flow(ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.VERIFY_ADMIN_UI_LINKS))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow triggerSetsInAdminUI() {
		return flow(TRIGGER_SETS_IN_ADMIN_UI)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.VERIFY_TRIGGER_SETS_IN_ADMIN_UI))
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.VERIFY_TRIGGER_SETS_ERRORS_IN_CLI))
				.build();

	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow licenseAdminUiFlow() {
		return flow(TRIGGER_SETS_IN_ADMIN_UI)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.VERIFY_ADMIN_UI_LICENSE))
				.build();

	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow isLicenseServerManagerGreenColor() {
		return flow(VERIFY_LICENSE_GREEN)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.VERIFY_LICENSE_GREEN))
				.build();

	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow closeBrowser() {
		return flow(ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, WepAppTestStep.StepIds.CLOSE_FIREFOX))
				.build();
	}
}
