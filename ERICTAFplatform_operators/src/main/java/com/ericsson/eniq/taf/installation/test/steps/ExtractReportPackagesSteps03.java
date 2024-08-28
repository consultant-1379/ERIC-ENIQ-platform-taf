package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
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
public class ExtractReportPackagesSteps03 {

	private static Logger logger = LoggerFactory.getLogger(ExtractReportPackagesSteps03.class);
	private static final String FINAL_MWS_FeaturePath = "shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-`;release=`echo $shipment | cut -d\".\" -f1`;num=`echo $shipment | cut -d\".\" -f2`;numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');featurePath=`echo \"Features_$release${numToAlpha[$num]}_$shipment\"`;echo $featurePath";
	private static final String LOGFILE_PATH = "/eniq/log/sw_log/platform_installer/runtime*.log";

	@Inject
	private Provider<RuntimeOperator> provider;

	@Inject
	private Provider<GeneralOperator> genProvider;

	/**
	 * @DESCRIPTION Verify only selected licensed features should be extracted
	 *              (EQEV-50469_port extraction of unvierses and reports_04)
	 * @PRE
	 */
	@TestStep(id = StepIds.VERIFY_REPORT_PACKAGES_03)
	public void verifyReportPackages() {
		// get operators from providers
		final RuntimeOperator operator = provider.get();
		final GeneralOperator genOperator = genProvider.get();

		String GET_LICENSED_FEATURES = "licmgr -getlicinfo | grep -i name | sed '\\''s/^.*: //g'\\'' | while read line; do grep -i $line /eniq/sw/conf/feature_report_packages ; done | sed '\\''s/^.*:://g'\\''";

		List<String> licensed = new LinkedList<String>();
		for (String i : operator.executeCommand(GET_LICENSED_FEATURES).split("\n")) {
			licensed.add(i.substring(0, i.length() - 1));
		}

		logger.info("LICENSED FEATURES (" + licensed.size() + ") : " + Arrays.asList(licensed));

		List<String> missPackages = new LinkedList<String>();
		String MWS_COMMON_PATH = DataHandler.getAttribute("platform.mws.common.path").toString();
		String Feature = genOperator.findMWSfeaturesPathCommand(FINAL_MWS_FeaturePath);

		String MWS_FEATURE_PATH = MWS_COMMON_PATH + Feature + "/eniq_parsers/../eniq_techpacks";
		for (String s : operator.executeCommand("ls " + MWS_FEATURE_PATH + " | grep -i BO_E_").split("\n")) {
			s = s.substring(0, s.lastIndexOf("_R"));
			if (missPackages.indexOf(s) > 0)
				missPackages.add(s);
		}

		assertEquals(missPackages.size(), 0,
				"The following packages are extracted but not licensed : " + missPackages.toString());
	}

	public static class StepIds {
		public static final String VERIFY_REPORT_PACKAGES_03 = "Verify only selected licensed features should be extracted";

		private StepIds() {
		}
	}
}