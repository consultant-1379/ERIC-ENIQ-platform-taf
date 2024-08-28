package com.ericsson.eniq.taf.installation.test.cases.bugs;

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
import com.ericsson.eniq.taf.installation.test.flows.MonitorHeapScriptFlow;

/**
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class MonitorHeapScriptTest extends TorTestCaseHelper {
	public static final String HEAP_SCRIPT_SCENARIO = "HeapScriptScenario";
	public static final String FILE_SYSTEM_SCRIPT_SCENARIO = "FileSystemScriptScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	@Inject
	private MonitorHeapScriptFlow monitorHeapScriptFlow;

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
	 * EQEV-62251_01
	 */
	@Test
	@TestId(id = "EQEV-62251_01", title = "Test Campaign for EQEV-62251: Monitor Heap Script Check")
	public void monitorHeapScriptVerificationTest() {
		final TestScenario scenario = scenario(HEAP_SCRIPT_SCENARIO)
				.addFlow(monitorHeapScriptFlow.verifyHeapScriptFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-61247_01
	 */
	@Test
	@TestId(id = "EQEV-61247", title = "System Freeze:Resource temporarily unavailable error while trying to switch to dcuser")
	public void fileSystemScriptVerificationTest() {
		final TestScenario scenario = scenario(FILE_SYSTEM_SCRIPT_SCENARIO)
				.addFlow(monitorHeapScriptFlow.verifyFileSystemScriptFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

}
