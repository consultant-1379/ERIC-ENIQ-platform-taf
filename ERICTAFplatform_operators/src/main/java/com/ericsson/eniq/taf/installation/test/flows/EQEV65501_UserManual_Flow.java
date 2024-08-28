package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV65501_UserManual_Steps;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;;

/**
 * launch User manual from AdminUi
 *
 * @author ZJSOLEA
 */
public class EQEV65501_UserManual_Flow {

	private static final String EQEV65501_UserManual_FLOW_01 = "EQEV65501_UserManual_Flow_01";

	@Inject
	private EQEV65501_UserManual_Steps steps;
	
	@Inject
	private epfgFileGeneratorSteps epfgStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV65501_UserManual_FLOW_01)
				.addTestStep(annotatedMethod(steps, EQEV65501_UserManual_Steps.StepIds.EQEV65501_UserManual_STEP_01)).build();
	}
}
