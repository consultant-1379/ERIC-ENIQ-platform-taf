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
import com.ericsson.eniq.taf.installation.test.flows.SessionLogFlow;

/**
 * Test campaign for Session Log functionality - 16467
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class SessionLogFunctionality extends TorTestCaseHelper {

	public static final String SESSION_LOG_STARTER_SCENARIO = "SessionLoader_StarterSetScenario";
	public static final String SESSION_LOG_ADAPTER_SCENARIO = "SessionLoader_AdapterSetScenario";
	public static final String SESSION_LOG_LOADER_SCENARIO = "SessionLoader_LoaderSetScenario";
	public static final String SESSION_LOG_AGGREGATOR_SCENARIO = "SessionLoader_AggregatorSetScenario";
	public static final String SESSION_LOG_UPDATE_MONITORING_SCENARIO = "SessionLoader_UpdateMonitoringScenario";
	public static final String SESSION_LOG_AGGREGATE_SCENARIO = "SessionLoader_AggregateSetScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private SessionLogFlow sessionFlow;

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
	 * EQEV_51278_session_log_1
	 */
	@Test
	@TestId(id = "EQEV_51278_session_log_1", title = "SessionLoader_StarterSet")
	public void sessionLogLoader_StarterSet() {
		final TestScenario scenario = scenario(SESSION_LOG_STARTER_SCENARIO)
				.addFlow(sessionFlow.sessionLogStarterFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV_51278_session_log_2
	 */
	@Test
	@TestId(id = "EQEV_51278_session_log_2", title = "SessionLoader_AdapterSet")
	public void sessionLogLoader_AdapterSet() {
		final TestScenario scenario = scenario(SESSION_LOG_ADAPTER_SCENARIO)
				.addFlow(sessionFlow.sessionLogAdapterFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV_51278_session_log_3
	 */
	@Test(testName = "EQEV_51278_session_log_3")
	@TestId(id = "EQEV_51278_session_log_3", title = "SessionLoader_LoaderSet")
	public void sessionLogLoader_LoaderSet() {
		final TestScenario scenario = scenario(SESSION_LOG_LOADER_SCENARIO).addFlow(sessionFlow.sessionLogLoaderFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV_51278_session_log_4
	 */
	@Test
	@TestId(id = "EQEV_51278_session_log_4", title = "SessionLoader_AggregatorSet")
	public void sessionLogLoader_AggregatorSet() {
		final TestScenario scenario = scenario(SESSION_LOG_AGGREGATOR_SCENARIO)
				.addFlow(sessionFlow.sessionLogAggregatorFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV_51278_session_log_5
	 */
	@Test
	@TestId(id = "EQEV_51278_session_log_5", title = "SessionLoader_UpdateMonitoringSet")
	public void sessionLogLoader_UpdateMonitoringSet() {
		final TestScenario scenario = scenario(SESSION_LOG_UPDATE_MONITORING_SCENARIO)
				.addFlow(sessionFlow.sessionLogUpdateMonitoringFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
	/*
		*//**
			 * EQEV_51278_session_log_6
			 *//*
			 * @Test(testName = "EQEV_51278_session_log_6")
			 * 
			 * @TestId(id = "EQEV_51278_session_log_6", title =
			 * "SessionLoader_AggregateSet") public void
			 * sessionLogLoader_AggregateSet() { final TestScenario scenario =
			 * scenario(SESSION_LOG_AGGREGATE_SCENARIO)
			 * .addFlow(sessionFlow.sessionLogAggregateFlow()).build(); final
			 * TestScenarioRunner runner = runner().withListener(new
			 * LoggingScenarioListener()).build();
			 * 
			 * runner.start(scenario); }
			 */
}