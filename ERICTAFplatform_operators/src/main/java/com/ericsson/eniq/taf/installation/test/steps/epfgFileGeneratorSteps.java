package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class epfgFileGeneratorSteps {

	private static Logger logger = LoggerFactory.getLogger(epfgFileGeneratorSteps.class);

	private final String RELEASE = DataHandler.getAttribute("platform.release.version").toString();
	private final String FDM = DataHandler.getAttribute("platform.fdm.version").toString();
	private final String SHIPMENT = DataHandler.getAttribute("platform.shipment.version").toString();
	private final String JENKINS_PASSWORD = DataHandler.getAttribute("platform.epgf.jenkins.pass").toString();
	private final String JENKINS_USER = DataHandler.getAttribute("platform.epgf.jenkins.user").toString();
	
	private final String CD = "cd";
	private final String SPACE = " ";
	private final String SEMI_CO = ";";
	private final String EPFG_CONFIG_PATH = "/eniq/home/dcuser/epfg/config/";
	private final String EPFG_PATH = "/eniq/home/dcuser/epfg/";
	private final String GREP = "grep -i";
	private final String GENFLAG = "GenFlag=";
	private final String EPFG_PROPERTIES = "epfg.properties";
	private final String SED_CMD = "sed -i";
	private final String START_TIME = "08-07-2019-10:00";
	private final String END_TIME = "08-07-2019-10:15";
	
	private final String EPFG_URL = "cat /eniq/home/dcuser/getEpfgVersion.txt |  grep -o -P '(?<=href=\\\").*(?=\\\">)'\""+'"';
	//private final String BASE_URL = "https://clearcase-eniq.seli.wh.rnd.internal.ericsson.com/eniqdel/";
    //private final String IP_URL = "sed -i 's/clearcase-eniq.seli.wh.rnd.internal.ericsson.com/https://clearcase-eniq.seli.wh.rnd.internal.ericsson.com/g' /eniq/home/dcuser/getEpfgVersion.txt";
	
	
	private final String EPFG_VERSION = "cat /eniq/home/dcuser/getEpfgVersion.txt | grep -o -P '(?<=FDM/).*(?=\\\\\\\">)'";
	private final String DCUSER_PATH = "/eniq/home/dcuser/";
	private final String PRE_CONFIG_FT_SCRIPT = "./epfg_preconfig_for_ft.sh";
	private final String CAT = "cat";
	private final String GenFlagYES = "epfg/config/epfg.properties| grep -i 'genflag=YES'";
	private final String epfgPropertiesFile = "chmod 777 /eniq/home/dcuser/epfg/config/epfg.properties";
	
	private final String BASE_URL = "http://10.120.176.102/eniqdel/";
	private final String IP_URL = "sed -i 's/clearcase-eniq.seli.wh.rnd.internal.ericsson.com/10.120.176.102/' getEpfgVersion.txt";
	private final String IP_URL_MODIFIED="sed -i 's/https/http/' getEpfgVersion.txt";
	
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws ConfigurationException
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.EPFG_TOOL)
	public void epfgFileGenerator() throws ConfigurationException, FileNotFoundException, InterruptedException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		generalOperator.executeCommandDcuser("/eniq/home/dcuser/epfg/stop_epfg.sh");
		logger.info("\nDownloading EPFG Tool...!");
		String getEpfgToolLink = generalOperator
				.executeCommandDcuser("/usr/bin/wget --no-check-certificate "+ " -O - " + BASE_URL + RELEASE
						+ "/" + FDM + "/SOLARIS_baseline.html"
						+ "| grep epfg_ft | grep '.zip' > /eniq/home/dcuser/getEpfgVersion.txt");

		String ipURL = generalOperator.executeCommandDcuser(IP_URL);
		String ipURL_Modified = generalOperator.executeCommandDcuser(IP_URL_MODIFIED);//changing https to http
		String epfgTooolFinalURL = generalOperator.executeCommandDcuserDiff(EPFG_URL);

		logger.info("\nFinal EPFG Tool URL : " + epfgTooolFinalURL);

		String downloadedEpfgTool = generalOperator.executeCommandDcuser(CD + SPACE + DCUSER_PATH + ";"
				+ "/usr/bin/wget --no-check-certificate -N " + epfgTooolFinalURL);

		logger.info("\nDownloaded EPFG Tool : " + downloadedEpfgTool);

		if (downloadedEpfgTool.contains(".zip' saved")) {
			String epfgVersion = generalOperator.executeCommandDcuserDiff(EPFG_VERSION);
			String unZipEpfgVersion = generalOperator.executeCommandDcuserDiff(CD + SPACE + DCUSER_PATH + ";"
					+ "unzip -o " + epfgVersion + " | tail -1 | cut -d " + "' '" + " -f4");
			if (unZipEpfgVersion.contains(".zip")) {
				String unZipEpfgVersionAgain = generalOperator
						.executeCommandDcuser(CD + SPACE + DCUSER_PATH + ";" + "unzip -o " + unZipEpfgVersion);
				logger.info("\n Extracted EPFG Tool " + unZipEpfgVersion);

				String chmod = generalOperator
						.executeCommandDcuser(CD + SPACE + DCUSER_PATH + ";" + "/usr/bin/chmod -R 777 epfg");
				logger.info("Running Pre Config for EPFG to change the properties file....");

				String preConfig = generalOperator.executeCommand(CD + SPACE + EPFG_PATH + ";" + PRE_CONFIG_FT_SCRIPT);

				String permissionForEpfgPerperties = generalOperator.executeCommand(epfgPropertiesFile);
				logger.info("\nupdated access permisson to EPFG Properties file" + permissionForEpfgPerperties);

				String pythonscr = FileFinder.findFile("NodeTypes.txt").get(0);
				logger.info("File : " + pythonscr);

				Scanner scan = new Scanner(new File(pythonscr));
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					String genFlagYES = generalOperator
							.executeCommandDcuserDiff(CD + SPACE + EPFG_CONFIG_PATH + SEMI_CO + SED_CMD + SPACE + "'/"
									+ "^" + line + GENFLAG + "/ s/=.*/=YES/'" + SPACE + EPFG_PROPERTIES);

					// String startTime = generalOperator
					// .executeCommandDcuser(CD + SPACE + EPFG_CONFIG_PATH +
					// SEMI_CO + SED_CMD + SPACE + "'/" + line
					// + "StartTime=" + "/ s/=.*/" + START_TIME + "/'" + SPACE +
					// EPFG_PROPERTIES);

					// String endTime = generalOperator
					// .executeCommandDcuser(CD + SPACE + EPFG_CONFIG_PATH +
					// SEMI_CO + SED_CMD + SPACE + "'/" + line
					// + "EndTime=" + "/ s/=.*/" + END_TIME + "/'" + SPACE +
					// EPFG_PROPERTIES);
				}

				logger.info("\nModified GenFlag as YES for below NodeTypes: \n"
						+ generalOperator.executeCommandDcuser(CAT + SPACE + DCUSER_PATH + GenFlagYES));

				if (preConfig.contains("EPFG properties file is Successfully updated")) {

					logger.info("\nOwnership change root to dcuser for : /eniq/data/pmdata/tmp  folder\n"
							+ generalOperator.executeCommand("chown -R dcuser:dc5000 /eniq/data/pmdata/tmp"));

					String epfgToolOutput = generalOperator
							.executeCommandDcuser(CD + SPACE + EPFG_PATH + ";" + EPFG_PATH + "start_epfg.sh");
					logger.info("EPFG Files Generated " + epfgToolOutput);
					TimeUnit.SECONDS.sleep(60);

					generalOperator.executeCommandDcuser("rm -fr *.zip");
					logger.info("Removed zip files from " + DCUSER_PATH);

					assertTrue(true);
				} else {
					logger.info("EPFG properties file is NOT updated");
					assertTrue(false, "Failed due to : " + preConfig);
				}

			} else {
				logger.info("Unable to extract the EPFG Tool" + unZipEpfgVersion);
				assertTrue(false, "Failed due to : " + unZipEpfgVersion);
			}

		} else {
			logger.info("\nError while downloading EPFG Tool..! ");
			assertTrue(false, "Failed due to : " + downloadedEpfgTool);
		}

	}

	@TestStep(id = StepIds.CLOSE_FIREFOX_PROCESS)
	public void closeFirefoxProcess() throws InterruptedException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		logger.info("\nKilling all Firefox browsers process...!\n");
		String result = generalOperator.closeFirefoxProcess("ps -aux | grep firefox");
		String result2 = generalOperator.closeFirefoxProcess("killall firefox");
		assertTrue(true, "\nFailed to kill the firefox browsers \n" + result2);
	}

	public static class StepIds {
		public static final String EPFG_TOOL = "epfg File Generator";
		public static final String CLOSE_FIREFOX_PROCESS = "Firefox Browser Close";

		private StepIds() {
		}
	}
}
