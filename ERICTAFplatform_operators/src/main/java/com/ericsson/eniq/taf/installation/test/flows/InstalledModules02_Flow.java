package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.InstalledModules02_Steps;

/**
 * Verify installed modules duplicates
 *
 * @author ZJSOLEA
 */
public class InstalledModules02_Flow {

	private static final String INSTALLED_MODULES_FLOW_02 = "InstalledModules_Flow02";

	@Inject
	private InstalledModules02_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(INSTALLED_MODULES_FLOW_02)
				.addTestStep(annotatedMethod(steps, InstalledModules02_Steps.StepIds.InstalledModules_Step02)).build();
	}
}
