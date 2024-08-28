package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.TAF_Prechecks_01_Steps;

/**
 * Engine profile should be in ‘Normal’
 *
 * @author ZJSOLEA
 */
public class TAF_Prechecks_01_Flow {

	private static final String TAF_Prechecks_01_Flow = "TAF_Prechecks_01_Flow";

	@Inject
	private TAF_Prechecks_01_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(TAF_Prechecks_01_Flow)
				.addTestStep(annotatedMethod(steps, TAF_Prechecks_01_Steps.StepIds.TAF_Prechecks_01_STEP)).build();
	}
}
