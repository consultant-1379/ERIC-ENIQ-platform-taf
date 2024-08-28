package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.EQEV71745_Loader_Delimitter_Cron_Flow;

/**
 * Verify that loader_delimiter cron entry is removed from dcuser cron
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV71745_Loader_Delimitter_Cron extends TorTestCaseHelper {
	public static final String EQEV71745_Loader_Delimitter_Cron_SCENARIO = "EQEV71745_Loader_Delimitter_Cron";

	@Inject
	private EQEV71745_Loader_Delimitter_Cron_Flow flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {
	}

	/**
	 * EQEV71745_Loader_Delimitter_Cron
	 */
	@Test
	@TestId(id = "EQEV71745_Loader_Delimitter_Cron", title = "Verify that loader_delimiter cron entry is removed from dcuser cron")
	public void verification() {
		final TestScenario scenario = scenario(EQEV71745_Loader_Delimitter_Cron_SCENARIO).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
}