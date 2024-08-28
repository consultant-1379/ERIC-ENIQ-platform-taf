package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyEWMTestSteps;

/**
 * Flows to test the DeltaView Re-Creation
 */
public class VerifyEWMFlow {

	private static final String EWM_PMDATA_VERIFICATION = "Verify PMDATA Entry in SUNOS.INI File";
	private static final String EWM_WIFIDATA_VERIFICATION = "Verify WIFI Data in Static.Properties File";
	private static final String EWM_WIFIPARSER_VERIFICATION = "Verify WIFI DATA in VersionDb.properties File";
	private static final String EWM_WIFI_DATA_ABSENCE = "Verify EWM Wifi Data Absence";
	private static final String WIFI_INVERTORY= "Verify wifiInvertory.vm does not exists in common module ";

	@Inject
	private VerifyEWMTestSteps dvTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */

	public TestStepFlow EWM_PMDATA_VERIFICATION() {
		return flow(EWM_PMDATA_VERIFICATION)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.VERIFY_SCRIPT_INI_VERIFICATION))
				.build();
	}

	public TestStepFlow EWM_WIFIDATA_VERIFICATION() {
		return flow(EWM_WIFIDATA_VERIFICATION)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(
						annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.VERIFY_SCRIPT_WIFIDATA_VERIFICATION))
				.build();
	}

	public TestStepFlow EWM_WIFIPARSER_VERIFICATION() {
		return flow(EWM_WIFIPARSER_VERIFICATION)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(dvTestSteps,
						VerifyEWMTestSteps.StepIds.VERIFY_SCRIPT_WIFIDATAPARSER_VERIFICATION))
				.build();
	}

	public TestStepFlow wifiDataAbsence() {
		return flow(EWM_WIFI_DATA_ABSENCE)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.WIFI_DATA_ABSENCE)).build();
	}
	
	public TestStepFlow wifiInvertory() {
		return flow(WIFI_INVERTORY)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyEWMTestSteps.StepIds.EWM_WIFI_INVERTORY)).build();
	}
}
