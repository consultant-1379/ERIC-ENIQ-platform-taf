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
import com.ericsson.eniq.taf.installation.test.flows.VerifyPortInstallUpgradeScriptsFlow;

/**
 * Test Campaign for EQEV-50436-Port install/upgrade scripts to RHEL OS
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class VerifyPortInstallUpgradeScriptsTest extends TorTestCaseHelper {

	public static final String INSTALL_SCRIPT_SCENARIO = "Install Script Verification Scenario";
	public static final String LOG_FLOW_SCENARIO = "Log Verification Scenario";
	public static final String VERSIONDB_FLOW_SCENARIO = "Versiondb Update Scenario";
	public static final String MODULES_EXTRACTED_FLOW_SCENARIO = "PF Modules extraction Scenario";
	public static final String SPECIFIC_LOG_SCENARIO = "Log Verification for assureddc pkg Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private VerifyPortInstallUpgradeScriptsFlow portInstallUpgradeScript;

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
	 * EQEV-50436-Port Install Scripts_01
	 */
	/*
	 * 
	 * @TestSuite
	 * 
	 * @Test(testName = "EQEV-50436-Port Install Scripts_01")
	 * 
	 * @TestId(id = "EQEV-50436-Port Install Scripts_01", title =
	 * "Verify the initial installation of all platform modules through install_eniq Script"
	 * ) public void verifyInstallEniqScript() { final TestScenario scenario =
	 * scenario(INSTALL_SCRIPT_SCENARIO)
	 * .addFlow(portInstallUpgradeScript.isInstallScriptExistsInMws()).build();
	 * final TestScenarioRunner runner = runner().withListener(new
	 * LoggingScenarioListener()).build();
	 * 
	 * runner.start(scenario); }
	 * 
	 */

	/**
	 * EQEV-50436-Port Install Scripts_02
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50436-Port Install Scripts_02", title = "Verify the installation logs of platform modules")
	public void isEngineLogVerificationSuccessful() {
		final TestScenario scenario = dataDrivenScenario(LOG_FLOW_SCENARIO)
				.addFlow(portInstallUpgradeScript.platformLogFlow())
				.withScenarioDataSources(dataSource("all_platform_modules1")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50436-Port Install Scripts_03
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50436-Port Install Scripts_03", title = "Verify the each platform module version is updated in versiondb.properties file")
	public void isVersionDBPropertiesUpdated() {
		final TestScenario scenario = dataDrivenScenario(VERSIONDB_FLOW_SCENARIO)
				.addFlow(portInstallUpgradeScript.platformVersionDbPropetiesFlow())
				.withScenarioDataSources(dataSource("all_platform_modules2")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50436-Port Install Scripts_04
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50436-Port Install Scripts_04", title = "Verify the each platform module package is extracted under /eniq/sw/platform")
	public void isAllModulesExtacted() {
		final TestScenario scenario = dataDrivenScenario(MODULES_EXTRACTED_FLOW_SCENARIO)
				.addFlow(portInstallUpgradeScript.platformModulesExtractedFlow())
				.withScenarioDataSources(dataSource("all_platform_modules3")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50436_EQEV-54317-Port Install Scripts_05
	 */
	@Test
	@TestId(id = "EQEV-50436_EQEV-54317-Port Install Scripts_05", title = "Verify assureddc package is not getting installed during installation/upgarde on ES server")
	public void isAssureddcPkgIsInstalled() {
		final TestScenario scenario = scenario(SPECIFIC_LOG_SCENARIO)
				.addFlow(portInstallUpgradeScript.assureddcIsExistsFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}