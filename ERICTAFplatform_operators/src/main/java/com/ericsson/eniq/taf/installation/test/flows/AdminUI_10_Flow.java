package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUI_10_Steps;

/**
 * Verification of launching of adminui in same browser in multiple tabs
 *
 * @author ZJSOLEA
 */
public class AdminUI_10_Flow {

	private static final String ADMINUI_10_FLOW_01 = "AdminUI_10_Flow01";

	@Inject
	private AdminUI_10_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(ADMINUI_10_FLOW_01)
				.addTestStep(annotatedMethod(steps, AdminUI_10_Steps.StepIds.ADMINUI_10_STEP_01)).build();
	}
}
