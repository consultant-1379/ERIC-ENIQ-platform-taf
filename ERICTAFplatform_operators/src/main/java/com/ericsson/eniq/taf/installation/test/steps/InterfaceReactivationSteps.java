package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.InterfaceReactivationOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class InterfaceReactivationSteps {

	private static Logger logger = LoggerFactory.getLogger(InterfaceReactivationSteps.class);

	private static final String INTERFACE_REACTIVATION_LOG = "*reactivate_interface.log";
	private static final String SINGLE_RNC_INTERFACE = "cd /eniq/sw/installer/;printf 'INTF_DC_E_RNC\n' > Single_Interfaces_RNC";
	private static final String LIST_OF_ACTIVE_INTERFACES = "cd /eniq/sw/installer;./get_active_interfaces |  awk '{ print $1 }' > listOfActiveInterfaces";
	private static final String INTERFACE_SINGLE_REACTIVATION_CMD = "cd /eniq/sw/installer/;./reactivate_interfaces -f Single_Interfaces_RNC -m listOfActiveInterfaces -r /eniq/sw/conf/feature_techpacks";
	private static final String REMOVE_CMD = "cd /eniq/sw/installer/;rm -fr Single_Interfaces_RNC listOfActiveInterfaces";
	private static final String REMOVE_INSTALL_LOCKFILE = "rm -fr /eniq/sw/installer/install_lockfile";
	private static final String LATEST_TP_INSTALLER_LOG_FILE = "cd /eniq/log/sw_log/tp_installer/;ls *tp_installer.log | tail -1";
	private static final String FEATURE_INTERFACES = "sed -n '/Getting interfaces of feature/,/List of TP to be installed/p' ";
	private static final String GREP_INTERFACES = " | grep INTF ";
	private static final String WRITE_FILE = " > ";
	private static final String FEATURE_INTERFACES_TEXT_FILE = "featureInterfacesList";
	private static final String CAT_CMD = "cat";
	private static final String REACTIVATION_SCRIPT_1 = "cd /eniq/sw/installer/;./reactivate_interfaces -f ";
	private static final String REACTIVATION_SCRIPT_2 = " -m listOfActiveInterfaces -r /eniq/sw/conf/feature_techpacks";

	@Inject
	private Provider<InterfaceReactivationOperator> provider;

	/**
	 * @DESCRIPTION This test case covers verification of Interface Reactivation
	 * @PRE Interfaces
	 */

	@TestStep(id = StepIds.VERIFY_SINGLE_INTERFACE_REACTIVATION)
	public void verifySingleInterfaceReactivation() {
		final InterfaceReactivationOperator interfaceReactivation = provider.get();

		logger.info("Preparing pre-requisites for the test case");

		// pre-requisite 1: remove install_lockfile from /eniq/sw/installer/
		final String removeInstallLockFile = interfaceReactivation.cmdExecute(REMOVE_INSTALL_LOCKFILE);

		// pre-requisite 2: Single interface text file
		interfaceReactivation.cmdExecute(SINGLE_RNC_INTERFACE);
		// logger.info("created single interface text file \n" + interfaceReactivation.cmdExecute("cd /eniq/sw/installer;cat
		// Single_Interfaces_RNC"));

		// pre-requisite 3: list Of Active Interfaces text file
		final String listOfActiveInterfaces = interfaceReactivation.cmdExecute(LIST_OF_ACTIVE_INTERFACES);
		// logger.info("created list Of Active Interfaces text file \n" + listOfActiveInterfaces);

		logger.info("Verifying Single Interface Reactivation for RNC...");
		final String fileInfo = interfaceReactivation.interfaceReactivation(INTERFACE_SINGLE_REACTIVATION_CMD);

		// Removing pre-requisite text file the server: Single_Interfaces_RNC & listOfActiveInterfaces
		final String removedFile = interfaceReactivation.cmdExecute(REMOVE_CMD);
		logger.info("\nRemoved pre-requisite text files the server. \n" + removedFile);

		assertTrue(fileInfo.contains("Interface re-activation completed successfully"),
				"Interface Reactivation failed due to : \n " + fileInfo);
	}

	@TestStep(id = StepIds.VERIFY_FEATURE_INTERFACE_REACTIVATION)
	public void verifyFeatureInterfaceReactivation() {
		final InterfaceReactivationOperator interfaceReactivation = provider.get();

		logger.info("Preparing pre-requisites for the test case");

		final String removeInstallLockFile = interfaceReactivation.cmdExecute(REMOVE_INSTALL_LOCKFILE);
		
		final String logFile = interfaceReactivation.cmdExecute(LATEST_TP_INSTALLER_LOG_FILE);
		
		logger.info("Executing : " + FEATURE_INTERFACES + " /eniq/log/sw_log/tp_installer/" +logFile);
		
		//final String featureFile = interfaceReactivation.cmdExecute(FEATURE_INTERFACES +  "/eniq/log/sw_log/tp_installer/"+ logFile);
		final String featureFile = interfaceReactivation.cmdExecute("sed -n '/Getting interfaces of feature/,/List of TP to be installed/p'  /eniq/log/sw_log/tp_installer/2019-01-25:asd_tp_installer.log");

		if (featureFile.equals(null) || featureFile.equals("")) {
			logger.info("No feature log file genarated");
			return;

		} else {

			logger.info("Executing : " + FEATURE_INTERFACES + logFile + GREP_INTERFACES + WRITE_FILE
					+ FEATURE_INTERFACES_TEXT_FILE);
			final String readInterfaces = interfaceReactivation.cmdExecute(
					FEATURE_INTERFACES + logFile + GREP_INTERFACES + WRITE_FILE + FEATURE_INTERFACES_TEXT_FILE);

			final String featureInterfacesList = interfaceReactivation
					.cmdExecute(CAT_CMD + " " + FEATURE_INTERFACES_TEXT_FILE);
			logger.info("List of Delivered Feature's Interfaces : \n" + featureInterfacesList);

			// pre-requisite 3: list Of Active Interfaces text file
			final String listOfActiveInterfaces = interfaceReactivation.cmdExecute(LIST_OF_ACTIVE_INTERFACES);

			logger.info("Verifying Single Interface Reactivation for RNC...");
			final String fileInfo = interfaceReactivation.interfaceReactivation(
					REACTIVATION_SCRIPT_1 + FEATURE_INTERFACES_TEXT_FILE + REACTIVATION_SCRIPT_2);

			final String removedFile = interfaceReactivation.cmdExecute(REMOVE_CMD);
			logger.info("\nRemoved pre-requisite text files the server. \n" + removedFile);

			assertTrue(fileInfo.contains("Interface re-activation completed successfully"),
					"Interface Reactivation failed due to : \n " + fileInfo);
		}
	}

	@TestStep(id = StepIds.VERIFY_INTERFACE_REACTIVATION_LOGS)
	public void verifyInterfaceReactivationLogFile() {
		final InterfaceReactivationOperator interfaceReactivation = provider.get();

		logger.info("Verifying Interface Reactivation log contents");
		final String fileInfo = interfaceReactivation.getInterfaceReactivationLogContent(INTERFACE_REACTIVATION_LOG);
		assertFalse(fileInfo.length() > 0, "Interface Reactivation has Exceptions : \n " + fileInfo);
	}

	public static class StepIds {
		public static final String VERIFY_INTERFACE_REACTIVATION_LOGS = "Verify the logs after reactivation of interface";
		public static final String VERIFY_SINGLE_INTERFACE_REACTIVATION = "To verify the reactivation of interface manually";
		public static final String VERIFY_FEATURE_INTERFACE_REACTIVATION = "All Interfaces corresponding to selected feature shall be re-activated as part of feature upgrade";

		private StepIds() {
		}
	}
}
