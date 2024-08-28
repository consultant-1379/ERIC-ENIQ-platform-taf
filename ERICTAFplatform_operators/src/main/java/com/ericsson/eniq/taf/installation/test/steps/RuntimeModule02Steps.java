package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.RuntimeOperator;

/**
 * @author ZJSOLEA
 */
public class RuntimeModule02Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);

	private static final String LOGFILE_PATH = "/eniq/log/sw_log/platform_installer/runtime*.log";
	private static final String CHECK_LOGFILE = "cat " + LOGFILE_PATH + " | grep -i \"error\\|exception\\|warning\" | grep -v \"\\.html$\\|\\.exsd$\"";
	
	@Inject
	private Provider<RuntimeOperator> provider;
	
	/**
	 * @DESCRIPTION This test case covers verification runtime module installation logs.
	 * @PRE 
	 */
	@TestStep(id = StepIds.VERIFY_RUNTIME_MODULE_02)
	public void verifyRuntimeModule() {
		// get operators from providers
		final RuntimeOperator runtimeOperator = provider.get();
		
		String[] output = runtimeOperator.executeCommand(CHECK_LOGFILE).split("\n");

		assertEquals(output.length, 1, "Error or warning or exception found in runtime logs");
		assertEquals(output[0], "", "Error or warning or exception found in runtime logs");
	}

	public static class StepIds {
		public static final String VERIFY_RUNTIME_MODULE_02 = "To verify runtime module installation logs";

		private StepIds() {
		}
	}
}