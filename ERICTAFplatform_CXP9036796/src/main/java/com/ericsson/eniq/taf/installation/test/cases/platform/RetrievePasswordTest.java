package com.ericsson.eniq.taf.installation.test.cases.platform;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataDrivenScenario;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;

import javax.inject.Inject;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.annotations.TestSuite;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.eniq.taf.installation.test.flows.RetrievePasswordFlow;

/**
 * Test Campaign for EQEV-68047 - Create a wrapper script to get password from
 * niq.ini
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class RetrievePasswordTest extends TorTestCaseHelper {
	public static final String VERIFY_PASSWORD_RETRIEVE_SCENARIO = "VerifyPasswordRetrieveScenario";

	@Inject
	private RetrievePasswordFlow flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {

	}

	/**
	 * EQEV-68047_TC03,EQEV-68047_TC04,EQEV-68047_TC05 and EQEV-68047_TC06 Verify
	 * the script to retrieve password of the user dcpublic,dwhrep,etlrep,dba
	 */
	@TestSuite
	@Test
	@TestId(id = "Verify_password", title = "Verify the script to retrieve password of the user dcpublic,dwhrep,etlrep,dba")
	public void verifyPasswordRetrieve() {
		final TestScenario scenario = dataDrivenScenario(VERIFY_PASSWORD_RETRIEVE_SCENARIO)
				.addFlow(flow.verifyPassword()).withScenarioDataSources(dataSource("users")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
