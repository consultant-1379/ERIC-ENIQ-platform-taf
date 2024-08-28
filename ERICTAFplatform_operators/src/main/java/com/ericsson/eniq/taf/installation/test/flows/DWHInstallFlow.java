package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.DWHMonitorInstallSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class DWHInstallFlow {
	private static final String DWH_MONITOR_INSTALL_LOG_FLOW = "DWHMonitorInstallationLogVerification";

	@Inject
	private DWHMonitorInstallSteps dWHMonitorInstallSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dwhMonitorInstallLogVerication() {
		return flow(DWH_MONITOR_INSTALL_LOG_FLOW)
				.addTestStep(annotatedMethod(dWHMonitorInstallSteps,
						DWHMonitorInstallSteps.StepIds.VERIFY_LOADING_ENGINE_LOGS))
				//.addTestStep(annotatedMethod(dWHMonitorInstallSteps,
						//DWHMonitorInstallSteps.StepIds.VERIFY_LOADING_ERROR_LOGS))
				//.addTestStep(annotatedMethod(dWHMonitorInstallSteps,
					//	DWHMonitorInstallSteps.StepIds.VERIFY_LOADING_ENGINE_LOGS_MONITOR))
				//.addTestStep(annotatedMethod(dWHMonitorInstallSteps,
					//	DWHMonitorInstallSteps.StepIds.VERIFY_LOADING_ERROR_LOGS_MONITOR))
				.build();
	}

}
