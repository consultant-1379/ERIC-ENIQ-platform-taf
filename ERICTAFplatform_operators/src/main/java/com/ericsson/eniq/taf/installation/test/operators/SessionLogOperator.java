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
public class SessionLogOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(SessionLogOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();

	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private static final String DBIP = "cat /etc/hosts | grep repdb";
	private static final String CMD1 = "cat /eniq/log/sw_log/scheduler/";
	private static final String CMD2 = " | grep -i 'SessionLoader_Starter' | cut -d ' ' -f2";
	private static final String CMD3 = "| wc -l";

	private CLICommandHelper handler;
	private Host eniqshost;

	public SessionLogOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @return String
	 */
	public String latestschedulerLogFile(String command) {
		logger.info("Executing : " + PROFILE + command + "'");
		final String output = handler.simpleExec(PROFILE + command + "'");
		logger.info("Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listOfSetStarterTime(String command) {
		String completeOutput = handler.simpleExec(command);
		List<String> dir = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			dir.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return dir;
	}

	/**
	 * 
	 * @return list
	 */
	public String countOflines(String command) {
		logger.info("Executing : " + command);
		final String output = handler.simpleExec(command);
		logger.info("Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}
}