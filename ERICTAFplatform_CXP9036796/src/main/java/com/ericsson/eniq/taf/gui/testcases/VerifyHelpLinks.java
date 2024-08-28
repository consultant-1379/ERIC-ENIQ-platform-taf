package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.VerifyHelpLinksFlow;

/**
 * Verify whether all the references related to WIfi is removed from Admin UI
 * 
 * @author ZJSOLEA
 *
 */
// @Test(enabled = false)
public class VerifyHelpLinks extends TorTestCaseHelper {

	public static final String BEFORE = "OpenBrowserScenario";
	public static final String VERIFY_HELP_LINKS_SCENARIO = "VerifyHelpLinksScenario";
	public static final String AFTER = "CLoseBrowserScenario";

	@Inject
	private VerifyHelpLinksFlow flow;

	@Inject
	AdminUI_Flows wepAppTestFlow;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).addFlow(flow.openBrowser())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * Verify_help_links
	 */
	@TestSuite
	@Test
	@TestId(id = "Verify_help_links", title = "Verify help links")
	public void verify_help_links() {
		//wepAppTestFlow.basicTest();
		final TestScenario scenario = dataDrivenScenario(VERIFY_HELP_LINKS_SCENARIO).addFlow(flow.verification())
				.withScenarioDataSources(dataSource("helplinks")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * initialize
	 */
	@AfterTest
	public void closeAll() {
		final TestScenario scenario = scenario(AFTER).addFlow(flow.closeBrowser()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}