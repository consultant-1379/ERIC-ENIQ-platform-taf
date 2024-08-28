package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyLoadingCountersSteps;

/**
 * Flows to test Loading of pm tables
 */
public class VerifyLoadingCountersFlow {

	private static final String LOADING_COUNTERS_FLOW = "PmLoadingTagsFlow";

	@Inject
	private VerifyLoadingCountersSteps loadingSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow pmFilesLoadingVerication() {
		return flow(LOADING_COUNTERS_FLOW)
				.addTestStep(annotatedMethod(loadingSteps, VerifyLoadingCountersSteps.StepIds.VERIFY_LOADING_COUNTERS)).build();
	}
}
