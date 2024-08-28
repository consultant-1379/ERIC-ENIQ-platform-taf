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
public class RuntimeOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(LoadingOperator.class);

	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private static final String DBIP = "cat /etc/hosts | grep repdb";

	private CLICommandHelper handler;
	private Host eniqshost;

	public RuntimeOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
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

}
