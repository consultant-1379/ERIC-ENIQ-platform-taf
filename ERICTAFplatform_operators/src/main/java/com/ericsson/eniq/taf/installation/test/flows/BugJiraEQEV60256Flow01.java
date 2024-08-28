package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.BugJiraEQEV60256Step01;

/**
 * verification for bug Jira EQEV60256
 *
 * @author ZJSOLEA
 */
public class BugJiraEQEV60256Flow01 {

	private static final String BUGJIRAEQEV60256_FLOW_01 = "BugJiraEQEV60256Flow01";

	@Inject
	private BugJiraEQEV60256Step01 steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(BUGJIRAEQEV60256_FLOW_01)
				.addTestStep(annotatedMethod(steps, BugJiraEQEV60256Step01.StepIds.BUGJIRAEQEV60256_STEP_01)).build();
	}
}
