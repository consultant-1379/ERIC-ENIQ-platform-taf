package com.ericsson.eniq.taf.installation.test.steps;

import java.io.FileNotFoundException;

import javax.inject.Inject;
import javax.inject.Provider;
import static org.testng.Assert.assertTrue;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class TeckPackIdeSteps {

	private static Logger logger = LoggerFactory.getLogger(TeckPackIdeSteps.class);

	private final String RELEASE = DataHandler.getAttribute("platform.release.version").toString();
	private final String FDM = DataHandler.getAttribute("platform.fdm.version").toString();
	private final String SHIPMENT = DataHandler.getAttribute("platform.shipment.version").toString();

	private final String CD = "cd";
	private final String SPACE = " ";
	private final String JNLP_FILE_NAME = "techpackide.jnlp";
	private final String TMP_PATH = "/tmp";
	private final String SEMI_CO = ";";
	private final String LS = "ls";
	private final String EPFG_CONFIG_PATH = "/eniq/home/dcuser/epfg/config/";
	private final String EPFG_PATH = "/eniq/home/dcuser/epfg/";
	private final String GREP = "grep -i";
	private final String GENFLAG = "GenFlag=";
	private final String EPFG_PROPERTIES = "epfg.properties";
	private final String SED_CMD = "sed -i";
	private final String START_TIME = "08-07-2019-10:00";
	private final String END_TIME = "08-07-2019-10:15";
	// private final String EPFG_URL = "cat /eniq/home/dcuser/getEpfgVersion.txt
	// | grep -o -P '(?<=href=\").*(?=\">epfg)'";
	private final String EPFG_URL = "cat /eniq/home/dcuser/getEpfgVersion.txt |  grep -o -P '(?<=href=\\\").*(?=\\\">epfg)'";
	private final String BASE_URL = "http://150.132.181.253/view/www_eniq/vobs/ossrc/del-mgt/html/eniqdel/";
	private final String IP_URL = "sed -i 's/clearcase-oss.lmera.ericsson.se/150.132.181.253/g' /eniq/home/dcuser/getEpfgVersion.txt";
	private final String EPFG_VERSION = "cat /eniq/home/dcuser/getEpfgVersion.txt | grep -o -P '(?<=.FDM/).*(?=\\\">epfg)'";
	private final String DCUSER_PATH = "/eniq/home/dcuser/";
	private final String PRE_CONFIG_FT_SCRIPT = "./epfg_preconfig_for_ft.sh";
	private final String CAT = "cat";
	private final String GenFlagYES = "epfg/config/epfg.properties| grep -i genflag'YES'";
	private final String epfgPropertiesFile = "chmod 777 /eniq/home/dcuser/epfg/config/epfg.properties";

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws ConfigurationException
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.TECKPACK_IDE_JNLP_FILE)
	public void epfgFileGenerator() throws ConfigurationException, FileNotFoundException, InterruptedException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		String url = generalOperator.getWebAppUrl();
		String output = generalOperator
				.executeCommand(CD + SPACE + TMP_PATH + SEMI_CO + " wget -N " + SPACE + url + JNLP_FILE_NAME);
		logger.info("\nDownloading " + JNLP_FILE_NAME + " File\n" + output);
		String ListJnlpFile = generalOperator
				.executeCommand(CD + SPACE + TMP_PATH + SEMI_CO + LS + SPACE + JNLP_FILE_NAME);

		logger.info("\nJNLP File  " + ListJnlpFile);
		String removeJnlpFile = generalOperator
				.executeCommand(CD + SPACE + TMP_PATH + SEMI_CO + "rm -fr " + SPACE + JNLP_FILE_NAME);
		logger.info("\nRemoved downloaded " + JNLP_FILE_NAME + "File");
		assertTrue(ListJnlpFile.equals(JNLP_FILE_NAME), "Unble to download the " + JNLP_FILE_NAME + "File");
	}

	public static class StepIds {
		public static final String TECKPACK_IDE_JNLP_FILE = "Verify .jnlp file downloads";

		private StepIds() {
		}
	}
}
