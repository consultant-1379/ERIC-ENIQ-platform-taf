package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPortInstallUpgradeScriptsOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class VerifyPortInstallUpgradeScriptsSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyPortInstallUpgradeScriptsSteps.class);
	private List<String> logFiles = new ArrayList<>();

	@Inject
	private Provider<VerifyPortInstallUpgradeScriptsOperator> provider;

	private final String assureddc_PKG = DataHandler.getAttribute("platform.log.assureddc.pkg").toString();
	private static final String MWS_PATH = DataHandler.getAttribute("platform.mws.path").toString();
	private static final String INSTALL_SCRIPT = DataHandler.getAttribute("platform.mws.install.script").toString();

	/**
	 * @DESCRIPTION This Test Case covers the verification of platform modules
	 *              installation logs on RHEL environments
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_INSTALL_SCRIPT_EXISTS_IN_MWS)
	public void verifyInstallScriptIsExists() {
		LOGGER.info("Verifying install_eniq.sh script is available under /eniq_sw path on MWS server");
		final VerifyPortInstallUpgradeScriptsOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		final String output = platformLogOperator
				.executeWithoutSSHCommand("cd " + MWS_PATH + ";" + " ls | grep " + INSTALL_SCRIPT);
		LOGGER.info("Command Output : " + output);
		if (output.contains(INSTALL_SCRIPT)) {
			LOGGER.info(INSTALL_SCRIPT + " script is available under /eniq_sw/ path on MWS server.");
			assertTrue(true);
		} else {
			LOGGER.info(INSTALL_SCRIPT + " script is NOT available under /eniq_sw/ path on MWS server.");
			assertTrue(false);
		}
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_LOG_EXISTS)
	public void verifyPlatFormLogExists(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		LOGGER.info("Verifying log file Exists for : " + moduleName);
		final VerifyPortInstallUpgradeScriptsOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		if (logFiles.size() == 0) {
			logFiles = platformLogOperator.listPlatformLogFiles();
		}
		boolean fileExists = false;
		for (final String f : logFiles) {
			if (f.startsWith(moduleName + "_" + getRstate(moduleName)) && f.endsWith(".log")) {
				fileExists = true;
				LOGGER.info("Log file for Module : " + f + " is exists");
				break;
			}
		}
		assertTrue(fileExists, "Log file for module " + moduleName + getRstate(moduleName) + " not Exists");
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_LOG_ERRORS)
	public void verifyPlatFormLogFile(@Input(Parameters.PLATFORM_MODULE) String moduleName) {

		final VerifyPortInstallUpgradeScriptsOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();

		String fileName = null;
		for (final String f : logFiles) {
			if (f.startsWith(moduleName + "_" + getRstate(moduleName)) && f.endsWith(".log")) {
				fileName = f;
				break;
			}
		}
		LOGGER.info("Verify log contents for the module : " + fileName);
		final String fileInfo = platformLogOperator.getPlatformLogContent(fileName);
		assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + " has Exceptions \n" + fileInfo);
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_VERSION_DB_PROPERTIES)
	public void verifyVersionDBPropetiesUpdated(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		LOGGER.info(moduleName + "- Verify VersionDBPropetiesUpdated");
		final VerifyPortInstallUpgradeScriptsOperator platformOperator = provider.get();
		platformOperator.initialise();
		final String output = platformOperator.versionDBModuleUpdated(moduleName);
		assertTrue(output.contains("module." + moduleName + "=" + getRstate(moduleName)),
				"versiondb.properties is not updated for module " + moduleName + ":" + output);
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_PLATFORM_MODULES_EXTRACTED)
	public void verifyModulesExtracted(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		LOGGER.info("Verifying Extraction for : " + moduleName);
		final VerifyPortInstallUpgradeScriptsOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		final String output = platformLogOperator.modulesExtracted(moduleName);
		if (moduleName.equals("AdminUI") || moduleName.equals("BusyHour") || moduleName.equals("alarmcfg")
				|| moduleName.equals("helpset_stats")) {
			assertTrue(true);
		} else {
			assertTrue(output.contains(moduleName + "-" + getRstate(moduleName)),
					moduleName + "-" + getRstate(moduleName) + " is not present under /eniq/sw/platform ");
		}
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_SPECIFIC_LOG_IS_EXISTS)
	public void verifyAssureddcIsExists() {
		LOGGER.info("Verifying assureddc log file is exists in the log path /eniq/log/sw_log/platform_installer : "
				+ assureddc_PKG);
		final VerifyPortInstallUpgradeScriptsOperator platformLogOperator = provider.get();
		final String output = platformLogOperator.specificModuleExtracted(assureddc_PKG);
		if (output.contains(assureddc_PKG)) {
			LOGGER.info(assureddc_PKG + " log file is exists in the log path /eniq/log/sw_log/platform_installer.");
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

	public static class StepIds {
		public static final String VERIFY_INSTALL_SCRIPT_EXISTS_IN_MWS = "Verify that install_eniq.sh is available under /eniq_sw/ path on MWS server.";
		public static final String VERIFY_PLATFORM_LOG_EXISTS = "Verify Platform Log exists.";
		public static final String VERIFY_PLATFORM_LOG_ERRORS = "Verify Platform Log any error exists.";
		public static final String VERIFY_PLATFORM_VERSION_DB_PROPERTIES = "Verify the each platform module version is updated in versiondb.properties file.";
		public static final String VERIFY_PLATFORM_MODULES_EXTRACTED = "Verify the each platform module package is extracted under /eniq/sw/platform.";
		public static final String VERIFY_SPECIFIC_LOG_IS_EXISTS = "Verify assureddc package is not getting installed during installation/upgarde on ES server.";

		private StepIds() {
		}
	}

	public static class Parameters {
		public static final String PLATFORM_MODULE = "platform_modules";

		private Parameters() {
		}
	}

	private String getRstate(String moduleName) {
		return getModuleRstates().get(moduleName) == null ? "" : getModuleRstates().get(moduleName);
	}
}
