package com.ericsson.eniq.taf.installation.test.cases.platform;

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
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.VerifyLoadingCountersFlow;

/**
 * Test Campaign for loading - 16353
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class VerifyLoadingCounters extends TorTestCaseHelper {

	public static final String LOADING_SCENARIO = "LoadingScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyLoadingCountersFlow loadingFlow;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-50683_Loading_02
	 */
//	@Test
//	@TestId(id = "EQEV-50683_Loading_02_Step_13", title = "Verify that all counter values are loaded tables from PM file")
//	public void pmFilesLoading() {
//		final TestScenario scenario = scenario(LOADING_SCENARIO).addFlow(loadingFlow.pmFilesLoadingVerication())
//				.build();
//		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
//
//		runner.start(scenario);
//	}
}