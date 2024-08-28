package com.ericsson.eniq.taf.installation.test.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.User;
import com.ericsson.de.tools.cli.CliIntermediateResult;
import com.ericsson.de.tools.cli.CliToolShell;
import com.ericsson.de.tools.cli.CliTools;
import com.ericsson.de.tools.cli.WaitConditions;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class VerifyKeystorePasswordOperator extends CLIOperator {

	private final String logsPath = DataHandler.getAttribute("platform.scheduler.logsPath").toString();
	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();

	private final static String versionDBDir = DataHandler.getAttribute("platform.portInstall.versionDBDir").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();

	private final String LIST_LOG_FILES_CMD = "cd " + logsPath + ";" + " ls -t -1 *.log";
	private final String GREP_ERROR = "cd " + logsPath + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String UNIQ = "| sort | uniq";
	private final String GREP_EXCEPTION = "cd " + logsPath + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + logsPath + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED = "cd " + logsPath + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND = "cd " + logsPath + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	private static final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep -i scheduler";
	private final String CAT_CMD = "cat ";
	private final String PROFILE = ". /eniq/home/dcuser/.profile; ";

	private final String DCUSER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String licenseLogFileDir = DataHandler.getAttribute("platform.install.license.logFileDir").toString();
	private final String lmDir = DataHandler.getAttribute("platform.install.lm.dir").toString();

	private final String LICENSE_MGR_LOG_CMD = "cd " + licenseLogFileDir + "; cat ";
	private final String LICENSE_MGR_LIST_LOG_CMD = "cd " + licenseLogFileDir + "; ls -t -1";
	private final String LICENSE_SERVER_STATUS_CMD = "cd " + lmDir + "; ./licmgr -serverstatus";
	private final String LICENSE_MGR_STATUS_CMD = "cd " + lmDir + "; ./licmgr -status";
	private final String LICENSE_MAPPING_INFO_CMD = "licmgr -map description";
	private final String LICENSE_VALIDATION_CMD = "licmgr -isvalid";
	private final String LICENSE_CMD = "licmgr";
	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private CliToolShell handler;
	private Host eniqshost;
	private User usr;

	Logger logger = LoggerFactory.getLogger(VerifyKeystorePasswordOperator.class);

	public VerifyKeystorePasswordOperator() {

		CliToolShell handler = CliTools.sshShell("atvts3371.athtem.eei.ericsson.se").withUsername("root")
				.withPassword("shroot12").withPort(2251).build();

	}

	public String writelnsdf(String command) {
		handler.writeLine(command, WaitConditions.substring("Enter Old Keystore Password:",  300));
		handler.writeLine("EniqOnSSL", WaitConditions.substring("Enter New Keystore Password:"));

		handler.writeLine("EniqsOnSSL", WaitConditions.substring("Re-enter New Keystore Password:"));
		CliIntermediateResult result = handler.writeLine("EniqsOnSSL");
		String output = result.getOutput();
		handler.close();
		return output;
	}

}