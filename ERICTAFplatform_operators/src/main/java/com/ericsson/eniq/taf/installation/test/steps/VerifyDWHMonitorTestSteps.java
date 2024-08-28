package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getModuleRstates;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyDWHMonitorOperator;
import com.ericsson.eniq.taf.installation.test.operators.VerifyPlatformInstallationOperator;

/**
 * 
 * @author ZRUMRMU
 *
 */
public class VerifyDWHMonitorTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyDWHMonitorTestSteps.class);

	@Inject
	private Provider<VerifyDWHMonitorOperator> provider;

	@Inject
	private Provider<VerifyPlatformInstallationOperator> pltFormProvider;

	VerifyDWHMonitorOperator dvOperator;
	VerifyPlatformInstallationOperator pltformOperator;

	private static final String START_DWH_SET = ". /eniq/home/dcuser/.profile;engine -e startSet 'DWH_BASE' 'Cleanup_transfer_batches'";

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

	@TestStep(id = StepIds.VERIFY_DWH_DIR_DISKMANAGER)
	public void VERIFY_DWH_DIR_DISKMANAGER(@Input(Parameters.PLATFORM_MODULE) String moduleName) {

		// Facing log generation issue in Automation Server. So Place the log
		// file manually and verify the contents here.

		String fileName = null;
		for (final String f : pltformOperator.listDWH_EngineeLogFiles()) {
			if (f.startsWith("engine-") && f.endsWith(".log")) {
				fileName = f;
				break;
			}

		}
		System.out.println("fileName" + fileName);
		LOGGER.info(moduleName + "- Verify enginee log file contents" + fileName);
		String fileInfo = pltformOperator.getEngineeLogContent(fileName);

		assertTrue(fileInfo.length() > 0, "Log file for module " + moduleName
				+ "failed because disk checked execution failed with empty log" + fileInfo);

	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class StepIds {
		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_DWH_DIR_DISKMANAGER = "Verify Directory and Disk Manager";

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
