package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.RunScriptOperator;

public class RunScripTestSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(RunScripTestSteps.class);

    @Inject
    private Provider<RunScriptOperator> provider;

    @TestStep(id = StepIds.VERIFY_CMD_COMMAND)
    public void TestSteps(@Input(Parameters.TEST_CASE_ID) String testCaseId) {
        LOGGER.info(testCaseId + "- Verify echo script Execution on vApp");
        RunScriptOperator runScriptOperator = provider.get();
        runScriptOperator.copyScript();
        LOGGER.info("******  Copied  *****");

        runScriptOperator.permission();

        String executescript_result = runScriptOperator.executescript();

        LOGGER.info("Verifying response :  " + executescript_result);
        if (executescript_result.contains("Hell")) {
            assertEquals(executescript_result, executescript_result);
        } else {
            Assert.fail("Failed to execute the echo script");
        }
    }

    public static class StepIds {
        public static final String VERIFY_CMD_COMMAND = "Running a Script on vApp";

        private StepIds() {
        }
    }

    public static class Parameters {
        public static final String TEST_CASE_ID = "testCaseId";

        private Parameters() {
        }

    }
}
