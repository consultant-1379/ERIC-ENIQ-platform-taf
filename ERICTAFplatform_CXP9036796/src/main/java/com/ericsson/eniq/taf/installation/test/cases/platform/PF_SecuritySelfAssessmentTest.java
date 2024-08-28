package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.PF_SecuritySelfAssessmentFlow;

/**
 * Test Campaign for EQEV-52563 - ES PF Security Self Assessment: Defense in
 * Depth and use of Choke Points - 17817
 *
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class PF_SecuritySelfAssessmentTest extends TorTestCaseHelper {

	public static final String PF_SECURITY_SCENARIO = "PF_SecuritySelfAssessmentTest Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private PF_SecuritySelfAssessmentFlow pfSecurity;

	/**
	 * initialize
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-52563_05
	 */
	@Test
	@TestId(id = "EQEV-52563_05", title = "verify the different command after installing the installer module")
	public void isSchedulerPacakgeInstalled() {
		final TestScenario scenario = scenario(PF_SECURITY_SCENARIO).addFlow(pfSecurity.pfSecurityFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
