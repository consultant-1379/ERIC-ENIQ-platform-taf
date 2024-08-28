package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.DWH_Operator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.InterfaceActivationOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class DWH_Steps {

	private static Logger LOGGER = LoggerFactory.getLogger(DWH_Steps.class);

	private static final String DWH_DB_QUERY = "pm/dwh_base_functionality.sql";
	private static final String START_DWH_SET = "engine -e startSet DWH_BASE Cleanup_transfer_batches";
	private static final String ENGIN_DWHSET_CLEANUP_DIR_CMD = "engine -e startSet DWH_BASE Cleanup_logdir";
	private static final String scheduler_TEST_FILE = "cd /eniq/log/sw_log/scheduler/;touch -t 201203101513 'TAF_test_file.log'";
	private static final String scheduler_LIST_TEST_FILE = "cd /eniq/log/sw_log/scheduler/;ls -lrt TAF_test_*.log";
	private static final String DWH_BASE_TEST_FILE = "cd /eniq/log/sw_log/engine/DWH_BASE/;touch -t 201203101513 'TAF_test_file.log'";
	private static final String DWH_BASE_LIST_TEST_FILE = "cd /eniq/log/sw_log/engine/DWH_BASE/;ls -lrt TAF_test_*.log";
	private static final String DWH_PARTITION_PATH = "/eniq/log/sw_log/engine/DWH_BASE/";
	private static final String LATEST_ENGINE_LOG_FILE = "cd /eniq/log/sw_log/engine/DWH_BASE/; ls engine-2*.log | tail -1";
	private static final String TAIL = "tail ";
	private static final String CAT = "cat ";
	private static final String GREP = "grep ";
	private static final String PIPE = " | ";

	private static final String DWH_PARTITION = "engine -e startSet DWH_BASE DWHM_Partition_DWH_BASE";
	private static final String DWH_DWHM_StorageTimeUpdate = "engine -e startSet DWH_BASE DWHM_StorageTimeUpdate_DWH_BASE";
	private static final String DWH_BASE_ExecutionProfiler = "engine -e startSet DWH_BASE ExecutionProfiler";
	private static final String ExecutionProfiler_GREP1 = "ExecutionProfiler.SlotRebuilder : Deleting profiles";
	private static final String ExecutionProfiler_GREP2 = "ExecutionProfiler.SlotRebuilder : Adding profiles and slots";
	private static final String ExecutionProfiler_GREP3 = "ExecutionProfiler.SlotRebuilder : Excution Profile successfully updated";
	private static final String DWH_BASE_SchedulerActivate = "engine -e startSet DWH_BASE Scheduler_activate";
	private static final String SchedulerActivate = "Scheduler_activate : Logged successful set execution";
	private static final String SchedulerStatus = "scheduler status";
	private static final String DWH_BASE_TriggerPartitioning = "engine -e startSet DWH_BASE Trigger_Partitioning";
	private static final String TriggerPartitioning1 = "Trigger_Partitioning.0.SetTypeTrigger : Triggering Partition sets";
	private static final String TriggerPartitioning2 = "Partition.Trigger_Partitioning : Logged successful set execution";
	private static final String DWH_BASE_TriggerService = "engine -e startSet DWH_BASE Trigger_Service";
	private static final String Trigger_Service1 = "SetTypeTrigger : 1 Sets Triggered";
	private static final String Trigger_Service2 = "Diskmanager_DWH_BASE : Logged successful set execution";
	private static final String DWH_BASE_UpdateDates = "engine -e startSet DWH_BASE Trigger_Service";
	private static final String UpdateDates = "DWH_BASE.Support.Update_Dates : Logged successful set execution";

	@Inject
	private Provider<DWH_Operator> provider;

	@Inject
	private Provider<InterfaceActivationOperator> cmd_provider;
	
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @DESCRIPTION This Test Case covers the verification of
	 *              cleanup_transer_batches functionality. on RHEL environments
	 * @PRE cleanup_transer_batches functionality
	 */

	@TestStep(id = StepIds.VERIFY_DWH)
	public void verifyVersionDBPropetiesUpdated() {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();

		final String dwhSetOutput = cmdLineOpertor.dwhSetExecuteCommandInDcuser(START_DWH_SET);
		if (dwhSetOutput.contains("Starting set Cleanup_transfer_batches")
				&& dwhSetOutput.contains("Start set requested successfully")) {
			final DWH_Operator dwhOperator = provider.get();
			final List<String> dboutput = dwhOperator.executeDBQuery(DWH_DB_QUERY);
			assertTrue(dboutput.isEmpty(), "Faield due to : Data is not deleted : " + dboutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + START_DWH_SET + "\n" + dwhSetOutput);
		}
	}

	@TestStep(id = StepIds.VERIFY_DWH_SET)
	public void verifyDWHset_Cleanup_logdir() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		// Create test file with older date
		cmdLineOpertor.dwhSetEcecuteCommandInRoot(scheduler_TEST_FILE);
		final String listTestFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(scheduler_LIST_TEST_FILE);
		if (listTestFile.contains("TAF_test_")) {
			cmdLineOpertor.dwhSetExecuteCommandInDcuser(ENGIN_DWHSET_CLEANUP_DIR_CMD);
			TimeUnit.SECONDS.sleep(60);
			final String finalOutput = cmdLineOpertor.dwhSetExecuteCommandInDcuser(scheduler_LIST_TEST_FILE);
			assertTrue(finalOutput.contains("TAF_test_*.log: No such file or directory"), finalOutput);
		}
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_DISK_MANAGER)
	public void verifyDWH_BASE_DiskManager() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		
		/* DB and service checks to check and start engine and dbs. */
		GeneralOperator operator = generalOperatorProvider.get();
		
		if (operator.executeCommandDcuser("scheduler status").contains("not running"))
			operator.executeCommandDcuser("scheduler start");
		if (operator.executeCommandDcuser("repdb status").contains("is not running"))
			operator.executeCommandDcuser("repdb start");
		if (operator.executeCommandDcuser("dwhdb status").contains("not running"))
			operator.executeCommandDcuser("dwhdb start");
		if (operator.executeCommandDcuser("engine status").contains("not running"))
			operator.executeCommandDcuser("engine start");
		
		/* the above block can be removed if not needed.*/
		
		// Create test file with older date
		cmdLineOpertor.dwhSetEcecuteCommandInRoot(DWH_BASE_TEST_FILE);
		final String listTestFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_LIST_TEST_FILE);
		if (listTestFile.contains("TAF_test_")) {
			cmdLineOpertor.dwhSetExecuteCommandInDcuser(ENGIN_DWHSET_CLEANUP_DIR_CMD);
			TimeUnit.SECONDS.sleep(180);
			final String finalOutput = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_LIST_TEST_FILE);
			assertTrue(finalOutput.contains("TAF_test_*.log: No such file or directory"), finalOutput);
		}
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_PARTITION)
	public void verifyDWH_BASE_Partition() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		// Create test file with older date
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_PARTITION);
		if (output.contains("Start set requested successfully")) {
			final String latestEngineLogFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(LATEST_ENGINE_LOG_FILE);
			TimeUnit.SECONDS.sleep(10);
			final String finalOutput = cmdLineOpertor
					.dwhSetExecuteCommandInDcuser(TAIL + DWH_PARTITION_PATH + latestEngineLogFile);
			assertTrue(finalOutput.contains("Logged successful set execution"),
					"Expected :'Logged successful set execution' but found \n " + finalOutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + DWH_PARTITION + "\n" + output);
		}
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_UPDATE)
	public void verifyDWH_BASE_update() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_DWHM_StorageTimeUpdate);
		assertTrue(output.contains("succeeded"), "\nExpected String is : 'succeeded' but found \n" + output);
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_EXECUTION_PROFILER)
	public void verifyDWH_BASE_ExecutionProfiler() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_ExecutionProfiler);
		if (output.contains("Start set requested successfully")) {
			final String latestEngineLogFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(LATEST_ENGINE_LOG_FILE);
			TimeUnit.SECONDS.sleep(5);

			final StringBuilder allCommandOuput = new StringBuilder();
			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(CAT + DWH_PARTITION_PATH
					+ latestEngineLogFile + PIPE + GREP + "'" + ExecutionProfiler_GREP1 + "'"));

			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(CAT + DWH_PARTITION_PATH
					+ latestEngineLogFile + PIPE + GREP + "'" + ExecutionProfiler_GREP2 + "'"));
			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(CAT + DWH_PARTITION_PATH
					+ latestEngineLogFile + PIPE + GREP + " -i " + "'" + ExecutionProfiler_GREP3 + "'"));

			final String finalOutput = allCommandOuput.toString();

			assertTrue(
					finalOutput.contains(ExecutionProfiler_GREP1) && finalOutput.contains(ExecutionProfiler_GREP2)
							&& finalOutput.contains(ExecutionProfiler_GREP3),
					"\nGREP String Not found : below are list of commands executed \n" + CAT + DWH_PARTITION_PATH
							+ latestEngineLogFile + PIPE + GREP + "'" + ExecutionProfiler_GREP1 + "' \n " + CAT
							+ DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + ExecutionProfiler_GREP2
							+ "' \n " + CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + " -i " + "'"
							+ ExecutionProfiler_GREP3 + "' \n " + finalOutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + DWH_BASE_ExecutionProfiler + "\n" + output);
		}
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER)
	public void verifyDWH_BASE_SchedulerActivateAndTrigger() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_SchedulerActivate);
		assertTrue(output.contains("Start set requested successfully"),
				"Expected :\n" + "Start set requested successfully" + " but found : \n" + output);
		
/*		
		if (output.contains("Start set requested successfully")) {
			final String latestEngineLogFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(LATEST_ENGINE_LOG_FILE);
			TimeUnit.MINUTES.sleep(100);
			final String finalOutput = cmdLineOpertor.dwhSetExecuteCommandInDcuser(
					CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + SchedulerActivate + "'");
			assertTrue(finalOutput.contains(SchedulerActivate),
					"Expected :\n" + SchedulerActivate + " but found : \n" + finalOutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + DWH_BASE_SchedulerActivate + "\n" + output);
		}*/
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_STATUS)
	public void verifyDWH_BASE_SchedulerActivateAndTriggerStatus() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String finalOutput = cmdLineOpertor.dwhSetExecuteCommandInDcuser(SchedulerStatus);
		assertTrue(finalOutput.contains("Status: active"), "Expected 'Status: active' but found :\n" + finalOutput);
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_TRIGGER_PARTITIONING)
	public void verifyDWH_BASE_TriggerPartitioning() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_TriggerPartitioning);
		assertTrue(output.contains("Start set requested successfully"),"Expected 'Start set requested successfully' but found :" + output);
		
		
	/*	
		if (output.contains("Start set requested successfully")) {
			final String latestEngineLogFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(LATEST_ENGINE_LOG_FILE);
			TimeUnit.SECONDS.sleep(5);

			final StringBuilder allCommandOuput = new StringBuilder();
			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(
					CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + TriggerPartitioning1 + "'"));

			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(
					CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + TriggerPartitioning2 + "'"));

			final String finalOutput = allCommandOuput.toString();

			assertTrue(finalOutput.contains(TriggerPartitioning1) && finalOutput.contains(TriggerPartitioning2),
					"\nGREP String Not found : below are list of commands executed \n" + CAT + DWH_PARTITION_PATH
							+ latestEngineLogFile + PIPE + GREP + "'" + TriggerPartitioning1 + "'" + " \n" + CAT
							+ DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + TriggerPartitioning2 + "'"
							+ " \n" + finalOutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + DWH_BASE_TriggerPartitioning + "\n" + output);
		}*/
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_TRIGGER_SERVICE)
	public void verifyDWH_BASE_TriggerService() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_TriggerService);
	
		assertTrue(output.contains("Start set requested successfully"),"Expected 'Start set requested successfully' but found : " + output );
		
	/*	if (output.contains("Start set requested successfully")) {
			final String latestEngineLogFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(LATEST_ENGINE_LOG_FILE);
			TimeUnit.SECONDS.sleep(5);

			final StringBuilder allCommandOuput = new StringBuilder();
			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(
					CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + Trigger_Service1 + "'"));

			allCommandOuput.append(cmdLineOpertor.dwhSetExecuteCommandInDcuser(
					CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + Trigger_Service2 + "'"));

			final String finalOutput = allCommandOuput.toString();

			assertTrue(finalOutput.contains(Trigger_Service1) && finalOutput.contains(Trigger_Service2),
					"\nGREP String Not found : below are list of commands executed \n" + CAT + DWH_PARTITION_PATH
							+ latestEngineLogFile + PIPE + GREP + "'" + Trigger_Service1 + "'" + " \n" + CAT
							+ DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + "'" + Trigger_Service2 + "'"
							+ " \n" + finalOutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + DWH_BASE_TriggerService + "\n" + output);
		}*/
	}

	@TestStep(id = StepIds.VERIFY_DWH_BASE_UPDATE_DATES)
	public void verifyDWH_BASE_updateDates() throws InterruptedException {
		final InterfaceActivationOperator cmdLineOpertor = cmd_provider.get();
		final String output = cmdLineOpertor.dwhSetExecuteCommandInDcuser(DWH_BASE_UpdateDates);
		if (output.contains("Start set requested successfully")) {
			final String latestEngineLogFile = cmdLineOpertor.dwhSetExecuteCommandInDcuser(LATEST_ENGINE_LOG_FILE);
			TimeUnit.SECONDS.sleep(10);
			final String finalOutput = cmdLineOpertor.dwhSetExecuteCommandInDcuser(
					CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + " '" + UpdateDates + "'");
			assertTrue(finalOutput.contains(UpdateDates),
					"\nCommand Executed : " + CAT + DWH_PARTITION_PATH + latestEngineLogFile + PIPE + GREP + " '"
							+ UpdateDates + "' \n" + "Expected : '" + UpdateDates + "' but found \n" + finalOutput);
		} else {
			assertTrue(false, "\nCommand excution failed : " + DWH_BASE_UpdateDates + "\n" + output);
		}
	}

	public static class StepIds {
		public static final String VERIFY_DWH = "Verify cleanup_transer_batches funcationality.";
		public static final String VERIFY_DWH_SET = "Verify dwh_base set funcationality";
		public static final String VERIFY_DWH_BASE_DISK_MANAGER = "Verify Disk Manager _DWH_BASE funcationality";
		public static final String VERIFY_DWH_BASE_PARTITION = "Verify DWH_partition_DWH_BASE set";
		public static final String VERIFY_DWH_BASE_UPDATE = "DWH_Storage time update_DWH_bASE functionality verification";
		public static final String VERIFY_DWH_BASE_EXECUTION_PROFILER = "Execution profiler Verification";
		public static final String VERIFY_DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER = "Scheduler_activate and Trigger partition";
		public static final String VERIFY_DWH_BASE_SCHEDULER_ACTIVATE_AND_TRIGGER_STATUS = "Scheduler_activate and Trigger partition status";
		public static final String VERIFY_DWH_BASE_TRIGGER_PARTITIONING = "Verify DWH_BASE Trigger_Partitioning";
		public static final String VERIFY_DWH_BASE_TRIGGER_SERVICE = "Verify DWH_BASE Trigger_Service";
		public static final String VERIFY_DWH_BASE_UPDATE_DATES = "update_dates Verification";

		private StepIds() {
		}
	}
}
