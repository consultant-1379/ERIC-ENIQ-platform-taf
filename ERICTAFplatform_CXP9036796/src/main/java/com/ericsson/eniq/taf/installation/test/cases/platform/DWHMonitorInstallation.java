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
import com.ericsson.eniq.taf.installation.test.flows.DWHInstallFlow;

/**
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class DWHMonitorInstallation extends TorTestCaseHelper {
	public static final String DWH_MONITOR_INSTALLATION_SCENARIO = "Verify the DWH_MONITOR installation";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private DWHInstallFlow dWHInstallFlow;

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
	 * EQEV-50683_Loading_02
	 */
	@Test
	@TestId(id = "EQEV-51257_DeltaView Re-creation script02", title = "Verify the DWH_MONITOR installation logs")
	public void dWHInstallLogVerification() {
		final TestScenario scenario = scenario(DWH_MONITOR_INSTALLATION_SCENARIO)
				.addFlow(dWHInstallFlow.dwhMonitorInstallLogVerication()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
