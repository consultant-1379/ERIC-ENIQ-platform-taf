package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.BugJiraEQEV66467_TC02Step;

/**
 * Verify that keystore password encrypted in server.xml
 *
 * @author ZJSOLEA
 */
public class BugJiraEQEV66467_TC02Flow {

	private static final String BUGJIRAEQEV66467_TC02_FLOW_01 = "BugJiraEQEV66467_TC02Flow";
	private static final String BUGJIRAEQEV66468_TC03_FLOW_01 = "BugJiraEQEV66467_TC03Flow";

	@Inject
	private BugJiraEQEV66467_TC02Step steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(BUGJIRAEQEV66467_TC02_FLOW_01)
				.addTestStep(annotatedMethod(steps, BugJiraEQEV66467_TC02Step.StepIds.BUGJIRAEQEV66467_TC02_STEP_01)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification_tc03() {
		return flow(BUGJIRAEQEV66468_TC03_FLOW_01)
				.addTestStep(annotatedMethod(steps, BugJiraEQEV66467_TC02Step.StepIds.BUGJIRAEQEV66468_TC03_STEP_01)).build();
	}

}
