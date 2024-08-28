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
public class VerifyPlatformInstallationOperator extends CLIOperator {

	Logger logger = LoggerFactory.getLogger(VerifyPlatformInstallationOperator.class);

	private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
	private final String ROOT_USER = DataHandler.getAttribute("platform.user.root").toString();
	private final String ROOT_PASSWORD = DataHandler.getAttribute("platform.password.root").toString();
	private final String logFileDir = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String DeltaviewLogDir = DataHandler.getAttribute("platform.install.DeltaviewLog").toString();
	private final String ExecutionLogDir = DataHandler.getAttribute("platform.install.ExecutionLog").toString();
	private final String AccessLogDir = DataHandler.getAttribute("platform.install.AccessLog").toString();
	private final String PWDDataWifi = DataHandler.getAttribute("platform.install.EMWSunOSIni").toString();
	private final String EMWStaticProp = DataHandler.getAttribute("platform.install.EMWStaticProp").toString();
	private final String EMWVersionDBProp = DataHandler.getAttribute("platform.install.EMWVersionDBProp").toString();
	private final String ExecLogDir = DataHandler.getAttribute("platform.install.ExecLog").toString();
	private final String LOG_GREP_ERROR_CMD = DataHandler.getAttribute("platform.log.dv_error.grep").toString();
	private final String LOG_GREP_EXCEPTION_CMD = DataHandler.getAttribute("platform.log.dv_exception.grep").toString();
	private final String LOG_GREP_WARNING_CMD = DataHandler.getAttribute("platform.log.dv_warning.grep").toString();
	private final String platformLogDir = DataHandler.getAttribute("platform.install.logFileDir").toString();
	private final String GREP_WIFI_CMD = DataHandler.getAttribute("platform.log.EWMWifi").toString();
	private final String lteFileDir = DataHandler.getAttribute("platform.install.ltewcdmaLogDir").toString();
	private final String versionDBDir = DataHandler.getAttribute("platform.install.versionDBDir").toString();
	private final String moduleExtractionDir = DataHandler.getAttribute("platform.install.moduleExtractionDir")
			.toString();
	private final String LOG_EXCEPTION_GREP_CMD = DataHandler.getAttribute("platform.log.exceptions.grep").toString();
	private final String LOG_EXCEPTION_GREP_CMD1 = DataHandler.getAttribute("platform.log.timing").toString();
	private final String DWHLogDir = DataHandler.getAttribute("platform.install.DWHEngineeLog").toString();

	private final String GREP_ERROR_PLATFORM_LOGS = "cd " + platformLogDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION_PLATFORM_LOGS = "cd " + platformLogDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS_PLATFORM_LOGS = "cd " + platformLogDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String LIST_LOG_FILES_CMD = "cd " + logFileDir + ";" + " ls -t -1";
	private final String LTE_LIST_LOG_FILES_CMD = "cd " + lteFileDir + ";" + " ls -t -1";
	private final String DELTA_VIEW_LOG_FILES_CMD = "cd " + DeltaviewLogDir + ";" + " ls -t -1";
	private final String EXECUTION_LOG_FILES_CMD = "cd " + ExecutionLogDir + ";" + " ls -t -1";
	private final String EXEC_LOG_FILES_CMD = "cd " + ExecLogDir + ";" + " ls -t -1";
	private final String ACCESS_LOG_FILES_CMD = "cd " + AccessLogDir + ";" + " ls -t -1";
	private final String PWDDATA_INI_FILES_CMD = "cd " + PWDDataWifi + ";" + " ls -t -1";
	private final String WIFI_PROPERTY_FILES_CMD = "cd " + EMWStaticProp + ";" + " ls -t -1";
	private final String WIFI_DBPROPERTY_FILES_CMD = "cd " + EMWVersionDBProp + ";" + " ls -t -1";
	private final String GREP_ERROR = "cd " + DeltaviewLogDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String GREP_EXCEPTION = "cd " + DeltaviewLogDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String GREP_WARNINGS = "cd " + DeltaviewLogDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_WIFI = "cd " + PWDDataWifi + ";" + GREP_WIFI_CMD + " ";
	private final String GREP_PROPERTY_WIFI = "cd " + EMWStaticProp + ";" + GREP_WIFI_CMD + " ";
	private final String GREP_PROPERTY_WIFIPARSER = "cd " + EMWVersionDBProp + ";" + GREP_WIFI_CMD + " ";
	private final String EXECUTION_LOG_GREP_ERROR = "cd " + ExecutionLogDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String EXECUTION_LOG_GREP_EXCEPTION = "cd " + ExecutionLogDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String EXECUTION_LOG_GREP_WARNINGS = "cd " + ExecutionLogDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String ACCESS_LOG_GREP_ERROR = "cd " + AccessLogDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String ACCESS_LOG_GREP_EXCEPTION = "cd " + AccessLogDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String ACCESS_LOG_GREP_WARNINGS = "cd " + AccessLogDir + ";" + LOG_GREP_WARNING_CMD + " ";
	private final String GREP_EXCEPTION_LOG_FILES_CMD = "cd " + logFileDir + ";" + LOG_EXCEPTION_GREP_CMD + " ";
	private final String GREP_EXCEPTION_DELTAVIEWLOG_FILES_CMD = "cd " + DeltaviewLogDir + ";" + LOG_EXCEPTION_GREP_CMD
			+ " ";
	private final String GREP_EXCEPTION_LTE_LOG_FILES_CMD = "cd " + lteFileDir + ";" + LOG_EXCEPTION_GREP_CMD + " ";
	private final String CAT_VERSION_DBPROPERTIES_CMD = "cd " + versionDBDir + ";"
			+ " cat versiondb.properties | grep ";
	private final String MODULES_EXTRACTED_CMD = "cd " + moduleExtractionDir + ";" + " ls -d */ | grep ";
	private final String EXECUTION_LOG_FILES_CMD1 = "cd " + ExecLogDir + ";" + LOG_EXCEPTION_GREP_CMD1 + " ";
	private final String GREP_DWH_CMD = DataHandler.getAttribute("platform.log.DWHDIR").toString();
	private final String DWHMONITOR_LOG_CMD = "cd " + DWHLogDir + ";" + " ls -t -1";
	private final String GREP_DWHMONITOR_DIR = "cd " + DWHLogDir + ";" + GREP_DWH_CMD + " ";
	private final String DWH_MONITOR_GREP_ERROR = "cd " + DeltaviewLogDir + ";" + LOG_GREP_ERROR_CMD + " ";
	private final String DWH_MONITOR_GREP_EXCEPTION = "cd " + DeltaviewLogDir + ";" + LOG_GREP_EXCEPTION_CMD + " ";
	private final String DWH_MONITOR_GREP_WARNINGS = "cd " + DeltaviewLogDir + ";" + LOG_GREP_WARNING_CMD + " ";

	private final String ACCESS_LOG_ERROR = "cd " + AccessLogDir + "; cat ";
	private final String ACCESS_LOG_EXCEPTION = "cd " + AccessLogDir + "; cat ";
	private final String ACCESS_LOG_WARNING = "cd " + AccessLogDir + "; cat ";

	private CLICommandHelper handler;
	private Host eniqshost;

	public VerifyPlatformInstallationOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listPlatformLogFiles() {
		logger.info("verifyPlatformLogExists::");
		String completeOutput = handler.simpleExec(LIST_LOG_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listPlatformltewcdmaLogFiles() {
		logger.info("verifyPlatformLogExists::");
		String completeOutput = handler.simpleExec(DELTA_VIEW_LOG_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listSunosiniFiles() {
		logger.info("verifyIniFileExist::");

		String completeOutput = handler.simpleExec(PWDDATA_INI_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listpropetiesFiles() {
		logger.info("verifyPropertyFileExist::");
		String completeOutput = handler.simpleExec(WIFI_PROPERTY_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listdbpropetiesFiles() {
		logger.info("verifyPropertyFileExist::");
		String completeOutput = handler.simpleExec(WIFI_DBPROPERTY_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listExecutionLogFiles() {
		logger.info("verifyPlatformLogExists::");
		String completeOutput = handler.simpleExec(EXEC_LOG_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listAccessLogFiles() {
		logger.info("verifyPlatformLogExists::");
		String completeOutput = handler.simpleExec(ACCESS_LOG_FILES_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getPlatformLogContent(String fileName) {
		logger.info("verifyPlatformLogExists::" + fileName);
		logger.info("verifyPlatformLogExists file path::" + platformLogDir + fileName);

		logger.info("\nChecking any errors/exceptions/warnings in : " + platformLogDir + fileName);
		final StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(GREP_ERROR_PLATFORM_LOGS + fileName));
		log_Content.append(handler.simpleExec(GREP_EXCEPTION_PLATFORM_LOGS + fileName));
		log_Content.append(handler.simpleExec(GREP_WARNINGS_PLATFORM_LOGS + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();

		// return handler.execute(GREP_EXCEPTION_LTE_LOG_FILES_CMD + fileName);
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getPlatformLteWCDMALogContent(String fileName) {
		logger.info("verifyDeltaViewLogExists::" + fileName);

		logger.info("verifyDeltaViewLogExists file path::" + DeltaviewLogDir + fileName);
		StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(DWH_MONITOR_GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(DWH_MONITOR_GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(DWH_MONITOR_GREP_WARNINGS + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getIniFileContent(String fileName) {
		logger.info("verifyIniExists::" + fileName);
		logger.info("verifyIniExists file path::" + PWDDataWifi + fileName);
		String file_Content = handler.simpleExec(GREP_WIFI + fileName);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return file_Content;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getPropertyFileContent(String fileName) {
		logger.info("verifyPropertyExists::" + fileName);
		logger.info("verifyPropertyExists file path::" + EMWStaticProp + fileName);
		String file_Content = handler.simpleExec(GREP_PROPERTY_WIFI + fileName);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return file_Content;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getDbPropertyFileContent(String fileName) {
		logger.info("verifyVersionDBPropertyExists::" + fileName);
		logger.info("verifyVersionDBPropertyExists file path::" + EMWVersionDBProp + fileName);
		String file_Content = handler.simpleExec(GREP_PROPERTY_WIFIPARSER + fileName);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return file_Content;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getExecutionLogContent(String fileName) {
		logger.info("verifyExecutionLogExists::" + fileName);
		logger.info("verifyExecutionLogExists file path::" + ExecutionLogDir + fileName);
		StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(EXECUTION_LOG_GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(EXECUTION_LOG_GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(EXECUTION_LOG_GREP_WARNINGS + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getAccessVerifyLogContent(String fileName) {
		logger.info("verifyAccessVerifyLogExists::" + fileName);
		logger.info("verifyAccessVerifyLogExists file path::" + AccessLogDir + fileName);
		StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(ACCESS_LOG_GREP_ERROR + fileName));
		log_Content.append(handler.simpleExec(ACCESS_LOG_GREP_EXCEPTION + fileName));
		log_Content.append(handler.simpleExec(ACCESS_LOG_GREP_WARNINGS + fileName));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getScriptExecutionTiming(String fileName) {
		logger.info("verifyExecutionLogExists::" + fileName);
		String result = handler.simpleExec(EXECUTION_LOG_FILES_CMD1 + fileName);

		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return result;

	}

	/**
	 * 
	 * @param module
	 *            module
	 * @return String
	 */
	public String versionDBModuleUpdated(String module) {
		logger.info("versionDBModuleUpdated::" + module);
		String completeOutput = handler.simpleExec(CAT_VERSION_DBPROPERTIES_CMD + module);
		logger.info("versionDBModuleUpdated::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @param module
	 *            module
	 * @return String
	 */
	public String modulesExtracted(String module) {
		logger.info("modulesExtractedUpdated::" + module);
		String completeOutput = handler.simpleExec(MODULES_EXTRACTED_CMD + module);
		logger.info("modulesExtractedUpdated::output" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput;
		// return listFile(logFileDir+module);
	}

	/**
	 * 
	 * @return list
	 */
	public List<String> listDWH_EngineeLogFiles() {
		logger.info("Verify Directory and Disk Manager::");

		String completeOutput = handler.simpleExec(DWHMONITOR_LOG_CMD);
		List<String> filesMatchingPattern = new ArrayList<>();
		for (String output : completeOutput.split("\\n")) {
			filesMatchingPattern.add(output.trim());
		}
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return filesMatchingPattern;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String getEngineeLogContent(String fileName) {
		logger.info("verifyEngineeLogExists::" + fileName);
		logger.info("verifyEngineeLogExists File Path::" + DWHLogDir + fileName);
		String file_Content = handler.simpleExec(GREP_DWHMONITOR_DIR + fileName);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return file_Content;
	}

	/**
	 * 
	 * @param fileName
	 *            file name
	 * @return String
	 */
	public String verifyAccessVerifyLogContent(String fileName) {

		StringBuilder log_Content = new StringBuilder();
		log_Content.append(handler.simpleExec(
				ACCESS_LOG_ERROR + fileName + " | awk '{$4=" + '"' + '"' + "; print}' | " + " grep -i error"));
		log_Content.append(handler.simpleExec(
				ACCESS_LOG_EXCEPTION + fileName + " | awk '{$4=" + '"' + '"' + "; print}' | " + " grep -i exception"));
		log_Content.append(handler.simpleExec(
				ACCESS_LOG_WARNING + fileName + " | awk '{$4=" + '"' + '"' + "; print}' | " + " grep -i warning"));
		logger.info("\n Content of " + fileName + " log file : \n" + log_Content.toString());

		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return log_Content.toString();
	}
}
