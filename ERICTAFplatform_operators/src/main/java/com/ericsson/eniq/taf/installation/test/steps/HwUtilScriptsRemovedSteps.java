package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.CertificateOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class HwUtilScriptsRemovedSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(HwUtilScriptsRemovedSteps.class);

	private final static String MWS_COMMON_PATH = DataHandler.getAttribute("platform.mws.common.path").toString();
	private final static String VAPP_SHIPMENT = "shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-`;num=`echo $shipment | cut -d\".\" -f2`;numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');featurePath=`echo \"Features_19${numToAlpha[$num]}_$shipment\"`;echo $shipment";
	private final static String ENIQ_BASE_SW_DIR = "/eniq_base_sw/";
	private final static String LS_CMD = "ls";
	private final static String SPACE = " ";
	private final static String PIPE = "|";
	private final static String GREP = "grep -i";
	private final static String HARDDUTE = "hardute";

	@Inject
	private Provider<CertificateOperator> provider;

	/**
	 * @DESCRIPTION H/w Util related scripts are removed from ES ISO
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_HW_SCRIPTS_REMOVED)
	public void verifyCertificateForCER() {
		final CertificateOperator certificateOperator = provider.get();

		String vAppShipment = certificateOperator.executeCommands(VAPP_SHIPMENT);
		LOGGER.info("\nvApp Shipment : " + vAppShipment);
		String harduteDirOutput = certificateOperator.executeCommands(LS_CMD + SPACE + MWS_COMMON_PATH + vAppShipment
				+ ENIQ_BASE_SW_DIR + SPACE + PIPE + SPACE + GREP + SPACE + HARDDUTE);

		assertTrue(harduteDirOutput.isEmpty(), " '" + HARDDUTE + "'" + " Directory still exist under " + MWS_COMMON_PATH
				+ vAppShipment + ENIQ_BASE_SW_DIR);
	}

	public static class StepIds {

		public static final String VERIFY_HW_SCRIPTS_REMOVED = "H/w Util related scripts are removed from ES ISO";

		private StepIds() {
		}
	}
}
