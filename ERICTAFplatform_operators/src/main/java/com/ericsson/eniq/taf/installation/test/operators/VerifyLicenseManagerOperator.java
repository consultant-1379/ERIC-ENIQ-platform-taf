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
public class VerifyLicenseManagerOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyLicenseManagerOperator.class);

	private final String DCUSER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String licenseLogFileDir = DataHandler.getAttribute("platform.install.license.logFileDir").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();
	private final String lmDir = DataHandler.getAttribute("platform.install.lm.dir").toString();

	private final String GREP_ERROR = "cd " + licenseLogFileDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION = "cd " + licenseLogFileDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + licenseLogFileDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED = "cd " + licenseLogFileDir + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND = "cd " + licenseLogFileDir + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	private final String LICENSE_MGR_LOG_CMD = "cd " + licenseLogFileDir + "; cat ";
	private final String LICENSE_MGR_LIST_LOG_CMD = "cd " + licenseLogFileDir + "; ls -t -1";
	private final String LICENSE_SERVER_STATUS_CMD = "cd " + lmDir + "; ./licmgr -serverstatus";
	private final String LICENSE_MGR_STATUS_CMD = "cd " + lmDir + "; ./licmgr -status";
	private final String LICENSE_MAPPING_INFO_CMD = "licmgr -map description";
	private final String LICENSE_VALIDATION_CMD = "licmgr -isvalid";
	private final String LICENSE_CMD = "licmgr";
	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyLicenseManagerOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
		
	}

	/**
	 * 
	 * @return String
	 */
	public String getLicenseManagerLogContent() {
		String completeOutput = handler.simpleExec(PROFILE + LICENSE_MGR_LIST_LOG_CMD + "'");
		logger.info("completeOutput:::" + completeOutput);
		List<String> files = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			files.add(output.trim());
		}
		String log;
		if (!files.isEmpty()) {
			log = handler.simpleExec(PROFILE + LICENSE_MGR_LOG_CMD + files.get(0) + "'");
		} else {
			log = "License manager log does not exists.";
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log;
	}

	/**
	 * 
	 * @return String
	 */
	public String licenseServerStatus() {
		logger.info("licenseServerStatus ");
		return handler.simpleExec(PROFILE + LICENSE_SERVER_STATUS_CMD + "'");
		
	}

	/**
	 * 
	 * @return String
	 */
	public String licenseMgrStatus() {
		logger.info("licenseServerStatus ");
		return handler.simpleExec(PROFILE + LICENSE_MGR_STATUS_CMD + "'");
	}

	/**
	 * 
	 * @return String
	 */
	public String verifyFeatureMappingInfo(String cxcNumber) {
		logger.info("\nExecuting : " + PROFILE + LICENSE_MAPPING_INFO_CMD + " " + cxcNumber + "'");
		final String output = handler.simpleExec(PROFILE + LICENSE_MAPPING_INFO_CMD + " " + cxcNumber + "'");
		logger.info("\nCommand Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output;
	}

	/**
	 * 
	 * @return String
	 */
	public String validateLicenseManager(String cxcNumber) {
		logger.info("\nExecuting : " + PROFILE + LICENSE_VALIDATION_CMD + " " + cxcNumber + "'");
		final String output = handler.simpleExec(PROFILE + LICENSE_VALIDATION_CMD + " " + cxcNumber + "'");
		logger.info("\nCommand Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output;
	}

	/**
	 * 
	 * @return String
	 */
	public String validateLicenseManagerOperations(String operations) {
		logger.info("\nExecuting : " + PROFILE + LICENSE_CMD + " " + operations + "'");
		final String output = handler.simpleExec(PROFILE + LICENSE_CMD + " " + operations + "'");
		logger.info("\nCommand Output : " + output);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return output;
	}
}
