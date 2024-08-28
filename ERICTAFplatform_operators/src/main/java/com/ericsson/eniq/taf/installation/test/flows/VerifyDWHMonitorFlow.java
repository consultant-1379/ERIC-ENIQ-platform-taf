package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyDWHMonitorTestSteps;

/**
 * Flows to test the DWH_Monitor_DirectoryandDiskManagerCheck
 */
public class VerifyDWHMonitorFlow {

	private static final String DWH_DIR_DISKMANAGER_VERIFICATION = "Verify Directory and Disk Manager";

	@Inject
	private VerifyDWHMonitorTestSteps dvTestSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */

	public TestStepFlow DWH_DIR_DISKMANAGER_VERIFICATION() {
		return flow(DWH_DIR_DISKMANAGER_VERIFICATION)
				.addTestStep(annotatedMethod(dvTestSteps, VerifyDWHMonitorTestSteps.StepIds.CLI_INITIALIZE))
				.addTestStep(annotatedMethod(dvTestSteps, VerifyDWHMonitorTestSteps.StepIds.VERIFY_DWH_DIR_DISKMANAGER))
				.build();
	}
}
