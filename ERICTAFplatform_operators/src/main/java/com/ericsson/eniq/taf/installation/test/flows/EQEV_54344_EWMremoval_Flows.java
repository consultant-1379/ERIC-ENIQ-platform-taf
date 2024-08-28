package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV_54344_EWMremoval_Steps;

/**
 * 
 * @author xsounpk
 *
 */
public class EQEV_54344_EWMremoval_Flows {
	private static final String EQEV54344_EWM_removal_06_FLOW = "EQEV54344_EWM_removal_06_Flow";
	private static final String EQEV54344_EWM_removal_09_FLOW = "EQEV54344_EWM_removal_09_Flow";

	@Inject
	private EQEV_54344_EWMremoval_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow crontabEntryVerification() {
		return flow(EQEV54344_EWM_removal_06_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV_54344_EWMremoval_Steps.StepIds.EQEV54344_EWM_removal_06_STEP))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow wifiScriptVerification() {
		return flow(EQEV54344_EWM_removal_09_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV_54344_EWMremoval_Steps.StepIds.EQEV54344_EWM_removal_09_STEP))
				.build();
	}

}
