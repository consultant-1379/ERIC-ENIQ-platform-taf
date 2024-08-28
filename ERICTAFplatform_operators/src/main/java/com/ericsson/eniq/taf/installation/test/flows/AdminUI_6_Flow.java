package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUI_5_Steps;
import com.ericsson.eniq.taf.installation.test.steps.AdminUI_6_Steps;

/**
 * Launching of adminui after changing the password
 *
 * @author ZJSOLEA
 */
public class AdminUI_6_Flow {

	private static final String ADMINUI_6_FLOW_01 = "AdminUI_6_Flow01";

	@Inject
	private AdminUI_6_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(ADMINUI_6_FLOW_01)
				.addTestStep(annotatedMethod(steps, AdminUI_6_Steps.StepIds.ADMINUI_6_STEP_01)).build();
	}
}
