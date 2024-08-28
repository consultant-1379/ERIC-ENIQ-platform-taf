package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.EQEV_50504_AdminUI_02_Steps;
import com.ericsson.eniq.taf.installation.test.steps.epfgFileGeneratorSteps;;

/**
 * Verify session logs not displaying error "Row count exceeded $toomany -rows"
 *
 * @author ZJSOLEA
 */
public class EQEV_50504_AdminUI_02_Flow {

	private static final String EQEV_50504_AdminUI_02_FLOW_01 = "EQEV_50504_AdminUI_02_Flow_01";
	
	@Inject
	private epfgFileGeneratorSteps epfgStesps;
	
	@Inject
	private EQEV_50504_AdminUI_02_Steps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verification() {
		return flow(EQEV_50504_AdminUI_02_FLOW_01)
				.addTestStep(annotatedMethod(steps, EQEV_50504_AdminUI_02_Steps.StepIds.EQEV_50504_AdminUI_02_STEP_01)).build();
	}
}
