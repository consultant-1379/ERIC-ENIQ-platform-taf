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
import com.ericsson.eniq.taf.installation.test.flows.VerifyEngineInstallationFlow;
import com.ericsson.eniq.taf.installation.test.flows.VerifyPlatformInstallationFlow;

/**
 * Test Campaign for Port OS specific impacts in Engine to RHEL OS - 15968
 * 
 * @author zvaddee
 *
 */
@Test(enabled = false)
public class VerifyEngineInstallationTest extends TorTestCaseHelper {

	public static final String LOG_FLOW_SCENARIO = "Engine Log_Scenario";
	public static final String VERSIONDB_FLOW_SCENARIO = "Platform_Version_dbproperties_Scenario";
	public static final String MODULES_EXTRACTED_FLOW_SCENARIO = "Platform_Modules_extracted_Scenario";
	public static final String PROFILE_SCENARIO = "Engine_Profile_Scenario";
	public static final String ENGINE_SERVICE_FLOW_SCENARIO = "Engine_Service_Scenario";
	public static final String ENGIN_LICENSE_ESCENARIO = "Engine_License_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyPlatformInstallationFlow pltformInstallationFlows;

	@Inject
	private VerifyEngineInstallationFlow engine;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
/*
	
	*//**
		 * EQEV-50502_engine_01
		 *//*
	@TestSuite
	@Test
	@TestId(id = "EQEV-50502_engine_01", title = "Verify engine package installation by performing manually,Initial Install and Upgrade as well.")
	public void isEngineLogVerificationSuccessful() {
		final TestScenario scenario = dataDrivenScenario(LOG_FLOW_SCENARIO)
				.addFlow(pltformInstallationFlows.platformLogFlow())
				.withScenarioDataSources(dataSource("engine_platform_modules")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	*//**
	 * EQEV-50442_platform_Module_03
	 *//*
	@TestSuite
	@Test
	@TestId(id = "EQEV-50502_engine_03", title = "Verify and compare the installation/Upgrade timings with ES18.2 release.")
	public void isVersionDBPropertiesUpdated() {
		final TestScenario scenario = dataDrivenScenario(VERSIONDB_FLOW_SCENARIO)
				.addFlow(pltformInstallationFlows.platformVersionDBPropetiesFlow())
				.withScenarioDataSources(dataSource("engine_platform_modules")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	*//**
	 * EQEV-50442_platform_Module_04
	 *//*
	@TestSuite
	@Test
	@TestId(id = "EQEV-50502_engine_04", title = "Verify lower R-state engine installation is skipped if already higher version of engine module is already installed on the server.")
	public void isAllModulesExtacted() {
		final TestScenario scenario = dataDrivenScenario(MODULES_EXTRACTED_FLOW_SCENARIO)
				.addFlow(pltformInstallationFlows.platformModulesExtractedFlow())
				.withScenarioDataSources(dataSource("engine_platform_modules")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
*/
	// /**
	// * EQEV-50502_engine_10
	// */
	// @TestSuite
	// @Test(testName = "EQEV-50502_engine_10")
	// @TestId(id = "EQEV-50502_engine_10", title = "Verify the engine profile
	// can be set to Loads / No Loads.")
	// public void isPlatformInstalledSuccessful() {
	// final TestScenario scenario =
	// scenario(PROFILE_SCENARIO).addFlow(engine.engineProfilesFlow())
	// .build();
	// final TestScenarioRunner runner = runner().withListener(new
	// LoggingScenarioListener()).build();
	//
	// runner.start(scenario);
	// }
	//
	// /**
	// * EQEV-50502_engine_12
	// */
	// @TestSuite
	// @Test(testName = " ")
	// @TestId(id = "EQEV-50502_engine_12", title = "Verify the dependency
	// services(svcs -d engine) of engine.")
	// public void isEngineServiceOnline() {
	// final TestScenario scenario =
	// scenario(ENGINE_SERVICE_FLOW_SCENARIO).addFlow(engine.engineService()).build();
	// final TestScenarioRunner runner = runner().withListener(new
	// LoggingScenarioListener()).build();
	//
	// runner.start(scenario);
	// }

	/**
	 * EQEV-50502_engine_13
	 */
	@Test
	 @TestId(id = "EQEV-50502_engine_13", title = "Verify the license check")
	 public void verifyLicenseCheck() {
		 final TestScenario scenario = scenario(ENGIN_LICENSE_ESCENARIO).addFlow(engine.licenseCheck()).build();
		 final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		 runner.start(scenario);
	 }
}