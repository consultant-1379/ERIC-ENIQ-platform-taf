package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

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
// @Test(enabled = false)
public class LicenseServerManagerGreenColor extends TafTestBase {

	public static final String ADMIN_UI_LICENSE_COLOR = "LicenseServerManagerGreenColorScenario";
	public static final String BEFORE = "basicTest";

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

	@Test
	@TestId(id = "License Server Manager Green Color", title = "LicenseServerManagerGreenColor")
	public void serverGreenColor() {
		final TestScenario scenario = scenario(ADMIN_UI_LICENSE_COLOR)
				.addFlow(wepAppTestFlow.isLicenseServerManagerGreenColor()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
