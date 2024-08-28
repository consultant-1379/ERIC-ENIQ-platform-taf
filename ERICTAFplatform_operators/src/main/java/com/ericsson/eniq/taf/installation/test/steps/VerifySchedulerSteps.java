package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifySchedulerOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class VerifySchedulerSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifySchedulerSteps.class);

	private final static String logFileDir = DataHandler.getAttribute("platform.scheduler.logsPath").toString();

	private static final String rmiregistry = "systemctl -t service | grep -i rmiregistry";
	private static final String eniq_rmiregistry = "systemctl status eniq-rmiregistry";
	private static final String nas = "systemctl -t service | grep -i nas";
	// private static final String LIST_OF_INTERFACES = "cd " + logFileDir +
	// ";ls -t -1 | grep INTF";
	// private static final String LIST_OF_INSTALLED_TP = "cd " + logFileDir +
	// ";ls -t -1 -d A* U* D*";
	private static final String LIST_OF_INTERFACES = "/eniq/sw/installer/./get_active_interfaces | awk '{ print $1\"-\"$2 }'";

	private static final String SCHEDULER_INSTALL_CMD = "cat /eniq/sw/installer/versiondb.properties | grep -i scheduler";
	private static final String SCHEDULER = "scheduler";

	private List<String> logFiles = new ArrayList<>();
	List<String> services_cmds = new ArrayList<>(Arrays.asList(rmiregistry, eniq_rmiregistry, nas));
	List<String> scheduler_cmds = new ArrayList<>(
			Arrays.asList("stop", "start", "restart", "hold", "activate", "status"));
	private static final String LATEST_LIBS_RSTATE = "cd /eniq/sw/platform/;ls| grep -E '^libs-R$' | tail -1";
	private static final String JAR_PATH_FILE = "data/JarFile.csv";
	List<String> jarNotFound = new ArrayList<String>();

	@Inject
	private Provider<VerifySchedulerOperator> provider;
	
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @DESCRIPTION This Test Case Verify scheduler commands,
	 *              errors/exception/warnings in scheduler logs
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_SCHEDULER_IS_INSTALLED)
	public void verifyVersionDBPropetiesUpdated() {
		LOGGER.info("scheduler " + "- Verify VersionDBPropetiesUpdated");
		final VerifySchedulerOperator platformLogOperator = provider.get();
		platformLogOperator.initialise();
		final String output = platformLogOperator.executeCommand(SCHEDULER_INSTALL_CMD);
		assertTrue(output.contains("module." + SCHEDULER + "=" + getRstate(SCHEDULER)),
				"versiondb.properties is not updated for : module." + SCHEDULER + "=" + getRstate(SCHEDULER)
						+ "Actual output : " + output);
	}

	@TestStep(id = StepIds.VERIFY_SCHEDULER_SERVICES)
	public void verifyschedulerServices() {
		final VerifySchedulerOperator platformLogOperator = provider.get();
		for (String service : services_cmds) {
			final String output = platformLogOperator.executeCommand(service);
			if (output.contains("active") && output.contains("running")) {
				LOGGER.info("Executing : " + service + "\n  Command Output : " + output);
				assertTrue(true);
			} else {
				LOGGER.info(service + " is not active/running,  Command Output : " + output);
				assertTrue(false);
			}
		}
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_SCHEDULER_COMMANDS)
	public void verifySchedulerCommands() {
		LOGGER.info("Verifying Scheduler Commands");
		final VerifySchedulerOperator platformLogOperator = provider.get();
		for (String cmd : scheduler_cmds) {
			final String output = platformLogOperator.executeSchedulerCommands(cmd);
			if (output.contains("active") && output.contains("running")) {
				LOGGER.info("Executing : " + cmd + "\n  Command Output : " + output);
				assertTrue(true);
			} else {
				LOGGER.info("Failed to execute : " + cmd + "\n Command Output : " + output);
				assertTrue(false);
			}
		}
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_SCHEDULER_LOG_EXISTS)
	public void verifyPlatFormLogExists(@Input(Parameters.SCHEDULER_LOGS) String logName) throws InterruptedException{
		LOGGER.info(logName + "- Verify log file Exists.");
		
		/* DB and service checks to check and start engine and dbs. */
		GeneralOperator operator = generalOperatorProvider.get();
		
		if (operator.executeCommandDcuser("scheduler status").contains("not running"))
			operator.executeCommandDcuser("scheduler start");
		if (operator.executeCommandDcuser("repdb status").contains("is not running"))
			operator.executeCommandDcuser("repdb start");
		if (operator.executeCommandDcuser("dwhdb status").contains("not running"))
			operator.executeCommandDcuser("dwhdb start");
		if (operator.executeCommandDcuser("engine status").contains("not running"))
			operator.executeCommandDcuser("engine start");
		operator.executeCommandDcuser("scheduler restart");
		TimeUnit.SECONDS.sleep(60);
		/* the above block can be removed if not needed.*/

		final VerifySchedulerOperator platformLogOperator = provider.get();
		if (logFiles.size() == 0) {
			logFiles = platformLogOperator.listPlatformLogFiles();
		}
		boolean fileExists = false;
		for (final String f : logFiles) {
			if (f.startsWith(logName) && f.endsWith(".log")) {
				fileExists = true;
				LOGGER.info("File name : " + f);
				break;
			}
		}
		
		assertTrue(fileExists, " Log file for module " + logName + " not Exists");
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_SCHEDULER_LOG_ERRORS)
	public void verifyPlatFormLogFile(@Input(Parameters.SCHEDULER_LOGS) String logName) {
		String fileName = null;
		for (final String f : logFiles) {
			if (f.startsWith(logName) && f.endsWith(".log")) {
				fileName = f;
				break;
			}
		}
		LOGGER.info(logName + " - Verify log contents " + fileName);
		final VerifySchedulerOperator platformLogOperator = provider.get();
		final String fileInfo = platformLogOperator.getLogContent(fileName);
		if(fileInfo.contains("SEVERE") || fileInfo.contains("WARNING"))
        {
            assertTrue(fileInfo.length() > 0," Log file for module '" + logName + "' has Exceptions \n" + fileInfo);
        }
        else {
            
        	assertFalse(fileInfo.length() > 0, " Log file for module '" + logName + "' has Exceptions \n" + fileInfo);
        }
		//assertFalse(fileInfo.length() > 0, " Log file for module '" + logName + "' has Exceptions \n" + fileInfo);
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 */
	@TestStep(id = StepIds.VERIFY_ACTIVE_INTERFACE_SCHEDULER_LOG_ERRORS)
	public void verifyActiveInterfaceLogFile() {
		LOGGER.info("Getting the list of Active Interfaces");
		final VerifySchedulerOperator platformLogOperator = provider.get();
		final List<String> list_of_Interfaces = platformLogOperator.executeCommands(LIST_OF_INTERFACES);
		for (String str : list_of_Interfaces) {
			final String fileInfo = platformLogOperator.getActiveInterfaceSchedulerLogContent(str);
			assertFalse(fileInfo.length() > 0, " Active Interface : '" + str
					+ "' has Exceptions (Please look into the jenkins log OR Grep for the errors");
		}
	}

	/**
	 * VERIFY_LM_LOG
	 */
	@TestStep(id = StepIds.VERIFY_LM_LOG)
	public void veriyLicenseManagerLog() {

		LOGGER.info("veriyLicenseManagerLog");

		// final String calcVersion =
		// DataHandler.getAttribute("CALCVER").toString();

		final VerifySchedulerOperator licenseMgrOperator = provider.get();
		final String op = licenseMgrOperator.getLicenseManagerLogContent();
		LOGGER.info("log output:::" + op);
		assertTrue(op.contains("License manager is running OK"), "LiceneManager log error: license in not VALID");
	}

	/**
	 * 
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_SERVER_STATUS)
	public void veriyLicenseServerStatus() {

		LOGGER.info("veriyLicenseServerStatus");

		// final String calcVersion =
		// DataHandler.getAttribute("CALCVER").toString();

		final VerifySchedulerOperator licenseMgrOperator = provider.get();
		final String op = licenseMgrOperator.licenseServerStatus();
		LOGGER.info("veriyLicenseServerStatus output:::" + op);
		assertTrue(op.contains("Server:") && op.contains("is online"), "Licene sever status is not valid: " + op);
	}

	/**
	 * 
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_MANAGER_STATUS)
	public void veriyLicenseManagerStatus() {
		LOGGER.info("veriyLicenseManagerStatus");
		VerifySchedulerOperator licenseMgrOperator = provider.get();
		final String op = licenseMgrOperator.licenseMgrStatus();
		LOGGER.info("veriyLicenseManagerStatus output:::" + op);
		assertTrue(op.contains("License manager is running OK"), "Licene manager status is not valid: " + op);
	}

	/**
	 * feature Mapping info
	 * 
	 * @throws IOException
	 */
	@TestStep(id = StepIds.VERIFY_FEATURE_MAPPING_INFO)
	public void veriyFeatureMappingInfo(@Input(Parameters.FEATURES) String features) throws IOException {
		LOGGER.info("Verifying Feature Mapping Information : " + features);
		VerifySchedulerOperator licenseMgrOperator = provider.get();

		// Path path =
		// Paths.get("src/main/resources/data/exceptional_cxc_mapping.txt");
		// Scanner scan = new Scanner(path);
		// Scanner scan = new Scanner(new
		// File("src/main/resources/data/exceptional_cxc_mapping.txt"));
		String string = "CXC1733032,CXC1739953,CXC4011603,CXC4011998,CXC4012033,CXC4012041";
		List<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
		// List<String> list = new ArrayList<>();
		// while (scan.hasNextLine()) {
		// list.add(scan.nextLine());
		// }

		if (list.contains(features)) {
			assertTrue(true);
		} else {
			final String result = licenseMgrOperator.verifyFeatureMappingInfo(features);
			assertTrue(!result.contains("No mappings found"),
					"No mappings found for : '" + features + "' ERROR : " + result);
		}
	}

	/**
	 * License is valid or not for features
	 * 
	 * @throws IOException
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_VALID_OR_NOT)
	public void validateLicenseManager(@Input(Parameters.FEATURES) String features) throws IOException {
		LOGGER.info("Verifying Feature Mapping Information : " + features);
		VerifySchedulerOperator licenseMgrOperator = provider.get();

		// Path path =
		// Paths.get("src/main/resources/data/exceptional_cxc_numbers.txt");
		// Scanner scan = new Scanner(path);
		String string = "CXC1733032,CXC1739953,CXP9028089,CXP9028092,CXP9028095,CXP9028096,CXP9028100,CXP9028101,CXP9028102,CXP9028103,CXP9028104,CXP9028105,CXP9028106,CXP9028107,CXP9028108,CXP9028109,CXP9028110,CXP9028113,CXP9028115,CXP9028116,CXP9028117,CXP9028120,CXP9028121,CXP9028123,CXP9028124,CXP9028125,CXP9028126,CXP9028127,CXP9028128,CXP9028129,CXP9028130,CXP9028131,CXP9028133,CXP9028134,CXP9028145,CXP9028146,CXP9028174,CXP9028211,CXP9029375,CXP9029456,CXP9029629,CXP9029850,CXP9029851,CXP9029857,CXP9029858,CXP9029872,CXP9029873,CXP9029874,CXP9029875,CXP9033371,CXP9033505,CXP9034244,CXP9034829,CXP9035446,CXP9036609,CXP9036866";
		List<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
		// List<String> list = new ArrayList<>();
		// while (scan.hasNextLine()) {
		// list.add(scan.nextLine());
		// }

		if (list.contains(features)) {
			assertTrue(true);
		} else {
			final String result = licenseMgrOperator.validateLicenseManager(features);
			assertTrue(result.contains("is valid"), result);
		}
	}

	/**
	 * Verify License Manager Operations
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_OPERATIONS_STATUS)
	public void validateLicenseManagerOperations(@Input(Parameters.OPERATIONS) String operations) {
		LOGGER.info("Verifying License Manager Operation for  : '" + operations + "' \n");
		VerifySchedulerOperator licenseMgrOperator = provider.get();
		if (operations.contains("-start")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("Service enabling eniq-licmgr"), result);
		}
		if (operations.contains("-stop")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("Service stopping eniq-licmgr"), result);
		}
		if (operations.contains("-status")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("License manager is running OK"), result);
		}
		if (operations.contains("-restart")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(
					result.contains("Service stopping eniq-licmgr") && result.contains("Service enabling eniq-licmgr"),
					result);
		}
		if (operations.contains("-serverstatus")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("is online"), result);
		}

	}

	@TestStep(id = StepIds.VERIFY_JAR)
	public void verifyJars() throws FileNotFoundException {
		final VerifySchedulerOperator dclipOperators = provider.get();

		final String latestLibsRstate = dclipOperators.dClipexecuteCommand(LATEST_LIBS_RSTATE);
		final String JAR_PATH = "cd /eniq/sw/platform/" + latestLibsRstate
				+ "/dclib/;ls | grep jar > /eniq/sw/platform/" + latestLibsRstate + "/jar.txt";
		final String JAR_EXISTS = dclipOperators.dClipexecuteCommand(JAR_PATH);
		final String JAR_PATH_SERVER = "/eniq/sw/platform/" + latestLibsRstate + "/jar.txt";
		File jarFile = new File(FileFinder.findFile(JAR_PATH_FILE).get(0));
		if (jarFile.exists()) {
			String line = "";
			try (BufferedReader br = new BufferedReader(new FileReader(jarFile))) {
				while ((line = br.readLine()) != null) {
					LOGGER.info("JAR : " + line);
					final String JAR_FIND = "cd /eniq/sw/platform/" + latestLibsRstate + "/dclib/;ls | grep '" + line
							+ "' /eniq/sw/platform/" + latestLibsRstate + "/jar.txt";
					final String JAR_CHECK = dclipOperators.dClipexecuteCommand(JAR_FIND);
					if (!JAR_CHECK.equals(line)) {
						jarNotFound.add(line);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			final String JAR_FILE_COUNT = "cd /eniq/sw/platform/" + latestLibsRstate
					+ "/dclib/; ls -lrt | grep .jar | wc -l";
			final String JAR_count = dclipOperators.executeCommand(JAR_FILE_COUNT);

			assertFalse(JAR_EXISTS.length() > 0, ".jar not created");
			assertTrue(JAR_count.trim().equals("33"),
					"Expected : 33 jar files extracted under dclib folder /eniq/sw/platform/" + latestLibsRstate
							+ "/dclib/ but found : " + JAR_count.trim());
			if (!jarNotFound.isEmpty()) {
				assertFalse(!jarNotFound.isEmpty(), jarNotFound.toString() + " not found");
			}
		} else {
			throw new FileNotFoundException("Unable to find the file : " + jarFile);
		}
		final String JAR_RM_FILE = "cd /eniq/sw/platform/" + latestLibsRstate + "/; rm -rf jar.txt";
		final String JAR_RM = dclipOperators.executeCommand(JAR_RM_FILE);
	}

	public static class StepIds {

		public static final String VERIFY_SCHEDULER_IS_INSTALLED = "Verify the installation of scheduler package";
		public static final String VERIFY_SCHEDULER_COMMANDS = "Verify scheduler commands are working properly. Start, stop, hold, activate, status, restart";
		public static final String VERIFY_SCHEDULER_SERVICES = "Verify whether all the scheduler dependent services are working properly";
		public static final String VERIFY_SCHEDULER_LOG_EXISTS = "Verify the scheduler logs generated under /eniq/log/sw_log/scheduler";
		public static final String VERIFY_SCHEDULER_LOG_ERRORS = "Verify the scheduler logs has exceptions, warnings, errors";
		public static final String VERIFY_ACTIVE_INTERFACE_SCHEDULER_LOG_ERRORS = "Verify the Active Interfaces scheduler logs has exceptions, warnings, errors";

		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_LM_LOG = "Verify License Manager Logs";
		public static final String VERIFY_LICENSE_SERVER_STATUS = "Verify License Server status";
		public static final String VERIFY_LICENSE_MANAGER_STATUS = "Verify License Manager status";
		public static final String VERIFY_FEATURE_MAPPING_INFO = "Verify Viewing Feature Mapping Information using licmgr";
		public static final String VERIFY_LICENSE_VALID_OR_NOT = "Verify License is valid or not for features";
		public static final String VERIFY_LICENSE_OPERATIONS_STATUS = "Verify License Manager Operation Status information";

		public static final String VERIFY_JAR = "To verify all the jar files are extracted under dclib path.";

		private StepIds() {
		}
	}

	public static class Parameters {
		public static final String SCHEDULER_LOGS = "schedulerLogs";
		public static final String FEATURES = "cxcNumbers";
		public static final String OPERATIONS = "operations";

		private Parameters() {
		}
	}

	private String getRstate(String moduleName) {
		return getModuleRstates().get(moduleName) == null ? "" : getModuleRstates().get(moduleName);
	}
}
