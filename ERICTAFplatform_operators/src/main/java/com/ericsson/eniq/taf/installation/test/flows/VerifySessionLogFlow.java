package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyProcessManagementTestSteps;

/**
 * Flows to test the cli calculator
 */
public class VerifySessionLogFlow {

    private static final String EXECUTE_DISKMANAGER_ALLINTERFACE_FLOW = "Execute DiskManager_AllInterface.bsh flow";
    private static final String EXECUTE_DISABLECOUNTERS_FLOW = "Execute disableCounters.bsh flow";
    private static final String EXECUTE_ENABLECOUNTERS_FLOW = "Execute enableCou ter.bsh flow";
    private static final String EXECUTE_REMOVE_HIDDEN_FILES_FLOW = "Execute remove_hidden_files flow";

    @Inject
    private VerifyProcessManagementTestSteps processTestSteps;

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow excecuteAllInteraceFlow() {
        return flow(EXECUTE_DISKMANAGER_ALLINTERFACE_FLOW)
                .addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.CLI_INITIALIZE))
                .addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_ENABLE)).build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow diaableContersFlow() {
        return flow(EXECUTE_DISABLECOUNTERS_FLOW).addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.CLI_INITIALIZE))
                .addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_DISABLE)).build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow enableCountersFlow() {
        return flow(EXECUTE_ENABLECOUNTERS_FLOW).addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.CLI_INITIALIZE))
                .addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_SERVICE_STOP)).build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow removeHiddenilesFlow() {
        return flow(EXECUTE_REMOVE_HIDDEN_FILES_FLOW)
                .addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.CLI_INITIALIZE))
                .addTestStep(annotatedMethod(processTestSteps, VerifyProcessManagementTestSteps.StepIds.VERIFY_DEPENDENT_SERVICES)).build();
    }

   
}
