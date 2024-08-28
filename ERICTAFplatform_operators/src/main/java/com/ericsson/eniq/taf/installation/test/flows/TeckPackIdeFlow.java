package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.TeckPackIdeSteps;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;

/**
 * Flows to test launching of Techpackid
 */
public class TeckPackIdeFlow {

	private static final String TACKPACK_IDE_FLOW = "launching of Techpackid";

	@Inject
	private TeckPackIdeSteps teckpackIdeSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow jnlpFileDownloadFlow() {
		return flow(TACKPACK_IDE_FLOW).addTestStep(annotatedMethod(teckpackIdeSteps, TeckPackIdeSteps.StepIds.TECKPACK_IDE_JNLP_FILE))
				.build();
	}
}