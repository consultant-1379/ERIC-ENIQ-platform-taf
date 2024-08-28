package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUiRunningServicesTest_Steps;

/**
 * Verify that all running services are green
 *
 * @author ZJSOLEA
 */
public class AdminUiRunningServicesTest_Flow {

	private static final String AdminUiRunningServicesTest_Flow_01 = "AdminUiRunningServicesTest_Flow01";

	@Inject
	private AdminUiRunningServicesTest_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(AdminUiRunningServicesTest_Flow_01)
				.addTestStep(annotatedMethod(steps, AdminUiRunningServicesTest_Steps.StepIds.AdminUiRunningServicesTest_Step)).build();
	}
}
