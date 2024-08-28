package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyInstallerTestSteps;

/**
 * Flows to test Installer Module
 */
public class VerifyInstallerModuleFlow {

    private static final String EXECUTE_INSTALLER_LOG_FLOW = "InstallerModuleLog";
    private static final String EXECUTE_INSTALLER_VERSION_DB_FLOW = "InstallerModuleVersionDB";

    @Inject
    private VerifyInstallerTestSteps installerTestSteps;

    /**
     * 
     * @return TestStepFlow
     */
    public TestStepFlow installerLogFlow() {
        return flow(EXECUTE_INSTALLER_LOG_FLOW)
                .addTestStep(annotatedMethod(installerTestSteps, VerifyInstallerTestSteps.StepIds.VERIFY_INSTALLER_LOG_EXISTS))
                .addTestStep(annotatedMethod(installerTestSteps, VerifyInstallerTestSteps.StepIds.VERIFY_INSTALLER_LOG_ERRORS))
                .build();
    }

    /**
     * 
     * @return TestStepFlow
     */
	public TestStepFlow installerVersionDBPropetiesFlow() {
        return flow(EXECUTE_INSTALLER_VERSION_DB_FLOW).addTestStep(
                annotatedMethod(installerTestSteps, VerifyInstallerTestSteps.StepIds.VERIFY_INSTALLER_VERSION_DB_PROPERTIES))
                .build();
    }
}