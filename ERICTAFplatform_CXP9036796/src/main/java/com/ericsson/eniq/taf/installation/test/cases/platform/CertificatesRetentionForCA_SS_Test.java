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
import com.ericsson.eniq.taf.installation.test.flows.CertificatesRetentionFlow;

/**
 * Test Campaign for CA and SS certificates retention after PF upgrade/migration
 * - 18426
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class CertificatesRetentionForCA_SS_Test extends TorTestCaseHelper {

	public static final String FLOW_SCENARIO = "CertificatesRetentionScenario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private CertificatesRetentionFlow certificatesRetentionFlows;

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
	 * EQEV-60966_TC01
	 */
	@Test
	@TestId(id = "EQEV-60966_TC01", title = "Verify CA signed and Self signed certificates are retained after PF upgrade")
	public void isCertificatesRetainedCER() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(certificatesRetentionFlows.verifycertificatesRetentionCER()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-60966_TC01
	 */
	@Test
	@TestId(id = "EQEV-60966_TC01", title = "Verify CA signed and Self signed certificates are retained after PF upgrade")
	public void isCertificatesRetainedPEM() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(certificatesRetentionFlows.verifycertificatesRetentionPEM()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-60966_TC05
	 */
	@Test
	@TestId(id = "EQEV-60966_TC05", title = "Verify keystore.jks certificate is retained after migration")
	public void isCertificatesRetainedJKS() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(certificatesRetentionFlows.verifycertificatesRetentionJKS()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-60966_TC07
	 */
	@Test
	@TestId(id = "EQEV-60966_TC07", title = "Verify .csr signed certificates are retained after PF upgrade")
	public void isCertificatesRetainedCSR() {
		final TestScenario scenario = scenario(FLOW_SCENARIO)
				.addFlow(certificatesRetentionFlows.verifycertificatesRetentionCSR()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-60966_TC09
	 */
	/*
	 * @TestSuite
	 * 
	 * @Test(testName = "EQEV-60966_TC09")
	 * 
	 * @TestId(id = "EQEV-60966_TC09", title =
	 * "Verify enm certificates are retained after migration") public void
	 * isCertificatesRetainedENM() { final TestScenario scenario =
	 * scenario(FLOW_SCENARIO)
	 * .addFlow(certificatesRetentionFlows.verifycertificatesRetentionENM()).
	 * build() ; final TestScenarioRunner runner = runner().withListener(new
	 * LoggingScenarioListener()).build();
	 * 
	 * runner.start(scenario); }
	 * 
	 *//**
		 * EQEV-60966_TC11
		 *//*
		 * @TestSuite
		 * 
		 * @Test(testName = "EQEV-60966_TC11")
		 * 
		 * @TestId(id = "EQEV-60966_TC11", title =
		 * "Verify truststore.ts file is retained after PF upgrade which is integrated with ENM"
		 * ) public void isCertificatesRetainedTS() { final TestScenario
		 * scenario = scenario(FLOW_SCENARIO)
		 * .addFlow(certificatesRetentionFlows.verifycertificatesRetentionTS()).
		 * build(); final TestScenarioRunner runner = runner().withListener(new
		 * LoggingScenarioListener()).build();
		 * 
		 * runner.start(scenario); }
		 */
}