package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class MWSpathUpdateSteps {

	private static Logger logger = LoggerFactory.getLogger(MWSpathUpdateSteps.class);

	private static final String FINAL_MWS_FeaturePath = "shipment=`cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-`;release=`echo $shipment | cut -d\".\" -f1`;num=`echo $shipment | cut -d\".\" -f2`;numToAlpha=( ['1']='A.1' ['2']='B' ['3']='B.1' ['4']='C');featurePath=`echo \"Features_$release${numToAlpha[$num]}_$shipment\"`;echo $featurePath";
	//private static final String FINAL_MWS_FeaturePath = "ls /net/10.45.192.153/JUMP/ENIQ_STATS/ENIQ_STATS/  | grep _Linux | grep Features_ |tail -2 | head -1";
	private static final String MWS_COMMON_PATH = "/net/10.45.192.153/JUMP/ENIQ_STATS/ENIQ_STATS/";
	private static final String ENIQ_PARSERS = "/eniq_parsers/";

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws ConfigurationException
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.UPDATE_MWS_PATH)
	public void verifyErrorLog() throws ConfigurationException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		
		//added the code for creation of JCAT_Log directory for PF_TAF_DAILY job
		final String JcatPath="/home/lciadm100/jenkins/workspace/PF_TAF_DAILY/target/com.ericsson.eniq.stats.taf.test-pom-platform/target/Jcat_LOGS/";
		logger.info(generalOperator.executeCommand("mkdir -p " + JcatPath));
		
		//added the code for creation of JCAT_Log directory for PF_KGB_TAF_TESTCASES job
		final String JcatPath_kgb="/home/lciadm100/jenkins/workspace/PF_KGB_TAF_TESTCASES/target/com.ericsson.eniq.stats.taf.test-pom-platform/target/Jcat_LOGS/";
		logger.info(generalOperator.executeCommand("mkdir -p " + JcatPath_kgb));
		

		String mwsFeaturesPath = generalOperator.findMWSfeaturesPathCommand(FINAL_MWS_FeaturePath);
		final String mws_eniq_featuresPath = MWS_COMMON_PATH + mwsFeaturesPath + ENIQ_PARSERS;
		logger.info("\nvApp's MWS Features path : " + mws_eniq_featuresPath);
		logger.info("\nList of eniq_parsers packages under : " + mws_eniq_featuresPath + "\n");
		final String createTextFileForListOfMWS_eniq_featuresPath = generalOperator
				.findMWSfeaturesPathCommand("ls " + mws_eniq_featuresPath + "  |  awk '{print $1}' > /eniq/home/dcuser/latest_eniqs_parser_packages.txt" );

		
		final String listOfMWS_eniq_featuresPath = generalOperator
				.findMWSfeaturesPathCommand("cat " + "/eniq/home/dcuser/latest_eniqs_parser_packages.txt");

		if (mws_eniq_featuresPath.contains("eniq_parsers")) {

			PropertiesConfiguration update = new PropertiesConfiguration(
					FileFinder.findFile("platform.properties").get(0));
			update.setProperty("platform.parser.mws.path", mws_eniq_featuresPath);
			update.save();
			logger.info("\nMWS Features path used in TAF properties file : " + mws_eniq_featuresPath);
			assertTrue(true);
		} else {
			assertTrue(false, "MWS Path not populated properly : " + mws_eniq_featuresPath);
		}
	}

	public static class StepIds {
		public static final String UPDATE_MWS_PATH = "Update MWS Path";

		private StepIds() {
		}
	}
}
