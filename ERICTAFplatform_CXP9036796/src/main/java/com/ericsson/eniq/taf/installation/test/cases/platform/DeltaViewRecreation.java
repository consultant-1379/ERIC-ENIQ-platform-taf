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
import com.ericsson.eniq.taf.installation.test.flows.VerifyDeltaViewCreationFlow;

/**
 * Test Campaign for DeltaView Re-Creation Scripts Impacts to be ported on
 * RHEL-15847
 * 
 * @author ZRUMRMU
 *
 */

public class DeltaViewRecreation extends TorTestCaseHelper {

	private static final String DV_INSTALL_LOG_VERFICATION_SCENARIO = "To Verify the installation"
			+ " of deltaverification logs";
	private static final String DV_DELTA_VIEW_PERMISSION = "To Verify DC User have the permission for Deltaview Recreation";
	private static final String DV_LOGS_VERIFICATION = "Verify Script and Execution Logs";
	private static final String DV_SCRIPTEXECUTION_VERIFICATION = "Verify Script Executed every 15 min";
	private static final String DV_ACCESS_VERIFICATION_LOG = "Verify Access Verification Log Flow";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private VerifyDeltaViewCreationFlow dvFlows;

	/**
	 * initialize the test case
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-5127_DeltaView Re-Creation Script08
	 */
	 @Test
	 @TestId(id = "EQEV-51257_DeltaView Re-creation script08", title = "Verify whether Delta views have the permission for all users")
	 public void isDeltaViewCreationSuccess() {
		 final TestScenario scenario = scenario(DV_ACCESS_VERIFICATION_LOG)
				 .addFlow(dvFlows.DV_ACCESS_VERIFICATION_FLOW()).build();
		 final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		 runner.start(scenario);
	 }
/*
	*//**
	 * EQEV-5127_DeltaView Re-Creation Script12
	 *//*
	@TestSuite
	@Test(testName = "EQEV-51257_DeltaView Re-creation script12")
	@TestId(id = "EQEV-51257_DeltaView Re-creation script12", title = "Verify whether user is able to run the script manually from the console.")
	public void isDeltaviewAccessible_Recreation() {
		final TestScenario scenario = scenario(DV_DELTA_VIEW_PERMISSION).addFlow(dvFlows.DV_ACCESSIBLE_FLOW()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	*//**
	 * EQEV-5127_DeltaView Re-Creation Script15
	 *//*
	@TestSuite
	@Test(testName = "EQEV-51257_DeltaView Re-creation script15")
	@TestId(id = "EQEV-51257_DeltaView Re-creation script15", title = "Verify whether the Proper logs are getting generated in case of error and non error scenario.")
	public void isLogsValid() {
		final TestScenario scenario = scenario(DV_LOGS_VERIFICATION).addFlow(dvFlows.DV_LOGS_VERIICATION()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}*/

	/**
	 * EQEV-5127_DeltaView Re-Creation Script05
	 */
	@Test
	@TestId(id = "EQEV-51257_DeltaView Re-creation script05", title = "Verify whether the delta views are created for the delta supported table.")
	public void isDeltaviewAccessible() {
		final TestScenario scenario = scenario(DV_DELTA_VIEW_PERMISSION).addFlow(dvFlows.DV_ACCESSIBLE_FLOW()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-5127_DeltaView Re-Creation Script10
	 */
	 @Test
	 @TestId(id = "EQEV-51257_DeltaView Re-creation script10", title = "Verify whether the script is executed every 5th Min of hour.")
	 public void isScriptExecutedSucessfully() {
		 final TestScenario scenario = scenario(DV_SCRIPTEXECUTION_VERIFICATION)
				 .addFlow(dvFlows.DV_SCRIPT_EXECUTION_FLOW()).build();
		 final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		 runner.start(scenario);
	 }
}