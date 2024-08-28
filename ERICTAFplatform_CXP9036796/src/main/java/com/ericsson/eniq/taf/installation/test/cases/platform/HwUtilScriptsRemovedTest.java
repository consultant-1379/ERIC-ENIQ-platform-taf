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
import com.ericsson.eniq.taf.installation.test.flows.HwUtilScriptsRemovedFlow;

/**
 * EQEV-54840-HwUtil related scripts shall be removed from ES - 18069
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class HwUtilScriptsRemovedTest extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "HwUtilScriptsRemovedScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private HwUtilScriptsRemovedFlow hwUtilScriptsRemovedFlows;

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
	 * EQEV-54840_TC_01
	 */
	@Test
	@TestId(id = "EQEV-54840_TC_01", title = "H/w Util related scripts are removed from ES ISO")
	public void isHwUtilScriptsRemoved() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(hwUtilScriptsRemovedFlows.verifyHwUtilScriptsRemoved()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}