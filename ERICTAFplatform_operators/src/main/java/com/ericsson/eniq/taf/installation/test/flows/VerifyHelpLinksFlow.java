package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.VerifyHelpLinksSteps;
import com.ericsson.eniq.taf.installation.test.steps.WepAppTestStep;;

/**
 * Verify whether all the references related to WIfi is removed from Admin UI
 *
 * @author ZJSOLEA
 */
public class VerifyHelpLinksFlow {
	private static final String OPEN_BROWSER = "OpenBrowser";
	private static final String VERIFY_HELP_LINKS_FLOW = "VerifyHelpLinksFlow";
	private static final String CLOSE_BROWSER = "CloseBrowser";
	
	@Inject
	private VerifyHelpLinksSteps steps;

	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow openBrowser() {
		return flow(OPEN_BROWSER)
				.addTestStep(annotatedMethod(steps, VerifyHelpLinksSteps.StepIds.OPEN_FIREFOX))
				.build();
	}
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(VERIFY_HELP_LINKS_FLOW)
				.addTestStep(annotatedMethod(steps, VerifyHelpLinksSteps.StepIds.VERIFY_HELP_LINKS_STEP)).build();
	}
	
	
	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow closeBrowser() {
		return flow(CLOSE_BROWSER)
				.addTestStep(annotatedMethod(steps, VerifyHelpLinksSteps.StepIds.CLOSE_FIREFOX))
				.build();
	}
}
