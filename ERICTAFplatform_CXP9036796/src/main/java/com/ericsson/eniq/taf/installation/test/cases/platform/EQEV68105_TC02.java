package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.EQEV68105_TC02_Flow;

/**
 * change the password of database user dcbo
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV68105_TC02 extends TorTestCaseHelper {
	public static final String EQEV68105_TC02_SCENARIO = "EQEV68105_TC02_SCENARIO";

	@Inject
	private EQEV68105_TC02_Flow flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {

	}

	/**
	 * EQEV68105_TC02
	 */
	@Test
	@TestId(id = "EQEV68105_TC02", title = "change the password of database user dcbo")
	public void verification() {
		final TestScenario scenario = scenario(EQEV68105_TC02_SCENARIO).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}