package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.VerifyEWMOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPlatformInstallationOperator;

/**
 * 
 * @author ZRUMRMU
 *
 */
public class VerifyEWMTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyEWMTestSteps.class);

	@Inject
	private Provider<VerifyEWMOperator> provider;

	@Inject
	private Provider<VerifyPlatformInstallationOperator> pltFormProvider;

	VerifyEWMOperator dvOperator;
	VerifyPlatformInstallationOperator pltformOperator;

	private final String ENIQ_SW_BIN = DataHandler.getAttribute("platform.install.lm.dir").toString();
	private final String ENIQ_SW_PLATFORM = DataHandler.getAttribute("platform.install.moduleExtractionDir").toString();
	private final String LS = "ls";
	private final String SPACE = " ";
	private final String PIPE = "|";
	private final String GREP = "grep -i ";
	private final String FIND = "find";
	private final String ENIQ_WEB_APPD_DIR = "/eniq/sw/runtime/tomcat/webapps/";

	/**
	 * initialise
	 */
	@TestStep(id = StepIds.CLI_INITIALIZE)
	public void cliInitialize() {
		dvOperator = provider.get();
		pltformOperator = pltFormProvider.get();
		// cvOperator.initialise();
		// pltformOperator.initialise();
		assertTrue(true);
	}

	/**
	 * VERIFY_LM_LOG
	 */

	@TestStep(id = StepIds.VERIFY_SCRIPT_INI_VERIFICATION)
	public void verifyINIExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listSunosiniFiles()) {
			if (f.startsWith("Sun") && f.endsWith(".ini")) {
				fileName = f;
				break;
			}

		}
		System.out.println("fileName" + fileName);
		LOGGER.info(moduleName + "- Verify Ini File contents" + fileName);
		String fileInfo = pltformOperator.getIniFileContent(fileName);

		assertFalse(fileInfo.length() > 0, "Ini file for module " + moduleName + "has PWDData Entry " + fileInfo);

	}

	@TestStep(id = StepIds.VERIFY_SCRIPT_WIFIDATA_VERIFICATION)
	public void verifyWifiDataExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listpropetiesFiles()) {
			if (f.startsWith("static") && f.endsWith(".properties")) {
				fileName = f;
				break;
			}

		}
		System.out.println("fileName" + fileName);
		LOGGER.info(moduleName + "- Verify static properties File contents" + fileName);
		String fileInfo = pltformOperator.getPropertyFileContent(fileName);

		assertFalse(fileInfo.length() > 0,
				"Properties file for module " + moduleName + "has WIFI Data Entry " + fileInfo);

	}

	@TestStep(id = StepIds.VERIFY_SCRIPT_WIFIDATAPARSER_VERIFICATION)
	public void verifyWifiParserDataExecution(@Input(Parameters.PLATFORM_MODULE) String moduleName) {
		String fileName = null;
		for (final String f : pltformOperator.listdbpropetiesFiles()) {
			if (f.startsWith("version") && f.endsWith(".properties")) {
				fileName = f;
				break;
			}

		}
		System.out.println("fileName" + fileName);
		LOGGER.info(moduleName + "- Verify versiondb properties File contents" + fileName);
		String fileInfo = pltformOperator.getDbPropertyFileContent(fileName);

		assertFalse(fileInfo.length() > 0,
				"Version DBProperties file for module " + moduleName + "has WIFI Parser Data Entry " + fileInfo);

	}

	@TestStep(id = StepIds.WIFI_DATA_ABSENCE)
	public void verifyWifiDataAbsence() {
		final VerifyEWMOperator ewmOperator = provider.get();
		String output = ewmOperator.executeCommand(
				LS + SPACE + ENIQ_SW_BIN + SPACE + PIPE + SPACE + GREP + SPACE + "addWifiIncrontab.bsh");
		assertTrue(output.isEmpty(), "Failed due to 'addWifiIncrontab.bsh' file still exists in " + ENIQ_SW_BIN);

	}

	@TestStep(id = StepIds.EWM_WIFI_INVERTORY)
	public void verifyWifiInvertory() {
		final VerifyEWMOperator ewmOperator = provider.get();
		String commonPkgDir = ewmOperator
				.executeCommand(LS + SPACE + ENIQ_SW_PLATFORM + SPACE + PIPE + GREP + SPACE + "common");
		String output = ewmOperator
				.executeCommand(FIND + SPACE + ENIQ_SW_PLATFORM + "/" + commonPkgDir + " -name " + "wifiinventory.vm");
		assertTrue(output.isEmpty(),
				"Failed due to 'wifiinventory.vm' file still exists in " + ENIQ_SW_PLATFORM + "/" + commonPkgDir);

	}

	@TestStep(id = StepIds.EXMAPLE_DIR_VERIFICATION)
	public void verifyExampleDir() {
		final VerifyEWMOperator ewmOperator = provider.get();
		String output = ewmOperator
				.executeCommand(LS + SPACE + ENIQ_WEB_APPD_DIR + SPACE + PIPE + GREP + SPACE + "example");
		assertTrue(output.isEmpty(), "Failed due to 'examples' folder still exists under " + ENIQ_WEB_APPD_DIR);

	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class StepIds {
		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_SCRIPT_INI_VERIFICATION = "Verify Ini File Verification";
		public static final String VERIFY_SCRIPT_WIFIDATA_VERIFICATION = "Verify WIFIDATA in Static.Properties File Verification";
		public static final String VERIFY_SCRIPT_WIFIDATAPARSER_VERIFICATION = "Verify WIFIDATA in VersionDb.Properties File Verification";
		public static final String WIFI_DATA_ABSENCE = "Wifi related things need to be removed from /eniq/sw/bin";
		public static final String EWM_WIFI_INVERTORY = "Wifi.vm and wifiInvertory.vm does not exists in common module";
		public static final String EXMAPLE_DIR_VERIFICATION = "verify whether 'example' dir is removed from '/eniq/sw/runtime/tomcat/webapps'";

		private StepIds() {
		}
	}

	public static class Parameters {
		public static final String PLATFORM_MODULE = "module";

		private Parameters() {
		}
	}

	private String getRstate(String moduleName) {
		return getModuleRstates().get(moduleName) == null ? "" : getModuleRstates().get(moduleName);

	}

}
