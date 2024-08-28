package com.ericsson.eniq.taf.gui.testcases;

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
import com.ericsson.eniq.taf.installation.test.flows.AdminUISpecialCharacterFlow;

/**
 * Test Campaign for EQEV-67891-special characters in Password
 * 
 * @author xsounpk
 *
 */
@Test(enabled = false)
public class AdminUISpecialCharacterTest extends TorTestCaseHelper {

	public static final String Manage_Tomcat_User_TC07_Test_Scenario = "Manage_Tomcat_User_TC07_Test_Scenario";
	public static final String Manage_Tomcat_User_TC06_Test_Scenario = "Manage_Tomcat_User_TC06_Test_Scenario";
	public static final String Manage_Tomcat_User_TC04_Test_Scenario = "Manage_Tomcat_User_TC04_Test_Scenario";
	public static final String Manage_Tomcat_User_TC05_Test_Scenario = "Manage_Tomcat_User_TC05_Test_Scenario";

	@Inject
	private AdminUISpecialCharacterFlow flow;

	/**
	 * initialize
	 */
	@BeforeSuite
	public void initialise() {

	}

	/**
	 * manage_tomcat_user.bsh_TC04
	 */
	@Test
	@TestId(id = "manage_tomcat_user_TC04", title = "Changing AdminUI User Password")
	public void verifyValidPasswordChange() {
		final TestScenario scenario = scenario(Manage_Tomcat_User_TC04_Test_Scenario)
				.addFlow(flow.verifyValidPasswordFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * manage_tomcat_user.bsh_TC06
	 */
	@Test
	@TestId(id = "manage_tomcat_user_TC06", title = "Verify restricted special characters are not accepted in Password while adding a new adminui user")
	public void verifyNewUserPasswordChange() {
		final TestScenario scenario = scenario(Manage_Tomcat_User_TC06_Test_Scenario)
				.addFlow(flow.verifyNewUserPasswordFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * manage_tomcat_user.bsh_TC07
	 */
	@TestSuite
	@Test
	@TestId(id = "manage_tomcat_user_TC07", title = "Verify restricted special characters are not accepted in Password while changing password of an existing adminui user")
	public void verifyPasswordSpecialCharacter() {
		final TestScenario scenario = scenario(Manage_Tomcat_User_TC07_Test_Scenario)
				.addFlow(flow.verifyPasswordSpecialCharacterFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * manage_tomcat_user.bsh_TC05
	 */
	@Test
	@TestId(id = "manage_tomcat_user_TC05", title = "Verify Users able to login to AdminUI after changing AdminUI User Password")
	public void verifyValidPasswordLoginChange() {
		final TestScenario scenario = scenario(Manage_Tomcat_User_TC05_Test_Scenario)
				.addFlow(flow.verifyValidPasswordLoginFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}
