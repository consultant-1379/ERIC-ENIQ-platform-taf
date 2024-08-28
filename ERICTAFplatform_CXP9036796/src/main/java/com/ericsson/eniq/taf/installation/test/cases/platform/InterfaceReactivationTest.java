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
import com.ericsson.eniq.taf.installation.test.flows.InterfaceReactivationFlow;

/**
 * Test campaign for Reactivation of interfaces - 16661
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class InterfaceReactivationTest extends TorTestCaseHelper {

	public static final String INTERFACE_REACTVATION_LOG_SCENARIO = "InterfaceReactivationLogScenario";
	public static final String INTERFACE_REACTVATION_SCENARIO = "InterfaceReaactivationScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private InterfaceReactivationFlow reactivationFlow;

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
	 * EQEV-50475_Reactivation of interface_01
	 */
	@Test
	@TestId(id = "EQEV-50475_Reactivation of interface_01", title = "Verify the reactivation of interface manually")
	public void verifyInterfacesReactivation() {
		final TestScenario scenario = scenario(INTERFACE_REACTVATION_SCENARIO)
				.addFlow(reactivationFlow.interfaceReactivationFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
	/*
		*//**
			 * EQEV-50475_Reactivation of interface_02
			 *//*
			 * @TestSuite
			 * 
			 * @Test(testName = "EQEV-50475_Reactivation of interface_02")
			 * 
			 * @TestId(id = "EQEV-50475_Reactivation of interface_02", title =
			 * "All Interfaces corresponding to selected feature shall be re-activated as part of feature upgrade"
			 * ) public void verifyFeatureInterfacesReactivation() { final
			 * TestScenario scenario = scenario(INTERFACE_REACTVATION_SCENARIO)
			 * .addFlow(reactivationFlow.featureInterfaceReactivationFlow()).
			 * build(); final TestScenarioRunner runner =
			 * runner().withListener(new LoggingScenarioListener()).build();
			 * 
			 * runner.start(scenario); }
			 */

	/**
	 * EQEV-50475_Reactivation of interface_03
	 */
	@Test
	@TestId(id = "EQEV-50475_Reactivation of interface_03", title = "Log verification")
	public void verifyInterfacesReactivationLog() {
		final TestScenario scenario = scenario(INTERFACE_REACTVATION_LOG_SCENARIO)
				.addFlow(reactivationFlow.interfaceReactivationLogFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}