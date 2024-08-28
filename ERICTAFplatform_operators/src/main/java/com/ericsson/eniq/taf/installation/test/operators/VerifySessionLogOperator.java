package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class VerifySessionLogOperator extends CLIOperator {

    Logger logger = LoggerFactory.getLogger(VerifySessionLogOperator.class);
    private final String schedulerLogFileDir = DataHandler.getAttribute("platform.scheduler.license.logFileDir").toString();
    private final String SCHEDULER_LOG_CMD = "cd " + schedulerLogFileDir + "; cat ";
    private final String SCHEDULER_LIST_LOG_CMD = "cd " + schedulerLogFileDir + "; ls -t -1";

    public VerifySessionLogOperator() {
        super();
    }

    /**
     * @param loader -adapter/aggregator/loader
     * @return String
     */
    public String getSchedulerLogContent(String loader, String tableName) {
        String completeOutput = executeCommand(SCHEDULER_LIST_LOG_CMD);
        logger.info("getSchedulerLogContent:::" + completeOutput);
        List<String> files = new ArrayList<>();
        for (String output : completeOutput.split("\\n")) {
            files.add(output.trim());
        }
        String log;
        if (!files.isEmpty()) {
            log = executeCommand(SCHEDULER_LOG_CMD + files.get(0) 
                    + "| grep -i SessionLogLoader_" + loader);
        } else {
            log = "Scheduler log does not exists.";
        }
        
        return log;
    }
}
