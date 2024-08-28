package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.RuntimeOperator;

/**
 * @author ZJSOLEA
 */
public class ExtractReportPackagesSteps01 {

	private static Logger logger = LoggerFactory.getLogger(ExtractReportPackagesSteps01.class);
	private static final String FINAL_MWS_FeaturePath = "shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-`;release=`echo $shipment | cut -d\".\" -f1`;num=`echo $shipment | cut -d\".\" -f2`;numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');featurePath=`echo \"Features_$release${numToAlpha[$num]}_$shipment\"`;echo $featurePath";
	private static final String LOGFILE_PATH = "/eniq/log/sw_log/platform_installer/runtime*.log";

	@Inject
	private Provider<GeneralOperator> genProvider;

	@Inject
	private Provider<RuntimeOperator> provider;

	/**
	 * @DESCRIPTION Verify Universes extracted to BO Universes directory
	 *              (EQEV-50469_port extraction of Universe and report_02)
	 * @PRE
	 */
	@TestStep(id = StepIds.VERIFY_REPORT_PACKAGES_01)
	public void verifyRuntimeModule() {
		// get operators from providers
		final RuntimeOperator operator = provider.get();
		final GeneralOperator genOoperator = genProvider.get();
		String out1 = operator.executeCommand("ls /eniq/sw/installer/  | grep -i bouniverses");
		assertEquals(out1, "bouniverses", "/eniq/sw/installer/bouniverses directory is not created.");

		List<String> missingPackages = new LinkedList<String>();
		List<String> extPackages = new LinkedList<String>();
		for (String x : (operator.executeCommand("ls /eniq/sw/installer/bouniverses | grep BO_E").split("\n"))) {
			extPackages.add(x.substring(0, x.lastIndexOf("_R")));
		}

		logger.info("MWS Count : " + extPackages.size());

		String MWS_COMMON_PATH = DataHandler.getAttribute("platform.mws.common.path").toString();
		String MWS_PARSER_PATH = genOoperator.findMWSfeaturesPathCommand(FINAL_MWS_FeaturePath);
		String finalMws_parser_Path = MWS_COMMON_PATH + MWS_PARSER_PATH + "/eniq_parsers/" + "/../eniq_techpacks/";

		for (String s : operator.executeCommand("ls " + finalMws_parser_Path + " | grep -i BO_E_").split("\n")) {
			if (s == null || s.trim().isEmpty())
				continue;
			else
				logger.info("Checking : " + s);
			s = s.substring(0, s.lastIndexOf("_R"));
			if (extPackages.indexOf(s) < 0 && !s.equals("BO_E_WIFI"))
				missingPackages.add(s);
		}
		logger.info("Missing Count : " + missingPackages.size());
		logger.info("Missing List : " + missingPackages.toString());

		assertEquals(missingPackages.size(), 0,
				missingPackages.size() + " packages are missing : " + missingPackages.toString());

	}

	public static class StepIds {
		public static final String VERIFY_REPORT_PACKAGES_01 = "Verify Universes extracted to BO Universes directory";

		private StepIds() {
		}
	}
}