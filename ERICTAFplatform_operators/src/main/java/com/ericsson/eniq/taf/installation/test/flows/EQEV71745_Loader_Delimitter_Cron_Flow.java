package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV71745_Loader_Delimitter_Cron_Steps;

/**
 * Verify that loader_delimiter cron entry is removed from dcuser cron
 *
 * @author ZJSOLEA
 */
public class EQEV71745_Loader_Delimitter_Cron_Flow {

	private static final String EQEV71745_Loader_Delimitter_Cron_FLOW = "EQEV71745_Loader_Delimitter_Cron_FLOW";

	@Inject
	private EQEV71745_Loader_Delimitter_Cron_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV71745_Loader_Delimitter_Cron_FLOW)
				.addTestStep(annotatedMethod(steps, EQEV71745_Loader_Delimitter_Cron_Steps.StepIds.EQEV71745_Loader_Delimitter_Cron_STEP01)).build();
	}
}
