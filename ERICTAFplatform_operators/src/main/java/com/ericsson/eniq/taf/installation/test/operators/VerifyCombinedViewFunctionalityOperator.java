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
public class VerifyCombinedViewFunctionalityOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyCombinedViewFunctionalityOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String logFileDir = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String versionDBDir = DataHandler.getAttribute("platform.install.versionDBDir").toString();
	private final String AccessVerificationDir = DataHandler.getAttribute("platform.install.AccessVerification")
			.toString();

	private final String CV_INSTALLER_NAME_CMD = "cd /eniq/sw/installer; ls | grep 'installer' | grep '.zip' ";
	private final String CV_TEMP_DIR_INSTLLER_CMD = "cd /eniq/sw/installer/; rm -rf temp; mkdir temp;";
	private final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep ";
	private final String CV_LTE_WCDMA_SCRIPTS_CMD = "cd " + versionDBDir + ";"
			+ " ls | grep WCDMACombinedViewCreation.bsh | grep erbscombinedview.bsh ";
	private final String DV_ACCESS_VERIFICATION_SCRIPT_CMD = "cd " + AccessVerificationDir + ";"
			+ " ls| grep accessverificationscript.bsh";
	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private final String ltewcdmaLogDir = DataHandler.getAttribute("platform.install.ltewcdmaLogDir").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	
	private final String GREP_ERROR_IN_ENGINE_LOG = "cd " + ltewcdmaLogDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_IN_ENGINE_LOG = "cd " + ltewcdmaLogDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS_IN_ENGINE_LOG = "cd " + ltewcdmaLogDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED_IN_ENGINE_LOG = "cd " + ltewcdmaLogDir + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND_IN_ENGINE_LOG = "cd " + ltewcdmaLogDir + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	
	private final String ERROR_IN_ENGINE_LOG = "cd " + logFileDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String EXCEPTION_IN_ENGINE_LOG = "cd " + logFileDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String WARNINGS_IN_ENGINE_LOG = "cd " + logFileDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String FAILED_IN_ENGINE_LOG = "cd " + logFileDir + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String NOTFOUND_IN_ENGINE_LOG = "cd " + logFileDir + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	
	
	
	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyCombinedViewFunctionalityOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @return String
	 */
	public String installetionOfInstaller() {
		final String completeOutput = handler.simpleExec(CV_INSTALLER_NAME_CMD);
		logger.info("CV_INSTALLER_NAME_CMD :::" + completeOutput);
		List<String> files = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			files.add(output.trim());
		}
		String op = null;
		if (!files.isEmpty()) {
			op = handler.simpleExec(CV_TEMP_DIR_INSTLLER_CMD + " cp " + files.get(0) + " ./temp; cd temp; unzip "
					+ files.get(0) + "; chmod u+x install_installer.sh; ./install_installer.sh -v");
		}
		logger.info("CV_INSTALLEer :::" + op);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return op;
	}

	/**
	 * 
	 * @param module module
	 * @return String
	 */
	public String versionDBModuleUpdated() {
		String completeOutput = handler.simpleExec(CAT_VERSION_DBPROPERTIES_CMD + "installer");
		logger.info("versionDBModuleUpdated::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return String
	 */
	public String getLteAndWcdmaScripts() {
		String completeOutput = handler.simpleExec(CV_LTE_WCDMA_SCRIPTS_CMD);
		logger.info("getLteAndWcdmaScripts::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return String
	 */
	public String viewsG1AndG2Accessibility() {
		String completeOutput = handler.simpleExec(CV_LTE_WCDMA_SCRIPTS_CMD);
		logger.info("getLteAndWcdmaScripts::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

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
	public String executeCommandDcuser(String command) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + command + "'");
		final String output = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("\nOutput : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output.trim();
	}
	
	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String logContent(String fileName) {
		logger.info("Engine Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_FAILED_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(GREP_NOTFOUND_IN_ENGINE_LOG + fileName));
		logger.info("\n Content of Engine log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}
	
	
	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String PFlogContent(String fileName) {
		logger.info("Engine Log : ");

		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(ERROR_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(EXCEPTION_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(WARNINGS_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(FAILED_IN_ENGINE_LOG + fileName));
		log_Content.append(handler.simpleExec(NOTFOUND_IN_ENGINE_LOG + fileName));
		logger.info("\n Content of Engine log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}
}
