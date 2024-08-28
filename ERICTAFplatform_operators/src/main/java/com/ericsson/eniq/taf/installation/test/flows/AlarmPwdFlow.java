package com.ericsson.eniq.taf.installation.test.flows;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eniq.taf.installation.test.steps.AlarmPwdStep;
import com.ericsson.eniq.taf.installation.test.steps.CheckFailedETLSets_Steps;

/**
 * Verify that there are no failed ETL sets
 *
 * @author XARUNHA
 */
public class AlarmPwdFlow {

	private static final String Alarm_Pwd_Change_Allowed_Flow = "Alarm_Pwd_Change_With_Allowed_Flow";
	private static final String Alarm_Pwd_Change_Not__Allowed_Flow = "Alarm_Pwd_Change_With_Not_Allowed_Flow";

	@Inject
	private AlarmPwdStep steps;

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow allowedPwd() {
		return flow(Alarm_Pwd_Change_Allowed_Flow).addTestStep(annotatedMethod(steps, AlarmPwdStep.StepIds.ALLOWED_PWD))
				.build();
	}

	/**
	 * 
	 * @return TestStepFlow
	 */
	public TestStepFlow notAllowedPwd() {
		return flow(Alarm_Pwd_Change_Not__Allowed_Flow)
				.addTestStep(annotatedMethod(steps, AlarmPwdStep.StepIds.NOT_ALLOWED_PWD)).build();
	}
}