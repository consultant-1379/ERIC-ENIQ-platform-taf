package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyPlatformInstallationTestSteps;

/**
 * Flows to test the cli calculator
 */
public class VerifyPlatformInstallationFlow {

    private static final String EXECUTE_PLATFORM_LOG_FLOW = "platFormLog";
    private static final String EXECUTE_PLATFORM_VERSION_DB_FLOW = "platFormVersionDB";
    private static final String EXECUTE_PLATFORM_MODULES_EXTRACTED_FLOW = "platFormVersionDB";

    @Inject
    private VerifyPlatformInstallationTestSteps platformInstallationTestSteps;

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow platformLogFlow() {
        return flow(EXECUTE_PLATFORM_LOG_FLOW)
                .addTestStep(annotatedMethod(platformInstallationTestSteps, VerifyPlatformInstallationTestSteps.StepIds.VERIFY_PLATFORM_LOG_EXISTS))
                .addTestStep(annotatedMethod(platformInstallationTestSteps, VerifyPlatformInstallationTestSteps.StepIds.VERIFY_PLATFORM_LOG_ERRORS))
                .build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow platformVersionDBPropetiesFlow() {
        return flow(EXECUTE_PLATFORM_VERSION_DB_FLOW).addTestStep(
                annotatedMethod(platformInstallationTestSteps, VerifyPlatformInstallationTestSteps.StepIds.VERIFY_PLATFORM_VERSION_DB_PROPERTIES))
                .build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow platformModulesExtractedFlow() {
        return flow(EXECUTE_PLATFORM_MODULES_EXTRACTED_FLOW)
                .addTestStep(
                        annotatedMethod(platformInstallationTestSteps, VerifyPlatformInstallationTestSteps.StepIds.VERIFY_PLATFORM_MODULES_EXTRACTED))
                .build();
    }
}