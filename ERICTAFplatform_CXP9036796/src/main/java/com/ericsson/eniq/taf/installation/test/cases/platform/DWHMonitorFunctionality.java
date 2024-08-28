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
import com.ericsson.eniq.taf.installation.test.flows.VerifyDWHMonitorFlow;

/**
 * Test Campaign for DWH_Monitor
 * 
 * @author ZRUMRMU
 *
 */

public class DWHMonitorFunctionality extends TorTestCaseHelper {

	private static final String DWH_MONITOR_DIR_CHECK_VERIFICATION = "Verify Directory and Disk Manager";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private VerifyDWHMonitorFlow dvFlows;

	/**
	 * initialize the test case
	 */
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}

	/**
	 * EQEV-51275_02
	 */
	@Test
	@TestId(id = "EQEV-51275_02", title = "Directory_Checker and Disk manager DWH_MONITOR Verification")
	public void DirectoryCheckVerification() {
		final TestScenario scenario = scenario(DWH_MONITOR_DIR_CHECK_VERIFICATION)
				.addFlow(dvFlows.DWH_DIR_DISKMANAGER_VERIFICATION()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
