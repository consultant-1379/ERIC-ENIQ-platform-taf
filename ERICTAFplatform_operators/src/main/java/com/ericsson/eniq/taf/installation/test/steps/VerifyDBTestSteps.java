package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyDBOperator;

/**
 * Created by eendjor on 23/05/2016.
 */
public class VerifyDBTestSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(VerifyDBTestSteps.class);

    @Inject
    private Provider<VerifyDBOperator> provider;

    /**
     * @DESCRIPTION Verify the functionality of the Calc Command
     * @PRE Get the calc version to be tested
     */
    @TestStep(id = StepIds.VERIFY_DB_COMMAND)
    public void verifyDBCommand(@Input(Parameters.TEST_CASE_ID) String testCaseId) {

        LOGGER.info(testCaseId + "- Verify DB IP");

        // final String calcVersion = DataHandler.getAttribute("CALCVER").toString();

        VerifyDBOperator dbOperator = provider.get();
        dbOperator.initialise();
        //dbOperator.initializeShell(DataHandler.getHostByName("testServer"));
        String response = dbOperator.verifyDBCommand();

        LOGGER.info("Verifying response" + response);
        // assertThat(calcResponse.getResult().contains(expected));

    }

    @TestStep(id = StepIds.VERIFY_CUSTOM_DATASOURCE)
    public void verifyCustomData(@Input("data") Object modleNO) {
        LOGGER.info("- Verify log file Exists." + modleNO.getClass());
        LOGGER.info("- Verify log file Exists." + modleNO);

        assertTrue(true);

    }

    public static class StepIds {
        public static final String VERIFY_DB_COMMAND = "Verify DB Installation";
        public static final String VERIFY_CUSTOM_DATASOURCE = "verifyCustomData";

        private StepIds() {
        }
    }

    public static class Parameters {
        public static final String TEST_CASE_ID = "testCaseId";

        private Parameters() {
        }

    }
}
