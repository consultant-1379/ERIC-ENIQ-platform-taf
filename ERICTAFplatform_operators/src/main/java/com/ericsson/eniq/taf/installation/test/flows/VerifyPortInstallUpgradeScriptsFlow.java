package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyPortInstallUpgradeScriptsSteps;

/**
 * Flows to test platform Logs, Platform version DB Properties and Platform
 * Modules extraction
 */
public class VerifyPortInstallUpgradeScriptsFlow {
	
	private static final String INSTALL_SCRIPT_EXISTS_FLOW = "assureddcIsExistFlow";
	private static final String EXECUTE_PLATFORM_LOG_FLOW = "platformLog";
	private static final String EXECUTE_PLATFORM_VERSION_DB_FLOW = "platformVersionDB";
	private static final String EXECUTE_PLATFORM_MODULES_EXTRACTED_FLOW = "platformMaduleExtraction";
	private static final String assureddc_IS_EXISTS_FLOW = "assureddcIsExistFlow";
	

	@Inject
	private VerifyPortInstallUpgradeScriptsSteps platformInstallationTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow isInstallScriptExistsInMws() {
		return flow(INSTALL_SCRIPT_EXISTS_FLOW).addTestStep(annotatedMethod(platformInstallationTestSteps,
				VerifyPortInstallUpgradeScriptsSteps.StepIds.VERIFY_INSTALL_SCRIPT_EXISTS_IN_MWS)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow platformLogFlow() {
		return flow(EXECUTE_PLATFORM_LOG_FLOW)
				.addTestStep(annotatedMethod(platformInstallationTestSteps,
						VerifyPortInstallUpgradeScriptsSteps.StepIds.VERIFY_PLATFORM_LOG_EXISTS))
				.addTestStep(annotatedMethod(platformInstallationTestSteps,
						VerifyPortInstallUpgradeScriptsSteps.StepIds.VERIFY_PLATFORM_LOG_ERRORS))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow platformVersionDbPropetiesFlow() {
		return flow(EXECUTE_PLATFORM_VERSION_DB_FLOW).addTestStep(annotatedMethod(platformInstallationTestSteps,
				VerifyPortInstallUpgradeScriptsSteps.StepIds.VERIFY_PLATFORM_VERSION_DB_PROPERTIES)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow platformModulesExtractedFlow() {
		return flow(EXECUTE_PLATFORM_MODULES_EXTRACTED_FLOW).addTestStep(annotatedMethod(platformInstallationTestSteps,
				VerifyPortInstallUpgradeScriptsSteps.StepIds.VERIFY_PLATFORM_MODULES_EXTRACTED)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow assureddcIsExistsFlow() {
		return flow(assureddc_IS_EXISTS_FLOW).addTestStep(annotatedMethod(platformInstallationTestSteps,
				VerifyPortInstallUpgradeScriptsSteps.StepIds.VERIFY_SPECIFIC_LOG_IS_EXISTS)).build();
	}

}
