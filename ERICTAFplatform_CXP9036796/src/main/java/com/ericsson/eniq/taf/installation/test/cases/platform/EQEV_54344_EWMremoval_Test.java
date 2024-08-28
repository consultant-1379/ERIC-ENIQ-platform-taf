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
import com.ericsson.eniq.taf.installation.test.flows.EQEV_54344_EWMremoval_Flows;

/**
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class EQEV_54344_EWMremoval_Test extends TorTestCaseHelper {

	public static final String EQEV54344_EWM_removal_06_Test_Scenario = "EQEV54344_EWM_removal_06_Test_scenario";
	public static final String EQEV54344_EWM_removal_09_Test_Scenario = "EQEV54344_EWM_removal_09_Test_scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private EQEV_54344_EWMremoval_Flows flow;

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
	 * EQEV_54344_EWM_removal_06
	 */
	@Test
	@TestId(id = "EQEV_54344_EWM_removal_06", title = "Verify crontab entries on the server both root/dcuser")
	public void verify_EQEV_54344_EWM_removal_06() {
		final TestScenario scenario = scenario(EQEV54344_EWM_removal_06_Test_Scenario)
				.addFlow(flow.crontabEntryVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV_54344_EWM_removal_09
	 */
	@Test
	@TestId(id = "EQEV_54344_EWM_removal_09", title = "Verify whether all related scripts removed from /eniq/sw/installer and /eniq/admin/bin")
	public void verify_EQEV_54344_EWM_removal_09() {
		final TestScenario scenario = scenario(EQEV54344_EWM_removal_09_Test_Scenario)
				.addFlow(flow.wifiScriptVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
