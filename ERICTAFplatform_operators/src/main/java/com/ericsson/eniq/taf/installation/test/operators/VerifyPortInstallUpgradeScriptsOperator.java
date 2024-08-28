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
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class VerifyPortInstallUpgradeScriptsOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyPortInstallUpgradeScriptsOperator.class);

	private final String ROOT = DataHandler.getAttribute("platform.user.root").toString();
	private final String ROOT_PASSWORD = DataHandler.getAttribute("platform.password.root").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String logFileDir = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String versionDBDir = DataHandler.getAttribute("platform.portInstall.versionDBDir").toString();
	private final String moduleExtractionDir = DataHandler.getAttribute("platform.install.moduleExtractionDir")
			.toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();

	private final String LIST_LOG_FILES_CMD = "cd " + logFileDir + ";" + " ls -t -1";
	private final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep ";
	private final String MODULES_EXTRACTED_CMD = "cd " + moduleExtractionDir + ";" + " ls -d */ | grep ";
	private final String GREP_ERROR = "cd " + logFileDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION = "cd " + logFileDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + logFileDir + ";" + LOG_GREP_WARNING_CMD + " ";

	private final String SPECIFIC_MODULE_EXTRACTED_CMD = "cd " + logFileDir + ";" + " ls -d * | grep ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyPortInstallUpgradeScriptsOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listPlatformLogFiles() {
		logger.info("verify Platform Log Exists::");
		String completeOutput = handler.simpleExec(LIST_LOG_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @param fileName
	 *            fileName
	 * @return String
	 */
	public String getPlatformLogContent(String fileName) {
		logger.info("\nChecking any errors/exceptions/warnings in : " + logFileDir + fileName);
		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param module
	 *            module
	 * @return String
	 */
	public String versionDBModuleUpdated(String module) {
		logger.info("Verifying Version DB update for the module : " + module);
		String completeOutput = handler.simpleExec(CAT_VERSION_DBPROPERTIES_CMD + module);
		logger.info("Version DB Module updated: Output : " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

	/**
	 * 
	 * @param module
	 *            module
	 * @return String
	 */
	public String modulesExtracted(String module) {
		logger.info("Checking Module Extraction for : " + module);
		String completeOutput = handler.simpleExec(MODULES_EXTRACTED_CMD + module);
		logger.info("Extracted Module Output : " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

	/**
	 * 
	 * @param module
	 *            module
	 * @return String
	 */
	public String specificModuleExtracted(String module) {
		logger.info("Checking assureddc pkg log exists in installation log path : " + module);
		String completeOutput = handler.simpleExec(SPECIFIC_MODULE_EXTRACTED_CMD + module);
		logger.info("Extracted Module Output : " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

}
