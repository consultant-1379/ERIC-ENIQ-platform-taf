package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RunScripTestSteps;

public class RunScriptFlow {

    private static final String EXECUTE_CMD_COMMAND_FLOW = "Running echo Script on vApp";

    @Inject
    private RunScripTestSteps TestSteps;

    public TestStepFlow runningScriptFlow() {
        return flow(EXECUTE_CMD_COMMAND_FLOW).addTestStep(annotatedMethod(TestSteps, RunScripTestSteps.StepIds.VERIFY_CMD_COMMAND)).build();
    }
}
