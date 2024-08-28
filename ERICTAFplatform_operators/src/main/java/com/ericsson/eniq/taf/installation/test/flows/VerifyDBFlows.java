package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyDBTestSteps;

/**
 * Flows to test the cli calculator
 */
public class VerifyDBFlows {

    private static final String EXECUTE_DB_COMMAND_FLOW = "DBFlow";

    @Inject
    private VerifyDBTestSteps verifyDBTestSteps;

    public TestStepFlow dbFlow() {
        return flow(EXECUTE_DB_COMMAND_FLOW).addTestStep(annotatedMethod(verifyDBTestSteps, VerifyDBTestSteps.StepIds.VERIFY_DB_COMMAND)).build();
    }

    public TestStepFlow customFlow() {
        return flow("DAta driven").addTestStep(annotatedMethod(verifyDBTestSteps, VerifyDBTestSteps.StepIds.VERIFY_CUSTOM_DATASOURCE)).build();
    }
}
