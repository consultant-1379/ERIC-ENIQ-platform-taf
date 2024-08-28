package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.LoadingSteps;

/**
 * Flows to test Loading
 */
public class LoadingFlow {

	private static final String LOADING_LOG_FLOW = "ParserLogVerification";

	@Inject
	private LoadingSteps loadingStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow pmFilesLoadingVerication() {
		return flow(LOADING_LOG_FLOW)
				.addTestStep(annotatedMethod(loadingStesps, LoadingSteps.StepIds.VERIFY_LOADING_ENGINE_LOGS))
				.addTestStep(annotatedMethod(loadingStesps, LoadingSteps.StepIds.VERIFY_LOADING_ERROR_LOGS)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow pmFilesLoadingDuplicate() {
		return flow(LOADING_LOG_FLOW)
				.addTestStep(annotatedMethod(loadingStesps, LoadingSteps.StepIds.VERIFY_LOADING_DUPLICATE)).build();
	}

}
