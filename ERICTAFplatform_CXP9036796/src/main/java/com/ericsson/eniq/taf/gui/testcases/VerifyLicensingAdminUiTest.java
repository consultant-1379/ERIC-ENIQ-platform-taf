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
public class VerifyLicensingAdminUiTest extends TafTestBase {

	public static final String ADMIN_UI_LICENSE = "LicenseAdminUIScenario";
	public static final String BEFORE = "AdminUILoginScenario";
	public static final String AFTER = "AdminUILoginScenario";

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

	/*
	 * EQEV-50686_Lic_Mgr_03
	 */
	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_03", title = "Verify the licenseAdminUi")
	public void verifyLicenseAdminUI() {

		final TestScenario scenario = scenario(ADMIN_UI_LICENSE).addFlow(wepAppTestFlow.licenseAdminUiFlow()).build();
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
