package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.WebserverRestartSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class WebserverRestartFlow {
	private static final String WEBSERVER_RESTART_FLOW = "WebserverRestart_Flow";

	@Inject
	private WebserverRestartSteps steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow webserverRestartFlow() {
		return flow(WEBSERVER_RESTART_FLOW)
				.addTestStep(annotatedMethod(steps, WebserverRestartSteps.StepIds.EQEV_65253_Webserver_Restart_Step))
				.build();
	}

}
