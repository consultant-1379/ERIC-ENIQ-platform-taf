package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.LoadingDwhDBOperator;
import com.ericsson.eniq.taf.installation.test.operators.LoadingDwhrepDbOperator;
import com.ericsson.eniq.taf.installation.test.operators.LoadingOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class LoadingSteps {

	private static Logger logger = LoggerFactory.getLogger(LoadingSteps.class);

	private static final String PM_LOADING_ALL_TABLES = "pm/pmLoadingAllTables.sql";
	private static final String PM_LOADING_ONE_TABLE = "pm/testing.sql";
	private static final String PM_LOADING_SELECT_ONE_TABLE = "pm/pmLoadingSelectOneTable.sql";
	private static final String LATEST_ENGINE_LOG = "cd /eniq/log/sw_log/engine/;ls engine-2*.log | tail -1";
	private static final String LATEST_ERROR_LOG = "cd /eniq/log/sw_log/engine/;ls error-2*.log | tail -1";

	@Inject
	private Provider<LoadingOperator> provider;

	@Inject
	private Provider<LoadingDwhrepDbOperator> dbhrepprovider;

	@Inject
	private Provider<LoadingDwhDBOperator> dwhDBprovider;

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.VERIFY_LOADING_ENGINE_LOGS)
	public void verifyEngineLog() {
		final LoadingOperator LoadingOperator = provider.get();

		final String latestEngineLog = LoadingOperator.executeCommand(LATEST_ENGINE_LOG);
		final String engineLogContent = LoadingOperator.engineLogContent(latestEngineLog);
		assertFalse(engineLogContent.length() > 0, "Engine log file has Exceptions :  \n " + engineLogContent);
	}

	@TestStep(id = StepIds.VERIFY_LOADING_ERROR_LOGS)
	public void verifyErrorLog() {
		final LoadingOperator LoadingOperator = provider.get();

		final String latestErrorLog = LoadingOperator.executeCommand(LATEST_ERROR_LOG);
		final String errorLogContent = LoadingOperator.errorLogContent(latestErrorLog);
		assertFalse(errorLogContent.length() > 0, "Error log file has Exceptions :  \n " + errorLogContent);
	}

	@TestStep(id = StepIds.VERIFY_LOADING_DUPLICATE)
	public void verifyDuplicateDataInRowStatus() {
		final LoadingDwhrepDbOperator dwhrepDbOperator = dbhrepprovider.get();
		final LoadingDwhDBOperator dwhDBOperator = dwhDBprovider.get();
		HashMap<String, String> onetable_db_output = null;

		dwhrepDbOperator.verifyDBCommand2(PM_LOADING_ALL_TABLES);
		Map<String, String> value = dwhDBOperator.verifyDBCommand2(PM_LOADING_SELECT_ONE_TABLE);
		for (Map.Entry<String, String> entry : value.entrySet()) {
			HashMap<String, String> valaueMap = new HashMap<String, String>();
			valaueMap.put("allDCTables", entry.getValue());
			onetable_db_output = dwhDBOperator.verifyDBCommand3(PM_LOADING_SELECT_ONE_TABLE, valaueMap);
		}
		assertFalse(onetable_db_output.containsValue("DUPLICATE") || onetable_db_output.containsValue("not found"),
				"\n Below are the list of 'DUPLICATE' tables \n" + onetable_db_output);
	}

	public static class StepIds {
		public static final String VERIFY_LOADING_ENGINE_LOGS = "To verify whether warning / error / exception / fail in the ENGINE log file.";
		public static final String VERIFY_LOADING_ERROR_LOGS = "To verify whether warning / error / exception / fail in the ERROR log file.";
		public static final String VERIFY_LOADING_DUPLICATE = "Verify that there is no data loaded as duplicate for all fact tables";

		private StepIds() {
		}
	}
}
