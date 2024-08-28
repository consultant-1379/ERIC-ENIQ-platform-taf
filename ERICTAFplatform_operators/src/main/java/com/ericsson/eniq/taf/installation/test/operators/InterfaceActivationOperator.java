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
public class InterfaceActivationOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(InterfaceActivationOperator.class);

	private final String USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String versionDBDir = DataHandler.getAttribute("platform.install.versionDBDir").toString();
	private final String moduleExtractionDir = DataHandler.getAttribute("platform.install.moduleExtractionDir")
			.toString();

	private final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep ";
	private final String MODULES_EXTRACTED_CMD = "cd " + moduleExtractionDir + ";" + " ls -d */ | grep ";
	private final String PROFILE = ". /eniq/home/dcuser/.profile; ";
	private final String DCUSER = "su - dcuser -c '";

	private CLICommandHelper handler;
	private Host eniqshost;

	public InterfaceActivationOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

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
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommand(String command) {
		logger.info("Executing : " + PROFILE + command);
		final String output = handler.simpleExec(PROFILE + command);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @return String
	 */
	public String executeCommandwithoutProfile(String command) {
		logger.info("Executing : " + command);
		final String output = handler.simpleExec(command);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
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
		return dir;
	}

	/**
	 * 
	 * @return String
	 */
	public String dwhSetExecuteCommandInDcuser(String command) {
		logger.info("\nExecuting : " + DCUSER + command + "'");
		final String output = handler.simpleExec(DCUSER + command + "'");
		logger.info("Command Output : \n " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @return String
	 */
	public String dwhSetEcecuteCommandInRoot(String command) {
		logger.info("\nExecuting : " + command);
		final String output = handler.simpleExec(command);
		logger.info("Command Output : \n " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}
}
