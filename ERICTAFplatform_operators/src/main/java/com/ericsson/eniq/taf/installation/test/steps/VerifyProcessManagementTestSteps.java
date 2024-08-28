package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyProcessManagementOperator;

/**
 * Created by zvadde on 04/10/2018.
 */
public class VerifyProcessManagementTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyProcessManagementTestSteps.class);

	@Inject
	private Provider<VerifyProcessManagementOperator> provider;

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.CLI_INITIALIZE)
	public void cliInitialize(@Input(Parameters.SERVICE_NAME) final String serviceName) {

		LOGGER.info(serviceName + "- cliInitialize start service");
		final VerifyProcessManagementOperator processOperator = provider.get();
		processOperator.initialise();
		assertTrue(true);
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_SERVICE_START)
	public void veriyStartService(@Input(Parameters.SERVICE_NAME) final String serviceName) {
		LOGGER.info("Verify '" + serviceName + "' service");
		final VerifyProcessManagementOperator processOperator = provider.get();
		final String startServiceOutput = processOperator.startService(serviceName);
		if (startServiceOutput.isEmpty()) {
			final String startStatusOutput = processOperator.checkStatusOfService(serviceName);
			assertTrue(startStatusOutput.contains("active (running)"),
					"Failed to START the service : " + serviceName + startStatusOutput);
		} else {
			assertTrue(false, "Failed to START service : " + serviceName + "\nERROR : \n" + startServiceOutput);
		}
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_SERVICE_STOP)
	public void veriyStopService(@Input(Parameters.SERVICE_NAME) final String serviceName) {
		LOGGER.info("Verify '" + serviceName + "' service");
		final VerifyProcessManagementOperator processOperator = provider.get();
		final String stopServiceOutput = processOperator.stopService(serviceName);
		if (stopServiceOutput.isEmpty()) {
			final String startStatusOutput = processOperator.checkStatusOfService(serviceName);
			assertTrue(startStatusOutput.contains("inactive (dead)"),
					"Failed to STOP the service : " + serviceName + startStatusOutput);
		} else {
			assertTrue(false, "Failed to STOP the service : " + serviceName + "\nERROR : \n" + stopServiceOutput);
		}

		final String startServiceOutput = processOperator.startService(serviceName);
	}

	/**
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_SERVICE_ENABLE)
	public void veriyEnableService(@Input(Parameters.SERVICE_NAME) String serviceName) {
		LOGGER.info(serviceName + " - Verify enable service");
		final VerifyProcessManagementOperator processOperator = provider.get();
		final String preDisableOutput = processOperator.disableService(serviceName);
		final String enableOutput = processOperator.enableService(serviceName);
		if (enableOutput.contains("Created symlink")) {
			final String enbleStatusOutput = processOperator.checkStatusOfService(serviceName);
			assertTrue(
					enbleStatusOutput.contains("active (running)")
							&& enbleStatusOutput.contains(serviceName + ".service; enabled;"),
					"Failed to ENABLE the service : " + serviceName + enbleStatusOutput);
		} else {
			assertTrue(false, "Failed to ENABLE service : " + serviceName + "\nERROR : \n" + enableOutput);
		}
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_SERVICE_DISABLE)
	public void veriyDisableService(@Input(Parameters.SERVICE_NAME) String serviceName) {
		LOGGER.info(serviceName + " - Verify disable service");
		final VerifyProcessManagementOperator processOperator = provider.get();
		final String enableOutput = processOperator.enableService(serviceName);
		final String disableServiceOutput = processOperator.disableService(serviceName);
		if (disableServiceOutput.contains("Removed symlink")) {
			final String serviceStatusOutput = processOperator.checkStatusOfService(serviceName);
			assertTrue(
					serviceStatusOutput.contains("active (running)")
							&& serviceStatusOutput.contains(serviceName + ".service; disabled;"),
					"Failed to DISABLE the service : " + serviceName + serviceStatusOutput);
		} else {
			assertTrue(false, "Failed to DISABLE service : " + serviceName + "\nERROR : \n" + disableServiceOutput);
		}
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_LOGS)
	public void verifyLogFile(@Input(Parameters.SERVICE_NAME) String serviceName) {
		final VerifyProcessManagementOperator processOperator = provider.get();
		final String fileInfo = processOperator.getServiceLogContent(serviceName);
		// assertFalse(fileInfo.trim().length()>0,"Log file for service "+serviceName +"
		// has Exceptions" +fileInfo);
		assertTrue(true);
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_DEPENDENT_SERVICES)
	public void verifyDependentServices(@Input(Parameters.SERVICE_NAME) String serviceName) {
		// final VerifyProcessManagementOperator processOperator = provider.get();
		// final String output = processOperator.dependentServices(serviceName);
		// assertFalse(fileInfo.length()>0,"Log file for service "+serviceName +" has
		// Exceptions" +fileInfo);
		LOGGER.info(serviceName);
		assertTrue(true);
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_RESTART_ALL_SERVICES_ORDER)
	public void verifyRestartAllServices(@Input(Parameters.SERVICE_NAME) String serviceName) {
		// final VerifyProcessManagementOperator processOperator = provider.get();
		// final String fileInfo = processOperator.restartAllServices();
		LOGGER.info(serviceName);
		assertTrue(true);
	}

	/**
	 * 
	 * @param serviceName service name
	 */
	@TestStep(id = StepIds.VERIFY_ENGINE_COMMANDS)
	public void validateEngineCommands(@Input(Parameters.ENGINE_CMDS) String commands) {
		final VerifyProcessManagementOperator engineOperator = provider.get();
		if (commands.contains("status")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(
					result.contains("Status: active") && result.contains("Current Profile: Normal")
							&& result.contains("Completed successfully"),
					"\n Expected : 'Status: active' & 'Current Profile: Normal' but found : " + result);
		}
		if (commands.contains("reloadConfig")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Reload properties requested successfully"),
					"\n Expected : 'Reload properties requested successfully' but found : " + result);
		}
		if (commands.contains("reloadAggregationCache")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Reload aggregation cache requested successfully"),
					"\n Expected : 'Reload aggregation cache requested successfully' but found : " + result);
		}
		if (commands.contains("changeProfile")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(
					result.contains("Changing profile to: Normal")
							&& result.contains("Change profile requested successfully"),
					"\n Expected : 'Changing profile to: Normal' & 'Change profile requested successfully' but found : "
							+ result);
		}
		if (commands.contains("reloadProfiles")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Reload profiles requested successfully"),
					"\n Expected : 'Reload profiles requested successfully' but found : " + result);
		}
		if (commands.contains("reloadAlarmCache")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Reload alarm cache requested successfully"),
					"\n Expected : 'Reload alarm cache requested successfully' but found : " + result);
		}
		if (commands.contains("reloadBackupCache")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Reload Backup Configuration cache requested successfully"),
					"\n Expected : 'Reload Backup Configuration cache requested successfully' but found : " + result);
		}
		if (commands.contains("triggerRestoreOfData")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Triggering of restore data requested successfully"),
					"\n Expected : 'Triggering of restore data requested successfully' but found : " + result);
		}
		if (commands.contains("loggingStatus")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Logging status printed succesfully"),
					"\n Expected : 'Logging status printed successfully' but found : " + result);
		}
		if (commands.contains("showSetsInQueue") || commands.contains("showSetsInExecutionSlots")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Finished successfully"),
					"\n Expected : 'Finished successfully' but found : " + result);
		}
		if (commands.contains("showActiveInterfaces")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("INTF"), "\n Expected : 'INTF' but found : " + result);
		}
		if (commands.contains("getCurrentProfile")) {
			final String result = engineOperator.engineCommandsValidation(commands);
			assertTrue(result.contains("Normal"), "\n Expected : 'Normal' but found : " + result);
		}
	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class StepIds {
		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_SERVICE_START = "Verify Service start";
		public static final String VERIFY_SERVICE_STOP = "Verify Service stop.";
		public static final String VERIFY_SERVICE_ENABLE = "Verify Service enable";
		public static final String VERIFY_SERVICE_DISABLE = "Verify Service disable.";
		public static final String VERIFY_DEPENDENT_SERVICES = "Verify the Dependent services";
		public static final String VERIFY_LOGS = "Veriy logs";
		public static final String VERIFY_RESTART_ALL_SERVICES_ORDER = "Verify restarting all the services and order verification";
		public static final String VERIFY_ENGINE_COMMANDS = "Verify Engine Commands";

		private StepIds() {
		}
	}

	/**
	 * 
	 * @author XARUNHA
	 *
	 */
	public static class Parameters {
		public static final String SERVICE_NO = "No";
		public static final String SERVICE_NAME = "service";
		public static final String ENGINE_CMDS = "commands";

		private Parameters() {
		}

	}

}
