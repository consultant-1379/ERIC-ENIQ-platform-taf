package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyParserInstallationTestSteps;

/**
 * Flows to test the cli calculator
 */
public class VerifyParserInstallationFlow {

    private static final String EXECUTE_PARSER_MODULEFOLDER_FLOW = "parserModuleFolder";
    private static final String EXECUTE_PARSER_VERSION_DB_FLOW = "parserVersionDB";
    private static final String EXECUTE_PARSER_MODULES_LOG_FLOW = "parserVersionDB";

    @Inject
    private VerifyParserInstallationTestSteps parserInstallationTestSteps;

    /**
     * 
     * @return license manager
     */
    public TestStepFlow parserLogFlow() {
        return flow(EXECUTE_PARSER_MODULES_LOG_FLOW)
                .addTestStep(annotatedMethod(parserInstallationTestSteps, VerifyParserInstallationTestSteps.StepIds.VERIFY_PARSER_MODULES_LOGS))
                .build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow parserVersionDBPropetiesFlow() {
        return flow(EXECUTE_PARSER_VERSION_DB_FLOW)
                .addTestStep(
                        annotatedMethod(parserInstallationTestSteps, VerifyParserInstallationTestSteps.StepIds.VERIFY_PARSER_VERSION_DB_PROPERTIES))
                .build();
    }

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow parserModulesExtractedFlow() {
        return flow(EXECUTE_PARSER_MODULEFOLDER_FLOW)
                .addTestStep(
                        annotatedMethod(parserInstallationTestSteps, VerifyParserInstallationTestSteps.StepIds.VERIY_PARSER_MODULE_FOLDERS_EXISTS))
                .build();
    }
}
