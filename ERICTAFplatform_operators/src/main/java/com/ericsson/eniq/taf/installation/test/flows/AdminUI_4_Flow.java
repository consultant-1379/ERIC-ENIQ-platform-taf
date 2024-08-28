package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUI_4_Steps;

/**
 * Launching of adminui of same server in one more browser
 *
 * @author ZJSOLEA
 */
public class AdminUI_4_Flow {

	private static final String ADMINUI_FLOW_01 = "AdminUIFlow01";

	@Inject
	private AdminUI_4_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(ADMINUI_FLOW_01)
				.addTestStep(annotatedMethod(steps, AdminUI_4_Steps.StepIds.ADMINUI_4_STEP_01)).build();
	}
}
