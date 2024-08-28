package com.ericsson.eniq.taf.installation.test.operators;

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
public class VerifyEngineInstallationOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyEngineInstallationOperator.class);

	private final String DC_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DC_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	/*
	 * private final String SERVICES_STOP_CMD =
	 * DataHandler.getAttribute("platform.engine.services.stop").toString(); private
	 * final String SERVICES_START_CMD =
	 * DataHandler.getAttribute("platform.engine.services.stop").toString(); private
	 * final String LOG_EXCEPTION_GREP_CMD =
	 * DataHandler.getAttribute("platform.log.exceptions.grep").toString();
	 */
	private final String versionDBDir = DataHandler.getAttribute("platform.install.versionDBDir").toString();
	private final String moduleExtractionDir = DataHandler.getAttribute("platform.install.moduleExtractionDir")
			.toString();

	private final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep ";
	private final String MODULES_EXTRACTED_CMD = "cd " + moduleExtractionDir + ";" + " ls -d */ | grep ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyEngineInstallationOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/*	*//**
			 * 
			 * @param fileName file name
			 * @return String
			 *//*
				 * public String getEngineLogContent(String fileName) {
				 * logger.info("verifyPlatformLogExists::" + fileName);
				 * logger.info("verifyPlatformLogExists file path::" + logFileDir + fileName);
				 * return executeCommand(GREP_EXCEPTION_LOG_FILES_CMD + fileName); }
				 */

	/**
	 * 
	 * @param module module
	 * @return String
	 */
	public String versionDBModuleUpdated(String module) {
		logger.info("versionDBModuleUpdated::" + module);
		String completeOutput = executeCommand(CAT_VERSION_DBPROPERTIES_CMD + module);
		logger.info("versionDBModuleUpdated::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @param module module
	 * @return String
	 */
	public String modulesExtracted(String module) {
		logger.info("modulesExtractedUpdated::" + module);
		String completeOutput = executeCommand(MODULES_EXTRACTED_CMD + module);
		logger.info("modulesExtractedUpdated::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommand(String command) {
		logger.info("\n  Command Execution : " + command);
		String completeOutput = handler.simpleExec(command);
		logger.info("\n  Command Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
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
}
