package com.ericsson.eniq.taf.installation.test.cases.bugs;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.WebserverRestartFlow;

/**
 * Bug jira for EQEV-65253 - Webserver Restart on latest runtime module
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class WebserverRestartTest extends TorTestCaseHelper {

	public static final String EQEV_65253_Bug_Jira_Test_Scenario = "EQEV_65253_Bug_Jira_Test_Scenario";

	@Inject
	private WebserverRestartFlow flow;

	/**
	 * EQEV-65253_Bug_Jira
	 */
	@Test
	@TestId(id = "EQEV-65253", title = "Webserver Restart on latest runtime module")
	public void verify_EQEV_68105_Negative_TC05() {
		final TestScenario scenario = scenario(EQEV_65253_Bug_Jira_Test_Scenario).addFlow(flow.webserverRestartFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
