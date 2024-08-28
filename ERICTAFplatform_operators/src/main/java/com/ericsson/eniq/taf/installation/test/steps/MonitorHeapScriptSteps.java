package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.MonitorHeapScriptOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class MonitorHeapScriptSteps {
	private static Logger Logger = LoggerFactory.getLogger(MonitorHeapScriptSteps.class);

	private final String GET_RSTATE = "cat /eniq/sw/installer/versiondb.properties | grep -i install";
	private final String GET_HEAP_SCRIPT_VALUE = "cd /eniq/log/sw_log/iq/dwhdb/; cat logscheck.log | tail -2";
	private final String GET_FILE_SYSTEM_SCRIPT = "cd /eniq/admin/bin/; ls -lrt | grep -i FileSystemCheck";
	private final String RUN_FILE_SYSTEM_SCRIPT = "cd /eniq/admin/bin/; chmod 777 FileSystemCheck.bsh; ./FileSystemCheck.bsh";
	private final String CHECK_LOG_FILES = "cd /eniq/log/sw_log/engine/; ls | grep -i FileSystemCheck | tail -1";
	Set<String> logFileData = new HashSet<String>();

	@Inject
	private Provider<MonitorHeapScriptOperator> provider;
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @DESCRIPTION Monitor Heap Script is fetching values
	 * 
	 */

	@TestStep(id = StepIds.VERIFY_HEAP_SCRIPT_EXECUTION)
	public void VerifyMonitorHeapScript() {
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
		
		final MonitorHeapScriptOperator monitorHeapScriptOperator = provider.get();
		String getRstate = monitorHeapScriptOperator.executeCommands(GET_RSTATE);
		String getHeapValue = monitorHeapScriptOperator.executeCommands(GET_HEAP_SCRIPT_VALUE);
		if ((!getRstate.isEmpty() && getRstate.contains("module.installer=R"))) {
			if (getHeapValue.contains("null")) {
				Logger.info("Monitor heap script fetching is null values: " + getHeapValue);
				assertFalse(getHeapValue.contains("null"), "Monitor Heap Script is fetching null values.");
			}
		} else {
			assertFalse((!getRstate.isEmpty() && getRstate.contains("module.installer=R")),
					"No installer present with Rstate");
		}
	}

	@TestStep(id = StepIds.VERIFY_LOG_FILES_LIMIT_EXCEEDS)
	public void VerifyEngineLogFileLimit() {
		final MonitorHeapScriptOperator monitorHeapScriptOperator = provider.get();
		String getFileScript = monitorHeapScriptOperator.executeCommand(GET_FILE_SYSTEM_SCRIPT);
		assertFalse(!getFileScript.contains("FileSystemCheck.bsh"), "Does not contain FileSystemCheck.bsh script");
		String checkLimit = monitorHeapScriptOperator.executeCommands(RUN_FILE_SYSTEM_SCRIPT);
		assertFalse(checkLimit.contains("Connection to engine failed"),
				"Unable to execute FilesystemCheck script due to connection to engine failed");
		assertFalse(!checkLimit.contains("% /eniq/log"), "FileSystemCheck script execution failed");
		assertFalse(checkLimit.contains("100%"), "Log files exceeds the memory limit");
		String fileUtilize = monitorHeapScriptOperator.executeCommands(CHECK_LOG_FILES);
		String logFileCheck = monitorHeapScriptOperator
				.executeCommands("cd /eniq/log/sw_log/engine/; tail -1 " + fileUtilize.trim());
		if (logFileCheck.length() > 0) {
			String logFile[] = logFileCheck.split(":");
			for (int i = 0; i < logFile.length; i++) {
				logFileData.add(logFile[3]);
			}
			Logger.info("Log File Value : " + logFileData.toString());
			if (logFileData.isEmpty() && logFileData.size() > 0) {
				assertFalse(!logFileData.toString().contains("FileSystem utilization is Normal"),
						"FileSystem utilization is not normal");
			}
		}
	}

	public static class StepIds {

		public static final String VERIFY_HEAP_SCRIPT_EXECUTION = "Verify the Monitor Heap script is fetching the values.";
		public static final String VERIFY_LOG_FILES_LIMIT_EXCEEDS = "Verify the log files exceeds the memory limit using FileSystemCheck script.";

		private StepIds() {
		}
	}
}
