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
import com.ericsson.eniq.taf.installation.test.flows.VerifyKeystorePasswordFlow;

/**
 * Test Campaign for EQEV-65438 - 20453
 * 
 * @author xarunha
 *
 */

@Test(enabled = false)
public class VerifyKeystorePasswordTest extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "KeystorePassword_Scenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyKeystorePasswordFlow keystorePasswordFlows;

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
	 * EQEV-50442_platform_Module_02
	 */
	@Test
	@TestId(id = "EQEV-65438_TC_01", title = "Verify that keystore password can be changed using configure_newkeystore.sh script")
	public void verifyKeystorePassword() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(keystorePasswordFlows.keystorePasswordEncryptionFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}