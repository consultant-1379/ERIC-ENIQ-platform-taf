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
import com.ericsson.eniq.taf.installation.test.flows.SelfSignedValidityFlow;

/**
 * 
 * Test Campaign for EQEV-58184_Self signed validity and renewal
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class SelfSignedValidityTest extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "CertificateBackupScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private SelfSignedValidityFlow selfSignedValidityFlow;

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
	 * EQEV-58184_01
	 */
	@Test
	@TestId(id = "EQEV-58184_01", title = "Certificate Backup")
	public void certificateBackup() {
		final TestScenario scenario = scenario(FLOW_SCENARIO).addFlow(selfSignedValidityFlow.verifycertificateBackup())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-58184_02
	 */
	@Test
	@TestId(id = "EQEV-58184_02", title = "To check the expiry of CA Signed Certificate")
	public void cAExpiryCheck() {
		final TestScenario scenario = scenario(FLOW_SCENARIO).addFlow(selfSignedValidityFlow.verifyCAExpiryCheck())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-58184_03
	 */
	// @Test
	// @TestId(id = "EQEV-58184_03", title = "Verify the webserver restart and UI behavior")
	// public void webserverRestart() {
		// final TestScenario scenario = scenario(FLOW_SCENARIO).addFlow(selfSignedValidityFlow.verifywebRestart())
				// .build();
		// final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		// runner.start(scenario);
	// }

	/**
	 * EQEV-52560_01
	 */
	@Test
	@TestId(id = "EQEV-52560_01", title = "Verify whether weak ciphers removed from server.xml")
	public void weakCiphersRemoved() {
		final TestScenario scenario = scenario(FLOW_SCENARIO).addFlow(selfSignedValidityFlow.verifyweakCiphers())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
