package com.ericsson.eniq.taf.installation.test.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.CLIOperator;
import com.ericsson.cifwk.taf.tools.cli.handlers.impl.RemoteObjectHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.google.inject.Singleton;

@Singleton
public class RunScriptOperator extends CLIOperator {

	private final String SCRIPTPATH = "/tmp/";
	private final String SHELL = "sh";
	private final String PERMISSIONS = "777";
	private final String CHMOD = "chmod";
	private final String SCRIPTNAME = "echo_script.sh";

	private CLICommandHelper handler;
	private Host eniqshost;
	Logger logger = LoggerFactory.getLogger(RunScriptOperator.class);

	public RunScriptOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	public void copyScript() {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		if (eniqshost.getHostname().trim().equalsIgnoreCase("eniqs")) {

			preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());

			String pythonscr = FileFinder.findFile("echo_script.sh").get(0);
			logger.info("File : " + pythonscr);

			preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, SCRIPTPATH);
			logger.info("****** Copied the echo_script.sh script to server in /tmp location ******");
		}

	}

	public void permission() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append(CHMOD);
		cmdToExec.append(" ");
		cmdToExec.append(PERMISSIONS);
		cmdToExec.append(" ");
		cmdToExec.append(SCRIPTPATH);
		cmdToExec.append(SCRIPTNAME);

		logger.info("****** Given permission to the echo_script.sh script ******");
		logger.info("Executing : " + cmdToExec.toString());
		logger.info(handler.simpleExec(cmdToExec.toString()));
	}

	public String executescript() {
		final StringBuilder cmdToExec = new StringBuilder();
		cmdToExec.append(SHELL);
		cmdToExec.append(" ");
		cmdToExec.append(SCRIPTPATH);
		cmdToExec.append(SCRIPTNAME);

		logger.info("Executing : " + cmdToExec.toString());
		return handler.simpleExec(cmdToExec.toString());
	}
}
