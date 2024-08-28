package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.TAF_Prechecks_03_Steps;

/**
 * all services should be in active state
 *
 * @author ZJSOLEA
 */
public class TAF_Prechecks_03_Flow {

	private static final String TAF_Prechecks_03_Flow = "TAF_Prechecks_03_Flow";

	@Inject
	private TAF_Prechecks_03_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(TAF_Prechecks_03_Flow)
				.addTestStep(annotatedMethod(steps, TAF_Prechecks_03_Steps.StepIds.TAF_Prechecks_03_STEP)).build();
	}
}
