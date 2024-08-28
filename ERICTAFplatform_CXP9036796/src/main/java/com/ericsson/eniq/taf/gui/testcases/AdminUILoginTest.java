package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TafTestBase;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;

/**
 * Created by xarunha
 */
// @Test(enabled = false)
public class AdminUILoginTest extends TafTestBase {

	public static final String BEFORE = "basicTest";
	public static final String ADMIN_UI_LOGIN = "AdminUILoginScenario";
	public static final String ADMIN_UI_LINKS = "AdminUIlinksScenario";
	public static final String AFTER = "AdminUILoginScenario";
	@Inject
	AdminUI_Flows wepAppTestFlow;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {

		final TestScenario scenario = scenario(ADMIN_UI_LOGIN).addFlow(wepAppTestFlow.basicTest())
				.addFlow(wepAppTestFlow.openBrowser()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

/*	@Test
	@TestId(id = "Adminui_1", title = "Launching of admiui and login as eniq")
	public void verifyAdminUILogin() {

		final TestScenario scenario = scenario(ADMIN_UI_LOGIN).addFlow(wepAppTestFlow.adminUiLogin()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}*/

	/**
	 * 
	 */
	@TestSuite
	@Test
	@TestId(id = "Adminui_11", title = "Verification of drop down / check box in adminui links")
	public void verifyAdminUIlinks() {
		final TestScenario scenario = dataDrivenScenario(ADMIN_UI_LINKS).addFlow(wepAppTestFlow.verifyadminUiLinks())
				.withScenarioDataSources(dataSource("adminuiLinks")).build();
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
