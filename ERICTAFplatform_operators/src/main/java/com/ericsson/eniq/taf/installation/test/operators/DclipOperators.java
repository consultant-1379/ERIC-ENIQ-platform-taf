package com.ericsson.eniq.taf.installation.test.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.handlers.impl.RemoteObjectHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

/**
 * 
 * @author xsounpk
 *
 */
@Singleton
public class DclipOperators extends CLIOperator {
	Logger logger = LoggerFactory.getLogger(DclipOperators.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String ROOT_USER = DataHandler.getAttribute("platform.user.root").toString();
	private final String ROOT_PASSWORD = DataHandler.getAttribute("platform.password.root").toString();
	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private CLICommandHelper handler;
	private Host eniqshost;

	public DclipOperators() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		eniqshost.setHostname(DataHandler.getAttribute("host.eniqs.ip").toString());
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

	/**
	 * 
	 * @param fileName
	 * @param serverPath
	 */
	public void copyFileToServer(String fileName, String serverPath) {
		logger.info("Copy script to server");
		RemoteObjectHandler preCheckRemoteFileHandler = null;
		preCheckRemoteFileHandler = new RemoteObjectHandler(eniqshost, eniqshost.getDefaultUser());
		String pythonscr = FileFinder.findFile(fileName).get(0);
		logger.info("File : " + pythonscr);

		preCheckRemoteFileHandler.copyLocalFileToRemote(pythonscr, serverPath);
		logger.info("****** Copied the " + fileName + " script to server in " + serverPath + " location ******");
		
	}

}
