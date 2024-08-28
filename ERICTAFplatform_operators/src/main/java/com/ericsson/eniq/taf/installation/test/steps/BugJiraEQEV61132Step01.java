package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author ZJSOLEA
 */
public class BugJiraEQEV61132Step01 {

	private static Logger logger = LoggerFactory.getLogger(BugJiraEQEV61132Step01.class);

	private static final String SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/MINI-LINK/dir1";
	private static final String SAMPLE_FILE_ONE = "A20190311.1000+0100-1015+0100_SubNetwork=ENM2-01,MeContext=TN-10-41-99-100,ManagedElement=TN-10-41-99-100_statsfile_ethernet.xml.gz";
	private static final String SAMPLE_FILE_TWO = "A20190311.1000+0100-1015+0100_SubNetwork=ENM2-01,MeContext=TN-10-41-99-100,ManagedElement=TN-10-41-99-100_statsfile_ethsoam.xml.gz";
	private static final String SAMPLE_FILE_THREE = "A20190312.1000+0100-1015+0100_SubNetwork=ENM2-01,MeContext=TN-10-41-99-100,ManagedElement=TN-10-41-99-100_statsfile_ethernet.xml.gz";
	private static final String SAMPLE_FILE_FOUR = "A20190312.1000+0100-1015+0100_SubNetwork=ENM2-01,MeContext=TN-10-41-99-100,ManagedElement=TN-10-41-99-100_statsfile_ethsoam.xml.gz";
	
	private static final String EXECUTE_ENGINE_COMMAND = "cd " + SERVER_PATH + ";engine -e startSet \'INTF_DC_E_IPTRANSPORT_MINILINK_ECIM-eniq_oss_1\' \'Adapter_INTF_DC_E_IPTRANSPORT_MINILINK_ECIM_3GPP32435DYN\'";
	private static final String CHANGE_FILE_PERMISSIONS = "echo shroot12 | su - root -c \"chown -R dcuser:dc5000 " + SERVER_PATH + "/.. ; chown dcuser:dc5000 " + SERVER_PATH + "/*;\"";
	private static final String VERIFY_LOG = "cd /eniq/log/sw_log/engine/INTF_DC_E_IPTRANSPORT_MINILINK_ECIM-eniq_oss_1/;ls engine-2*.log | grep -i \"Exception\"";

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION verification for bug Jira EQEV61132
	 */
	@TestStep(id = StepIds.BUGJIRAEQEV61132_STEP_01)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		// copy files to server
		operator.createDirectory(SERVER_PATH);
		operator.copyFileToServer(SAMPLE_FILE_ONE, SERVER_PATH);
		operator.copyFileToServer(SAMPLE_FILE_TWO, SERVER_PATH);
		operator.executeCommandDcuser(CHANGE_FILE_PERMISSIONS);
		operator.executeCommandDcuser(EXECUTE_ENGINE_COMMAND);
		
		// verify logs
		String output = operator.executeCommand(VERIFY_LOG);
		assertEquals(output, "", "Error in loading file");
		

		// copy files to server
		operator.createDirectory(SERVER_PATH);
		operator.copyFileToServer(SAMPLE_FILE_FOUR, SERVER_PATH);
		operator.copyFileToServer(SAMPLE_FILE_THREE, SERVER_PATH);
		operator.executeCommandDcuser(CHANGE_FILE_PERMISSIONS);
		operator.executeCommandDcuser(EXECUTE_ENGINE_COMMAND);

		// verify logs
		output = operator.executeCommand(VERIFY_LOG);
		assertEquals(output, "", "Error in loading file");

		return;
	}

	public static class StepIds {
		public static final String BUGJIRAEQEV61132_STEP_01 = "verification for bug Jira EQEV61132";

		private StepIds() {
		}
	}
}