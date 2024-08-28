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
import com.ericsson.eniq.taf.installation.test.flows.VerifyCombinedViewFunctionalityFlow;

/**
 * Test Campaign for Combined view functionality to be ported on Linux - 15924
 * 
 * @author zvaddee
 *
 */

public class CombinedViewFunctionalityTest extends TorTestCaseHelper {

	private static final String CV_INSTALL_SCENARIO = "To Verify the installation of installer";
	private static final String CV_INSTALL_LOG_VERFICATION_SCENARIO = "To Verify the installation"
			+ " of installer logs";
	private static final String CV_LTE_WCDMA_SCRIPTS_SCENARIO = "To Verify the LTE and WCDMA Scripts"
			+ " are present under eniq/sw/installer path.";
	private static final String CV_LTE_LOG_VERIFICATION = "Verify whether both G1 and G2 log Verification and Script Present";
	private static final String CV_LTE_ACCESSIBLE_SCENARIO = "Verify whether both G1 & G2 views of LTE are accessible by all the users";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyCombinedViewFunctionalityFlow cvFlows;

	/**
	 * initialize the test case
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
	/*
		*//**
			 * EQEV-51198_Combined view functionality-1
			 *//*
			 * @TestSuite
			 * 
			 * @Test(testName = "EQEV-51198_Combined view functionality-1")
			 * 
			 * @TestId(id = "EQEV-51198_Combined view functionality-1", title =
			 * "Verify the installation of installer and feature files.") public
			 * void installerInstallationIsSuccess() { final TestScenario
			 * scenario =
			 * scenario(CV_INSTALL_SCENARIO).addFlow(cvFlows.installerFlow()).
			 * build(); final TestScenarioRunner runner =
			 * runner().withListener(new LoggingScenarioListener()).build();
			 * 
			 * runner.start(scenario); }
			 */

	/**
	 * EQEV-51198_Combined view functionality-2
	 */
	@Test
	@TestId(id = "EQEV-51198_Combined view functionality-2", title = "Verify the installation logs of installer.")
	public void isInstallerLogsValid() {
		final TestScenario scenario = scenario(CV_INSTALL_LOG_VERFICATION_SCENARIO).addFlow(cvFlows.installerLogFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51198_Combined view functionality-3
	 */
	@Test
	@TestId(id = "EQEV-51198_Combined view functionality-3", title = "Verify whether Combined view scripts of LTE and WCDMA are under eniq/sw/installer path.")
	public void isLTEAndWCDMAScriptsPresent() {
		final TestScenario scenario = scenario(CV_LTE_WCDMA_SCRIPTS_SCENARIO).addFlow(cvFlows.lteAndWcdmaScriptsFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51198_Combined view functionality-8
	 */
	@Test
	@TestId(id = "EQEV-51198_Combined view functionality-8", title = "Script should execute automatically when LTE and WCDMA feature is Installed / upgraded.")
	public void isLTEAccesibleByAllUsers() {
		final TestScenario scenario = scenario(CV_LTE_ACCESSIBLE_SCENARIO).addFlow(cvFlows.ltewcdmaLogFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/*	*//**
			 * EQEV-51198_Combined view functionality-6
			 *//*
			 * @TestSuite
			 * 
			 * @Test(testName = "EQEV-51198_Combined view functionality-6")
			 * 
			 * @TestId(id = "EQEV-51198_Combined view functionality-6", title =
			 * "Verify whether both G1 & G2 views of LTE are accessible by all the users(dc,dcpublic and dcbo)."
			 * ) public void isG1andG2ScriptsPresent() { final TestScenario
			 * scenario = scenario(CV_LTE_LOG_VERIFICATION).addFlow(cvFlows.
			 * g1AndG2ScriptFlow()).build(); final TestScenarioRunner runner =
			 * runner().withListener(new LoggingScenarioListener()).build();
			 * 
			 * runner.start(scenario); }
			 */
}
