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
import com.ericsson.eniq.taf.installation.test.flows.DcuserPswdVerificationFlow01;

/**
 * Test Campaign for EQEV-69142 dcuser password verification 
 * @author ZJSOLEA
 */
@Test(enabled = false)
public class DcuserPswdVerification extends TorTestCaseHelper {
	public static final String DCUSER_PASSWORD_VERIFICATION_01 = "DCUSER_PASSWORD_VERIFICATION_01";
	public static final String DCUSER_PASSWORD_VERIFICATION_02 = "DCUSER_PASSWORD_VERIFICATION_02";
	public static final String DCUSER_PASSWORD_VERIFICATION_03 = "DCUSER_PASSWORD_VERIFICATION_03";
	public static final String DCUSER_PASSWORD_VERIFICATION_04 = "DCUSER_PASSWORD_VERIFICATION_04";
	public static final String DCUSER_PASSWORD_VERIFICATION_05 = "DCUSER_PASSWORD_VERIFICATION_05";

	@Inject
	private DcuserPswdVerificationFlow01 flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {
	}

	/**
	 * dcuser pwd verification_1
	 */
	@Test
	@TestId(id = "dcuser pwd verification_1", title = "Verify dcuser pswd by providing the pwd length less than 8 and greater than 30 characters.")
	public void verification01() {
		final TestScenario scenario = scenario(DCUSER_PASSWORD_VERIFICATION_01).addFlow(flow.verification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();
		runner.start(scenario);
	}
}