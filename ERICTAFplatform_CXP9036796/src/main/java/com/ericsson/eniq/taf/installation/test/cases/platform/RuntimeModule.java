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
import com.ericsson.eniq.taf.installation.test.flows.RuntimeModule02Flow;
import com.ericsson.eniq.taf.installation.test.flows.RuntimeModule04Flow;
import com.ericsson.eniq.taf.installation.test.flows.RuntimeModule06Flow;
import com.ericsson.eniq.taf.installation.test.flows.RuntimeModule10Flow;
import com.ericsson.eniq.taf.installation.test.flows.RuntimeModule12Flow;
import com.ericsson.eniq.taf.installation.test.flows.RuntimeModule15Flow;

/**
 * Test Campaign for runtime modules - 15664
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class RuntimeModule extends TorTestCaseHelper {

	public static final String RUNTIME_MODULES_SCENARIO_02 = "RuntimeModulesScenario02";
	public static final String RUNTIME_MODULES_SCENARIO_04 = "RuntimeModulesScenario04";
	public static final String RUNTIME_MODULES_SCENARIO_06 = "RuntimeModulesScenario06";
	public static final String RUNTIME_MODULES_SCENARIO_10 = "RuntimeModulesScenario10";
	public static final String RUNTIME_MODULES_SCENARIO_12 = "RuntimeModulesScenario12";
	public static final String RUNTIME_MODULES_SCENARIO_15 = "RuntimeModulesScenario15";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private RuntimeModule02Flow runtime02Flow;

	@Inject
	private RuntimeModule04Flow runtime04Flow;

	@Inject
	private RuntimeModule06Flow runtime06Flow;

	@Inject
	private RuntimeModule10Flow runtime10Flow;

	@Inject
	private RuntimeModule12Flow runtime12Flow;

	@Inject
	private RuntimeModule15Flow runtime15Flow;

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
	 * EQEV-50497_runtime_module_02
	 */
	@Test
	@TestId(id = "EQEV-50497_runtime_module_02", title = "Verify installation logs")
	public void runtimeModule02() {
		final TestScenario scenario = scenario(RUNTIME_MODULES_SCENARIO_02)
				.addFlow(runtime02Flow.runtimeModuleVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50497_runtime_module_04
	 */
	@Test
	@TestId(id = "EQEV-50497_runtime_module_04", title = "Verify java and tomact versions are latest or not")
	public void runtimeModule04() {
		final TestScenario scenario = scenario(RUNTIME_MODULES_SCENARIO_04)
				.addFlow(runtime04Flow.runtimeModuleVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50497_runtime_module_06
	 */
	@Test
	@TestId(id = "EQEV-50497_runtime_module_06", title = "Check the runtime package is extracted under /eniq/sw/runtime")
	public void runtimeModule06() {
		final TestScenario scenario = scenario(RUNTIME_MODULES_SCENARIO_06)
				.addFlow(runtime06Flow.runtimeModuleVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50497_runtime_module_10
	 */
	@Test
	@TestId(id = "EQEV-50497_runtime_module_10", title = "verify whether SSL is configured as part of tomcat installation")
	public void runtimeModule10() {
		final TestScenario scenario = scenario(RUNTIME_MODULES_SCENARIO_10)
				.addFlow(runtime10Flow.runtimeModuleVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50497_runtime_module_12
	 */
	@Test
	@TestId(id = "EQEV-50497_runtime_module_12", title = "Verify JAVA execute permissions are provided without any issues.")
	public void runtimeModule12() {
		final TestScenario scenario = scenario(RUNTIME_MODULES_SCENARIO_12)
				.addFlow(runtime12Flow.runtimeModuleVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50497_runtime_module_15
	 */
	@Test
	@TestId(id = "EQEV-50497_runtime_module_15", title = "verify the web server status")
	public void runtimeModule15() {
		final TestScenario scenario = scenario(RUNTIME_MODULES_SCENARIO_15)
				.addFlow(runtime15Flow.runtimeModuleVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}