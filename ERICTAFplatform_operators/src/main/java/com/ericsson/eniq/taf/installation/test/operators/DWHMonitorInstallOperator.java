package com.ericsson.eniq.taf.installation.test.operators;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.eniq.taf.cli.CLIOperator;

/**
 * 
 * @author xsounpk
 *
 */
@Singleton
public class DWHMonitorInstallOperator extends CLIOperator{ 
	Logger logger = LoggerFactory.getLogger(DWHMonitorInstallOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("log.warning.grep").toString();
	private final String LOG_GREP_FAILED_CMD = DataHandler.getAttribute("log.fail.grep").toString();
	private final String LOG_GREP_FATAL_CMD = DataHandler.getAttribute("log.fatal.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();

	private final String enginelogsPath = DataHandler.getAttribute("platform.install.DWHEngineeLog").toString();
	private final String errorlogsPath = DataHandler.getAttribute("platform.install.DWHEngineeLog").toString();
	private final String enginelogsPath1 = DataHandler.getAttribute("platform.log.tp_installer").toString();
	private final String errorlogsPath1 = DataHandler.getAttribute("platform.log.tp_installer").toString();

	private final String GREP_ERROR_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNING_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAIL_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_FAILED_CMD + " ";
	private final String GREP_FATAL_IN_ENGINE = "cd " + enginelogsPath + ";" + LOG_GREP_FATAL_CMD + " ";

	private final String GREP_ERROR_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNING_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAIL_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_FAILED_CMD + " ";
	private final String GREP_FATAL_IN_ERROR = "cd " + errorlogsPath + ";" + LOG_GREP_FATAL_CMD + " ";

	private final String GREP_ERROR_IN_ENGINE_1 = "cd " + enginelogsPath1 + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ENGINE_1 = "cd " + enginelogsPath1 + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNING_IN_ENGINE_1 = "cd " + enginelogsPath1 + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAIL_IN_ENGINE_1 = "cd " + enginelogsPath1 + ";" + LOG_GREP_FAILED_CMD + " ";
	private final String GREP_FATAL_IN_ENGINE_1 = "cd " + enginelogsPath1 + ";" + LOG_GREP_FATAL_CMD + " ";

	private final String GREP_ERROR_IN_ERROR_1 = "cd " + errorlogsPath1 + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ERROR_1 = "cd " + errorlogsPath1 + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNING_IN_ERROR_1 = "cd " + errorlogsPath1 + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAIL_IN_ERROR_1 = "cd " + errorlogsPath1 + ";" + LOG_GREP_FAILED_CMD + " ";
	private final String GREP_FATAL_IN_ERROR_1 = "cd " + errorlogsPath1 + ";" + LOG_GREP_FATAL_CMD + " ";

	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public DWHMonitorInstallOperator() {
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

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String engineLogContent(String fileName) {
		logger.info("Engine Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ENGINE + fileName));
		//log_Content.append(handler.simpleExec(GREP_WARNING_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_FAIL_IN_ENGINE + fileName));
		log_Content.append(handler.simpleExec(GREP_FATAL_IN_ENGINE + fileName));
		logger.info("\n Content of Engine log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String errorLogContent(String fileName) {
		logger.info("Error Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNING_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_FAIL_IN_ERROR + fileName));
		log_Content.append(handler.simpleExec(GREP_FATAL_IN_ERROR + fileName));
		logger.info("\n Content of Error log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String engineLogContent1(String fileName) {
		logger.info("Engine Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ENGINE_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ENGINE_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNING_IN_ENGINE_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_FAIL_IN_ENGINE_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_FATAL_IN_ENGINE_1 + fileName));
		logger.info("\n Content of Engine log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName file name
	 * @return String
	 */
	public String errorLogContent1(String fileName) {
		logger.info("Error Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ERROR_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ERROR_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNING_IN_ERROR_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_FAIL_IN_ERROR_1 + fileName));
		log_Content.append(handler.simpleExec(GREP_FATAL_IN_ERROR_1 + fileName));
		logger.info("\n Content of Error log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

}
