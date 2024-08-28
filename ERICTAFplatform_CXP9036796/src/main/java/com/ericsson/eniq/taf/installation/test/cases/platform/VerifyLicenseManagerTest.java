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
import com.ericsson.eniq.taf.installation.test.flows.VerifyLicenseManagerFlow;

/**
 * Test Campaign for License Manager impacts - 15815
 * 
 * @author zvaddee
 *
 */
@Test(enabled = false)
public class VerifyLicenseManagerTest extends TorTestCaseHelper {

	private static final String LM_LOG_SCENARIO = "License Manager Log Screnario";
	private static final String LM_SERVER_STATUS_SCENARIO = "License Server Screnario";
	private static final String LM_STATUS_SCENARIO = "License Manager Status Screnario";
	private static final String LM_FEATURE_INFO_SCENARIO = "Feature Mapping Information using licmgr Screnario";
	private static final String LM_STATUS_INFO_SCENARIO = "License Manager Status Screnario";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private VerifyLicenseManagerFlow lmFlows;

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
	 * EQEV-50686_Lic_Mgr_02
	 */
	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_02", title = "Verify the licensing log files using command line")
	public void isLicenseMgrLogsValid() {
		final TestScenario scenario = scenario(LM_LOG_SCENARIO).addFlow(lmFlows.lmLogFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50686_Lic_Mgr_05
	 */
	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_05", title = "Verify the status of license server from command line")
	public void isLicenseServerStatusValid() {
		final TestScenario scenario = scenario(LM_SERVER_STATUS_SCENARIO).addFlow(lmFlows.licenseServerStatusFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50686_Lic_Mgr_06
	 */

	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_06", title = "Post Installer")
	public void isLicenseManagerStatusValid() {
		final TestScenario scenario = scenario(LM_STATUS_SCENARIO).addFlow(lmFlows.licenseManagerStatusFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50686_Lic_Mgr_08 cxc_numbers.csv file is updated based on below
	 * shipment ENIQ_STATUS ENIQ_Statistics_Shipment_19.2.13_Linux AOM901171 R1N
	 * INST_DATE 2019-Mar-25_15.24.13
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_08", title = "Verify Viewing Feature Mapping Information using licmgr")
	public void isFeatureMappingInformationValid() {
		final TestScenario scenario = dataDrivenScenario(LM_FEATURE_INFO_SCENARIO).addFlow(lmFlows.featureMappingInfo())
				.withScenarioDataSources(dataSource("cxc_numbers")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50686_Lic_Mgr_00 cxc_numbers.csv file is updated based on below
	 * shipment updated Valid CXC numbers as per the DM team on 27/06/2019
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_00", title = "Verify Feature is valid or not using licmgr")
	public void isFeatureIsValid() {
		final TestScenario scenario = dataDrivenScenario(LM_FEATURE_INFO_SCENARIO)
				.addFlow(lmFlows.featureIsValidOrNot()).withScenarioDataSources(dataSource("cxc_numbers")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50686_Lic_Mgr_09 cxc_numbers.csv file is updated based on below
	 * shipment updated Valid CXC numbers as per the DM team on 27/06/2019
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-50686_Lic_Mgr_09", title = "Verify start, stop and restart of license manager")
	public void ValidateLicenseManager() {
		final TestScenario scenario = dataDrivenScenario(LM_STATUS_INFO_SCENARIO)
				.addFlow(lmFlows.licenseManagerStatus()).withScenarioDataSources(dataSource("pm_licmgr_operation"))
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}