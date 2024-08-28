package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.CheckFailedETLSets_Steps;

/**
 * Verify that there are no failed ETL sets
 *
 * @author ZJSOLEA
 */
public class CheckFailedETLSets_Flow {

	private static final String CheckFailedETLSets_Flow = "CheckFailedETLSets_Flow";

	@Inject
	private CheckFailedETLSets_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(CheckFailedETLSets_Flow)
				.addTestStep(annotatedMethod(steps, CheckFailedETLSets_Steps.StepIds.CheckFailedETLSets_Step)).build();
	}
}