package com.ericsson.eniq.taf.installation.test.cases;

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
import com.ericsson.eniq.taf.installation.test.flows.VerifyDBFlows;

public class VerifyDBTest extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "PostInstallationflow_Scenario";

	@Inject
	private VerifyDBFlows dbFlows;

	@BeforeTest
	public void initialise() {
		//VerifyDBOperator.initialise();
	}

	@Test(groups = { "KGB", "CDB" })
	@TestId(id = "1", title = "Post Installer")	
	public void isPacakageIsInstalledSuccessful() {
		TestScenario scenario = scenario(FLOW_SCENARIO).addFlow(dbFlows.dbFlow()).build();

		TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}