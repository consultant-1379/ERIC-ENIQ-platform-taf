package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.MWSpathUpdateSteps;

/**
 * Flows to test Parsing
 */
public class mwsFlow {

	private static final String MWS_FLOW = "ParserVerification";
	private static final String PARSER_LOG_FLOW = "ParserLogVerification";
	private static final String PARSER_MISSING_DATA_ENTRY_FLOW = "ParserDataTagMissingEntryVerification";

	@Inject
	private MWSpathUpdateSteps mwsStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow mwsPathUpdateFlow() {
		return flow(MWS_FLOW).addTestStep(annotatedMethod(mwsStesps, MWSpathUpdateSteps.StepIds.UPDATE_MWS_PATH))
				.build();
	}
}