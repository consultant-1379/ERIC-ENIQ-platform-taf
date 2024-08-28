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
import com.ericsson.eniq.taf.installation.test.flows.RHELOSScriptsMaintenanceFlow;

/**
 * Test Campaign for EQEV-50510: Port OS specific impacts in maintenance scripts
 * to RHEL OS
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class RHELOSScriptsMaintenanceTest extends TorTestCaseHelper {
	public static final String FLOW_SCENARIO = "ScriptExecutionScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private RHELOSScriptsMaintenanceFlow rHELOSScriptsMaintenanceFlow;

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
	 * EQEV-50510_maintenance_scripts_04
	 */
	@Test
	@TestId(id = "EQEV-50510_maintenance_scripts_04", title = "Verify the execution of remove_hidden_files.bsh")
	public void certificateBackup() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(rHELOSScriptsMaintenanceFlow.verifyHiddenScriptExecutionFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50510_maintenance_script_03
	 */
	@Test
	@TestId(id = "EQEV-50510_maintenance_script_03", title = "Test Campaign for EQEV-50510: Port OS specific impacts in maintenance scripts to RHEL OS")
	public void rHELInterfaceVerificationTest() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(rHELOSScriptsMaintenanceFlow.verifyInterfaceScriptExecutionFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-50510_maintenance_scripts_01
	 */
	/*
	 * @TestSuite
	 * 
	 * @Test(testName = "EQEV-50510_maintenance_script_01")
	 * 
	 * @TestId(id = "EQEV-50510_maintenance_script_01", title =
	 * "Verify the execution of hosts_file_update.sh") public void
	 * hostsFileScriptExecution() { final TestScenario scenario =
	 * scenario(FLOW_SCENARIO)
	 * .addFlow(rHELOSScriptsMaintenanceFlow.verifyHostsScriptExecutionFlow()).build
	 * (); final TestScenarioRunner runner = runner().withListener(new
	 * LoggingScenarioListener()).build();
	 * 
	 * runner.start(scenario); }
	 */

	/**
	 * EQEV-57200_RHEL_Deployed_Service_01
	 */
	@Test
	@TestId(id = "EQEV-57200_RHEL_Deployed_Service_01", title = "Test Campaign for EQEV-57200: RHEL deployed service - Verify RMIRegistry is stopping the dependent services")
	public void rHELrmiRegistryStatusVerificationTest() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(rHELOSScriptsMaintenanceFlow.verifyRMIRegistryStatusCheckFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-59658_lwphelper_Service_01
	 */
	@Test
	@TestId(id = "EQEV-59658_lwphelper_Service_01", title = "Test Campaign for EQEV-59658: Verify lwphelper services is in active status")
	public void lwphelperStatusVerificationTest() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(rHELOSScriptsMaintenanceFlow.verifyLwphelperServiceStatusFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

}
