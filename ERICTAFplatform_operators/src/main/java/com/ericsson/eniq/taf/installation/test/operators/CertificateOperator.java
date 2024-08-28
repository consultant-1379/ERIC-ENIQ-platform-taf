package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class CertificateOperator extends CLIOperator {

	private final String logsPath = DataHandler.getAttribute("platform.scheduler.logsPath").toString();

	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";

	private CLICommandHelper handler;
	private Host eniqshost;

	Logger logger = LoggerFactory.getLogger(CertificateOperator.class);

	public CertificateOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public String executeCommands(String command) {
		logger.info("\nExecuting : " + command);
		String completeOutput = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("\nCommand Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput.trim();
	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public List<String> executeCommandsRetunList(String command) {
		List<String> finalOutput = new ArrayList<>();
		logger.info("\nExecuting : " + command);
		String completeOutput = handler.simpleExec(DCUSER_PROFILE + command + "'");
		for (String str : completeOutput.split("\n")) {
			finalOutput.add(str);
		}
		logger.info("\nCommand Output : \n" + finalOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return finalOutput;
	}
}