package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.RHELOSScriptsMaintenanceSteps;

/**
 * 
 * @author xsounpk
 *
 */
public class RHELOSScriptsMaintenanceFlow {
	private static final String HIDDEN_SCRIPT_EXECUTION_FLOW = "HiddenScriptExecutionFlow";
	private static final String HOSTS_SCRIPT_EXECUTION_FLOW = "HostsScriptExecutionFlow";
	private static final String INTERFACE_SCRIPT_EXECUTION_FLOW = "InterfaceScriptExecutionFlow";
	private static final String RMIREGISTRY_SERVICES_STATUS_CHECK_FLOW = "RMIRegistryServiceStatusFlow";
	private static final String LWPHELPER_SERVICES_STATUS_CHECK_FLOW = "lwphelperServiceStatusFlow";

	@Inject
	private RHELOSScriptsMaintenanceSteps rHELOSScriptsMaintenanceSteps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyHiddenScriptExecutionFlow() {
		return flow(HIDDEN_SCRIPT_EXECUTION_FLOW).addTestStep(annotatedMethod(rHELOSScriptsMaintenanceSteps,
				RHELOSScriptsMaintenanceSteps.StepIds.VERIFY_HIDDEN_SCRIPT_EXECUTION)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyHostsScriptExecutionFlow() {
		return flow(HOSTS_SCRIPT_EXECUTION_FLOW).addTestStep(annotatedMethod(rHELOSScriptsMaintenanceSteps,
				RHELOSScriptsMaintenanceSteps.StepIds.VERIFY_HOSTS_FILE_EXECUTION)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */

	public TestStepFlow verifyInterfaceScriptExecutionFlow() {
		return flow(INTERFACE_SCRIPT_EXECUTION_FLOW).addTestStep(annotatedMethod(rHELOSScriptsMaintenanceSteps,
				RHELOSScriptsMaintenanceSteps.StepIds.VERIFY_INTERFACE_FILE_EXECUTION)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */

	public TestStepFlow verifyRMIRegistryStatusCheckFlow() {
		return flow(RMIREGISTRY_SERVICES_STATUS_CHECK_FLOW).addTestStep(annotatedMethod(rHELOSScriptsMaintenanceSteps,
				RHELOSScriptsMaintenanceSteps.StepIds.VERIFY_RHEL_DEPLOYED_SERVICES)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow verifyLwphelperServiceStatusFlow() {
		return flow(LWPHELPER_SERVICES_STATUS_CHECK_FLOW).addTestStep(annotatedMethod(rHELOSScriptsMaintenanceSteps,
				RHELOSScriptsMaintenanceSteps.StepIds.VERIFY_LWPHELPER_SERVICES)).build();
	}

}
