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
import com.ericsson.eniq.taf.installation.test.flows.EniqsComplianceFlow;

/**
 * Test Campaign for EMW Removal
 * 
 * @author XARUNHA
 *
 */

public class EniqsComplianceTest extends TorTestCaseHelper {

	private static final String EXMAPLE_DIR_VERIFICATION = "Verify PMDATA Entry in SUNOS.INI File";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	
	@Inject
	private EniqsComplianceFlow eniqCom;

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
	 * EQEV-58513_01
	 */
	@Test
	@TestId(id = "EQEV-58513_01", title = "verify whether 'example' dir is removed from '/eniq/sw/runtime/tomcat/webapps'")
	public void isPMData_Available() {
		final TestScenario scenario = scenario(EXMAPLE_DIR_VERIFICATION).addFlow(eniqCom.exmapleDirVerification())
				.build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

}
