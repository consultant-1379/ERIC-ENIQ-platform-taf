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
import com.ericsson.eniq.taf.installation.test.flows.InitialInstallationFlow;

/**
 * Test Campaign for ENIQ Server Initial Installation - 18098
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class ENIQ_ServerInitialInstallationTest extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "InitialInstallationScenario";
	public static final String ENIQ_SERVICES_FLOW_SCENARIO = "ENIQservicesStatusScenario";
	public static final String ENIQ_SHIPMENT_FLOW_SCENARIO = "ENIQshipmentScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private InitialInstallationFlow initialInstallationFlows;

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
	 * EQEV-53431_TC_01
	 */
	@Test
	@TestId(id = "EQEV-53431_TC_01", title = "Verify that ENIQ-S Initial Installation is successful on Rack /Blade Server")
	public void isInitialInstallationSuccessful() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(initialInstallationFlows.verifyInitialInstallation()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-53431_TC_03
	 */
	@Test
	@TestId(id = "EQEV-53431_TC_03", title = "Verify all ENIQ-S services are deployed and actively running after Initial Installation is successful on Rack /Blade Server")
	public void verifyEniqServices() {
		final TestScenario scenario = scenario(ENIQ_SERVICES_FLOW_SCENARIO)
				.addFlow(initialInstallationFlows.verifyEniqServicesStatus()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-53431_TC_04
	 */
	@Test
	@TestId(id = "EQEV-53431_TC_04", title = "Verify all ENIQ-S installed version is updated properly with latest installed software information")
	public void verifyEniqShipment() {
		final TestScenario scenario = scenario(ENIQ_SHIPMENT_FLOW_SCENARIO)
				.addFlow(initialInstallationFlows.verifyEniqShipmentStatus()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}