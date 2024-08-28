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
import com.ericsson.eniq.taf.installation.test.flows.VerifyPlatformInstallationFlow;

/**
 * Test Campaign for ENIQ platform modules Verification - 15724
 * 
 * @author zvaddee
 *
 */
@Test(enabled = false)
public class VerifyPlatformInstallationTest extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "PlatformLog_Scenario";
	public static final String VERSIONDB_FLOW_SCENARIO = "Platform_Version_dbproperties_Scenario";
	public static final String MODULES_EXTRACTED_FLOW_SCENARIO = "Platform_Modules_extracted_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyPlatformInstallationFlow pltformInstallationFlows;

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
	 * EQEV-50442_platform_Module_02
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50442_platform_Module_02", title = "Verify the installation logs of platform modules")
	public void isPlatformInstalledSuccessful() {
		final TestScenario scenario = dataDrivenScenario(FLOW_SCENARIO)
				.addFlow(pltformInstallationFlows.platformLogFlow())
				.withScenarioDataSources(dataSource("platform_modules1")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50442_platform_Module_03
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50442_platform_Module_03", title = "Verify the each platform module version is updated in versiondb.properties file")
	public void isVersionDBPropertiesUpdated() {
		final TestScenario scenario = dataDrivenScenario(VERSIONDB_FLOW_SCENARIO)
				.addFlow(pltformInstallationFlows.platformVersionDBPropetiesFlow())
				.withScenarioDataSources(dataSource("platform_modules2")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50442_platform_Module_04
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50442_platform_Module_04", title = "Verify the each platform module package is extracted under /eniq/sw/platform")
	public void isAllModulesExtacted() {
		final TestScenario scenario = dataDrivenScenario(MODULES_EXTRACTED_FLOW_SCENARIO)
				.addFlow(pltformInstallationFlows.platformModulesExtractedFlow())
				.withScenarioDataSources(dataSource("platform_modules3")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}