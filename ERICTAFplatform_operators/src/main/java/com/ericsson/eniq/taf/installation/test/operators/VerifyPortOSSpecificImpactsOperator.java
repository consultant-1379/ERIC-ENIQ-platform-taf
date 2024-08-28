package com.ericsson.eniq.taf.installation.test.operators;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class VerifyPortOSSpecificImpactsOperator extends CLIOperator {
    
    private static Logger logger = LoggerFactory.getLogger(VerifyPortOSSpecificImpactsOperator.class);
    
    private static final String EXECUTE_DISKMANAGER_ALLINTERFACE_CMD = "cd /eniq/sw/bin; bash ./DiskManager_AllInterface.bsh";
    private static final String EXECUTE_DISABLECOUNTERS_CMD = "cd /eniq/admin/bin; bash ./disableCounters.bsh;";
    private static final String EXECUTE_ENABLECOUNTERS_CMD= "cd /eniq/admin/bin; bash ./enableCounter.bsh;";
    private static final String EXECUTE_REMOVE_HIDDEN_FILES_CMD = "cd /eniq/sw/bin;bash ./remove_hidden_files.bsh /eniq/data/pmdata/";
    private static final String VERIFY_TP_LOGS_ARCHIVED = "cd /eniq/logs/sw_log/tp_installer/";
    private static final String VERIFY_REMOVE_HIDDEN_FILES_LOGS = "cd cd /eniq/log/sw_log/engine/;grep -i remove_hidden_files_";

    public VerifyPortOSSpecificImpactsOperator() {
        super();
    }

    /**
     * 
     * @param serviceName service name
     * @return String
     */
    public String allDiskmanagerIntereces() {
        String completeOutput = executeCommand(EXECUTE_DISKMANAGER_ALLINTERFACE_CMD);

        return completeOutput;
        //return listFile(logFileDir+module);
    }

    /**
     * 
     * @return String
     */
    public String disableCounters() {
        logger.info("disableCounters::");
        String completeOutput = executeCommand(EXECUTE_DISABLECOUNTERS_CMD );

        return completeOutput;
        //return listFile(logFileDir+module);
    }

    /**
     * 
     * @param serviceName service name
     * @return String
     */
    public String enableCounters() {
        logger.info("enableCounters::");
        String completeOutput = executeCommand(EXECUTE_ENABLECOUNTERS_CMD);

        return completeOutput;
        //return listFile(logFileDir+module);
    }

    /**
     * 
     * @return String
     */
    public String removeHiddeniles() {
        logger.info("removeHiddeniles::");
        String completeOutput = executeCommand(EXECUTE_REMOVE_HIDDEN_FILES_CMD);

        return completeOutput;
        //return listFile(logFileDir+module);
    }

    /**
     * 
     * @return String
     */
    public String tpLogsArchives() {
        logger.info("tpLogsArchives file path::");
        return executeCommand(VERIFY_TP_LOGS_ARCHIVED);
    }

    /**
     * 
     * @return String
     */
    public String removeHiddenilesLogs() {
        logger.info("removeHiddenilesLogs::");
        Date date = new Date();
        String completeOutput = executeCommand(VERIFY_REMOVE_HIDDEN_FILES_LOGS
                +date.getYear() + "_" + date.getMonth() + "_" + date.getDay());
        logger.info("removeHiddenilesLogs" + completeOutput);
        return completeOutput;
        //return listFile(logFileDir+module);
    }

}
