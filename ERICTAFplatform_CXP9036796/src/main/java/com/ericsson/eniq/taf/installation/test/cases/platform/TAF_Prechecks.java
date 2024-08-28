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
import com.ericsson.eniq.taf.installation.test.flows.TAF_Prechecks_01_Flow;
import com.ericsson.eniq.taf.installation.test.flows.TAF_Prechecks_02_Flow;
import com.ericsson.eniq.taf.installation.test.flows.TAF_Prechecks_03_Flow;

/**
 * Check prerequisites before starting TAF
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class TAF_Prechecks extends TorTestCaseHelper {

	public static final String TAF_PRECHECKS_01_SCENARIO = "TAF_PRECHECKS_01_SCENARIO";
	public static final String TAF_PRECHECKS_02_SCENARIO = "TAF_PRECHECKS_02_SCENARIO";
	public static final String TAF_PRECHECKS_03_SCENARIO = "TAF_PRECHECKS_03_SCENARIO";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private TAF_Prechecks_01_Flow flow01;
	@Inject
	private TAF_Prechecks_02_Flow flow02;
	@Inject
	private TAF_Prechecks_03_Flow flow03;

/*	*//**
	 * initialize
	 *//*
	@BeforeTest
	public void initialise() {
		final TestScenario scenario = scenario(BEFORE).addFlow(wepAppTestFlow.basicTest()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
*/
	/**
	 * TAF Prechecks 01
	 */
	@Test
	@TestId(id = "TAF Prechecks 01", title = "Engine profile should be in ‘Normal’")
	public void verification01() {
		final TestScenario scenario = scenario(TAF_PRECHECKS_01_SCENARIO).addFlow(wepAppTestFlow.basicTest()).addFlow(flow01.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * TAF Prechecks 02
	 */
	@Test
	@TestId(id = "TAF Prechecks 02", title = "all services should be in active state ")
	public void verification02() {
		final TestScenario scenario = scenario(TAF_PRECHECKS_02_SCENARIO).addFlow(flow02.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * TAF Prechecks 03
	 */
	@Test
	@TestId(id = "TAF Prechecks 03", title = "Repdb and dwhdb are up and running")
	public void verification03() {
		final TestScenario scenario = scenario(TAF_PRECHECKS_03_SCENARIO).addFlow(flow03.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}