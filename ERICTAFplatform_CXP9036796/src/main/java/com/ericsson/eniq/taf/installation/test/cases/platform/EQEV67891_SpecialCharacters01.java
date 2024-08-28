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
import com.ericsson.eniq.taf.installation.test.flows.EQEV67891_SpecialCharacters01_Flow;

/**
 * Adding New AdminUI Users with special characters in password
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class EQEV67891_SpecialCharacters01 extends TorTestCaseHelper {

	public static final String EQEV67891_SpecialCharacters01_SCENARIO = "EQEV67891_SpecialCharacters01_SCENARIO";


	@Inject
	private EQEV67891_SpecialCharacters01_Flow flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {

	}

	/**
	 * manage_tomcat_user.bsh_TC01
	 */
	@Test
	@TestId(id = "manage_tomcat_user.bsh_TC01", title = "Adding New AdminUI Users with special characters in password")
	public void verification() {
		final TestScenario scenario = scenario(EQEV67891_SpecialCharacters01_SCENARIO).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}