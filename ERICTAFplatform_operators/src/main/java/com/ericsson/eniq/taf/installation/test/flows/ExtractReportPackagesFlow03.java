package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.ExtractReportPackagesSteps02;
import com.ericsson.eniq.taf.installation.test.steps.ExtractReportPackagesSteps03;;

/**
 * Verify only selected licensed features should be extracted
 */
public class ExtractReportPackagesFlow03 {

	private static final String VERIFY_REPORT_PACKAGES_FLOW_03 = "ExtractReportPackages03Flow";

	@Inject
	private ExtractReportPackagesSteps03 steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow reportPackageVerification() {
		return flow(VERIFY_REPORT_PACKAGES_FLOW_03)
				.addTestStep(annotatedMethod(steps, ExtractReportPackagesSteps03.StepIds.VERIFY_REPORT_PACKAGES_03)).build();
	}
}
