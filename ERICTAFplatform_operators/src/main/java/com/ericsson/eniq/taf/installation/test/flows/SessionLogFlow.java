package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.SessionLogSteps;

/**
 * Flows to test Loading
 */
public class SessionLogFlow {

	private static final String SESSION_LOG_FLOW = "ParserLogVerification";

	@Inject
	private SessionLogSteps sessionLogStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sessionLogStarterFlow() {
		return flow(SESSION_LOG_FLOW)
				.addTestStep(annotatedMethod(sessionLogStesps, SessionLogSteps.StepIds.VERIFY_SESSION_LOG_STARTER))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sessionLogAdapterFlow() {
		return flow(SESSION_LOG_FLOW)
				.addTestStep(annotatedMethod(sessionLogStesps, SessionLogSteps.StepIds.VERIFY_SESSION_LOG_ADAPTER))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sessionLogLoaderFlow() {
		return flow(SESSION_LOG_FLOW)
				.addTestStep(annotatedMethod(sessionLogStesps, SessionLogSteps.StepIds.VERIFY_SESSION_LOG_LOADER))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sessionLogAggregatorFlow() {
		return flow(SESSION_LOG_FLOW)
				.addTestStep(annotatedMethod(sessionLogStesps, SessionLogSteps.StepIds.VERIFY_SESSION_LOG_AGGREGATOR))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sessionLogUpdateMonitoringFlow() {
		return flow(SESSION_LOG_FLOW)
				.addTestStep(
						annotatedMethod(sessionLogStesps, SessionLogSteps.StepIds.VERIFY_SESSION_LOG_UPDATE_MONITORING))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow sessionLogAggregateFlow() {
		return flow(SESSION_LOG_FLOW)
				.addTestStep(annotatedMethod(sessionLogStesps, SessionLogSteps.StepIds.VERIFY_SESSION_LOG_AGGREGATE))
				.build();
	}
}
