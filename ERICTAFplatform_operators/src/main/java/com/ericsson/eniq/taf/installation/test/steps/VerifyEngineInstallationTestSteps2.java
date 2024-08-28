package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyEngineInstallationOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPlatformInstallationOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class VerifyEngineInstallationTestSteps2 {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyEngineInstallationTestSteps2.class);

	private List<String> logFiles = new ArrayList<>();

	private final String ENGINE_SERIVES_CMD = "svcs -d engine | sed -n 2p";
	// private final String ENGINE_LIECENSE_LOG_CMD = "cd
	// /eniq/log/sw_log/engine/;ls| grep \"Starter license\" engine-*.log";
	private final String ENGINE_LIECENSE_LOG_CMD = "cd /eniq/log/sw_log/engine/;ls -1rt  |  grep \"Starter license successfully verified\"  engine-*.log | sed '$!d'";
	private final String ENGINE_LIECENSE_LOG_CMD_CHECK = "cd /eniq/log/sw_log/engine/;ls -1rt  |  grep \"Proceeding with valid ENIQ Statistics Starter and Capacity license\"  engine-*.log | sed '$!d'";
	private final String PROFILE_COMMAND = "engine -e changeProfile ";

	@Inject
	private Provider<VerifyPlatformInstallationOperator> provider;

	@Inject
	private Provider<VerifyEngineInstallationOperator> engine_provider;

	/**
	 * @DESCRIPTION Verify the functionality of the Calc Command
	 * @PRE Get the calc version to be tested
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_LOG_EXISTS)
	public void verifyPlatFormLogExists(@Input(Parameters.PLATFORM_MODULE) String moduleName) {

		LOGGER.info(moduleName + "- Verify log file Exists.");

		// final String calcVersion =
		// DataHandler.getAttribute("CALCVER").toString();

		final VerifyPlatformInstallationOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		if (logFiles.size() == 0) {
			logFiles = platformLogOperator.listPlatformLogFiles();
		}
		// Predicate<String> p1 = f -> f.startsWith(moduleName+rState) &&
		// f.endsWith(".log");
		// boolean fileExists = logFiles.stream().anyMatch(p1);
		boolean fileExists = false;
		for (final String f : logFiles) {
			if (f.startsWith(moduleName + "_" + getRstate(moduleName)) && f.endsWith(".log")) {
				fileExists = true;
				LOGGER.info("file name:::::" + f);
				break;
			}
		}

		assertTrue(fileExists, "Log file for module " + moduleName + getRstate(moduleName) + " not Exists");

	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_LOG_ERRORS)
	public void verifyPlatFormLogFile(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : logFiles) {
			if (f.startsWith(moduleName + "_" + getRstate(moduleName)) && f.endsWith(".log")) {
				fileName = f;
				break;
			}
		}
		LOGGER.info(moduleName + "- Verify log contents" + fileName);
		final VerifyPlatformInstallationOperator platformLogOperator = provider.get();
		final String fileInfo = platformLogOperator.getPlatformLogContent(fileName);
		assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + "has Exceptions" + fileInfo);

	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_VERSION_DB_PROPERTIES)
	public void verifyVersionDBPropetiesUpdated(@Input(Parameters.PLATFORM_MODULE) String moduleName) {

		LOGGER.info(moduleName + "- Verify verifyVersionDBPropetiesUpdated");
		final VerifyPlatformInstallationOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		final String output = platformLogOperator.versionDBModuleUpdated(moduleName);
		assertTrue(output.contains("module." + moduleName + "=" + getRstate(moduleName)),
				"versiondb.properties is not updated for module " + moduleName + ":" + output);

	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_MODULES_EXTRACTED)
	public void verifyModulesExtracted(@Input(Parameters.PLATFORM_MODULE) String moduleName) {

		LOGGER.info(moduleName + "- Verify verifyModulesExtracted");
		final VerifyPlatformInstallationOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		final String output = platformLogOperator.modulesExtracted(moduleName);
		if (moduleName.equals("AdminUI") || moduleName.equals("BusyHour") || moduleName.equals("alarmcfg")
				|| moduleName.equals("helpset_stats")) {
			assertTrue(true);
		} else {
			assertTrue(output.contains(moduleName + "-" + getRstate(moduleName)),
					moduleName + getRstate(moduleName) + " is not present under /eniq/sw/platform ");
		}

	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_ENGINE_PROFILE_NOLOAD)
	public void verifyEngineProfileNoLoad() {
		final VerifyEngineInstallationOperator engineOperator = engine_provider.get();
		final String profile = "NoLoads";

		LOGGER.info("Verifying the engine profile can be set to Loads / No Loads");
		final String profile_results = engineOperator.executeCommand(PROFILE_COMMAND + profile);
		if (profile_results.contains("successfully") && profile_results.contains(profile)) {
			assertTrue(true);
		} else {
			LOGGER.info("Unable to set Engine profile :" + profile);
			assertTrue(false);
		}
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_ENGINE_PROFILE_NORMAL)
	public void verifyEngineProfileNormal() {
		final VerifyEngineInstallationOperator engineOperator = engine_provider.get();
		final String profile = "Normal";

		LOGGER.info("Verifying the engine profile can be set to Loads / No Loads");
		final String profile_results = engineOperator.executeCommand(PROFILE_COMMAND + profile);
		if (profile_results.contains("successfully") && profile_results.contains(profile)) {
			assertTrue(true);
		} else {
			LOGGER.info("Unable to set Engine profile :" + profile);
			assertTrue(false);
		}
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_ENGINE_SERVICE)
	public void verifyschedulerServices() {
		final VerifyEngineInstallationOperator engineOperator = engine_provider.get();
		final String output = engineOperator.executeCommand(ENGINE_SERIVES_CMD);
		if (output.contains("online")) {
			LOGGER.info("Engine Service is Online Command Output : " + output);
			assertTrue(true);
		} else {
			LOGGER.info("Engine Service is NOT Online Command Output : " + output);
			assertTrue(false);
		}
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_ENGINE_LIECENSE)
	public void verifyEngineLicense() throws InterruptedException{
		final VerifyEngineInstallationOperator engineOperator = engine_provider.get();
		engineOperator.executeCommandDcuser("engine restart");
		TimeUnit.SECONDS.sleep(60);
		final String output = engineOperator.executeCommand(ENGINE_LIECENSE_LOG_CMD);
		if (! output.isEmpty())
		{
			assertTrue(output.contains("Starter license successfully verified"), "Expected 'Starter license successfully verified' but found :" + output);
		}
		else
		{
			final String output1 = engineOperator.executeCommand(ENGINE_LIECENSE_LOG_CMD_CHECK);
			assertTrue(output1.contains("Proceeding with valid ENIQ Statistics Starter and Capacity license"), "Expected 'Proceeding with valid ENIQ Statistics Starter and Capacity license' but found :" + output);
		}
		
	}

	public static class StepIds {
		public static final String VERIFY_PLATFORM_LOG_EXISTS = "Verify Platform Log exists.";
		public static final String VERIFY_PLATFORM_LOG_ERRORS = "Verify Platform Log any error exists.";
		public static final String VERIFY_PLATFORM_VERSION_DB_PROPERTIES = "Verify the each platform module version is updated in versiondb.properties file	";
		public static final String VERIFY_PLATFORM_MODULES_EXTRACTED = "Verify the each platform module package is extracted under /eniq/sw/platform	";
		public static final String VERIFY_ENGINE_PROFILE_NOLOAD = "Verify the engine profile can be set to Loads / No Loads";
		public static final String VERIFY_ENGINE_PROFILE_NORMAL = "Verify the engine profile can be set to Loads / No Loads";
		public static final String VERIFY_ENGINE_SERVICE = "Verify the dependency services(svcs -d engine) of engine.";
		public static final String VERIFY_ENGINE_LIECENSE = "Verify the license check.";

		private StepIds() {
		}
	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class Parameters {
		public static final String PLATFORM_MODULE = "module";

		private Parameters() {
		}

	}

	private String getRstate(String moduleName) {
		return getModuleRstates().get(moduleName) == null ? "" : getModuleRstates().get(moduleName);

	}
}
