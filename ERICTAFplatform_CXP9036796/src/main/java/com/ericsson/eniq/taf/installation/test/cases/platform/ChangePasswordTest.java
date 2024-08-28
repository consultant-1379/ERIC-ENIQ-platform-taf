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
import com.ericsson.eniq.taf.installation.test.flows.ChangePasswordFlow;

/**
 * Test Campaign for EQEV-68105 - Encryption of Passwords in META_DATABASES
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class ChangePasswordTest extends TorTestCaseHelper {

	public static final String EQEV_68105_Negative_TC05_Test_Scenario = "EQEV_68105_Negative_TC05_Test_scenario";
	public static final String EQEV_68105_Negative_TC06_Test_Scenario = "EQEV_68105_Negative_TC06_Test_scenario";
	public static final String EQEV_68105_TC07_Test_Scenario = "EQEV_68105_TC07_Test_scenario";
	public static final String DCPUBLIC_FLOW = "change the password of database user dcpublic";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private ChangePasswordFlow flow;

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
	 * EQEV-68105_TC03
	 */
	/*@TestSuite
	@Test
	@TestId(id = "EQEV-68105_TC03", title = "change the password of database user dcpublic")
	public void changeDcpublicDBpassword() {
		final TestScenario scenario = dataDrivenScenario(DCPUBLIC_FLOW).addFlow(flow.changeDcpublicDBpasswordFlow())
				.withScenarioDataSources(dataSource("dbUsers")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}*/

	/**
	 * EQEV_68105_Negative_TC05
	 */
	@Test
	@TestId(id = "EQEV_68105_Negative_TC05", title = "Verify whether the password for dwhrep user cannot be changed through change_db_password script")
	public void verify_EQEV_68105_Negative_TC05() {
		final TestScenario scenario = scenario(EQEV_68105_Negative_TC05_Test_Scenario)
				.addFlow(flow.dwhrepChangePassword()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV_68105_Negative_TC06
	 */
	@Test
	@TestId(id = "EQEV_68105_Negative_TC06", title = "Verify whether the password for etlrep user cannot be changed through change_db_password script")
	public void verify_EQEV_68105_Negative_TC06() {
		final TestScenario scenario = scenario(EQEV_68105_Negative_TC06_Test_Scenario)
				.addFlow(flow.etlrepChangePassword()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
	
	/**
	 * EQEV_68105_TC07
	 */
	/*@Test
	@TestId(id = "EQEV_68105_TC07", title = "Verify whether the password for dcuser user can be changed through change_db_password script")
	public void verify_EQEV_68105_TC07() {
		final TestScenario scenario = scenario(EQEV_68105_TC07_Test_Scenario).addFlow(flow.dcUserChangePassword())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}*/
}
