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
public class VerifyInstallerOperator extends CLIOperator {
	
	Logger logger = LoggerFactory.getLogger(VerifyInstallerOperator.class);
	
	private final String USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String logFileDir = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String versionDBDir = DataHandler.getAttribute("platform.portInstall.versionDBDir").toString();
	private final String logsPath = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();
	
	private final String LIST_LOG_FILES_CMD = "cd " + logFileDir + ";" + " ls -t -1";
	private final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";" + " cat versiondb.properties | grep ";
    private final String GREP_ERROR = "cd " + logsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION = "cd " + logsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + logsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED = "cd " + logsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND = "cd " + logsPath + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	
	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyInstallerOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
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
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String getInstallerModuleLogContent(String fileName) {
		logger.info("Verifying Installer Module Log Exists : " + fileName);
		
		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS + fileName));
		log_Content.append(handler.simpleExec(GREP_FAILED + fileName));
		log_Content.append(handler.simpleExec(GREP_NOTFOUND + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
		
		//return handler.simpleExec(GREP_EXCEPTION_LOG_FILES_CMD + fileName);
	}

	/**
	 * 
	 * @param module module
	 * @return String
	 */
	public String versionDBModuleUpdated(String module) {
		logger.info("Checkig versionDBModuleUpdate for : " + module);
		String completeOutput = handler.simpleExec(CAT_VERSION_DBPROPERTIES_CMD + module);
		logger.info("versionDBModuleUpdated output : " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}
}
