package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.InstalledModules01_Steps;

/**
 * Verify installed modules
 *
 * @author ZJSOLEA
 */
public class InstalledModules01_Flow {

	private static final String INSTALLED_MODULES_FLOW_01 = "InstalledModules_Flow01";

	@Inject
	private InstalledModules01_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(INSTALLED_MODULES_FLOW_01)
				.addTestStep(annotatedMethod(steps, InstalledModules01_Steps.StepIds.InstalledModules_Step01)).build();
	}
}
