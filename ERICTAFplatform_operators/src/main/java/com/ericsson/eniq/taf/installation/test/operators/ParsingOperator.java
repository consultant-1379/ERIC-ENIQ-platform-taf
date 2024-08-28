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
public class ParsingOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(ParsingOperator.class);

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
	//private final String SAMPLE_FILE_NAME = "A20210110.0815+0100-0830+0100_SubNetwork=ONRM_ROOT_MO,SubNetwork=ERBS-SUBNW-1,MeContext=ERBS1_statsfile.xml";
	//private final String SAMPLE_FILE_NAME = "A20210110.0815+0100-0830+0100_SubNetwork%3dONRM_ROOT_MO%2cSubNetwork%3dERBS-SUBNW-1%2cMeContext%3dERBS1_statsfile.xml";
	//private final String SAMPLE_FILE_NAME = "A20210127.0815+0100-0830+0100_SubNetwork=ONRM_ROOT_MO,SubNetwork=ERBS-SUBNW-1,MeContext=ERBS1_statsfile.xml";
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
	private final String PARSING_Q_VALUE = "engine -e showSetsInQueue | wc | cut -c1-10";
	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private final String AWK = "su - dcuser -c " + ". /eniq/home/dcuser/.profile; ";
	private CLICommandHelper handler;
	private Host eniqshost;

	public ParsingOperator() {
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
		logger.info("\nExecuting : " + PROFILE + command + "'");
		final String output = handler.simpleExec(PROFILE + command + "'");
		logger.info("\nOutput : " + output);
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

	public Boolean isParsingOnGoing() {
		final String stdout = this.executeCommand(PARSING_Q_VALUE).trim();
		final String[] tokens = stdout.split("\\s+");
		final String response = tokens[0];
		final int lineCount = Integer.parseInt(response);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return (lineCount > 6);
	}
}