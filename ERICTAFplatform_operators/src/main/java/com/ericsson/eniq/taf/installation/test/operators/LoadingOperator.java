package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.handlers.impl.RemoteObjectHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class LoadingOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(LoadingOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("log.fail.grep").toString();
	private final String LOG_GREP_FATAL_CMD = DataHandler.getAttribute("log.fatal.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();

	private final String enginelogsPath = DataHandler.getAttribute("platform.engine.logpath").toString();
	private final String errorlogsPath = DataHandler.getAttribute("platform.engine.logpath").toString();

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
	private final String SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/lterbs/dir1/";
	private final String SAMPLE_FILE_NAME = "A20181013.0800+0100-0815+0100_SubNetwork%3dONRM_ROOT_MO%2cSubNetwork%3dERBS-SUBNW-1%2cMeContext%3dERBS1_statsfile.xml";

	private final String GREP_ERROR_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNING_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAIL_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_FATAL_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_FATAL_CMD + " ";

	private final String GREP_ERROR_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNING_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAIL_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_FATAL_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_FATAL_CMD + " ";

	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private static final String DBIP = "cat /etc/hosts | grep repdb";

	private CLICommandHelper handler;
	private Host eniqshost;

	public LoadingOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listOfInterfaces(String command) {
		String completeOutput = handler.simpleExec(PROFILE + command);
		List<String> dir = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			dir.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return dir;
	}

	public List<String> listOfDBInterfaces(String command) {
		List<String> dir = new ArrayList<>();
		for (String output : command.split("\\n")) {
			dir.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return dir;
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommand(String command) {
		logger.info("Executing : " + PROFILE + command + "'");
		final String output = handler.simpleExec(PROFILE + command + "'");
		logger.info("Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String engineLogContent(String fileName) {
		logger.info("Engine Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNING_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_FAIL_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_FATAL_IN_ENGINE + fileName));
		logger.info("\n Content of Engine log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String errorLogContent(String fileName) {
		logger.info("Error Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNING_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_FAIL_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_FATAL_IN_ERROR + fileName));
		logger.info("\n Content of Error log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public void copySampleFileToServer() {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
		String pythonscr = FileFinder.findFile(SAMPLE_FILE_NAME).get(0);
		logger.info("File : " + pythonscr);

		preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, SERVER_PATH);
		logger.info(
				"****** Copied the " + SAMPLE_FILE_NAME + " script to server in " + SERVER_PATH + " location ******");
	}
	
	public void copyFileToServer(String fileName, String serverPath) {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
			preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
			String pythonscr = FileFinder.findFile(fileName).get(0);
			logger.info("File : " + pythonscr);

			preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, serverPath);
			logger.info("****** Copied the " + fileName + " script to server in " + serverPath
					+ " location ******");
	}

	public void createDirectory() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("mkdir");
		cmdToExec.append(" ");
		cmdToExec.append("-p");
		cmdToExec.append(" ");
		cmdToExec.append(SERVER_PATH);

		logger.info("****** Created Directory : /eniq/data/pmdata/eniq_oss_1/lterbs/dir1/ ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
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
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	public void removeDirectory() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append("rmdir");
		cmdToExec.append(" ");
		cmdToExec.append(SERVER_PATH);

		logger.info("****** Created Directory : /eniq/data/pmdata/eniq_oss_1/lterbs/dir1/ ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
	}

	public String getDBIP() {
		final String stdout = this.executeCommand(DBIP);
		final String[] tokens = stdout.split("\\s+");
		final String response_flow = tokens[0].trim();
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return response_flow;
	}
}
