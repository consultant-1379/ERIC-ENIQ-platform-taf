package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyCombinedViewFunctionalityOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPlatformInstallationOperator;

/**
 * 
 * @author zvaddee
 *
 */
public class VerifyCombinedViewFunctionalityTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyCombinedViewFunctionalityTestSteps.class);

	@Inject
	private Provider<VerifyCombinedViewFunctionalityOperator> provider;

	@Inject
	private Provider<VerifyPlatformInstallationOperator> pltFormProvider;

	VerifyCombinedViewFunctionalityOperator cvOperator;
	VerifyPlatformInstallationOperator pltformOperator;

	/**
	 * initialise
	 */
	@TestStep(id = StepIds.CLI_INITIALIZE)
	public void cliInitialize() {
		cvOperator = provider.get();
		pltformOperator = pltFormProvider.get();
		// cvOperator.initialise();
		// pltformOperator.initialise();
		assertTrue(true);
	}

	/**
	 * VERIFY_LM_LOG
	 */
	@TestStep(id = StepIds.VERIFY_INSTALLER_INSTALLATION)
	public void veriyInstallerInstallation() {
		final VerifyCombinedViewFunctionalityOperator cvOperator = provider.get();
		LOGGER.info("veriyInstallerInstallation");
		final String op = cvOperator.installetionOfInstaller();
		LOGGER.info("log output:::" + op);
		assertTrue(op.contains("Updating version database..."), " Error Versiondb.peroperties is not updated");
		assertTrue(op.contains("Installer installed"), " Error while installing installer");
	}

	/**
	 * VERIFY_LM_LOG
	 */
	@TestStep(id = StepIds.VERIFY_VERSION_DB_PROPERTIES)
	public void veriyVersionDBProperties() {
		LOGGER.info("veriyVersionDBProperties");
		final String op = cvOperator.versionDBModuleUpdated();
		LOGGER.info("log output:::" + op + "r state:::" + getRstate("installer"));
		assertTrue(op.contains("installer=" + getRstate("installer")),
				"versiondb.properties is not updated for installer.");
	}

	@TestStep(id = StepIds.VERIFY_INSTALLER_LOGS)
	public void verifyInstallerLogFile() {
		final VerifyCombinedViewFunctionalityOperator cvOperator = provider.get();
		String fileName = cvOperator.executeCommandDcuser("cd /eniq/log/sw_log/platform_installer/ ; ls | grep installer | tail -1");
		LOGGER.info("Verify log contents" + fileName);
		final String fileInfo = cvOperator.PFlogContent(fileName);
		assertFalse(fileInfo.length() > 0, "Log file for module " + fileName + " has Exceptions" + fileInfo);

	}

	@TestStep(id = StepIds.VERIFY_LTE_WCDMA_SCRIPTS)
	public void verifyLTEandWCDMAScripts() {
		final VerifyCombinedViewFunctionalityOperator cvOperator = provider.get();
		LOGGER.info("verify LTE and WCDMA Scripts exists under /eniq/sw/installer");
		final String WCDMACombinedViewCreation = cvOperator
				.executeCommandDcuser("cd /eniq/sw/installer;ls WCDMACombinedViewCreation.bsh");
		final String erbscombinedview = cvOperator
				.executeCommandDcuser("cd /eniq/sw/installer;ls erbscombinedview.bsh");
		assertTrue(
				WCDMACombinedViewCreation.contains("WCDMACombinedViewCreation.bsh")
						&& erbscombinedview.contains("erbscombinedview.bsh"),
				"erbscombinedview.bsh or WCDMACombinedViewCreation.bsh does not exists in /eniq/sw/installer");
	}

	@TestStep(id = StepIds.VERIFY_LTEWCDMAINSTALLER_LOGS)
	public void verifyInstallerlteLogFile() {
		final VerifyCombinedViewFunctionalityOperator cvOperator = provider.get();
		final String erbs_combined_view_createLogfile = cvOperator
				.executeCommandDcuser("cd /eniq/log/sw_log/tp_installer/;ls erbs_combined_view_create*.log");
		final String erbs_combined_view_createLogfileContent = cvOperator
				.logContent(erbs_combined_view_createLogfile);
		assertTrue(erbs_combined_view_createLogfileContent.length() == 0 ,"Log file has exception : /eniq/log/sw_log/tp_installer/" +erbs_combined_view_createLogfile + "\n" + erbs_combined_view_createLogfileContent);
		
	}

	@TestStep(id = StepIds.VERIFY_G1_G2_VIEWS_ARE_ACCESSIBLE)
	public void verifyG1andG2ViewsAccessible(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		LOGGER.info("verify G1 and G2 Views are Accessible");
		final String scriptsOutPut = cvOperator.getLteAndWcdmaScripts();
		assertTrue(
				scriptsOutPut.contains("WCDMACombinedViewCreation.bsh")
						&& scriptsOutPut.contains("erbscombinedview.bsh"),
				"erbscombinedview.bsh or WCDMACombinedViewCreation.bsh does not exists in /eniq/sw/installer");
	}

	@TestStep(id = StepIds.VERIFY_ACCESS_VERIFICATION_EXECUTION)
	public void verifyAccessVerificationExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		LOGGER.info("Execute and Verify DeltaViewCreation");
		final String scriptsOutPut = cvOperator.AccessVerification_Execution();
		System.out.println(scriptsOutPut);
		assertTrue(
				scriptsOutPut.contains("accessverificationscript.bsh")
						|| scriptsOutPut.contains("accessverification.bsh"),
				"accessverificationscript does not exists in /eniq/log/");
	}

	@TestStep(id = StepIds.VERIFY_ACCESS_LOGS)
	public void verifyAccessLogExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listAccessLogFiles()) {
			if (f.startsWith("access") && f.endsWith(".txt")) {
				fileName = f;
				System.out.println("filename" + fileName);

				break;
			}
		}
		LOGGER.info(moduleName + "- Verify log contents" + fileName);
		String fileInfo = pltformOperator.getAccessVerifyLogContent(fileName);
		assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + "has Exceptions" + fileInfo);

	}

	@TestStep(id = StepIds.VERIFY_ACCESS_ERROR_LOGS)
	public void verifyAccessVerifyErrorLogExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listAccessLogFiles()) {
			if (f.startsWith("error") && f.endsWith(".txt")) {
				fileName = f;
				System.out.println("filename" + fileName);

				break;
			}
		}
		LOGGER.info(moduleName + "- Verify log contents" + fileName);
		String fileInfo = pltformOperator.getAccessVerifyLogContent(fileName);
		assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + "has Exceptions" + fileInfo);

	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class StepIds {
		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_INSTALLER_INSTALLATION = "Verify installation of installer";
		public static final String VERIFY_VERSION_DB_PROPERTIES = "Verify versiondb.properties";
		public static final String VERIFY_INSTALLER_LOGS = "Verify installation of installer log";
		public static final String VERIFY_LTEWCDMAINSTALLER_LOGS = "Verify installation of installer log";
		public static final String VERIFY_LTE_WCDMA_SCRIPTS = "Verify LTE and WCDMA Scripts are present";
		public static final String VERIFY_G1_G2_SCRIPTS = "Verify G1 and G2 Scripts Present";
		public static final String VERIFY_G1_AND_G2_LOGS = "Verify G1 and G2 Acess Scripts log";
		public static final String VERIFY_G1_G2_VIEWS_ARE_ACCESSIBLE = "Verify G1 & G2 views of LTE are accessible";
		public static final String VERIFY_ACCESS_VERIFICATION_EXECUTION = "Verify DC User Permission for AccessVerification";
		public static final String VERIFY_ACCESS_LOGS = "Verify Access Verification log";
		public static final String VERIFY_ACCESS_ERROR_LOGS = "Verify Access Verification Error log";

		private StepIds() {
		}
	}

	public static class Parameters {
		public static final String PLATFORM_MODULE = "module";

		private Parameters() {
		}
	}

	private String getRstate(String moduleName) {
		return getModuleRstates().get(moduleName) == null ? "" : getModuleRstates().get(moduleName);

	}

}
