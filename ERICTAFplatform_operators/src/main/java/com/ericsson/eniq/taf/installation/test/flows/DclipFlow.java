package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.DclipSteps;
import com.ericsson.eniq.taf.installation.test.steps.VerifySchedulerSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class DclipFlow {
	private static final String DCLIP_JAR_CHECK = "JarVerification";

	@Inject
	private VerifySchedulerSteps dclipSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow jarExtractedVerication() {
		return flow(DCLIP_JAR_CHECK).addTestStep(annotatedMethod(dclipSteps, VerifySchedulerSteps.StepIds.VERIFY_JAR))
				.build();
	}

}
