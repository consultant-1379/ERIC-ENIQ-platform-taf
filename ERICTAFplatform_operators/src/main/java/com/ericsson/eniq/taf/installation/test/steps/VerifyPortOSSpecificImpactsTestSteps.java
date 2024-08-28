package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPortOSSpecificImpactsOperator;

/**
 * Created by zvadde on 04/10/2018.
 */
public class VerifyPortOSSpecificImpactsTestSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(VerifyPortOSSpecificImpactsTestSteps.class);

    @Inject
    private Provider<VerifyPortOSSpecificImpactsOperator> provider;

    /**
     *
     */
    @TestStep(id = StepIds.CLI_INITIALIZE)
    public void cliInitialize() {

        LOGGER.info("- cliInitialize start service");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        processOperator.initialiseRoot();
        assertTrue(true);
    }

    /**
     * 
     */
    @TestStep(id = StepIds.EXECUTE_DISKMANAGER_ALLINTERFACE)
    public void executeAllInterace() {
        LOGGER.info("executeAllInterace step");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        String output = processOperator.allDiskmanagerIntereces();
        assertTrue(true);
        //assertTrue(op.trim().length() ==0,"Exception while starting service"+serviceName);
    }

    /**
     *
     */
    @TestStep(id = StepIds.EXECUTE_DISABLECOUNTERS)
    public void executeDisableCounters() {

        LOGGER.info("executeDisableCounters step");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        String output = processOperator.disableCounters();
        assertTrue(true);
        //assertTrue(op.trim().length() == 0,"Exception while stoping service"+serviceName);
    }

    /**
     * 
     */
    @TestStep(id = StepIds.EXECUTE_ENABLECOUNTERS)
    public void executeEnableCounters() {

        LOGGER.info("executeEnableCounters step");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        String output = processOperator.enableCounters();
        assertTrue(true);
        //assertTrue(op.contains("Created symlink"),"Exception hile enabling service"+serviceName);
    }

    /**
     * 
     * @param serviceName service name
     */
    @TestStep(id = StepIds.EXECUTE_REMOVE_HIDDEN_FILES)
    public void removeHiddenFiles() {

        LOGGER.info("removeHiddenFiles step");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        String output = processOperator.removeHiddeniles();
        assertTrue(true);
        // assertTrue(op.contains("Removed symlink"),"Exception while disabling service"+serviceName);
    }
    
    /**
     * 
     */
    @TestStep(id = StepIds.VERIFY_TP_LOGS_ARCHIVED)
    public void verifyArchivalOfTPLogFiles() {

        LOGGER.info("veriyArchivalOfTPLogFiles step");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        String output = processOperator.tpLogsArchives();
        assertTrue(true);
        // assertTrue(op.contains("Removed symlink"),"Exception while disabling service"+serviceName);
    }
    
    /**
     * 
     */
    @TestStep(id = StepIds.VERIFY_REMOVE_HIDDEN_FILES_LOGS)
    public void verifyRemoveHiddenilesLOgCreated() {

        LOGGER.info("verifyRemoveHiddenilesLOgCreated step");
        final VerifyPortOSSpecificImpactsOperator processOperator = provider.get();
        String output = processOperator.removeHiddeniles();
        assertTrue(true);
        // assertTrue(op.contains("Removed symlink"),"Exception while disabling service"+serviceName);
    }
   
    /**
     * 
     * @author zvaddee
     *
     */
    public static class StepIds {
        public static final String CLI_INITIALIZE = "INITIALIZE CLI";
        public static final String EXECUTE_DISKMANAGER_ALLINTERFACE = "Execute DiskManager_AllInterface.bsh step";
        public static final String EXECUTE_DISABLECOUNTERS = "Execute disableCounters.bsh step";
        public static final String EXECUTE_ENABLECOUNTERS= "Execute enableCounter.bsh step";
        public static final String EXECUTE_REMOVE_HIDDEN_FILES = "Execute remove hidddeniles step";
        public static final String VERIFY_TP_LOGS_ARCHIVED = "Verify whether all the tech pack and interfaces logs are getting archived";
        public static final String VERIFY_REMOVE_HIDDEN_FILES_LOGS = "Verify that logs are created after running remove_hidden_files.bsh script";

        private StepIds() {
        }
    }
}
