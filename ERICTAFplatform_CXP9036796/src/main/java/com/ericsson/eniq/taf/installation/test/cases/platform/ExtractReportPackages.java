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
import com.ericsson.eniq.taf.installation.test.flows.ExtractReportPackagesFlow01;
import com.ericsson.eniq.taf.installation.test.flows.ExtractReportPackagesFlow02;
import com.ericsson.eniq.taf.installation.test.flows.ExtractReportPackagesFlow03;

/**
 * Test Campaign for Report and Universe Extraction - 16035
 * 
 * @author ZJSOLEA
 *
 */
@Test(enabled = false)
public class ExtractReportPackages extends TorTestCaseHelper {

	public static final String EXTRACT_REPORT_PACKAGES_SCENARIO_01 = "ExtractReportPackagesScenario01";
	public static final String EXTRACT_REPORT_PACKAGES_SCENARIO_02 = "ExtractReportPackagesScenario02";
	public static final String EXTRACT_REPORT_PACKAGES_SCENARIO_03 = "ExtractReportPackagesScenario03";
	public static final String BEFORE = "basicTest";

	@Inject
	AdminUI_Flows wepAppTestFlow;
	@Inject
	private ExtractReportPackagesFlow02 flow02;
	@Inject
	private ExtractReportPackagesFlow01 flow01;
	@Inject
	private ExtractReportPackagesFlow03 flow03;

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
	 * EQEV-50469_port extraction of Universe and report_02
	 */
	@Test
	@TestId(id = "EQEV-50469_port extraction of Universe and report_02", title = "Verify Universes extracted to BO Universes directory")
	public void extractReportPackages01() {
		final TestScenario scenario = scenario(EXTRACT_REPORT_PACKAGES_SCENARIO_01)
				.addFlow(flow01.reportPackageVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50469_porting extraction of unvierses and reports_03
	 */
	@Test
	@TestId(id = "EQEV-50469_porting extraction of unvierses and reports_03", title = "Verify KPI report extracted to BOUniverses directory")
	public void extractReportPackages02() {
		final TestScenario scenario = scenario(EXTRACT_REPORT_PACKAGES_SCENARIO_02)
				.addFlow(flow02.reportPackageVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}

	/**
	 * EQEV-50469_port extraction of unvierses and reports_04
	 */
	@Test
	@TestId(id = "EQEV-50469_port extraction of unvierses and reports_04", title = "Verify only selected licensed features should be extracted")
	public void extractReportPackages03() {
		final TestScenario scenario = scenario(EXTRACT_REPORT_PACKAGES_SCENARIO_03)
				.addFlow(flow03.reportPackageVerification()).build();
		final TestScenarioRunner runner = runner().withListener(new LoggingScenarioListener()).build();

		runner.start(scenario);
	}
}