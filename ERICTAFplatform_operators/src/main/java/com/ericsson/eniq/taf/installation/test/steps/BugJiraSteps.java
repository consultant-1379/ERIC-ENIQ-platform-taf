package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.operators.ClickAllLinks;
import com.ericsson.eniq.taf.installation.test.operators.DWH_Operator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.LoadingDwhrepDbOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class BugJiraSteps {

	private static Logger logger = LoggerFactory.getLogger(BugJiraSteps.class);

	private final String RELEASE = DataHandler.getAttribute("platform.release.version").toString();
	private final String TP_LOG_PATH = DataHandler.getAttribute("eniq.platform.teckpack.log.path").toString();
	private final String ENIQ_PLATFORM_PATH = DataHandler.getAttribute("eniq.platform.path").toString();
	private final String ENIQ_PLATFORM_ENGINE_PATH = DataHandler.getAttribute("platform.engine.logpath").toString();
	private final String INSTALLER_PATH = DataHandler.getAttribute("platform.install.versionDBDir").toString();

	private final String CD = "cd";
	private final String LS = "ls";
	private final String PIPE = "|";
	private final String SPACE = " ";
	private final String SEMI_CO = ";";
	private final String EPFG_CONFIG_PATH = "/eniq/home/dcuser/epfg/config/";
	private final String EPFG_PATH = "/eniq/home/dcuser/epfg/";
	private final String GREP = "grep -i";
	private final String RUNTIME_DIR = "/eniq/sw/runtime/";
	private final String ULIMIT = "ulimit -a";
	private final String DWHPARTITION = "pm/alarmAutoTrigger.sql";
	private final String ENDTIME_SQL_QUERY = "pm/alarmAutoTriggerENDTIME.sql";
	private final String PASSWORD_SQL_QUERY = "pm/passwordEncryption.sql";
	private final String UPDATE_CONFIG_FILE = "cd /eniq/database/dwh_main/;sed -c -i \"s/\\(-gm*  *\\).*/\\1$110/\" dwhdb.cfg";
	private final String REVERT_CONFIG_FILE = "cd /eniq/database/dwh_main/;sed -c -i \"s/\\(-gm*  *\\).*/\\1$1200/\" dwhdb.cfg";

	@Inject
	private GeneralOperator genOperator;

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	@Inject
	private Provider<LoadingDwhrepDbOperator> dwhrepOperatorProvider;

	@Inject
	private Provider<DWH_Operator> etlrepDB;

	@Inject
	private ClickAllLinks webApp;

	/**
	 * @throws ConfigurationException
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.APACHE_TOMCAT_FILES)
	public void epfgFileGenerator() {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		String tomcatVersion = generalOperator
				.executeCommandDcuser(CD + SPACE + RUNTIME_DIR + SEMI_CO + LS + PIPE + GREP + SPACE + "apache-tomcat-");
		logger.info("\nServer tomcat installed version is : " + tomcatVersion);
		String docsFolder = generalOperator.executeCommandDcuser(
				CD + SPACE + RUNTIME_DIR + tomcatVersion + SEMI_CO + LS + PIPE + GREP + SPACE + "docs");
		String indexFile = generalOperator.executeCommandDcuser(
				CD + SPACE + RUNTIME_DIR + tomcatVersion + SEMI_CO + LS + PIPE + GREP + SPACE + "index.jsp");

		assertFalse(docsFolder.contains("docs") || indexFile.contains("index.jsp"),
				"Failed due to 'docs' and 'index.jsp' folder/file still exists under " + RUNTIME_DIR + tomcatVersion);
	}

	@TestStep(id = StepIds.JAVA_CORE_DUMP_ENGINE)
	public void VerifyJavaCoreDumpAndEngine() {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		String coreFileSize = generalOperator.executeCommandDcuser(ULIMIT + PIPE + GREP + SPACE + "'core file size'");
		logger.info("\n Core Files Size is : " + coreFileSize);

		String openFilesSize = generalOperator.executeCommandDcuser(ULIMIT + PIPE + GREP + SPACE + "'open files'");
		logger.info("\n Open Files Size is : " + openFilesSize);
		assertTrue(coreFileSize.contains("unlimited") && openFilesSize.contains("32768"),
				"Failed due to 'ulimit -a' Command :  'core file size' is  not equals 'unlimited' AND 'open files' size is not equals to '32768' \n Command output is : \n"
						+ coreFileSize + "\n" + openFilesSize);
	}

	@TestStep(id = StepIds.DWH_BASEandDWH_MONITOR_Logs)
	public void verifyDWH_BASEandDWH_MONITOR_Log() {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		String dwhBASE = generalOperator.executeCommandDcuser(
				CD + SPACE + TP_LOG_PATH + SEMI_CO + LS + SPACE + PIPE + SPACE + GREP + SPACE + "DWH_BASE");
		logger.info("\nDWH_BASE Log File : " + dwhBASE);
		String dwhMONITOR = generalOperator.executeCommandDcuser(
				CD + SPACE + TP_LOG_PATH + SEMI_CO + LS + SPACE + PIPE + SPACE + GREP + SPACE + "DWH_MONITOR");
		logger.info("\nDWH_BASE Log File : " + dwhMONITOR);

		StringBuilder tpLog_Content = new StringBuilder();
		tpLog_Content.append(generalOperator.getTPlogContent(dwhBASE));
		tpLog_Content.append(generalOperator.getTPlogContent(dwhMONITOR));

		assertFalse(tpLog_Content.length() > 0,
				"Failed due to TP DWH_BASE and DWH_MONITOR Logs has exceptions/warnings \nException : "
						+ tpLog_Content);
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 * 
	 */
	@TestStep(id = StepIds.EWMremovalSteps)
	public void verifyModulesExtracted() {

		final GeneralOperator generalOperator = generalOperatorProvider.get();
		String installedCommonDir = generalOperator
				.executeCommandDcuser(CD + SPACE + ENIQ_PLATFORM_PATH + SEMI_CO + LS + PIPE + GREP + SPACE + "common-");
		String wifiVM = generalOperator.executeCommandDcuser(CD + SPACE + ENIQ_PLATFORM_PATH + installedCommonDir + "/"
				+ SEMI_CO + LS + SPACE + PIPE + SPACE + GREP + SPACE + "wifi.vm");
		String wifiinVentory = generalOperator.executeCommandDcuser(CD + SPACE + ENIQ_PLATFORM_PATH + installedCommonDir
				+ "/" + SEMI_CO + LS + SPACE + PIPE + SPACE + GREP + SPACE + "wifiinventory.vm");
		logger.info("\nwifiinVentory : " + wifiinVentory + "\n" + "\nwifi.vm : " + wifiVM + "\n");
		assertTrue(wifiVM.isEmpty() && wifiinVentory.isEmpty(),
				"\nFailed due to 'wifi.vm' OR 'wifiinventory.vm' files are present under " + ENIQ_PLATFORM_PATH
						+ installedCommonDir);
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 * 
	 */
	@TestStep(id = StepIds.PRLIMIT_TEST)
	public void verifyPrlimiValue() {

		final GeneralOperator generalOperator = generalOperatorProvider.get();
		String value = generalOperator.executeCommandDcuser("cat /etc/systemd/system.conf | grep -i nofile");

		assertTrue(value.contains("65536"),
				"\nFailed due to 'DefaultLimitNOFILE' value is not 65536 in /etc/systemd/system.conf file\n Expected DefaultLimitNOFILE=65536 but found "
						+ value);
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 * @throws ParseException
	 * @throws InterruptedException
	 * 
	 */
	@TestStep(id = StepIds.ALARM_AUTO_TRIGGER_TEST)
	public void verifyAlarmAutoTrigger() throws ParseException, InterruptedException {
		final LoadingDwhrepDbOperator dwhrepOperator = dwhrepOperatorProvider.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		HashMap<String, String> onetable_db_output = new HashMap<String, String>();
		HashMap<String, String> db2 = new HashMap<String, String>();
		List<String> db3 = new ArrayList<>();
		HashMap<String, String> map = new HashMap<String, String>();

		final HashMap<String, String> dbOutput = dwhrepOperator.verifyDBCommand2("pm/alarmAutoTrigger.sql");
		String currentDay = generalOperator.executeCommandDcuserDiff("date +'%d'");
		String currentMonth = generalOperator.executeCommandDcuserDiff("date +'%m'");
		String currentYear = generalOperator.executeCommandDcuserDiff("date +'%y'");

		String time = "00:00:00";
		String inserteDate = "20" + currentYear + "-" + currentMonth + "-" + currentDay;
		String finalCurrentDate = inserteDate + " " + time;
		map.put("currentdate", finalCurrentDate);
		db2 = dwhrepOperator.verifyDBCommand2(ENDTIME_SQL_QUERY);
		onetable_db_output = dwhrepOperator.verifyDBCommand3(ENDTIME_SQL_QUERY, map);

		logger.info("\nRunning the partition action....");

		String setOutput = generalOperator
				.executeCommandDcuser("engine -e startAndWaitSet DC_Z_ALARM DWHM_Partition_DC_Z_ALARM");

		logger.info("After triggering DWHM_Partition_DC_Z_ALARM Set : " + setOutput);
		// TimeUnit.SECONDS.sleep(60);

		String dateAfterSetCompletion = null;
		if (setOutput.contains("finished with status: succeeded")) {
			logger.info("\n\n Viewing the new partition plan!\n");
			dwhrepOperator.verifyDBCommand2("pm/alarmAutoTrigger.sql");

			logger.info("\nBelow date is concidered for furture date validation");

			db3 = dwhrepOperator.verifyDBCommand("pm/alarmAutoTriggerOnlyDate.sql");

			// List<String> list = new ArrayList<String>(db3.values());

			for (String str : db3) {
				if (str.contains("-")) {
					dateAfterSetCompletion = str;
				}
			}
			// Updated date from after triggering set
			// dateAfterSetCompletion = "2019-12-09 00:00:00";
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date oldDate = sdformat.parse(inserteDate);
			Date newDate = sdformat.parse(dateAfterSetCompletion);
			System.out.println("\n Current Date: " + sdformat.format(oldDate));
			System.out.println("\n Future Date: " + sdformat.format(newDate));

			if (newDate.compareTo(oldDate) > 0) {
				System.out.println("\nPASSED : " + dateAfterSetCompletion);
				assertTrue(true);
			} else if (newDate.compareTo(oldDate) < 0) {
				System.out.println("\nFAILED");
				assertTrue(false,
						"\nFailed due to : DWHM_Partition_DC_Z_ALARM set is unable to update the future date in DWHPARTITION table");
			} else if (newDate.compareTo(oldDate) == 0) {
				System.out.println("FAILED but Both dates are equal");
				assertTrue(false,
						"Failed due to : DWHM_Partition_DC_Z_ALARM set is unable to update the future date in DWHPARTITION table");
			}
		} else {
			assertTrue(false, "\nSet DWHM_Partition_DC_Z_ALARM trigger failed\n " + setOutput);
		}
	}

	@TestStep(id = StepIds.DBA_CONNECTION)
	public void verifyDBAconnections() throws InterruptedException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		logger.info("Executing : " + generalOperator.executeCommand(UPDATE_CONFIG_FILE));
		logger.info("Executing : " + generalOperator.executeCommand("cat /eniq/database/dwh_main/dwhdb.cfg"));
		logger.info(
				"Executing : " + generalOperator.executeCommand("cat /eniq/database/dwh_main/dwhdb.cfg | grep 'gm'"));
		logger.info("Executing : " + generalOperator.executeCommandDcuser("dwhdb restart"));

		basicTestcase();
		logger.info("Executing : " + generalOperator.executeCommandDcuser("webserver restart"));

		// click show loadings in adminui for 100 times. (Open Admin UI and
		// click)

		try {
			webApp.openBrowser();
			webApp.login();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			for (int i = 0; i <= 100; i++) {
				logger.info("\nNumber of clicks " + i);
				String link = "Show Loadings";
				webApp.clickMultipleTimes(link);
			}

		} catch (InterruptedException e1) {

			// TODO Auto-generated catch block
			logger.info("\nfailed to open browser or unable to login to adminui");
			e1.printStackTrace();
		}

		String dwhdbCountLogFile = generalOperator
				.executeCommand("cd " + ENIQ_PLATFORM_ENGINE_PATH + ";ls | grep dwhdbCount | tail -1");
		logger.info("Latest dwhdbCount Log File :" + dwhdbCountLogFile + "\n");
		String dwhdbCountLogFileOutput = generalOperator.executeCommand("cd /eniq/log/sw_log/engine/;tail -1 "
				+ dwhdbCountLogFile + "| awk '{print $(NF)}' | cut -d ',' -f3 | cut -d ')' -f1");
		logger.info("Number of DBA Connection left open  :" + dwhdbCountLogFileOutput + "\n");

		String latestEngineLogFileOutput = generalOperator
				.executeCommand("cd /eniq/log/sw_log/engine/;ls | grep engine-2 | tail -1");
		String engineLogFileOutput = generalOperator.executeCommand(
				"cd /eniq/log/sw_log/engine/;cat " + latestEngineLogFileOutput + " | grep 'connection limit exceeded'");

		// Reverting config fileto ther defult values
		logger.info("Executing : " + generalOperator.executeCommand(REVERT_CONFIG_FILE));
		logger.info("Executing : " + generalOperator.executeCommand("cat /eniq/database/dwh_main/dwhdb.cfg"));
		logger.info(
				"Executing : " + generalOperator.executeCommand("cat /eniq/database/dwh_main/dwhdb.cfg | grep 'gm'"));

		assertTrue(engineLogFileOutput.contains("connection limit exceeded"), "Expected 'connection limit exceeded' "
				+ "\nEngine log file : " + engineLogFileOutput + "\n But Found : " + engineLogFileOutput);

		try {
			webApp.closeBrowser();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.info("\nfailed close the browser");
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 * 
	 */
	@TestStep(id = StepIds.GET_PASSWORD_SCRIPT)
	public void verifyGetPasswordScript(@Input(Parameters.USERS) String user) {

		final GeneralOperator generalOperator = generalOperatorProvider.get();
		logger.info("\n\nBelow command is executed with dcuser user\n");
		String dcuserOutput = generalOperator
				.executeCommandDcuser(CD + " " + INSTALLER_PATH + ";./getPassword.bsh -u " + user);

		logger.info("\n\nBelow command is executed with root user \n");
		String rootOutput = generalOperator.executeCommand(CD + " " + INSTALLER_PATH + ";./getPassword.bsh -u " + user);

		assertTrue(
				dcuserOutput.contains(user.toUpperCase() + " " + "password:")
						&& dcuserOutput.contains(user.toUpperCase() + " " + "password:"),
				"\n Expected: " + user + " " + "password:" + " but found"
						+ "\n getPassword.bsh script execution failed \n Script Output as 'dcuser'" + dcuserOutput
						+ "\n Script Output as 'root'" + rootOutput);
	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 * 
	 */
	@TestStep(id = StepIds.PASSWORD_ENCRYPTION_FLOW)
	public void verifyEncrptionPasswordScript(@Input(Parameters.METADBUSERS) String user) {
		HashMap<String, String> onetable_db_output = new HashMap<String, String>();
		final DWH_Operator dwhrepOperator = etlrepDB.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();

		HashMap<String, String> map = new HashMap<String, String>();
		String eachUsers = user;
		map.put("eachDbUsers", eachUsers);
		onetable_db_output = dwhrepOperator.verifyDBCommand3(PASSWORD_SQL_QUERY, map);
		logger.info(onetable_db_output.toString());
		assertTrue(!onetable_db_output.containsValue(user), "Faied due to META_DATABASES user " + user
				+ " password is not Encrypted\n Output : " + onetable_db_output.toString());

	}

	/**
	 * 
	 * @param moduleName
	 *            moduleName
	 * @throws InterruptedException
	 * 
	 */
	@TestStep(id = StepIds.IPTRANSPORT_PARSING)
	public void IptransportParsingError() throws InterruptedException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		wepAppTestFlow.basicTest();
		String filePath = "/eniq/data/pmdata/eniq_oss_1/ER6000/dir1/";
		// Create input folder
		logger.info(generalOperator.executeCommandDcuser("mkdir -p " + filePath));

		// Copy input file to the remote server
		String fileName = "A20200204.0915+0000-0930+0000_SubNetwork=Europe,SubNetwork=Ireland,SubNetwork=NETSimW,MeContext=CORE11R6274004,ManagedElement=1_statsfile.xml";
		generalOperator.copyScript(fileName, filePath);

		// Change the owner permission
		logger.info(generalOperator.executeCommand("chown dcuser:dc5000 " + filePath + fileName));

		String parsingCmd = "engine -e startSet 'INTF_DC_E_IPTRANSPORT_ECIM_ENM-eniq_oss_1' 'Adapter_INTF_DC_E_IPTRANSPORT_ECIM_ENM_3gpp32435'";
		String parsingOutput = generalOperator.executeCommandDcuser(parsingCmd);

		while (generalOperator.executeCommand("ls /eniq/data/pmdata/eniq_oss_1/ER6000/dir1/").length() > 0) {

			TimeUnit.SECONDS.sleep(60);

		}
		System.out.println("Executed final statement*********************************************************");
		String latestEngine = generalOperator.executeCommand(
				"cd /eniq/log/sw_log/engine/INTF_DC_E_IPTRANSPORT_ECIM_ENM-eniq_oss_1/; ls engine-*.log | tail -1");
		String logOutput = generalOperator.executeCommand(
				"cd /eniq/log/sw_log/engine/INTF_DC_E_IPTRANSPORT_ECIM_ENM-eniq_oss_1/; grep 'String index out of range: -1' "
						+ latestEngine);

		assertTrue(logOutput.length() == 0,
				"Failed due to " + "/eniq/log/sw_log/engine/INTF_DC_E_IPTRANSPORT_ECIM_ENM-eniq_oss_1/" + latestEngine
						+ " has 'String index out of range: -1' exception \n" + logOutput);
	}

	public static class StepIds {
		public static final String APACHE_TOMCAT_FILES = "ENIQ-S : Apache Tomcat Default Files | BUG";
		public static final String JAVA_CORE_DUMP_ENGINE = "Number of open files and core file setting mismatch in RHEL";
		public static final String DWH_BASEandDWH_MONITOR_Logs = "DWH_BASE and DWH_MONITOR techpack installation is failing with latest 19.2 License";
		public static final String EWMremovalSteps = "As a part of EWM removal 'Wifi.vm and wifiInvertory.vm’ should be removed from common module.";
		public static final String PRLIMIT_TEST = "validate system.conf-> DefaultLimitNOFILE=65536 ";
		public static final String ALARM_AUTO_TRIGGER_TEST = "Alarm partition is not triggered automatically";
		public static final String DBA_CONNECTION = "DBA connection left open by adminUi package";
		public static final String GET_PASSWORD_SCRIPT = "Error while running getPassword.bsh script as root user";
		public static final String PASSWORD_ENCRYPTION_FLOW = "Encryption of Passwords in META_DATABASES";
		public static final String IPTRANSPORT_PARSING = "Files parsing is failing for INTF_DC_E_IPTRANSPORT_ECIM_ENM with error String index out of range: -1";

		private StepIds() {
		}
	}

	public void basicTestcase() throws InterruptedException {

		final String engineStatus = "engine status | grep 'Status'| grep 'active'";
		final String webserverRestart = "webserver restart";
		final String engineProfileStatus = "engine status | grep 'Current Profile' | grep 'Normal'";
		final String webserverStatus = "webserver status";
		final String licenseManagerStatus = "licmgr -status | grep 'License manager is running OK'";
		final String dwhdbStatus = "dwhdb status";
		final String repdbStatus = "repdb status";

		final String engineOnline = "engine start";
		final String engineProfileNormal = "engine -e changeProfile 'Normal'";
		final String webserverOnline = "webserver start";
		final String licenseManagerOnline = "licmgr -start";
		final String dwhdbOnline = "dwhdb start";
		final String repdbOnline = "repdb start";

		final String dwhdbStatusOutput = genOperator.executeCommandDcuser(dwhdbStatus);
		if (!dwhdbStatusOutput.contains("dwhdb is running OK")) {
			genOperator.executeCommandDcuser(dwhdbOnline);
			logger.info(genOperator.executeCommandDcuser(dwhdbStatus));
		}

		final String engineStatusOutput = genOperator.executeCommandDcuser(engineStatus);
		if (!engineStatusOutput.contains("Status: active")) {
			genOperator.executeCommandDcuser(engineOnline);
			logger.info(genOperator.executeCommandDcuser(engineStatus));
		}

		final String webserverStatusRestartOutput = genOperator.executeCommandDcuser(webserverRestart);
		if (!webserverStatusRestartOutput.contains("Service enabling eniq-webserver")) {
			genOperator.executeCommandDcuser(webserverOnline);
			logger.info(genOperator.executeCommandDcuser(webserverStatus));
		}

		final String webserverStatusOutput = genOperator.executeCommandDcuser(webserverStatus);
		if (!webserverStatusOutput.contains("webserver is running OK")) {
			genOperator.executeCommandDcuser(webserverOnline);
			logger.info(genOperator.executeCommandDcuser(webserverStatus));
		}

		final String licenseManagerOutput = genOperator.executeCommandDcuser(licenseManagerStatus);
		if (!licenseManagerOutput.contains("License manager is running OK")) {
			genOperator.executeCommandDcuser(licenseManagerOnline);
			logger.info(genOperator.executeCommandDcuser(licenseManagerStatus));
		}

		final String repdbStatusOutput = genOperator.executeCommandDcuser(repdbStatus);
		if (!repdbStatusOutput.contains("repdb is running OK")) {
			genOperator.executeCommandDcuser(repdbOnline);
			logger.info(genOperator.executeCommandDcuser(repdbStatus));
		}

		final String engineProfileStatusOutput = genOperator.executeCommandDcuser(engineProfileStatus);
		if (!engineProfileStatusOutput.contains("Current Profile: Normal")) {
			genOperator.executeCommandDcuser(engineProfileNormal);
			logger.info(genOperator.executeCommandDcuser(engineProfileStatus));
		}
	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class Parameters {
		public static final String USERS = "users";
		public static final String METADBUSERS = "eachDbUsers";

		private Parameters() {
		}

	}
}
