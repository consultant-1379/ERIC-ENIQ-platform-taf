package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.VerifySchedulerCommandsFlow;

/**
 * Test Campaign for Scheduler Functionality
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class VerifySchedulerCommandsTest extends TorTestCaseHelper {

	public static final String SCHEDULER_PKG_SCENARIO = "Scheduler pkg Install Verification Scenario";
	public static final String SCHEDULER_COMMANDS_SCENARIO = "Scheduler Commands Verification Scenario";
	public static final String SCHEDULER_SERVICE_SCENARIO = "Scheduler Service Verification Scenario";
	public static final String SCHEDULER_LOG_SCENARIO = "Scheduler Log Verification Scenario";
	public static final String SCHEDULER_LOG_TP_INTERFACE_SCENARIO = "Scheduler Log Verification for TP and Interface Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	@Inject
	private VerifySchedulerCommandsFlow scheduler;

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
	 * EQEV-51274_scheduler_01
	 */
	@Test
	@TestId(id = "EQEV-51274_scheduler_01", title = "Verify the installation of scheduler package")
	public void isSchedulerPacakgeInstalled() {
		final TestScenario scenario = scenario(SCHEDULER_PKG_SCENARIO).addFlow(scheduler.schedulerInstallationFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/*	*//**
			 * EQEV-51274_scheduler_02
			 *//*
			 * @TestSuite
			 * 
			 * @Test(testName = "EQEV-51274_scheduler_02")
			 * 
			 * @TestId(id = "EQEV-51274_scheduler_02", title =
			 * "Verify scheduler commands are working properly. Start, stop, hold, activate, status, restart"
			 * ) public void isSchedulerCmdsExecuting() { final TestScenario
			 * scenario =
			 * scenario(SCHEDULER_COMMANDS_SCENARIO).addFlow(scheduler.
			 * schedulerCommandsFlow()) .build(); final TestScenarioRunner
			 * runner = runner().withListener(new
			 * LoggingScenarioListener()).build();
			 * 
			 * runner.start(scenario); }
			 */

	/**
	 * EQEV-51274_scheduler_03
	 */
	@Test
	@TestId(id = "EQEV-51274_scheduler_03", title = "Verify whether all the scheduler dependent services are working properly")
	public void isSchedulerServicesWorking() {
		final TestScenario scenario = scenario(SCHEDULER_SERVICE_SCENARIO).addFlow(scheduler.schedulerServicesFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51274_scheduler_module_04
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-51274_scheduler_module_04", title = "Verify the scheduler logs generated under /eniq/log/sw_log/scheduler and also check for exceptions, warnings, errors")
	public void isSchedulerLogsGenerated() {
		final TestScenario scenario = dataDrivenScenario(SCHEDULER_LOG_SCENARIO).addFlow(scheduler.schedulerLogsFlow())
				.withScenarioDataSources(dataSource("scheduler_logs")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51274_scheduler_04
	 */
	// @Test
	// @TestId(id = "EQEV-51274_scheduler_04", title = "Verify whether all the scheduler dependent services are working properly")
	// public void isSchedulerLogExistsInTpAndInterface() {
		// final TestScenario scenario = scenario(SCHEDULER_LOG_TP_INTERFACE_SCENARIO)
				// .addFlow(scheduler.schedulerTpAndInterfaceFlow()).build();
		// final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		// runner.start(scenario);
	// }
}