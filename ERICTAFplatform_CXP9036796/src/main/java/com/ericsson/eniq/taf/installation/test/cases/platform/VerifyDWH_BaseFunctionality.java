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
import com.ericsson.eniq.taf.installation.test.flows.DWH_Flow;

/**
 * Test Campaign for DWH_BASE_Functionality - 16099
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class VerifyDWH_BaseFunctionality extends TorTestCaseHelper {

	public static final String DWH_SCENARIO = "DWH_cleanup_transer_batches_Scenario";
	public static final String DWH_SET_SCENARIO = "DWH_setScenario";
	public static final String DWH_BASE_DiskManager_SCENARIO = "DWH_BASE_DiskManager Scenario";
	public static final String DWH_BASE_PARTITION_SCENARIO = "DWH_BASE Partition Scenario";
	public static final String DWH_BASE_UPDATE_SCENARIO = "DWH_BASE Partition Update Scenario";
	public static final String DWH_BASE_EXECUTION_PROFILER_SCENARIO = "DWH_BASE Partition Profiler Scenario";
	public static final String DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_SCENARIO = "DWH_BASE Partition Schedule and active Scenario";
	public static final String DWH_BASE_UPDATE_DATES_SCENARIO = "DWH_BASE Partition Update Dates Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	@Inject
	private DWH_Flow dwhFlow;

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
	 * EQEV-51276_01
	 */
	@Test
	@TestId(id = "EQEV-51276_01", title = "Verify dwh_base set funcationality")
	public void verifyDWH_BASEsetCleanup_logdir() {
		final TestScenario scenario = scenario(DWH_SET_SCENARIO).addFlow(dwhFlow.dwhSetFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_02
	 */
	@Test
	@TestId(id = "EQEV-51276_02", title = "Verify cleanup_transer_batches functionality.")
	public void verifyCleanupTranserBatches() {
		final TestScenario scenario = scenario(DWH_SCENARIO).addFlow(dwhFlow.dwhFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_03
	 */
	@Test
	@TestId(id = "EQEV-51276_03", title = "Verify Disk Manager _DWH_BASE funcationality")
	public void verifyDWH_BASE_Diskmanager_DWH_BASE() {
		final TestScenario scenario = scenario(DWH_BASE_DiskManager_SCENARIO).addFlow(dwhFlow.dwhBaseDiskManagerFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_04
	 */
	@Test
	@TestId(id = "EQEV-51276_04", title = "Verify DWH_partition_DWH_BASE set")
	public void verifyDWH_BASE_partition() {
		final TestScenario scenario = scenario(DWH_BASE_PARTITION_SCENARIO).addFlow(dwhFlow.dwhBasePartitionFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_05
	 */
	@Test
	@TestId(id = "EQEV-51276_05", title = "DWH_Storage time update_DWH_bASE functionality verification")
	public void verifyDWH_BASE_update() {
		final TestScenario scenario = scenario(DWH_BASE_UPDATE_SCENARIO).addFlow(dwhFlow.update_DWH_BaseFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_06
	 */
	@Test
	@TestId(id = "EQEV-51276_06", title = "Execution profiler Verification")
	public void verifyDWH_BASE_ExecutionProfiler() {
		final TestScenario scenario = scenario(DWH_BASE_EXECUTION_PROFILER_SCENARIO)
				.addFlow(dwhFlow.dwhBaseExecutionProfilerFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_07
	 */
	@Test
	@TestId(id = "EQEV-51276_07", title = "Scheduler_activate and Trigger partition")
	public void verifyDWH_BASE_SchedulerActivateAndTrigger() {
		final TestScenario scenario = scenario(DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_SCENARIO)
				.addFlow(dwhFlow.dwhBaseSchedulerActivateAndTriggerFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-51276_08
	 */
	// @Test
	// @TestId(id = "EQEV-51276_08", title = "update_dates Verification")
	// public void verifyDWH_BASE_updateDates() {
		// final TestScenario scenario = scenario(DWH_BASE_UPDATE_DATES_SCENARIO).addFlow(dwhFlow.dwhBaseupdateDatesFlow())
				// .build();
		// final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		// runner.start(scenario);
	// }
}