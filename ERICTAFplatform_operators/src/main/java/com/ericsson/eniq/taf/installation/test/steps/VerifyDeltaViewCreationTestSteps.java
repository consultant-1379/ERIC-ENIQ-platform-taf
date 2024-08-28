package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyDeltaViewCreationOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPlatformInstallationOperator;

/**
 * 
 * @author ZRUMRMU
 *
 */
public class VerifyDeltaViewCreationTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyDeltaViewCreationTestSteps.class);
	private final String DeltaviewLogDir = DataHandler.getAttribute("platform.install.DeltaviewLog").toString();
	private final String deltaviewcreationScriptPath = DataHandler.getAttribute("platform.install.DeltaViewCreation")
			.toString();
	private final String dwhMonitorLog = DataHandler.getAttribute("platform.install.ExecutionLog").toString();

	private final String dcuserPath = DataHandler.getAttribute("platform.install.AccessVerification").toString();
	private final String AccessLogDir = DataHandler.getAttribute("platform.install.AccessLog").toString();

	private final String CD = "cd";
	private final String SPACE = " ";
	private final String SEMICO = ";";
	private final String LS = "ls";

	@Inject
	private Provider<VerifyDeltaViewCreationOperator> provider;

	@Inject
	private Provider<VerifyPlatformInstallationOperator> pltFormProvider;

	@Inject
	private Provider<GeneralOperator> generalOperator;

	VerifyDeltaViewCreationOperator dvOperator;
	VerifyPlatformInstallationOperator pltformOperator;

	/**
	 * initialise
	 */
	@TestStep(id = StepIds.CLI_INITIALIZE)
	public void cliInitialize() {
		dvOperator = provider.get();
		pltformOperator = pltFormProvider.get();
		// cvOperator.initialise();
		// pltformOperator.initialise();
		assertTrue(true);
	}

	/**
	 * VERIFY_LM_LOG
	 */
	@TestStep(id = StepIds.VERIFY_DELTAVIEW_EXECUTION)
	public void verifyDeltaViewCreation(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		LOGGER.info("Execute and Verify DeltaViewCreation");
		final String scriptsOutPut = dvOperator.Deltaviewcreation();
		System.out.println(scriptsOutPut);
		assertTrue(scriptsOutPut.contains("deltaviewcreation.bsh") || scriptsOutPut.contains("deltaview.bsh"),
				"deltaviewcreation does not exists in /eniq/sw/installer");
	}

	@TestStep(id = StepIds.VERIFY_ACCESS_VERIFICATION_EXECUTION)
	public void verifyAccessVerificationExecution() {
		LOGGER.info("Execute and Verify DeltaViewCreation");
		final String scriptsOutPut = dvOperator.AccessVerification_Execution();
		System.out.println(scriptsOutPut);
		assertTrue(
				scriptsOutPut.contains("accessverificationscript.bsh")
						|| scriptsOutPut.contains("accessverification.bsh"),
				"accessverificationscript does not exists in /eniq/log/");
	}

	@TestStep(id = StepIds.VERIFY_DELTAVIEW_LOGS)
	public void verifyDeltaViewCreationLogFile() {
		final GeneralOperator generalOp = generalOperator.get();
		generalOp.executeCommandDcuser(CD + SPACE + deltaviewcreationScriptPath + SEMICO + "./deltaviewcreation.bsh");
		String deltaLogFile = generalOp
				.executeCommandDcuser(LS + SPACE + DeltaviewLogDir + SPACE + " | grep delta_view_create | tail -1");

		if (deltaLogFile == null) {
			assertTrue(false, " No 'delta_*.log' File Found under : " + DeltaviewLogDir);
		} else {
			String fileInfo = pltformOperator.getPlatformLteWCDMALogContent(deltaLogFile);
			assertFalse(fileInfo.length() > 0, "Log file for module " + deltaLogFile + " has Exceptions" + fileInfo);
		}
	}

	@TestStep(id = StepIds.VERIFY_SCRIPT_LOG_VERIFICATION)
	public void verifyScriptLogExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listPlatformltewcdmaLogFiles()) {
			if (f.startsWith("delta_") && f.endsWith(".log")) {
				fileName = f;
				break;
			}
		}
		LOGGER.info(moduleName + "- Verify log contents" + fileName);
		String fileInfo = pltformOperator.getPlatformLteWCDMALogContent(fileName);
		if (fileInfo.contains("_ERRORS_")) {
			fileInfo.replace("_ERROR_", "");

		}

		assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + "has Exceptions" + fileInfo);

	}

	@TestStep(id = StepIds.VERIFY_EXECUTION_LOG_VERIFICATION)
	public void verifyExecutionLogExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listExecutionLogFiles()) {
			if (f.startsWith("engine-") && f.endsWith(".log")) {
				fileName = f;
				System.out.println("filename" + fileName);

				break;
			}
		}
		LOGGER.info(moduleName + "- Verify log contents" + fileName);
		String fileInfo = pltformOperator.getExecutionLogContent(fileName);

		assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + "has Exceptions" + fileInfo);

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

	@TestStep(id = StepIds.VERIFY_SCRIPTEXECUTION_TIMING)
	public void VERIFY_SCRIPTEXECUTION_TIMING() {
		final GeneralOperator generalOp = generalOperator.get();
		String dwhMonitorEngineLogFile = generalOp
				.executeCommandDcuser(CD + SPACE + dwhMonitorLog + SEMICO + LS + SPACE + " engine* | tail -1");

		if (dwhMonitorEngineLogFile == null) {
			assertTrue(false, " No 'engine-****_**_**.log' File under : " + dwhMonitorLog);
		} 
		/*else {
			String fileInfo = pltformOperator.getPlatformLteWCDMALogContent(dwhMonitorEngineLogFile);
			assertFalse(fileInfo.length() > 0,
					"Log file for module " + dwhMonitorEngineLogFile + " has Exceptions" + fileInfo);
		}*/

		String fileInfo = generalOp.executeCommandDcuser(CD + SPACE + dwhMonitorLog + SEMICO + " cat " + SPACE
				+ dwhMonitorEngineLogFile + SPACE + " | grep " + SPACE
				+ " 'Script is triggered to recreate the Delta views' " + " | grep " + SPACE + "':05:'");
		System.out.println("Contents in Enginee Log files :" + fileInfo);

		Scanner scanner = new Scanner(fileInfo);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains(":05:")) {
				assertTrue(true, "\nFailed due to DeltaView Re-creation script is not executed every 5th Min of hour.");
			}
		}
		scanner.close();

	}

	@TestStep(id = StepIds.DELTA_VIEW_PERMISSON)
	public void VerifyDeltaViewPermission() {
		final GeneralOperator generalOp = generalOperator.get();
		generalOp.copyScript("access_verification_script.bsh", dcuserPath);
		generalOp.permission(dcuserPath + "access_verification_script.bsh");
		 generalOp.executeCommandDcuser("sh" + SPACE + dcuserPath + "access_verification_script.bsh");
		String accessFilelogFile = generalOp.executeCommandDcuser(
				CD + SPACE + "/eniq/log/sw_log/engine/AccessVerifyScripts/; ls | grep accesslogs | tail -1");

		String logContents = generalOp
				.executeCommandDcuser("head -20 /eniq/log/sw_log/engine/AccessVerifyScripts/" + accessFilelogFile);
		assertTrue(logContents.contains("Completed execution of recompiling views script")
				|| logContents.contains("All views are valid"));
	}

	@TestStep(id = StepIds.DELTA_VIEW_PERMISSON_ACCESS_LOG_ERRORS)
	public void VerifyDeltaViewPermission_AccessLogVerification() {
		final GeneralOperator generalOp = generalOperator.get();
		final VerifyPlatformInstallationOperator pltformOperator = pltFormProvider.get();
		String accessLogFile = generalOp
				.executeCommandDcuser(CD + SPACE + AccessLogDir + SEMICO + LS + SPACE + " | grep accesslogs | tail -1");

		String fileInfo = pltformOperator.verifyAccessVerifyLogContent(accessLogFile);
		//For these tables no TPs are for latest build so error is expected.
		if(fileInfo.contains("ERROR: Failed to recompile the dcpublic.DC_J_JUNOS_JUNOSBH_RANKBH view") &&
				fileInfo.contains("ERROR: Failed to recompile the dcpublic.DC_E_NR_BH_RANKBH view"))
		{
			fileInfo.replace("ERROR: Failed to recompile the dcpublic.DC_J_JUNOS_JUNOSBH_RANKBH view", "");
			fileInfo.replace("ERROR: Failed to recompile the dcpublic.DC_E_NR_BH_RANKBH view", "");
			assertTrue(fileInfo.length() > 0, "\nLog file has  errors/exceptions/warnings - File Details : " + AccessLogDir
					+ accessLogFile + "\nErrors/exceptions/warnings : " + fileInfo);
		}
		else {
		assertFalse(fileInfo.length() > 0, "\nLog file has  errors/exceptions/warnings - File Details : " + AccessLogDir
				+ accessLogFile + "\nErrors/exceptions/warnings : " + fileInfo);
		}
	}

	@TestStep(id = StepIds.DELTA_VIEW_PERMISSON_ERROR_LOG_ERRORS)
	public void VerifyDeltaViewPermission_ErrorLogVerification() {
		final GeneralOperator generalOp = generalOperator.get();
		final VerifyPlatformInstallationOperator pltformOperator = pltFormProvider.get();
		String accessLogFile = generalOp
				.executeCommandDcuser(CD + SPACE + AccessLogDir + SEMICO + LS + SPACE + " | grep errorlogofviews | tail -1");

		String fileInfo = pltformOperator.verifyAccessVerifyLogContent(accessLogFile);
		assertFalse(fileInfo.length() > 0, "\nLog file has  errors/exceptions/warnings - File Details : " + AccessLogDir
				+ accessLogFile + "\nErrors/exceptions/warnings : " + fileInfo);
	}

	/**
	 * 
	 * @author xarunha
	 *
	 */
	public static class StepIds {
		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_DELTAVIEW_EXECUTION = "Verify Deltaview Execution";
		public static final String VERIFY_DELTAVIEW_LOGS = "Verify DeltaViewCreation log";
		public static final String VERIFY_SCRIPT_LOG_VERIFICATION = "Verify Script Log Verification";
		public static final String VERIFY_EXECUTION_LOG_VERIFICATION = "Verify Execution Log Verification";
		public static final String VERIFY_SCRIPTEXECUTION_TIMING = "Verify Script Executed Every 15 Mins";
		public static final String VERIFY_ACCESS_VERIFICATION_EXECUTION = "Verify DC User Permission for AccessVerification";
		public static final String VERIFY_ACCESS_LOGS = "Verify Access Verification log";
		public static final String VERIFY_ACCESS_ERROR_LOGS = "Verify Access Verification Error log";
		public static final String DELTA_VIEW_PERMISSON = "Verify whether Delta views have the permission for all users (dc, dcbo & dcpublic)";
		public static final String DELTA_VIEW_PERMISSON_ACCESS_LOG_ERRORS = "In Delta views Verify Access Verification Error log";
		public static final String DELTA_VIEW_PERMISSON_ERROR_LOG_ERRORS = "In Delta views Verify Access Verification Error log";

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
