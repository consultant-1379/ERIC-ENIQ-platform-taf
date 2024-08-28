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
public class VerifyEWMOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyEWMOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String ROOT_USER = DataHandler.getAttribute("platform.user.root").toString();
	private final String ROOT_PASSWORD = DataHandler.getAttribute("platform.password.root").toString();
	private final String DeltaViewCreationDir = DataHandler.getAttribute("platform.install.DeltaViewCreation")
			.toString();
	private final String AccessVerificationDir = DataHandler.getAttribute("platform.install.AccessVerification")
			.toString();

	private final String DV_DELTAVIEWCREATION_SCRIPT_CMD = "cd " + DeltaViewCreationDir + ";"
			+ " ls | grep deltaviewcreation.bsh";
	private final String DV_ACCESS_VERIFICATION_SCRIPT_CMD = "cd " + AccessVerificationDir + ";"
			+ " ls| grep accessverificationscript.bsh";
	// private final String path= "chmod 777 /eniq/log/accessverificationscript.bsh";
	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	
	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyEWMOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @return String
	 */

	public String AccessVerification_Execution() {

		String completeOutput = handler.simpleExec(DV_ACCESS_VERIFICATION_SCRIPT_CMD);
		logger.info("Access_Verification_Script::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
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

}
