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
public class ExtractReportPackagesSteps02 {

	private static Logger logger = LoggerFactory.getLogger(ExtractReportPackagesSteps02.class);
	private static final String FINAL_MWS_FeaturePath = "shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-`;release=`echo $shipment | cut -d\".\" -f1`;num=`echo $shipment | cut -d\".\" -f2`;numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');featurePath=`echo \"Features_$release${numToAlpha[$num]}_$shipment\"`;echo $featurePath";
	
	@Inject
	private Provider<GeneralOperator> genProvider;
	
	@Inject
	private Provider<RuntimeOperator> provider;
	
	/**
	 * @DESCRIPTION Verify KPI report extracted to BOUniverses directory (EQEV-50469_porting extraction of unvierses and reports_03)
	 * @PRE 
	 */
	@TestStep(id = StepIds.VERIFY_REPORT_PACKAGES_02)
	public void verifyReportPackages() {
		// get operators from providers
		final RuntimeOperator operator = provider.get();
		final GeneralOperator genOperator = genProvider.get();
		String out1 = operator.executeCommand("ls /eniq/sw/installer/  | grep -i boreports");
		assertEquals(out1, "boreports", "/eniq/sw/installer/bouniverses directory is not created.");
		
		List<String> missingPackages = new LinkedList<String>();
		List<String> extPackages = new LinkedList<String>();
		for (String x: (operator.executeCommand("ls /eniq/sw/installer/boreports | grep -i ERIC_").split("\n"))) {
			extPackages.add(x.substring(0, x.lastIndexOf('_')));
		}

		logger.info("MWS Count : " + extPackages.size());
		
		String MWS_COMMON_PATH = DataHandler.getAttribute("platform.mws.common.path").toString();
		String Feature = genOperator.findMWSfeaturesPathCommand(FINAL_MWS_FeaturePath);
		String MWS_FEATURE_PATH = MWS_COMMON_PATH + Feature ;
		for (String s: operator.executeCommand("ls " + MWS_FEATURE_PATH + "/eniq_reports/reports | grep -i ERIC_").split("\n")) {
			s = s.substring(0, s.lastIndexOf('_'));
			if (extPackages.indexOf(s) < 0 && !s.equals("ERIC_ENIQ_Business_Intelligence_Report_Template_Package")) missingPackages.add(s);
		}
		logger.info("Missing Count : " + missingPackages.size());
		logger.info("Missing List : " + missingPackages.toString());
		
		assertEquals(missingPackages.size(), 0, missingPackages.size() + " packages are missing : " + missingPackages.toString());
	}

	public static class StepIds {
		public static final String VERIFY_REPORT_PACKAGES_02 = "Verify Universes extracted to BO Universes directory";

		private StepIds() {
		}
	}
}