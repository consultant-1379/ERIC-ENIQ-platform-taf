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
public class InterfaceReactivationOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(InterfaceReactivationOperator.class);

	private final String USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String logsPath = DataHandler.getAttribute("platform.log.tp_installer").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();

	private final String PROFILE = ". /eniq/home/dcuser/.profile; ";
	private final String GREP_ERROR = "cd " + logsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION = "cd " + logsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + logsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED = "cd " + logsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND = "cd " + logsPath + ";" + LOG_GREP_NOTFOUND_CMD + " ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public InterfaceReactivationOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getInterfaceReactivationLogContent(String fileName) {
		logger.info("Interface Reactivation Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS + fileName));
		log_Content.append(handler.simpleExec(GREP_FAILED + fileName));
		log_Content.append(handler.simpleExec(GREP_NOTFOUND + fileName));
		logger.info("\n Content Interface Reactivation file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String interfaceReactivation(String reactivation) {
		logger.info("Interface Reactivation Log : ");
		String output = handler.simpleExec(PROFILE + reactivation);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String cmdExecute(String cmd) {
		logger.info("Executing  : " + cmd);
		String output = handler.simpleExec(cmd);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output;
	}

}
