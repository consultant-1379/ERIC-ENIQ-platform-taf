package com.ericsson.eniq.taf.installation.test.cases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.epfgFlow;

/**
 * Close Firefox browser from CLI
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class CloseFirefoxProcess extends TorTestCaseHelper {

	public static final String EPFG_SCENARIO = "EPFG_File_Generator";

	@Inject
	private epfgFlow epfgFlow;


	/**
	 * MWS Path Update
	 */
	//@TestSuite
	@Test
	@TestId(id = "CloseFirefoxBrowserProcessFromCLI", title = "Close Firefox Browser Process From CLI")
	public void epfgToolGen() {
		final TestScenario scenario = scenario(EPFG_SCENARIO).addFlow(epfgFlow.closeFirefoxBrowserFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario); 
	}
}