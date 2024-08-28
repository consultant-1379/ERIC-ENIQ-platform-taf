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

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyInstallerOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class VerifyInstallerTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyInstallerTestSteps.class);

	@Inject
	private Provider<VerifyInstallerOperator> provider;

	private List<String> logFiles = new ArrayList<>();
	private final String INSTALLER_MODULE = "installer";

	/**
	 * @DESCRIPTION This Test Case covers the verification of installer module logs
	 *              on RHEL environments
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_INSTALLER_LOG_EXISTS)
	public void verifyPlatFormLogExists() {
		LOGGER.info(INSTALLER_MODULE + "- Verify log file Exists.");
		final VerifyInstallerOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		if (logFiles.size() == 0) {
			logFiles = platformLogOperator.listPlatformLogFiles();
		}
		boolean fileExists = false;
		for (final String f : logFiles) {
			if (f.startsWith(INSTALLER_MODULE + "_") && f.endsWith(".log")) {
				fileExists = true;
				LOGGER.info("file name:::::" + f);
				break;
			}
		}
		assertTrue(fileExists,
				"Log file for module " + INSTALLER_MODULE + "_"  + " not Exists under /eniq/log/sw_log/platform_installer/");
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_INSTALLER_LOG_ERRORS)
	public void verifyPlatFormLogFile() {
		final VerifyInstallerOperator platformLogOperator = provider.get();

		String fileName = null;
		for (final String f : logFiles) {
			if (f.startsWith(INSTALLER_MODULE + "_") && f.endsWith(".log")) {
				fileName = f;
				break;
			}
		}
		LOGGER.info(INSTALLER_MODULE + "- Verify log contents" + fileName);
		final String fileInfo = platformLogOperator.getInstallerModuleLogContent(fileName);
		assertFalse(fileInfo.length() > 0, " Log file for module " + INSTALLER_MODULE + "_" + " has Exceptions " + fileInfo);
	}

	/**
	 * 
	 * @param moduleName moduleName
	 */
	@TestStep(id = StepIds.VERIFY_INSTALLER_VERSION_DB_PROPERTIES)
	public void verifyVersionDBPropetiesUpdated() {

		LOGGER.info(INSTALLER_MODULE + "- Verify VersionDBPropetiesUpdated");
		final VerifyInstallerOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		final String output = platformLogOperator.versionDBModuleUpdated(INSTALLER_MODULE);
		assertTrue(output.contains("module." + INSTALLER_MODULE + "=" + getRstate(INSTALLER_MODULE)),
				"versiondb.properties is not updated for module " + INSTALLER_MODULE + " : " + output);
	}

	public static class StepIds {
		public static final String VERIFY_INSTALLER_LOG_EXISTS = "Verify the Installation log created for Install Module under the path /eniq/logs/sw_log/platform_installer.";
		public static final String VERIFY_INSTALLER_LOG_ERRORS = "Verify there are no exceptions, warnings, errors or failures in the installation logs";
		public static final String VERIFY_INSTALLER_VERSION_DB_PROPERTIES = "Verify the installer module version is updated in versiondb.properties file.";

		private StepIds() {
		}
	}

	private String getRstate(String moduleName) {
		return getModuleRstates().get(moduleName) == null ? "" : getModuleRstates().get(moduleName);
	}
}
