package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV68105_TC02_Steps;

/**
 * change the password of database user dcbo
 *
 * @author ZJSOLEA
 */
public class EQEV68105_TC02_Flow {

	private static final String EQEV68105_TC02_FLOW = "EQEV68105_TC02_FLOW";

	@Inject
	private EQEV68105_TC02_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV68105_TC02_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV68105_TC02_Steps.StepIds.EQEV68105_TC02_STEP01)).build();
	}
}
