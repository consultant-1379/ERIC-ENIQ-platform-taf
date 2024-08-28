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
import com.ericsson.eniq.taf.installation.test.flows.InterfaceActivationFlow;

/**
 * Test Campaign for ENIQ Interface activation on RHEL - 15839
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class InterfaceActivationTest extends TorTestCaseHelper {

	public static final String INTERFACE_ACTVATION_SCENARIO = "InterfaceActivationScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private InterfaceActivationFlow interfaceFlow;

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
	 * EQEV-50467_Interface_Activation_01
	 */
	@Test
	@TestId(id = "EQEV-50467_Interface_Activation_01", title = "Verify All the Interfaces for corresponding installed techpacks should be installed and activated automatically")
	public void verifyActiveInterfaces() {
		final TestScenario scenario = scenario(INTERFACE_ACTVATION_SCENARIO)
				.addFlow(interfaceFlow.ActiveInterfaceFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50467_Interface_Activation_03
	 */
	@Test
	@TestId(id = "EQEV-50467_Interface_Activation_03", title = "Verify All the Interfaces can be activated manually")
	public void isInterfacesActivatedWithOneAlias() {
		final TestScenario scenario = scenario(INTERFACE_ACTVATION_SCENARIO)
				.addFlow(interfaceFlow.interfaceActivationOneAliasFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50467_Interface_Activation_04
	 */
	@Test
	@TestId(id = "EQEV-50467_Interface_Activation_04", title = "Verify All the Interfaces can be activated for different OSS")
	public void isInterfacesActivatedWithOSS() {
		final TestScenario scenario = scenario(INTERFACE_ACTVATION_SCENARIO)
				.addFlow(interfaceFlow.interfaceActivationOssAliasFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}