package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.ExtractReportPackagesSteps01;;

/**
 * Verify Universes extracted to BO Universes directory
 */
public class ExtractReportPackagesFlow01 {

	private static final String VERIFY_REPORT_PACKAGES_FLOW = "ExtractReportPackages01Flow";

	@Inject
	private ExtractReportPackagesSteps01 steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow reportPackageVerification() {
		return flow(VERIFY_REPORT_PACKAGES_FLOW)
				.addTestStep(annotatedMethod(steps, ExtractReportPackagesSteps01.StepIds.VERIFY_REPORT_PACKAGES_01)).build();
	}
}
