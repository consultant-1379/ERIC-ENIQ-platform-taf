package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.DWHMonitorInstallOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class DWHMonitorInstallSteps {
	private static Logger logger = LoggerFactory.getLogger(DWHMonitorInstallSteps.class);

	private static final String LATEST_ENGINE_LOG = "cd /eniq/log/sw_log/engine/DWH_MONITOR/;ls engine-2*.log | tail -1";
	private static final String LATEST_ERROR_LOG = "cd /eniq/log/sw_log/engine/DWH_MONITOR/;ls error-2*.log | tail -1";
	private static final String LATEST_ENGINE_LOG_MONITOR = "cd /eniq/log/sw_log/tp_installer/;ls *_DWH_MONITOR_*.tpi.log | tail -1";
	private static final String LATEST_ERROR_LOG_MONITOR = "cd /eniq/log/sw_log/tp_installer/;ls *_tp_installer.log | tail -1";

	@Inject
	private Provider<DWHMonitorInstallOperator> provider;

	/**
	 * @DESCRIPTION This test case covers verification of DWH_Monitor Installation
	 *              logs
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.VERIFY_LOADING_ENGINE_LOGS)
	public void verifyEngineLog() {
		final DWHMonitorInstallOperator dWHMonitorInstallOperator = provider.get();

		final String latestEngineLog = dWHMonitorInstallOperator.executeCommand(LATEST_ENGINE_LOG);
		final String engineLogContent = dWHMonitorInstallOperator.engineLogContent(latestEngineLog);
		assertFalse(engineLogContent.length() > 0, "Engine log file has Exceptions :  \n " + engineLogContent);
	}

	@TestStep(id = StepIds.VERIFY_LOADING_ERROR_LOGS)
	public void verifyTpiLog() {
		final DWHMonitorInstallOperator dWHMonitorInstallOperator = provider.get();

		final String latestErrorLog = dWHMonitorInstallOperator.executeCommand(LATEST_ERROR_LOG);
		final String errorLogContent = dWHMonitorInstallOperator.errorLogContent(latestErrorLog);
		assertFalse(errorLogContent.length() > 0, "Error log file has Exceptions :  \n " + errorLogContent);
	}

	// @TestStep(id = StepIds.VERIFY_LOADING_ENGINE_LOGS_MONITOR)
	// public void verifyTpInstallLog() {
		// final DWHMonitorInstallOperator dWHMonitorInstallOperator = provider.get();

		// final String latestEngineLog = dWHMonitorInstallOperator.executeCommand(LATEST_ENGINE_LOG_MONITOR);
		// final String engineLogContent = dWHMonitorInstallOperator.engineLogContent(latestEngineLog);
		// assertFalse(engineLogContent.length() > 0, "Engine log file has Exceptions :  \n " + engineLogContent);
	// }

	// @TestStep(id = StepIds.VERIFY_LOADING_ERROR_LOGS_MONITOR)
	// public void verifyErrorLog() {
		// final DWHMonitorInstallOperator dWHMonitorInstallOperator = provider.get();

		// final String latestErrorLog = dWHMonitorInstallOperator.executeCommand(LATEST_ERROR_LOG_MONITOR);
		// final String errorLogContent = dWHMonitorInstallOperator.errorLogContent(latestErrorLog);
		// assertFalse(errorLogContent.length() > 0, "Error log file has Exceptions :  \n " + errorLogContent);
	// }

	public static class StepIds {
		public static final String VERIFY_LOADING_ENGINE_LOGS = "To verify whether warning / error / exception / fail in the ENGINE log file.";
		public static final String VERIFY_LOADING_ERROR_LOGS = "To verify whether warning / error / exception / fail in the ERROR log file.";
		//public static final String VERIFY_LOADING_ENGINE_LOGS_MONITOR = "To verify whether warning / error / exception / fail in the ENGINE log file.";
		//public static final String VERIFY_LOADING_ERROR_LOGS_MONITOR = "To verify whether warning / error / exception / fail in the ERROR log file.";

		private StepIds() {
		}
	}

}
