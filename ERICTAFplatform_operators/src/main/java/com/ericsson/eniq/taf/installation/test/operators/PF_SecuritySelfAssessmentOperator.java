package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class PF_SecuritySelfAssessmentOperator extends CLIOperator {

	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private CLICommandHelper handler;
	private Host eniqshost;
	private User usr;

	Logger logger = LoggerFactory.getLogger(PF_SecuritySelfAssessmentOperator.class);

	public PF_SecuritySelfAssessmentOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		logger.info("vApp Name: " + eniqshost.getIp());
		usr = eniqshost.getUser(eniqshost.getUser());
		handler = new CLICommandHelper(eniqshost, usr);

	}

	/**
	 *
	 * @return list
	 */
	public List<String> executeCommands(String command) {
		logger.info("\nExecuting : " + command);
		String completeOutput = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("\nCommand Output : " + completeOutput);
		List<String> dir = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			dir.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return dir;
	}
}
