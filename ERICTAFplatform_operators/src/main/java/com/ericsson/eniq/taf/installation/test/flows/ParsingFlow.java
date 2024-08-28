package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.ParsingSteps;

/**
 * Flows to test Parsing
 */
public class ParsingFlow {

	private static final String PARSER_FLOW = "ParserVerification";
	private static final String PARSER_LOG_FLOW = "ParserLogVerification";
	private static final String PARSER_MISSING_DATA_ENTRY_FLOW = "ParserDataTagMissingEntryVerification";

	@Inject
	private ParsingSteps parsingStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow inDirEmptyFlow() {
		return flow(PARSER_FLOW)
				.addTestStep(annotatedMethod(parsingStesps, ParsingSteps.StepIds.VERIFY_PARSING_BASED_ON_INPUT))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow parsingLogVerification() {
		return flow(PARSER_LOG_FLOW).addTestStep(annotatedMethod(parsingStesps, ParsingSteps.StepIds.VERIFY_ENGINE_LOG))
				.addTestStep(annotatedMethod(parsingStesps, ParsingSteps.StepIds.VERIFY_ERROR_LOG)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow missingDataTagInTopologyFileParsing() {
		return flow(PARSER_MISSING_DATA_ENTRY_FLOW)
				.addTestStep(annotatedMethod(parsingStesps, ParsingSteps.StepIds.VERIFY_TOPOLOGY_MISSING_DATA_TAG))
				.build();
	}
}
