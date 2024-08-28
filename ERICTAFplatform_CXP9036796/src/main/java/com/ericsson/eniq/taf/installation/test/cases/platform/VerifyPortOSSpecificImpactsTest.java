package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

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
import com.ericsson.eniq.taf.installation.test.flows.VerifyPortOSSpecificImpactsFlow;

/**
 * Test Campaign for Port OS specific impacts in Engine to RHEL OS - 15968
 * 
 * @author zvaddee
 *
 */
@Test(enabled = false)
public class VerifyPortOSSpecificImpactsTest extends TorTestCaseHelper {

	public static final String DISKMGRALLINTERFACE_FLOW_SCENARIO = "DiskManager_AllInterface_Scenario";
	public static final String DISABLECOUNTERS_FLOW_SCENARIO = "disableCounters_Scenario";
	public static final String ENABLECOUNTERS_FLOW_SCENARIO = "enableCounters_Scenario";
	public static final String REMOVE_HIDDEN_FILES_FLOW_SCENARIO = "remove_hidden_files_Scenario";
	public static final String ENGINE_COMMANDS_SCENARIO = "Validate Engine Commands";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyPortOSSpecificImpactsFlow portOSSSpecificFlows;

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
	 * EQEV-50510_maintenance_scripts_01
	 */
	/*
	 * 
	 * @TestSuite
	 * 
	 * @Test(testName = "EQEV-50510_maintenance_scripts_01")
	 * 
	 * @TestId(id = "EQEV-50510_maintenance_scripts_01", title =
	 * "OS Port Specific") public void executeDiskManagerAllInterface() { final
	 * TestScenario scenario = scenario(DISKMGRALLINTERFACE_FLOW_SCENARIO)
	 * .addFlow(portOSSSpecificFlows.excecuteAllInteraceFlow()).build(); final
	 * TestScenarioRunner runner = runner().withListener(new
	 * LoggingScenarioListener()).build();
	 * 
	 * runner.start(scenario); }
	 * 
	 *//**
		 * EQEV-50510_maintenance_scripts_01
		 */
	/*
	 * 
	 * @TestSuite
	 * 
	 * @Test(testName = "EQEV-50510_maintenance_scripts_01")
	 * 
	 * @TestId(id = "EQEV-50510_maintenance_scripts_01", title =
	 * "DisableCounters") public void disableCounters() { final TestScenario
	 * scenario = scenario(DISABLECOUNTERS_FLOW_SCENARIO)
	 * .addFlow(portOSSSpecificFlows.disableContersFlow()).build(); final
	 * TestScenarioRunner runner = runner().withListener(new
	 * LoggingScenarioListener()).build();
	 * 
	 * runner.start(scenario); }
	 * 
	 *//**
		 * EQEV-50510_maintenance_scripts_03
		 */
	/*
	 * 
	 * @TestSuite
	 * 
	 * @Test(testName = "EQEV-50510_maintenance_scripts_03")
	 * 
	 * @TestId(id = "EQEV-50510_maintenance_scripts_03", title =
	 * "Post Installer") public void enableCounters() { final TestScenario
	 * scenario = scenario(ENABLECOUNTERS_FLOW_SCENARIO)
	 * .addFlow(portOSSSpecificFlows.enableCountersFlow()).build(); final
	 * TestScenarioRunner runner = runner().withListener(new
	 * LoggingScenarioListener()).build();
	 * 
	 * runner.start(scenario); }
	 * 
	 */

	/**
	 * EQEV-50510_maintenance_scripts_04
	 */
	@Test
	@TestId(id = "EQEV-50510_maintenance_scripts_04", title = "Post Installer")
	public void removeHiddenFiles() {
		final TestScenario scenario = scenario(ENABLECOUNTERS_FLOW_SCENARIO)
				.addFlow(portOSSSpecificFlows.removeHiddenilesFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50502_engine_11
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50502_engine_11", title = "Verify the engine commands (e.g.showDisabledSets , showActiveInterfaces etc.).")
	public void verifyEngineCommands() {
		final TestScenario scenario = dataDrivenScenario(ENGINE_COMMANDS_SCENARIO)
				.addFlow(portOSSSpecificFlows.validateEngineCommands())
				.withScenarioDataSources(dataSource("engineCommands")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}