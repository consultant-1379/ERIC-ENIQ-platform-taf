package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.DwhdbOperator;


/**
 * @author ZJSOLEA
 */
public class BugJiraEQEV60256Step01 {

	private static Logger logger = LoggerFactory.getLogger(BugJiraEQEV60256Step01.class);

	private static final String SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/MINI-LINK/dir1";
	private static final String SAMPLE_FILE_ONE = "A20190819.0800+0100-0815+0100_SubNetwork=ENM2-01,MeContext=MINI-LINK PT2020,ManagedElement=MINI-LINK PT2020_statsfile_ethernet.xml";
	
	private static final String EXECUTE_ENGINE_COMMAND = "cd " + SERVER_PATH + ";engine -e startAndWaitSet \'INTF_DC_E_IPTRANSPORT_MINILINK_ECIM-eniq_oss_1\' \'Adapter_INTF_DC_E_IPTRANSPORT_MINILINK_ECIM_3GPP32435DYN\'";
	private static final String CHANGE_FILE_PERMISSIONS = "echo shroot12 | su - root -c \"chown -R dcuser:dc5000 " + SERVER_PATH + "/.. ; chown dcuser:dc5000 " + SERVER_PATH + "/*;\"";
	private static final String GET_VALUE_FROM_DB = "pm/eqev60256query01.sql";

	@Inject
	private Provider<GeneralOperator> provider;
	@Inject
	private Provider<DwhdbOperator> dwhdbProvider;
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;
	

    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	/**
	 * @throws IOException 
	 * @DESCRIPTION verification for bug Jira EQEV60256
	 */
	@TestStep(id = StepIds.BUGJIRAEQEV60256_STEP_01)
	public void verify() throws IOException {
		
		// get operators from providers
		final GeneralOperator operator = provider.get();
		final DwhdbOperator dwhdbOperator = dwhdbProvider.get();
		
		/* DB and service checks to check and start engine and dbs. */
		
		if (operator.executeCommandDcuser("scheduler status").contains("not running"))
			operator.executeCommandDcuser("scheduler start");
		if (operator.executeCommandDcuser("repdb status").contains("is not running"))
			operator.executeCommandDcuser("repdb start");
		if (operator.executeCommandDcuser("dwhdb status").contains("not running"))
			operator.executeCommandDcuser("dwhdb start");
		if (operator.executeCommandDcuser("engine status").contains("not running"))
			operator.executeCommandDcuser("engine start");
		
		/* the above block can be removed if not needed.*/
		
		// copy files to server
		operator.createDirectory(SERVER_PATH);
		operator.copyFileToServer(SAMPLE_FILE_ONE, SERVER_PATH);

		// Find yesterdays date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);
		Date yesterday = cal.getTime();
		
		String NEW_FILENAME = SAMPLE_FILE_ONE.replace("20190819", (new SimpleDateFormat("YYYYMMdd")).format(yesterday));
		String NEW_DATE = (new SimpleDateFormat("YYYY-MM-dd")).format(yesterday);
		
		logger.info("Changing date from 2019-08-19 to " + NEW_DATE + " in the input xml file");
		String output = operator.executeCommand("cd /eniq/data/pmdata/eniq_oss_1/MINI-LINK/dir1; sed -i 's/2019-08-19/" + NEW_DATE + "/g' *.xml");
		assertTrue(output.isEmpty(), "Error while changing date in input file name");
		
		logger.info("Changing filename from '" + SAMPLE_FILE_ONE + "' to '" + NEW_FILENAME);
		output = operator.executeCommand("cd /eniq/data/pmdata/eniq_oss_1/MINI-LINK/dir1; mv \"" + SAMPLE_FILE_ONE + "\" \"" + NEW_FILENAME + "\"");
		assertTrue(output.isEmpty(), "Error while changing input file name");
		
		logger.info("Changing file permissions to dcuser");
		output = operator.executeCommandDcuser(CHANGE_FILE_PERMISSIONS);
		logger.info("Output is '"+output+"'");
		
		logger.info("Starting engine execution");
		output = operator.executeCommandDcuser(EXECUTE_ENGINE_COMMAND);
		logger.info("Output is '"+output+"'");
		
		logger.info("Checking in b that nil values are correctly loaded as null values");
		List<Object[]> results = dwhdbOperator.ExecuteQueryFile(GET_VALUE_FROM_DB);
		assertTrue(results.size() > 0, "Counter values are not loaded correctly. Select query returned zero rows.");

		return;
	}

	public static class StepIds {
		public static final String BUGJIRAEQEV60256_STEP_01 = "verification for bug Jira EQEV60256";

		private StepIds() {
		}
	}
}