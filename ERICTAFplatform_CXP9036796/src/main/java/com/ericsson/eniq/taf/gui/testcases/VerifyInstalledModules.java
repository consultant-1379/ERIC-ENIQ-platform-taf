package com.ericsson.eniq.taf.gui.testcases;

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
import com.ericsson.eniq.taf.installation.test.flows.InstalledModules01_Flow;
import com.ericsson.eniq.taf.installation.test.flows.InstalledModules02_Flow;

/**
 * Verify Installed modules
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class VerifyInstalledModules extends TorTestCaseHelper {

	public static final String INSTALLED_MODULES_SCENARIO_01 = "Installed_modules_scenario_01";
	public static final String INSTALLED_MODULES_SCENARIO_02 = "Installed_modules_scenario_02";
	public static final String BEFORE = "basicTest";

	@Inject
	private InstalledModules01_Flow flow01;
	@Inject
	private InstalledModules02_Flow flow02;

	@Inject
	AdminUI_Flows wepAppTestFlow;

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
	 * InstalledModules01
	 */
	@Test
	@TestId(id = "InstalledModules01", title = "Verify installed modules")
	public void verifyInstalledModules01() {
		final TestScenario scenario = scenario(INSTALLED_MODULES_SCENARIO_01).addFlow(flow01.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * InstalledModules02
	 */
	@Test
	@TestId(id = "InstalledModules02", title = "Verify duplicate modules in installed modules")
	public void verifyInstalledModules02() {
		final TestScenario scenario = scenario(INSTALLED_MODULES_SCENARIO_02).addFlow(flow02.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}