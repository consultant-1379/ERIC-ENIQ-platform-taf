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
import com.ericsson.eniq.taf.installation.test.flows.RunScriptFlow;

public class RunScript extends TorTestCaseHelper {
	public static final String FLOW_SCENARIO = "Running a Script on vApp flow_Scenario";

	@Inject
	private RunScriptFlow runScriptFlows;

	@Test(priority = 0, groups = { "KGB", "CDB" }, description = "Running a Script on vApp flow_Scenario")

	@TestId(id = "1", title = "Command Execution")
	public void isPacakageIsInstalledSuccessful() {
		TestScenario scenario = scenario(FLOW_SCENARIO).addFlow(runScriptFlows.runningScriptFlow()).build();

		TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}