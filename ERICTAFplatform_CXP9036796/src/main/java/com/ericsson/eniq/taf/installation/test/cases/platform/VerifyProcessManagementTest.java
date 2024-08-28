package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.flows.VerifyProcessManagementFlow;

/**
 * Test Campaign for PF process Management (start/stop) - 15714
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class VerifyProcessManagementTest extends TorTestCaseHelper {

	private static final String ENABLE_SERVICE_SCENARIO = "enable service scenario";
	private static final String DISABLE_SCENARIO = "disable service scenario";
	private static final String START_SERVICE_SCENARIO = "start service scenario";
	private static final String STOP_SERVICE_SCENARIO = "stop service scenario";
	private static final String DEPENDENT_SERVICES_SCENARIO = "dependent services scenario";
	private static final String SERVICES_LOGS_SCENARIO = "services logs scenario";
	private static final String RESTART_SERVICES_ORDER_SCENARIO = "restart services and order scenario";
	// private static final String DATA_SOURCE = "services";
	private static final String DATA_SOURCE = "processMangementServices";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyProcessManagementFlow processFlows;

	/**
	 * initialise
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-50468_processmangement_1
	 */
	// @TestSuite
	// @Test
	// @TestId(id = "EQEV-50468_processmangement_1", title = "Verify enabling Process Management services")
	// public void isEnableServiceSuccessful() {
		// final TestScenario scenario = dataDrivenScenario(ENABLE_SERVICE_SCENARIO).addFlow(wepAppTestFlow.basicTest())
				// .addFlow(processFlows.serviceEnableFlow()).withScenarioDataSources(dataSource(DATA_SOURCE)).build();
		// final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		// runner.start(scenario);
	// }

	/**
	 * EQEV-50468_processmangement_1
	 */
	// @TestSuite
	// @Test
	// @TestId(id = "EQEV-50468_processmangement_1", title = "Verify starting Procees Management services")
	// public void isStartServiceSuccessful() {
		// final TestScenario scenario = dataDrivenScenario(START_SERVICE_SCENARIO)
				// .addFlow(processFlows.serviceStartFlow()).withScenarioDataSources(dataSource(DATA_SOURCE)).build();
		// final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		// runner.start(scenario);
	// }

	/**
	 * EQEV-50468_processmanagement_2
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50468_processmanagement_2", title = "Verify disabling Process Management services")
	public void isDisableServiceSuccessful() {
		final TestScenario scenario = dataDrivenScenario(DISABLE_SCENARIO).addFlow(processFlows.serviceDisableFlow())
				.withScenarioDataSources(dataSource(DATA_SOURCE)).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50468_processmangement_2
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50468_processmanagement_2", title = "Verify stopping Process Management services")
	public void isStopServiceSuccessful() {
		final TestScenario scenario = dataDrivenScenario(STOP_SERVICE_SCENARIO).addFlow(processFlows.serviceStopFlow())
				.withScenarioDataSources(dataSource(DATA_SOURCE)).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50468_processmanagement_3
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50468_processmanagement_3", title = "Verfiy dependent services and PID")
	public void isDependentServiceSuccessful() {
		final TestScenario scenario = dataDrivenScenario(DEPENDENT_SERVICES_SCENARIO)
				.addFlow(processFlows.serviceDependenciesFlow()).withScenarioDataSources(dataSource(DATA_SOURCE))
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50468_processmangement_1
	 */

	@TestSuite
	@Test
	@TestId(id = "EQEV-50468_processmangement_1", title = "Post Installer")
	public void isLogServiceSuccessful() {
		final TestScenario scenario = dataDrivenScenario(SERVICES_LOGS_SCENARIO)
				.addFlow(processFlows.serviceStartFlow()).withScenarioDataSources(dataSource(DATA_SOURCE)).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50468_processmangement_5
	 */
	@TestSuite
	@Test(testName = "EQEV-50468_processmangement_5")
	@TestId(id = "EQEV-50468_processmangement_5", title = "Verify restarting all the services and order verification")
	public void isRestartAllSuccessful() {
		final TestScenario scenario = dataDrivenScenario(RESTART_SERVICES_ORDER_SCENARIO)
				.addFlow(processFlows.serviceReStartAllFlow()).withScenarioDataSources(dataSource(DATA_SOURCE)).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}