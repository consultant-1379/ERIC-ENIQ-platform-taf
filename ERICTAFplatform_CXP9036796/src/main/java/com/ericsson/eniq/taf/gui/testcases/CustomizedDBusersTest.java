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
import com.ericsson.eniq.taf.installation.test.flows.GUI_Flows;

/**
 * Created by xarunha
 */
// @Test(enabled = false)
public class CustomizedDBusersTest extends TafTestBase {

	public static final String BEFORE = "basicTest";
	public static final String ADMIN_UI = "CustomizedDBusersInAdminUIScenario";
	public static final String ADMIN_UI_CREATE_DB = "CreateCustomizedDBusersScenario";
	public static final String ADMIN_UI_LINKS = "AdminUIlinksScenario";
	public static final String AFTER = "AdminUILoginScenario";
	@Inject
	GUI_Flows wepAppTestFlow;

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

	/**
	 * EQEV-69681_TC01
	 */
	@Test
	@TestId(id = "EQEV-69681_TC01", title = "Verify that Database User section is showing on AdminUI System Status Home Page")
	public void isCustomizedDBuserDisplayed() {
		final TestScenario scenario = scenario(ADMIN_UI).addFlow(wepAppTestFlow.customizedDBuserDisplayedFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);

	}

	/**
	 * EQEV-69681_TC02
	 */
	@Test
	@TestId(id = "EQEV-69681_TC02", title = "Verify that Database User section is showing on AdminUI System Status Home Page")
	public void CreateCustomizedDBuserTest() {
		final TestScenario scenario = scenario(ADMIN_UI_CREATE_DB).addFlow(wepAppTestFlow.createcustomizedDBuserFlow()).build();
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
