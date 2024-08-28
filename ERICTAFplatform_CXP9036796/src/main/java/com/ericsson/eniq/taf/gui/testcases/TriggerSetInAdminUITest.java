package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TafTestBase;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;

/**
 * Created by xarunha
 */
public class TriggerSetInAdminUITest extends TafTestBase {

	public static final String TRIGGER_SETS_IN_ADMIN_UI = "TriggerSetInAdminUIScenario";
	public static final String BEFORE = "openBrowser";
	public static final String AFTER = "closeBrowser";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest())
				.addFlow(wepAppTestFlow.openBrowser()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	@Test
	@TestId(id = "EQEV-51257_DeltaView Re-creation script11", title = "Verify whether user is able to trigger the set manually from the AdminUI")
	public void verifyTriggerSetInAdminUI() {
		final TestScenario scenario = scenario(TRIGGER_SETS_IN_ADMIN_UI).addFlow(wepAppTestFlow.triggerSetsInAdminUI())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * initialize
	 */
	@AfterTest
	public void closeAll() {
		final TestScenario scenario = scenario(AFTER).addFlow(wepAppTestFlow.closeBrowser()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
}
