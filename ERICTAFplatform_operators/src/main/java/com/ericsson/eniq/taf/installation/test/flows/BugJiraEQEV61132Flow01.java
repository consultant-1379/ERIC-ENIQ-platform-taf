package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.BugJiraEQEV61132Step01;

/**
 * verification for bug Jira EQEV61132
 *
 * @author ZJSOLEA
 */
public class BugJiraEQEV61132Flow01 {

	private static final String BUGJIRAEQEV61132_FLOW_01 = "BugJiraEQEV61132Flow01";

	@Inject
	private BugJiraEQEV61132Step01 steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(BUGJIRAEQEV61132_FLOW_01)
				.addTestStep(annotatedMethod(steps, BugJiraEQEV61132Step01.StepIds.BUGJIRAEQEV61132_STEP_01)).build();
	}
}
