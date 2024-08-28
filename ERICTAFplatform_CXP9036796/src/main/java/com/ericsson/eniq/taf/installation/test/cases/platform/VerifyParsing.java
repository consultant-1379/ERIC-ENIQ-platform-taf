package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.ParsingFlow;

/**
 * Test Campaign for parsing - 16352
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class VerifyParsing extends TorTestCaseHelper {

	public static final String PARSER_1_SCENARIO = "CheckParserInputDirScenario";
	public static final String PARSER_2_SCENARIO = "EngineAndErrorLogParserScenario";
	public static final String PARSER_3_SCENARIO = "ParserMissingDataTagScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private ParsingFlow parserFlow;

	
	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
	
	/**
	 * EQEV-50682_Parsing_01
	 */
	@Test
	@TestId(id = "EQEV-50682_Parsing_01", title = "To verify Topology files are parsed successfully")
	public void isInDirEmpty() {
		final TestScenario scenario = scenario(PARSER_1_SCENARIO).addFlow(parserFlow.inDirEmptyFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50682_Parsing_02
	 */
	@Test
	@TestId(id = "EQEV-50682_Parsing_02", title = "Verify Engine and Error Log Verification")
	public void verifyEngineAndErrorLog() {
		final TestScenario scenario = scenario(PARSER_2_SCENARIO).addFlow(parserFlow.parsingLogVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50682_Parsing_03
	 */
	 @Test
	 @TestId(id = "EQEV-50682_Parsing_03", title = "Verify that the sample file entry with a missing data tag is not processed in parser")
	 public void verifyMissingDataTagInParsing() {
		 final TestScenario scenario = scenario(PARSER_3_SCENARIO)
				 .addFlow(parserFlow.missingDataTagInTopologyFileParsing()).build();
		 final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		 runner.start(scenario);
	 }

}