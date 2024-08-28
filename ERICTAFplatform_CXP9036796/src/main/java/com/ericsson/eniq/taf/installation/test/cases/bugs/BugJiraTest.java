package com.ericsson.eniq.taf.installation.test.cases.bugs;

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
import com.ericsson.eniq.taf.installation.test.flows.BugJiraFlow;

/**
 * MWS Path Update
 * 
 * @author XARUNHA
 *
 */
@Test(enabled = false)
public class BugJiraTest extends TorTestCaseHelper {

	public static final String APACHE_FILES_SCENARIO = "Apache Tomcat Default Files Scenario ";
	public static final String ENGINE_SCENARIO = "Java coredump occured and engine went downScenario";
	public static final String EWM_REMOVAL_SCENARIO = "EWM Removal-Bug JIRA";
	public static final String PRLIMIT_SCENARIO = "Validate the value DefaultLimitNOFILE parameter";
	public static final String BEFORE = "basicTest";
	public static final String ALRAM_SCENARIO = "Alarm partition is not triggered automatically";
	public static final String DBA_CONNECTION_SCENARIO = "DBA_connection_Scenario";
	public static final String GET_PASSWORD_SCRIPT_SCENARIO = "getPassword.bsh_Scenario";
	public static final String PASSWORD_ENCRYPTION_SCENARIO = "EncryptionOfPasswordsInMetaDBScenario";
	public static final String IPTRANSPORT_SCENARIO = "IPTRANSPORT_ParsingScenario";

	@Inject
	AdminUI_Flows wepAppTestFlow;

	@Inject
	private BugJiraFlow bugJiraFlow;

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
	 * ENIQ-S : Apache Tomcat Default Files | BUG
	 */
	@Test
	@TestId(id = "EQEV-64499", title = "ENIQ-S : Apache Tomcat Default Files | BUG")
	public void ApacheTomcatDefaultFiles() {
		final TestScenario scenario = scenario(APACHE_FILES_SCENARIO)
				.addFlow(bugJiraFlow.apacheTomcatDefaultFilesFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * <3-18994727501>Java coredump occured and engine went down in 2975 server
	 */
	@Test
	@TestId(id = "EQEV-56998", title = "<3-18994727501>Java coredump occured and engine went down in 2975 server")
	public void JavaCoreDumpAndEngineDown() {
		final TestScenario scenario = scenario(ENGINE_SCENARIO).addFlow(bugJiraFlow.javaDumpAndEngineDownFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * 19.2: DWH_BASE and DWH_MONITOR techpack installation is failing with
	 * latest 19.2 License
	 */
	@Test
	@TestId(id = "EQEV-55124", title = "DWH_BASE and DWH_MONITOR techpack installation is failing with latest 19.2 License")
	public void DWH_BASEandDWH_MONITOR_LogTest() {
		final TestScenario scenario = scenario(ENGINE_SCENARIO).addFlow(bugJiraFlow.DWH_BASEandDWH_MONITOR_LogsFlow())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * As a part of EWM removal 'Wifi.vm and wifiInvertory.vm’ should be removed
	 * from common module.
	 * 
	 */
	@Test
	@TestId(id = "EQEV-56932", title = "As a part of EWM removal 'Wifi.vm and wifiInvertory.vm’ should be removed from common module.")
	public void EWM_RemovalBugJIRA() {
		final TestScenario scenario = scenario(EWM_REMOVAL_SCENARIO).addFlow(bugJiraFlow.EWMremovalFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * FileNotFoundException exception seen along with "too many open file"
	 * impacting parsing during recovery in SSD Rack server
	 * 
	 */
	@Test
	@TestId(id = "EQEV-67725", title = "FileNotFoundException exception seen along with 'too many open file' impacting parsing during recovery in SSD Rack server")
	public void prlimitTest() {
		final TestScenario scenario = scenario(PRLIMIT_SCENARIO).addFlow(bugJiraFlow.prlimitFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-66882
	 * 
	 */
	// @Test
	// @TestId(id = "EQEV-66882", title = "Alarm partition is not triggered automatically")
	// public void isAlarmPartitionTriggeredAutomatically() {
		// final TestScenario scenario = scenario(ALRAM_SCENARIO).addFlow(bugJiraFlow.alramFlow()).build();
		// final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		// runner.start(scenario);
	// }

/*	*//**
	 * EQEV-66399
	 * 
	 *//*
	@Test
	@TestId(id = "EQEV-66399", title = "DBA connection left open by adminUi package")
	public void connectionToDBAleftOpenbyAdminUI() {
		final TestScenario scenario = scenario(DBA_CONNECTION_SCENARIO).addFlow(bugJiraFlow.dbaFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
*/
	/**
	 * EQEV-68679
	 * 
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-68679", title = "Error while running getPassword.bsh script as root user")
	public void getPasswordScriptExecution() {
		final TestScenario scenario = dataDrivenScenario(GET_PASSWORD_SCRIPT_SCENARIO)
				.addFlow(bugJiraFlow.getPasswordFlow()).withScenarioDataSources(dataSource("getPasswordUsers")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-68105
	 * 
	 */
	@TestSuite
	@Test
	@TestId(id = "EQEV-68105", title = "Encryption of Passwords in META_DATABASES")
	public void verifyPasswordEncryptionInMetaDB() {
		final TestScenario scenario = dataDrivenScenario(PASSWORD_ENCRYPTION_SCENARIO)
				.addFlow(bugJiraFlow.passwordEncryptionInMetaDBFlow())
				.withScenarioDataSources(dataSource("metaDBusers")).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-66882
	 * 
	 */
	@Test
	@TestId(id = "EQEV-71654", title = "Files parsing is failing for INTF_DC_E_IPTRANSPORT_ECIM_ENM with error String index out of range: -1")
	public void IPTRANSPORT_Parsing_Error() {
		final TestScenario scenario = scenario(IPTRANSPORT_SCENARIO).addFlow(bugJiraFlow.IptransportFlow()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}