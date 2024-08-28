package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyPmLoadingTagsSteps;

/**
 * Flows to test Loading of pm tables
 */
public class VerifyPmLoadingTagsFlow {

	private static final String PM_LOADING_TAGS_FLOW = "PmLoadingTagsFlow";

	@Inject
	private VerifyPmLoadingTagsSteps loadingStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow pmFilesLoadingVerication() {
		return flow(PM_LOADING_TAGS_FLOW)
				.addTestStep(annotatedMethod(loadingStesps, VerifyPmLoadingTagsSteps.StepIds.VERIFY_PM_LOADING_TAGS)).build();
	}
}
