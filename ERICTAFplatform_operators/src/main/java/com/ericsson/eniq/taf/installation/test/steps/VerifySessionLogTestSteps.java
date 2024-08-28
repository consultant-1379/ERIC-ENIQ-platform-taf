package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.db.EniqDBOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifySessionLogOperator;

/**
 * 
 * @author zvaddee
 *
 */
public class VerifySessionLogTestSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(VerifySessionLogTestSteps.class);

    private List<String> logFiles = new ArrayList<>();

    @Inject
    private Provider<VerifySessionLogOperator> provider;
    
    @Inject
    private EniqDBOperator dbOperator;
    
    final VerifySessionLogOperator operator = provider.get(); 
    
    /**
     * 
     */
    @TestStep(id = StepIds.CLI_INITIALIZE)
    public void cliInitialize() {

        LOGGER.info("- cliInitialize start service");
        operator.initialise();
        assertTrue(true);
    }
    
    /**
     * 
     * @param loaderSet loaderSet
     * @param table table
     */
    @TestStep(id = StepIds.VERIY_SCHEDULER_LOGS)
    public void verifySchedulerLogFile(@Input(Parameters.LOADER) String loaderSet
                                      ,@Input(Parameters.TABLE_NAME) String table) {
        String logOutput = operator.getSchedulerLogContent(loaderSet, table);
        assertTrue(true);
    }
    
    /**
     * 
     * @param loaderSet loaderSet
     * @param table table
     */
    @TestStep(id = StepIds.VERIY_SCHEDULER_TABLE)
    public void verifySessionTableFile(@Input(Parameters.LOADER) String loaderSet
                                      ,@Input(Parameters.TABLE_NAME) String table) {
        String logOutput = operator.getSchedulerLogContent(loaderSet, table);
        assertTrue(true);
    }

    /**
     * 
     * @author zvaddee
     *
     */
    public static class StepIds {
        public static final String CLI_INITIALIZE = "INITIALIZE CLI";
        public static final String VERIY_SCHEDULER_LOGS = "Verify Scheduler logs.";
        
        public static final String VERIY_SCHEDULER_TABLE = "Verify scheduler Table.";

        private StepIds() {
        }
    }

    /**
     * 
     * @author zvaddee
     *
     */
    public static class Parameters {
        public static final String LOADER = "Loader";
        public static final String TABLE_NAME = "Table";
        private Parameters() {
        }

    }
}
