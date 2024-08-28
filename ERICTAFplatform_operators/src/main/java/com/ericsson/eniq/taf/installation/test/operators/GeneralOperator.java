package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.handlers.impl.RemoteObjectHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class GeneralOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(GeneralOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();
	private final String enginelogsPath = DataHandler.getAttribute("platform.engine.logpath").toString();
	private final String errorlogsPath = DataHandler.getAttribute("platform.error.logpath").toString();
	private final String ADMINUI_TP = DataHandler.getAttribute("eniq.platform.adminui.teckpackide.path").toString();
	private final String ADMINUI_TP_PORT = DataHandler.getAttribute("eniq.platform.adminui.teckpackide.port")
			.toString();
	private final String DWH_MONITOR_ENGINE_LOG_PATH = DataHandler.getAttribute("platform.install.ExecutionLog")
			.toString();

	private final String TP_LOG_PATH = DataHandler.getAttribute("eniq.platform.teckpack.log.path").toString();
	private final String TP_GREP_ERROR = "cd " + TP_LOG_PATH + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String TP_GREP_EXCEPTION = "cd " + TP_LOG_PATH + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String TP_GREP_WARNINGS = "cd " + TP_LOG_PATH + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String TP_GREP_FAILED = "cd " + TP_LOG_PATH + ";" + LOG_GREP_FAIELD_CMD + " ";

	private final String GREP_ERROR_IN_ENGINE_LOG = "cd " + enginelogsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ENGINE_LOG = "cd " + enginelogsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS_IN_ENGINE_LOG = "cd " + enginelogsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED_IN_ENGINE_LOG = "cd " + enginelogsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND_IN_ENGINE_LOG = "cd " + enginelogsPath + ";" + LOG_GREP_NOTFOUND_CMD + " ";

	private final String GREP_ERROR_IN_ERROR_LOG = "cd " + errorlogsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ERROR_LOG = "cd " + errorlogsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS_IN_ERROR_LOG = "cd " + errorlogsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED_IN_ERROR_LOG = "cd " + errorlogsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND_IN_ERROR_LOG = "cd " + errorlogsPath + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	private final String TOPOLOGY_SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/lte/topologyData/ERBS/";
	private final String PM_SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/lterbs/dir1";
	private final String SAMPLE_TOPOLOGY_FILE_NAME = "SubNetwork_ONRM_ROOT_MO_R%2cSubNetwork_ERBS-SUBNW-1%2cMeContext_ERBS1.xml";
	private final String SAMPLE_PM_FILE_NAME = "A20181013.0800+0100-0815+0100_SubNetwork%3dONRM_ROOT_MO%2cSubNetwork%3dERBS-SUBNW-1%2cMeContext%3dERBS1_statsfile.xml";
	//private final String SAMPLE_PM_FILE_NAME = "A20210110.0815+0100-0830+0100_SubNetwork=ONRM_ROOT_MO,SubNetwork=ERBS-SUBNW-1,MeContext=ERBS1_statsfile.xml";
	//private final String SAMPLE_PM_FILE_NAME ="A20210110.0815+0100-0830+0100_SubNetwork%3dONRM_ROOT_MO%2cSubNetwork%3dERBS-SUBNW-1%2cMeContext%3dERBS1_statsfile.xml";
	//private final String SAMPLE_PM_FILE_NAME = "A20210127.0815+0100-0830+0100_SubNetwork=ONRM_ROOT_MO,SubNetwork=ERBS-SUBNW-1,MeContext=ERBS1_statsfile.xml";
	
	private final String PROFILE = ". /eniq/home/dcuser/.profile; ";
	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private final String DCUSER_PROFILE_DIFF = "su - dcuser -c " + '"' + ". /eniq/home/dcuser/.profile; ";

	private final String GREP_ERROR_IN_DWH_MONITOR_ERROR_LOG = "cd " + DWH_MONITOR_ENGINE_LOG_PATH + ";"
			+ LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_DWH_MONITOR_ERROR_LOG = "cd " + DWH_MONITOR_ENGINE_LOG_PATH + ";"
			+ LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS_IN_DWH_MONITOR_ERROR_LOG = "cd " + DWH_MONITOR_ENGINE_LOG_PATH + ";"
			+ LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED_IN_DWH_MONITOR_ERROR_LOG = "cd " + DWH_MONITOR_ENGINE_LOG_PATH + ";"
			+ LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND_IN_DWH_MONITOR_ERROR_LOG = "cd " + DWH_MONITOR_ENGINE_LOG_PATH + ";"
			+ LOG_GREP_NOTFOUND_CMD + " ";

	private final String SHELL = "sh";
	private final String PERMISSIONS = "777";
	private final String CHMOD = "chmod";
	public static final String cretaeDBusers = "echo -e \"Dba12#\\nTest@123\\nTest@123\\nDba12#\\n1\\n3\\n\" |  bash /eniq/admin/bin/create_query_user.bsh -n ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public GeneralOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommand(String command) {
		logger.info("\nExecuting : " + command);
		final String output = handler.simpleExec(command);
		logger.info("\nOutput : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @return String
	 * @throws InterruptedException
	 */
	public String closeFirefoxProcess(String command) throws InterruptedException {
		logger.info("\nExecuting : " + command);
		TimeUnit.SECONDS.sleep(2);
		final String output = handler.simpleExec(command);
		TimeUnit.SECONDS.sleep(2);
		logger.info("\nOutput : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommandDcuser(String command) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + command + "'");
		final String output = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("\nOutput : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommandDcuserDiff(String command) {
		logger.info("\nExecuting : " + DCUSER_PROFILE_DIFF + command + '"');
		final String output = handler.simpleExec(DCUSER_PROFILE_DIFF + command + '"');
		logger.info("\nOutput : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String engineLogContent(String fileName) {
		logger.info("Engine Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_FAILED_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_NOTFOUND_IN_ENGINE_LOG + fileName));
		logger.info("\n Content of Engine log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String errorLogContent(String fileName) {
		logger.info("Error Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS_IN_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_FAILED_IN_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_NOTFOUND_IN_ERROR_LOG + fileName));
		logger.info("\n Content of Error log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();

	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public void copySampleTopologyFileToServer() {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
		String pythonscr = FileFinder.findFile(SAMPLE_TOPOLOGY_FILE_NAME).get(0);
		logger.info("File : " + pythonscr);

		preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, TOPOLOGY_SERVER_PATH);
		logger.info("****** Copied the " + SAMPLE_TOPOLOGY_FILE_NAME + " script to server in " + TOPOLOGY_SERVER_PATH
				+ " location ******");
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public void copySamplePmFileToServer() {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
		String pythonscr = FileFinder.findFile(SAMPLE_PM_FILE_NAME).get(0);
		logger.info("File : " + pythonscr);

		preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, PM_SERVER_PATH);
		logger.info("****** Copied the " + SAMPLE_PM_FILE_NAME + " script to server in " + PM_SERVER_PATH
				+ " location ******");
	}

	public void createPmDirectory() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("mkdir");
		cmdToExec.append(" ");
		cmdToExec.append("-p");
		cmdToExec.append(" ");
		cmdToExec.append(PM_SERVER_PATH);

		logger.info("****** Created Directory : " + PM_SERVER_PATH + " ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	public void createTopologyDirectory() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("mkdir");
		cmdToExec.append(" ");
		cmdToExec.append("-p");
		cmdToExec.append(" ");
		cmdToExec.append(TOPOLOGY_SERVER_PATH);

		logger.info("****** Created Directory : /eniq/data/pmdata/eniq_oss_1/lterbs/dir1/ ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	public void removePmDirectory() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("rmdir");
		cmdToExec.append(" ");
		cmdToExec.append(PM_SERVER_PATH);

		logger.info("****** Created Directory :  ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	public void removeTopologyDirectory() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("rmdir");
		cmdToExec.append(" ");
		cmdToExec.append(TOPOLOGY_SERVER_PATH);

		logger.info("****** Created Directory : /eniq/data/pmdata/eniq_oss_1/lterbs/dir1/ ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listOfInterfaces(String command) {
		List<String> dir = new ArrayList<>();
		String completeOutput = handler.simpleExec(PROFILE + command);
		if (completeOutput.length() != 0) {
			for (String output : completeOutput.split("\n")) {
				dir.add(output.trim());
			}
			logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
			return dir;
		} else {
			logger.info("No Input");
			logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
			return dir;
		}
	}

	public void copyFileToServer(String fileName, String serverPath) {
		logger.info("Copy file to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
		String srcfile = FileFinder.findFile(fileName).get(0);
		logger.info("File : " + srcfile);

		preCheckRemoteFileHandler.copyLocalFileToRemote(srcfile, serverPath);
		logger.info("****** Copied the " + fileName + " file to server in " + serverPath + " location ******");
	}

	public void createDirectory(String serverPath) {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("mkdir");
		cmdToExec.append(" ");
		cmdToExec.append("-p");
		cmdToExec.append(" ");
		cmdToExec.append(serverPath);

		logger.info("****** Created Directory : " + serverPath + " ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(executeCommandDcuser(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	/**
	 * 
	 * @return String
	 */
	public List<String> executeCmdSplitWithNewLine(String command) {
		logger.info("Executing : " + PROFILE + command);
		List<String> dir = new ArrayList<>();
		final String output = handler.simpleExec(PROFILE + command);
		for (String str : output.split("\n")) {
			dir.add(str.trim());
		}
		logger.info("Output : " + dir);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return dir;
	}

	/**
	 * 
	 * @return String
	 */
	public String findMWSfeaturesPathCommand(String command) {
		logger.info("Executing : " + command);
		final String output = handler.simpleExec(command);
		logger.info("Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public StringBuilder getTPlogContent(String fileName) {
		logger.info("\nVerify TechPack Log file: " + fileName);

		logger.info("\nChecking any errors/exceptions/warnings in : " + TP_LOG_PATH + fileName);
		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(TP_GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(TP_GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(TP_GREP_WARNINGS + fileName));
		log_Content.append(handler.simpleExec(TP_GREP_FAILED + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content;

	}

	public void copyScript(String scriptName, String scriptPathInServer) {
		logger.info("Copy " + scriptName + " script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		if (eniqshost.getHostname().trim().equalsIgnoreCase("eniqs")) {

			preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());

			String scriptNameinTAFproject = FileFinder.findFile(scriptName).get(0);
			logger.info("File Location : " + scriptNameinTAFproject);

			preCheckRemoteFileHandler.copyLocalFileToRemote(scriptNameinTAFproject, scriptPathInServer);
			logger.info("****** Copied the " + scriptName + " script to server in " + scriptPathInServer
					+ " location ******");
		}

	}

	public void permission(String scriptNameWithPath) {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append(CHMOD);
		cmdToExec.append(" ");
		cmdToExec.append(PERMISSIONS);
		cmdToExec.append(" ");
		cmdToExec.append(scriptNameWithPath);

		logger.info("****** Given permission to the echo_script.sh script ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
	}

	public String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		logger.info("\nHost Name : " + eniqHost.getHostname());
		return String.format("http://%s", eniqHost.getHostname() + ":" + ADMINUI_TP_PORT + ADMINUI_TP);
		// return "http://eniqs" + ":" + ADMINUI_TP_PORT + ADMINUI_TP;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String DWH_MONITORerrorLogContent(String fileName) {
		logger.info("Error Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_DWH_MONITOR_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_DWH_MONITOR_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS_IN_DWH_MONITOR_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_FAILED_IN_DWH_MONITOR_ERROR_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_NOTFOUND_IN_DWH_MONITOR_ERROR_LOG + fileName));
		logger.info("\n Content of Error log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();

	}

	public String executeScriptOnVapp(String SCRIPT) {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append(SHELL);
		cmdToExec.append(" ");
		cmdToExec.append(SCRIPT);

		logger.info("Executing : " + cmdToExec.toString());
		return handler.simpleExec(cmdToExec.toString());
	}

	public boolean createDbUsers(String DBusersName) {
		String output = executeCommand(cretaeDBusers + DBusersName);
		logger.info(output);
		return output.contains("Successfully created user " + DBusersName);
	}

}
