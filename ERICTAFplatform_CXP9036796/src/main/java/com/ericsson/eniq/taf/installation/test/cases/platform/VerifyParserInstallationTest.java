package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.VerifyParserInstallationFlow;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

/**
 * Test Campaign for ENIQ Install Parsers on RHEL OS - 15971
 * 
 * @author zvaddee
 *
 */
@Test(enabled = false)
public class VerifyParserInstallationTest extends TorTestCaseHelper {

	public static final String LOG_FLOW_SCENARIO = "ParserLog_Scenario";
	public static final String VERSIONDB_FLOW_SCENARIO = "Parser_Version_dbproperties_Scenario";
	public static final String MODULES_EXTRACTED_FLOW_SCENARIO = "Parser_Modules_extracted_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	@Inject
	private VerifyParserInstallationFlow parserInstallationFlows;

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
	 * EQEV-50465_Install_Parsers_03
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50465_Install_Parsers_03", title = "Verify installed parser modules folders are available in /eniq/sw/platform path")
	public void isParserModuleFolderExists() {
		final TestScenario scenario = dataDrivenScenario(MODULES_EXTRACTED_FLOW_SCENARIO)
				.addFlow(parserInstallationFlows.parserModulesExtractedFlow())
				.withScenarioDataSources(dataSource("parser_modules1")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50465_Install_Parsers_04
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50465_Install_Parsers_04", title = "Verify the each parser module version is updated in versiondb.properties file")
	public void isVersionDBPropertiesUpdated() {
		final TestScenario scenario = dataDrivenScenario(VERSIONDB_FLOW_SCENARIO)
				.addFlow(parserInstallationFlows.parserVersionDBPropetiesFlow())
				.withScenarioDataSources(dataSource("parser_modules2")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50465_Install_Parsers_05
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50465_Install_Parsers_05", title = "Verify the installation logs of parser modules")
	public void isExceptionsinLogs() {
		final TestScenario scenario = dataDrivenScenario(LOG_FLOW_SCENARIO)
				.addFlow(parserInstallationFlows.parserLogFlow()).withScenarioDataSources(dataSource("parser_modules3"))
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}