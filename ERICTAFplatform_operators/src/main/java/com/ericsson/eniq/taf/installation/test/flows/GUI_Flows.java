package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.GUI_TestStep;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;

/**
 * Admin UI Test Cases
 *
 * @author xarunha
 */
public class GUI_Flows {

	private static final String ADMINUI_LOGIN = "AdminUILogin";
	private static final String CREATE_ADMINUI_LOGIN = "CreateDBAdminUILogin";
	private static final String TRIGGER_SETS_IN_ADMIN_UI = "TriggerSetInAdminUIFlow";
	private static final String VERIFY_LICENSE_GREEN = "StatusOfLicenseManagerFlow";

	@Inject
	GUI_TestStep wepAppTestStep;

	@Inject
	private epfgFileGeneratorSteps epfgStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow openBrowser() {
		return flow(ADMINUI_LOGIN).addTestStep(annotatedMethod(wepAppTestStep, GUI_TestStep.StepIds.OPEN_FIREFOX))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow basicTest() {
		return flow(ADMINUI_LOGIN).addTestStep(annotatedMethod(wepAppTestStep, GUI_TestStep.StepIds.BASIC_TEST))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow customizedDBuserDisplayedFlow() {
		return flow(ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, GUI_TestStep.StepIds.CUSTOMIZED_DB_USERS_DISPLAYED))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow closeBrowser() {
		return flow(ADMINUI_LOGIN).addTestStep(annotatedMethod(wepAppTestStep, GUI_TestStep.StepIds.CLOSE_FIREFOX))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow createcustomizedDBuserFlow() {
		return flow(CREATE_ADMINUI_LOGIN)
				.addTestStep(annotatedMethod(wepAppTestStep, GUI_TestStep.StepIds.CREATE_CUSTOMIZED_DB_USERS)).build();
	}
}
