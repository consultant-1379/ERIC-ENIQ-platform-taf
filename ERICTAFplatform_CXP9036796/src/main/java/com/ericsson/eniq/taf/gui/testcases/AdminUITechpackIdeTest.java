package com.ericsson.eniq.taf.gui.testcases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TafTestBase;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.TeckPackIdeFlow;
import com.ericsson.eniq.taf.installation.test.steps.AdminUITechpacIdeSteps;

/**
 * Created by xarunha
 */
// @Test(enabled = false)
public class AdminUITechpackIdeTest extends TafTestBase {

	public static final String TECHPACK_ID_LOGIN = "launching of Techpackid Scenario";

	@Inject
	TeckPackIdeFlow techpacIdeFlow;
	
	@Inject
	AdminUITechpacIdeSteps adminUITechpacIdeStep;

/*	*//**
	 * Techpackide_1
	 *//*
	@Test
	public void verifyAdminUITechPackJnlpFileDownload() {

		TestScenario scenario = scenario(TECHPACK_ID_LOGIN)
				.addFlow(flow(TECHPACK_ID_LOGIN)
						.addTestStep(annotatedMethod(adminUITechpacIdeStep, AdminUITechpacIdeSteps.OPEN_BROWSER))
						.addTestStep(annotatedMethod(adminUITechpacIdeStep, AdminUITechpacIdeSteps.LOGIN))
						.addTestStep(annotatedMethod(adminUITechpacIdeStep,
								AdminUITechpacIdeSteps.EXECUTE_ADMINUI_TECKPACK_IDE_LOGIN))
						.addTestStep(annotatedMethod(adminUITechpacIdeStep, AdminUITechpacIdeSteps.LOGOUT))
						.addTestStep(annotatedMethod(adminUITechpacIdeStep, AdminUITechpacIdeSteps.CLOSE_BROWSER)))
				.build();
		runner().build().start(scenario);
	}*/
	
	
	/**
	 * Techpackide_1
	 */
	//@TestSuite
	@Test(testName = "Techpackide_1")
	//@TestId(id = "Techpackide_1", title = "launching of Techpackide")
	public void verifyTechPackJnlpFileDownloadVaiCli() {
		final TestScenario scenario = scenario(TECHPACK_ID_LOGIN)
				.addFlow(techpacIdeFlow.jnlpFileDownloadFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
