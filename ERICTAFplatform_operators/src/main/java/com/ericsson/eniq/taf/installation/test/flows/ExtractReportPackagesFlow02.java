package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.ExtractReportPackagesSteps02;;

/**
 * Verify KPI report extracted to BOUniverses directory
 */
public class ExtractReportPackagesFlow02 {

	private static final String VERIFY_REPORT_PACKAGES_FLOW_02 = "ExtractReportPackages02Flow";

	@Inject
	private ExtractReportPackagesSteps02 steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow reportPackageVerification() {
		return flow(VERIFY_REPORT_PACKAGES_FLOW_02)
				.addTestStep(annotatedMethod(steps, ExtractReportPackagesSteps02.StepIds.VERIFY_REPORT_PACKAGES_02)).build();
	}
}
