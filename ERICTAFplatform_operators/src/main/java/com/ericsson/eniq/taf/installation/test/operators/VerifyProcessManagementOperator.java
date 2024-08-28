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
public class VerifyProcessManagementOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyProcessManagementOperator.class);

	private final String logFileDir = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.warning.grep").toString();
	private final String LOG_GREP_FAIELD_CMD = DataHandler.getAttribute("platform.log.failed.grep").toString();
	private final String LOG_GREP_NOTFOUND_CMD = DataHandler.getAttribute("platform.log.notfound.grep").toString();
	private final String LOG_EXCEPTION_GREP_CMD = DataHandler.getAttribute("platform.log.exceptions.grep").toString();

	private final String GREP_ERROR = "cd " + logFileDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION = "cd " + logFileDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + logFileDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_FAILED = "cd " + logFileDir + ";" + LOG_GREP_FAIELD_CMD + " ";
	private final String GREP_NOTFOUND = "cd " + logFileDir + ";" + LOG_GREP_NOTFOUND_CMD + " ";
	private final String START_SERVICE_CMD = "systemctl start ";
	private final String STOP_SERVICE_CMD = "systemctl stop ";
	private final String ENABLE_SERVICE_CMD = "systemctl enable ";
	private final String DISABLE_SERVICE_CMD = "systemctl disable ";
	private final String STATUS_SERVICE_CMD = "systemctl status ";
	private final String SERVICE_LOG_CMD = "cd " + logFileDir + ";" + LOG_EXCEPTION_GREP_CMD + " ";
	private final String RESTART_ALL_SERVICES_CMD = "cd /eniq/admin/bin; bash ./manage_deployment_services.bsh -a restart -s ALL ";
	private final String PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";
	private final String ENGINE_COMMAND = "engine -e ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyProcessManagementOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String startService(String serviceName) {
		logger.info("\nExecuting : " + START_SERVICE_CMD + serviceName);
		String completeOutput = handler.simpleExec(START_SERVICE_CMD + serviceName);
		logger.info("Command Output :\n " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String enableService(String serviceName) {
		logger.info("\n Executing : " + ENABLE_SERVICE_CMD + " " + serviceName);
		String completeOutput = handler.simpleExec(ENABLE_SERVICE_CMD + serviceName);
		logger.info("Command Output :\n " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String checkStatusOfService(String serviceName) {
		logger.info("\nExecuting : " + STATUS_SERVICE_CMD + serviceName);
		String completeOutput = handler.simpleExec(STATUS_SERVICE_CMD + serviceName);
		logger.info("Command Output :\n " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String disableService(String serviceName) {
		logger.info("\nExecuting : " + DISABLE_SERVICE_CMD + serviceName);
		String completeOutput = handler.simpleExec(DISABLE_SERVICE_CMD + serviceName);
		logger.info("Command Output :\n " + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String serviceStatus(String serviceName) {
		logger.info("serviceStatus::" + serviceName);
		String completeOutput = handler.simpleExec(START_SERVICE_CMD + serviceName);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String getServiceLogContent(String serviceName) {
		logger.info("verifyPlatformLogExists file path::" + logFileDir + serviceName);
		return handler.simpleExec(SERVICE_LOG_CMD + serviceName);
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String stopService(String serviceName) {
		logger.info("stopService::" + serviceName);
		String completeOutput = handler.simpleExec(STOP_SERVICE_CMD + serviceName);
		logger.info("stopService::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @param serviceName service name
	 * @return String
	 */
	public String dependentServices(String serviceName) {
		logger.info("stopService::" + serviceName);
		String completeOutput = handler.simpleExec(STOP_SERVICE_CMD + serviceName);
		logger.info("stopService::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return String
	 */
	public String restartAllServices() {
		logger.info("restartAllServices");
		/*
		 * Map<Ports, String> port = new HashMap<Ports, String>(); port.put(Ports.SSH,
		 * "22"); host.setPort(port); User usr =
		 * HostGroup.getOssmaster().getNmsadmUser(); usr.setUsername("root");
		 * usr.setPassword("shroot"); try { sendFileRemotely(host,
		 * "manage_deployment_serivces.bsh", "/eniq/admin/bin"); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		String completeOutput = handler.simpleExec(RESTART_ALL_SERVICES_CMD);
	//	Host host = HostGroup.getOssmaster();
		logger.info("modulesExtractedUpdated::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @param engineCommands command
	 * @return String
	 */
	public String engineCommandsValidation(String commands) {
		logger.info("\nExecuting : " + PROFILE + ENGINE_COMMAND + commands + "'");
		String completeOutput = handler.simpleExec(PROFILE + ENGINE_COMMAND + commands + "'");
		logger.info("\nEngine Command Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
	}

}
