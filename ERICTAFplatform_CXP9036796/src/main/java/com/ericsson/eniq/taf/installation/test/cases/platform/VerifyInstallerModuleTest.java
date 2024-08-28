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
import com.ericsson.eniq.taf.installation.test.flows.VerifyInstallerModuleFlow;

/**
 * Test Campaign for installer Module Verification - 15723
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class VerifyInstallerModuleTest extends TorTestCaseHelper {

	public static final String INSTALLER_LOG_SCENARIO = "InstallerModuleLogScenario";
	public static final String VERSIONDB_SCENARIO = "InstallerModuleVersion_dbproperties_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyInstallerModuleFlow installerFlow;

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
	 * EQEV-50441_Installer Module_02
	 */
	@Test
	@TestId(id = "EQEV-50441_Installer Module_02", title = "Verify the installation logs of Installer module")
	public void isPlatformInstalledSuccessful() {
		final TestScenario scenario = scenario(INSTALLER_LOG_SCENARIO).addFlow(installerFlow.installerLogFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50441_Installer Module_03
	 */
	@Test
	@TestId(id = "EQEV-50441_Installer Module_03", title = "Verify the Installer version is updated in versiondb.properties file")
	public void isVersionDBPropertiesUpdated() {
		final TestScenario scenario = scenario(VERSIONDB_SCENARIO)
				.addFlow(installerFlow.installerVersionDBPropetiesFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}