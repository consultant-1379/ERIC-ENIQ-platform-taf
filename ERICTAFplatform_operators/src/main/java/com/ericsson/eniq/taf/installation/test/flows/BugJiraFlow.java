package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.BugJiraSteps;

/**
 * Flows to test Parsing
 */
public class BugJiraFlow {

	private static final String APACHE_FILES_FLOW = "apacheTomcatDefaultFilesFlow";
	private static final String ENGINE_FLOW = "javaCoreDumpAndEngineFlow";
	private static final String EWMremoval_FLOW = "EWM Removal Flow";
	private static final String PRLIMIT_FLOW = "prlimit_Flow";
	private static final String ALARM_FLOW = "AlarmBugFlow";
	private static final String DBA_FLOW = "DBAconnectionBugFlow";
	private static final String GET_PASSWORD_SCRIPT = "getPassword.bshScriptFlow";
	private static final String PASSWORD_ENCRYPTION_FLOW = "getPassword.bshScriptFlow";
	private static final String IPTRANSPORT_FLOW = "IPTRANSPORT Flow";

	@Inject
	private BugJiraSteps bugJiraStesps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow apacheTomcatDefaultFilesFlow() {
		return flow(APACHE_FILES_FLOW)
				.addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.APACHE_TOMCAT_FILES)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow javaDumpAndEngineDownFlow() {
		return flow(ENGINE_FLOW).addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.JAVA_CORE_DUMP_ENGINE))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow DWH_BASEandDWH_MONITOR_LogsFlow() {
		return flow(ENGINE_FLOW)
				.addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.DWH_BASEandDWH_MONITOR_Logs)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow EWMremovalFlow() {
		return flow(EWMremoval_FLOW).addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.EWMremovalSteps))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow prlimitFlow() {
		return flow(PRLIMIT_FLOW).addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.PRLIMIT_TEST))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow alramFlow() {
		return flow(ALARM_FLOW)
				.addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.ALARM_AUTO_TRIGGER_TEST)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow dbaFlow() {
		return flow(DBA_FLOW).addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.DBA_CONNECTION)).build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow getPasswordFlow() {
		return flow(DBA_FLOW).addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.GET_PASSWORD_SCRIPT))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow passwordEncryptionInMetaDBFlow() {
		return flow(DBA_FLOW).addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.PASSWORD_ENCRYPTION_FLOW))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow IptransportFlow() {
		return flow(IPTRANSPORT_FLOW)
				.addTestStep(annotatedMethod(bugJiraStesps, BugJiraSteps.StepIds.IPTRANSPORT_PARSING)).build();
	}
}