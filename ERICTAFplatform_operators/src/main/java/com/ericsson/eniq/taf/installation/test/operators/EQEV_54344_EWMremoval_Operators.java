package com.ericsson.eniq.taf.installation.test.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.eniq.taf.cli.CLIOperator;

public class EQEV_54344_EWMremoval_Operators extends CLIOperator {
	private final String logsPath = DataHandler.getAttribute("platform.scheduler.logsPath").toString();

	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";

	private CLICommandHelper handler;
	private Host eniqshost;

	Logger logger = LoggerFactory.getLogger(EQEV_54344_EWMremoval_Operators.class);

	public EQEV_54344_EWMremoval_Operators() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public String executeCommandDcuser(String command) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + command + "'");
		String completeOutput = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("\nCommand Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput.trim();
	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public String executeCommand(String command) {
		logger.info("\nExecuting : " + command);
		String completeOutput = handler.simpleExec(command);
		logger.info("\nCommand Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput.trim();
	}

}
