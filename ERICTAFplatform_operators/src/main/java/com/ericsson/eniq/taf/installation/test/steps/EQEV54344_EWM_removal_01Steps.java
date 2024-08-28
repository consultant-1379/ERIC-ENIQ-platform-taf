package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * @author ZJSOLEA
 */
public class EQEV54344_EWM_removal_01Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV54344_EWM_removal_01Steps.class);
	private static final String FINAL_MWS_FeaturePath = "shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-`;release=`echo $shipment | cut -d\".\" -f1`;num=`echo $shipment | cut -d\".\" -f2`;numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');featurePath=`echo \"Features_$release${numToAlpha[$num]}_$shipment\"`;echo $featurePath";
	@Inject
	private Provider<GeneralOperator> provider;

	/**
	 * @DESCRIPTION Verification of EWM details as part of manage_connection
	 *              integration script execution
	 */
	@TestStep(id = StepIds.EQEV54344_EWM_REMOVAL_01_STEP)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();

		String MWS_COMMON_PATH = DataHandler.getAttribute("platform.mws.common.path").toString();
		String MWS_PARSER_PATH = operator.findMWSfeaturesPathCommand(FINAL_MWS_FeaturePath);
		String finalMws_Path = MWS_COMMON_PATH + MWS_PARSER_PATH + "/../eniq_techpacks/";
		String MWS_FEATURE_PATH = finalMws_Path.substring(0, finalMws_Path.lastIndexOf('/'));
		String output = operator
				.executeCommand("timeout 3s /usr/bin/bash /eniq/connectd/bin/manage_connections.bsh -a add -d "
						+ MWS_FEATURE_PATH + "Features_19B_19.2.6/");
		assertTrue(!output.contains("EWM"), "EWM should not be listed in Available connection types");

		return;
	}

	public static class StepIds {
		public static final String EQEV54344_EWM_REMOVAL_01_STEP = "verification of EQEV-54344_EWM removal_01";

		private StepIds() {
		}
	}
}