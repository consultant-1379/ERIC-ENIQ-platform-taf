package com.ericsson.eniq.taf.installation.test.cases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.VerifyDBFlows;

public class CustomDataSouurceTest extends TorTestCaseHelper {

	@Inject
	private VerifyDBFlows dbFlows;
	
	@TestSuite
	@Test(groups = { "KGB", "CDB" })
	@TestId(id = "EQEV-1", title = "Post Installer")	
	public void isPlatformInstalledSuccessful() {
		TestScenario scenario = dataDrivenScenario("custom_flow")
				.addFlow(dbFlows.customFlow())
				.withScenarioDataSources(dataSource("custom_datasource"))
				.build();
		TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
