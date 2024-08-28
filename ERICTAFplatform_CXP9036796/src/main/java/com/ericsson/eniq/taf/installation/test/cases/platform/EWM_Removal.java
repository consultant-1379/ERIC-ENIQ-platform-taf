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
import com.ericsson.eniq.taf.installation.test.flows.VerifyEWMFlow;

/**
 * Test Campaign for EMW Removal
 * 
 * @author ZRUMRMU
 *
 */

public class EWM_Removal extends TorTestCaseHelper {

	private static final String EWM_PMDATA_VERIFICATION = "Verify PMDATA Entry in SUNOS.INI File";
	private static final String EWM_WIFIDATA_VERIFICATION = "Verify WIFI Data in Static.Properties File";
	private static final String EWM_WIFIPARSER_VERIFICATION = "Verify WIFI DATA in VersionDb.properties File";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private VerifyEWMFlow dvFlows;

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
	 * EQEV-54344_ EWMRemoval_03
	 */
	@Test
	@TestId(id = "EQEV-54344_EWM removal_03", title = "Verify entry in SunOS.ini file for pmdata_wifi directory")
	public void isPMData_Available() {
		final TestScenario scenario = scenario(EWM_PMDATA_VERIFICATION).addFlow(dvFlows.EWM_PMDATA_VERIFICATION())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-54344_ EWMRemoval_04
	 */
	@Test
	@TestId(id = "EQEV-54344_EWM removal_04", title = "Wifi, Wifi inventory Parser details should not present on the server")
	public void isWIFIParser_Available() {
		final TestScenario scenario = scenario(EWM_WIFIPARSER_VERIFICATION)
				.addFlow(dvFlows.EWM_WIFIPARSER_VERIFICATION()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-54344_ EWMRemoval_07
	 */
	@Test
	@TestId(id = "EQEV-54344_EWM removal_07", title = "Verify static.properties file content. Wifi related info should not be present")
	public void isWIFIData_Available() {
		final TestScenario scenario = scenario(EWM_WIFIDATA_VERIFICATION).addFlow(dvFlows.EWM_WIFIDATA_VERIFICATION())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-54344_EWM removal_10
	 */
	@Test
	@TestId(id = "EQEV-54344_EWM removal_10", title = "Wifi related things need to be removed from /eniq/sw/bin")
	public void isWIFIDataAbsence() {
		final TestScenario scenario = scenario(EWM_WIFIDATA_VERIFICATION).addFlow(dvFlows.wifiDataAbsence()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-54344_EWM removal_11
	 */
	@Test
	@TestId(id = "EQEV-54344_EWM removal_11", title = "Wifi.vm and wifiInvertory.vm does not exists in common module")
	public void isWifiInvertoryFileExists() {
		final TestScenario scenario = scenario(EWM_WIFIDATA_VERIFICATION).addFlow(dvFlows.wifiInvertory()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
