package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.List;

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
public class InitialInstallationSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(InitialInstallationSteps.class);

	private final static String ENIQ_PF_INITAIL_INSTALL_PATH = DataHandler
			.getAttribute("eniq.platform.initail.install.log.path").toString();
	private final static String LS_CMD = "ls";
	private final static String SPACE = " ";
	private final static String PIPE = "|";
	private final static String GRPE = "grep -i";
	private final String HOSTNAME = "hostname";
	private final String TAIL = "tail";
	private final String ENIQ_SHIPMENT_DETAILS = "/eniq/admin/version/eniq_status";
	private final String CAT = "cat";

	private final String ENIQ_SERVICES_STATUS = "systemctl -a -t service | grep  eniq*-";

	@Inject
	private Provider<CertificateOperator> provider;

	/**
	 * @DESCRIPTION H/w Util related scripts are removed from ES ISO
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_ENIQ_INITIAL_INSTALL)
	public void verifyCertificateForCER() {
		final CertificateOperator certificateOperator = provider.get();

		String hostName = certificateOperator.executeCommands(HOSTNAME);
		LOGGER.info("\nServer Host Name is : " + hostName);

		String initialInstallLogFile = certificateOperator.executeCommands(LS_CMD + SPACE + ENIQ_PF_INITAIL_INSTALL_PATH
				+ SPACE + PIPE + SPACE + GRPE + SPACE + hostName + "_install.log");
		LOGGER.info("\nInitial Install Log file : " + initialInstallLogFile);

		if (initialInstallLogFile.contains(hostName + "_install.log")) {
			String logFileOutput = certificateOperator
					.executeCommands(TAIL + SPACE + ENIQ_PF_INITAIL_INSTALL_PATH + initialInstallLogFile);
			LOGGER.info("\nInitial Install Log file Content : \n" + logFileOutput);
			assertTrue(logFileOutput.contains("ENIQ SW successfully installed"),
					"ENIQ Initial Install Failed due to : \n" + logFileOutput);
		} else {
			assertTrue(false, "No Initial install log file found under : " + ENIQ_PF_INITAIL_INSTALL_PATH);
		}
	}

	@TestStep(id = StepIds.VERIFY_ENIQ_SERVICES_FLOW)
	public void verifyEniqServicesStatus() {
		final CertificateOperator certificateOperator = provider.get();
		List<String> eniqServices = certificateOperator.executeCommandsRetunList(ENIQ_SERVICES_STATUS);
		for (String str : eniqServices) {
			assertTrue(str.contains("loaded") && str.contains("active"), "Failed due to service status is : " + str);
		}
	}

	@TestStep(id = StepIds.VERIFY_ENIQ_SHIPMENT_FLOW)
	public void verifyEniqShipmentStatus() {
		final CertificateOperator certificateOperator = provider.get();
		String eniqShipmentDetails = certificateOperator.executeCommands(CAT + SPACE + ENIQ_SHIPMENT_DETAILS);
		assertTrue(eniqShipmentDetails.contains("ENIQ_Statistics_Shipment") && eniqShipmentDetails.contains("AOM"),
				"ENIQ installed software information is not dispaying correctly : " + eniqShipmentDetails);
	}

	public static class StepIds {

		public static final String VERIFY_ENIQ_INITIAL_INSTALL = "Verify that ENIQ-S Initial Installation is successful on Rack /Blade Server";
		public static final String VERIFY_ENIQ_SERVICES_FLOW = "Verify all ENIQ-S services are deployed and actively running after Initial Installation is successful on Rack /Blade Server";
		public static final String VERIFY_ENIQ_SHIPMENT_FLOW = "Verify all ENIQ-S installed version is updated properly with latest installed software information";

		private StepIds() {
		}
	}
}
