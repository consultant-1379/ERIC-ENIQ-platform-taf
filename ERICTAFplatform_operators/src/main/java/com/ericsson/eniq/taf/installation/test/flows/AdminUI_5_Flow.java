package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUI_5_Steps;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;

/**
 * Launching of adminui after creating the new user
 *
 * @author ZJSOLEA
 */
public class AdminUI_5_Flow {

	private static final String ADMINUI_5_FLOW_01 = "AdminUI_5_Flow01";

	@Inject
	private epfgFileGeneratorSteps epfgStesps;
	
	@Inject
	private AdminUI_5_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(ADMINUI_5_FLOW_01)
				.addTestStep(annotatedMethod(steps, AdminUI_5_Steps.StepIds.ADMINUI_5_STEP_01)).build();
	}
}
