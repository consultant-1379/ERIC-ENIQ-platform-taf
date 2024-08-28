package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.Ports;
import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.handlers.impl.RemoteObjectHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class VerifySchedulerOperator extends CLIOperator {

	private final String logsPath = DataHandler.getAttribute("platform.scheduler.logsPath").toString();
	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();

	private final static String versionDBDir = DataHandler.getAttribute("platform.portInstall.versionDBDir").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();

	private final String LIST_LOG_FILES_CMD = "cd " + logsPath + ";" + " ls -t -1 *.log";
	private final String GREP_ERROR = "cd " + logsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String UNIQ = "| sort | uniq";
	private final String GREP_EXCEPTION = "cd " + logsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + logsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED = "cd " + logsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND = "cd " + logsPath + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	private static final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep -i scheduler";
	private final String CAT_CMD = "cat ";
	private final String PROFILE = ". /eniq/home/dcuser/.profile; ";

	private final String DCUSER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String licenseLogFileDir = DataHandler.getAttribute("platform.install.license.logFileDir").toString();
	private final String lmDir = DataHandler.getAttribute("platform.install.lm.dir").toString();

	private final String LICENSE_MGR_LOG_CMD = "cd " + licenseLogFileDir + "; cat ";
	private final String LICENSE_MGR_LIST_LOG_CMD = "cd " + licenseLogFileDir + "; ls -t -1";
	private final String LICENSE_SERVER_STATUS_CMD = "cd " + lmDir + "; ./licmgr -serverstatus";
	private final String LICENSE_MGR_STATUS_CMD = "cd " + lmDir + "; ./licmgr -status";
	private final String LICENSE_MAPPING_INFO_CMD = "licmgr -map description";
	private final String LICENSE_VALIDATION_CMD = "licmgr -isvalid";
	private final String LICENSE_CMD = "licmgr";
	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private CLICommandHelper handler;
	private Host eniqshost;
	private User usr;

	Logger logger = LoggerFactory.getLogger(VerifySchedulerOperator.class);

	public VerifySchedulerOperator() {

		eniqshost = DataHandler.getHostByType(HostType.RC);
		logger.info("vApp Name: " + eniqshost.getIp());
		usr = eniqshost.getUser(eniqshost.getUser());
		handler = new CLICommandHelper(eniqshost, usr);

	}

	/**
	 * 
	 * @param module
	 *            module
	 * @return String
	 */
	public String versionDBModuleUpdated(String module) {
		logger.info("versionDBModuleUpdated::" + module);
		String completeOutput = executeCommand(CAT_VERSION_DBPROPERTIES_CMD + module);
		logger.info("versionDBModuleUpdated::output" + completeOutput);
		handler.disconnect();
		return completeOutput;
	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public String executeSchedulerCommands(String command) {
		String completeOutput = handler.simpleExec(PROFILE + "scheduler " + command);
		logger.info("cmd output : " + completeOutput);
		handler.disconnect();
		return completeOutput;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String executeSchedulerServices(String command) {
		String completeOutput = handler.simpleExec(command);
		logger.info("cmd output : " + completeOutput);
		handler.disconnect();
		return completeOutput;
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listPlatformLogFiles() {
		logger.info("verifyPlatformLogExists::");
		String completeOutput = handler.simpleExec(LIST_LOG_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		handler.disconnect();
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @param fileName
	 *            fileName
	 * @return String
	 */
	public String getLogContent(String fileName) {
		logger.info("\nChecking any errors/exceptions/warnings in : " + fileName);
		final StringBuilder log_Content = new StringBuilder();

		logger.info("\nExecuting : " + GREP_ERROR + fileName + UNIQ);
		log_Content.append(handler.simpleExec(GREP_ERROR + fileName + UNIQ));

		logger.info("\nExecuting : " + GREP_EXCEPTION + fileName + UNIQ);
		log_Content.append(handler.simpleExec(GREP_EXCEPTION + fileName + UNIQ));

		logger.info("\nExecuting : " + GREP_WARNINGS + fileName + UNIQ);
		log_Content.append(handler.simpleExec(GREP_WARNINGS + fileName + UNIQ));

		logger.info("\nExecuting : " + GREP_FAILED + fileName + UNIQ);
		log_Content.append(handler.simpleExec(GREP_FAILED + fileName + UNIQ));

		logger.info("\nExecuting : " + GREP_NOTFOUND + fileName + UNIQ);
		log_Content.append(handler.simpleExec(GREP_NOTFOUND + fileName + UNIQ));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		handler.disconnect();
		return log_Content.toString();
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> executeCommands(String command) {
		String completeOutput = handler.simpleExec(PROFILE + command);
		List<String> dir = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			dir.add(output.trim());
		}
		handler.disconnect();
		return dir;
	}

	/**
	 * 
	 * @return String
	 */
	public String getActiveInterfaceSchedulerLogContent(String interfaceDir) {
		logger.info("\nChecking any errors/exceptions/warnings in : " + interfaceDir);
		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + interfaceDir + "/scheduler*.log | " + LOG_GREP_ERROR_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + interfaceDir + "/scheduler*.log | " + LOG_GREP_EXCEPTION_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + interfaceDir + "/scheduler*.log | " + LOG_GREP_WARNING_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + interfaceDir + "/scheduler*.log | " + LOG_GREP_FAIELD_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + interfaceDir + "/scheduler*.log | " + LOG_GREP_NOTFOUND_CMD));
		logger.info("\n Content of " + interfaceDir + " log file : \n" + log_Content.toString());
		handler.disconnect();
		return log_Content.toString();
	}

	/**
	 * 
	 * @return String
	 */
	public String getInstalledTeckPacks(String teckpackDir) {
		logger.info("\nChecking any errors/exceptions/warnings in : " + teckpackDir);
		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + teckpackDir + "/scheduler*.log | " + LOG_GREP_ERROR_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + teckpackDir + "/scheduler*.log | " + LOG_GREP_EXCEPTION_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + teckpackDir + "/scheduler*.log | " + LOG_GREP_WARNING_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + teckpackDir + "/scheduler*.log | " + LOG_GREP_FAIELD_CMD));
		log_Content.append(
				handler.simpleExec(CAT_CMD + logsPath + teckpackDir + "/scheduler*.log | " + LOG_GREP_NOTFOUND_CMD));
		logger.info("\n Content of " + teckpackDir + " log file : \n" + log_Content.toString());
		handler.disconnect();
		return log_Content.toString();
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommand(String command) {
		String completeOutput = handler.simpleExec(PROFILE + command);
		handler.disconnect();
		return completeOutput;
	}

	/**
	 * 
	 * @return String
	 */
	public String getLicenseManagerLogContent() {
		String completeOutput = handler.simpleExec(DCUSER_PROFILE + LICENSE_MGR_LIST_LOG_CMD + "'");
		logger.info("completeOutput:::" + completeOutput);
		List<String> files = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			files.add(output.trim());
		}
		String log;
		if (!files.isEmpty()) {
			log = handler.simpleExec(DCUSER_PROFILE + LICENSE_MGR_LOG_CMD + files.get(0) + "'");
		} else {
			log = "License manager log does not exists.";
		}
		handler.disconnect();
		return log;
	}

	/**
	 * 
	 * @return String
	 */
	public String licenseServerStatus() {
		logger.info("licenseServerStatus ");
		return handler.simpleExec(DCUSER_PROFILE + LICENSE_SERVER_STATUS_CMD + "'");
	}

	/**
	 * 
	 * @return String
	 */
	public String licenseMgrStatus() {
		logger.info("licenseServerStatus ");
		return handler.simpleExec(DCUSER_PROFILE + LICENSE_MGR_STATUS_CMD + "'");
	}

	/**
	 * 
	 * @return String
	 */
	public String verifyFeatureMappingInfo(String cxcNumber) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + LICENSE_MAPPING_INFO_CMD + " " + cxcNumber + "'");
		final String output = handler.simpleExec(DCUSER_PROFILE + LICENSE_MAPPING_INFO_CMD + " " + cxcNumber + "'");
		logger.info("\nCommand Output : " + output);
		handler.disconnect();
		return output;
	}

	/**
	 * 
	 * @return String
	 */
	public String validateLicenseManager(String cxcNumber) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + LICENSE_VALIDATION_CMD + " " + cxcNumber + "'");
		final String output = handler.simpleExec(DCUSER_PROFILE + LICENSE_VALIDATION_CMD + " " + cxcNumber + "'");
		logger.info("\nCommand Output : " + output);
		handler.disconnect();
		return output;
	}

	/**
	 * 
	 * @return String
	 */
	public String validateLicenseManagerOperations(String operations) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + LICENSE_CMD + " " + operations + "'");
		final String output = handler.simpleExec(DCUSER_PROFILE + LICENSE_CMD + " " + operations + "'");
		logger.info("\nCommand Output : " + output);
		handler.disconnect();
		return output;
	}

	/**
	 * 
	 * @return String
	 */
	public String dClipexecuteCommand(String command) {
		logger.info("Executing : " + DCUSER_PROFILE + command + "'");
		final String output = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("Output : " + output);
		handler.disconnect();
		return output.trim();
	}

	/**
	 * 
	 * @param fileName
	 * @param serverPath
	 */
	public void copyFileToServer(String fileName, String serverPath) {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
		String pythonscr = FileFinder.findFile(fileName).get(0);
		logger.info("File : " + pythonscr);

		preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, serverPath);
		logger.info("****** Copied the " + fileName + " script to server in " + serverPath + " location ******");
	}

}