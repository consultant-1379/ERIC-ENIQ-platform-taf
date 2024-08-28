package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.InterfaceActivationDBOperator;
import com.ericsson.eniq.taf.installation.test.operators.InterfaceActivationOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class InterfaceActivationSteps {

	private static Logger logger = LoggerFactory.getLogger(InterfaceActivationSteps.class);

	private static final String LIST_OF_INTERFACES = "cd /eniq/sw/installer;./get_active_interfaces | awk '{print $1}'";
	private static final String LIST_OF_INTERFACERS_FROM_DATABASE = "cd /eniq/sw/installer;./get_active_interfaces | awk '{print $1 \"-\" $2}'";
	private static final String ACTIVATE_INTERFACE = "cd /eniq/sw/installer/;./activate_interface -o";
	private static final String ENIQ_OSS_ALIAS_1 = "eniq_oss_1";
	private static final String REMOVE_INSTALL_LOCKFILE = "rm -fr /eniq/sw/installer/install_lockfile";
	private static final String DB_QUERY_ACTIVE_INTERFACES = "pm/InterfaceActivation.sql";

	List<String> OSS_ALIAS = new ArrayList<>(Arrays.asList("eniq_oss_2", "eniq_oss_3"));

	@Inject
	private Provider<InterfaceActivationOperator> provider;

	@Inject
	private Provider<InterfaceActivationDBOperator> dbprovider;

	/**
	 * @DESCRIPTION This test case covers verification of manual activation of
	 *              Interfaces
	 * @PRE Interfaces
	 */

	@TestStep(id = StepIds.VERIFY_INTERFACE_ACTIVATION)
	public void verifyAllInterfaceActivationWithOneOSSalias() {
		final InterfaceActivationOperator interfaceOperator = provider.get();

		final String remove_lockfile = interfaceOperator.executeCommand(REMOVE_INSTALL_LOCKFILE);
		logger.info("Removed /eniq/sw/installer/install_lockfile " + remove_lockfile);

		final List<String> listOfInterfaces = interfaceOperator.listOfInterfaces(LIST_OF_INTERFACES);
		logger.info("List of active Interfaces : \n" + listOfInterfaces);

		for (int i = 0; i < listOfInterfaces.size(); i++) {
			final String result = interfaceOperator
					.executeCommand(ACTIVATE_INTERFACE + " " + ENIQ_OSS_ALIAS_1 + " -i " + listOfInterfaces.get(i));
			logger.info(result);
			assertTrue(result.contains("Interface " + listOfInterfaces.get(i) + "_" + ENIQ_OSS_ALIAS_1 + " activated")
					&& result.contains("Change profile requested successfully"), "Failed due to :" + result);
		}
	}

	/**
	 * 
	 * @param Interface
	 *
	 */
	@TestStep(id = StepIds.VERIFY_INTERFACE_ACTIVATION_WITH_OSS)
	public void verifyAllInterfaceActivationWithDifferentOssAlias() {
		final InterfaceActivationOperator interfaceOperator = provider.get();

		final String remove_lockfile = interfaceOperator.executeCommand(REMOVE_INSTALL_LOCKFILE);
		logger.info("Removed /eniq/sw/installer/install_lockfile " + remove_lockfile);

		final List<String> listOfInterfaces = interfaceOperator.listOfInterfaces(LIST_OF_INTERFACES);

		logger.info("List of active Interfaces : \n" + listOfInterfaces);

		for (int j = 0; j < OSS_ALIAS.size(); j++) {

			for (int i = 0; i < listOfInterfaces.size(); i++) {
				final String result = interfaceOperator
						.executeCommand(ACTIVATE_INTERFACE + " " + OSS_ALIAS.get(j) + " -i " + listOfInterfaces.get(i));
				logger.info(result);
				assertTrue(
						result.contains("Interface " + listOfInterfaces.get(i) + "_" + OSS_ALIAS.get(j) + " activated")
								&& result.contains("Change profile requested successfully"),
						"Failed due to :" + result);
			}
		}
	}

	@TestStep(id = StepIds.VERIFY_ACTIVE_INTERFACES)
	public void verifyActiveInterfaces() {
		final InterfaceActivationOperator interfaceOperator = provider.get();

		final String remove_lockfile = interfaceOperator.executeCommand(REMOVE_INSTALL_LOCKFILE);
		logger.info("Removed /eniq/sw/installer/install_lockfile " + remove_lockfile);

		final List<String> expected = interfaceOperator.listOfInterfaces(LIST_OF_INTERFACERS_FROM_DATABASE);
		logger.info("List of active Interfaces : \n" + expected);

		logger.info("JDBC Connection....");

		InterfaceActivationDBOperator dbOperator = dbprovider.get();
		logger.info("JDBC Connected");
		List<String> actual = dbOperator.verifyDBCommand(DB_QUERY_ACTIVE_INTERFACES);

		for (String str : expected) {
			if (actual.contains(str)) {
				logger.info("Match Found for : " + str);
				assertTrue(true);
			} else {
				logger.info("Match NOT Found for : " + str);
				assertTrue(false);
			}
		}
	}

	public static class StepIds {
		public static final String VERIFY_INTERFACE_ACTIVATION = "Verify All the Interfaces can be activated manually.";
		public static final String VERIFY_INTERFACE_ACTIVATION_WITH_OSS = "Verify All the Interfaces can be activated for different OSS";
		public static final String VERIFY_ACTIVE_INTERFACES = "Verify All the Interfaces for corresponding installed techpacks should be installed and activated automatically";

		private StepIds() {
		}
	}
}
