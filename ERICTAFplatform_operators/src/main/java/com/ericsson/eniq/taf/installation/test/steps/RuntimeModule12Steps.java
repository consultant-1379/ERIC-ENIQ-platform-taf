package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.LoadingOperator;

/**
 * @author ZJSOLEA
 */
public class RuntimeModule12Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);
	

	private static final String CHECK_JAVA_1 =  "grep -i \"Java is installed.\" /eniq/log/sw_log/platform_installer/runtime_*.log 2>/dev/null";
	private static final String CHECK_JAVA_2 =  "grep -i \"Setting exec permissions to Java.\" /eniq/log/sw_log/platform_installer/runtime_*.log 2>/dev/null";
	private static final String CHECK_JAVA_3 =  "grep -i \"Exec permissions to Java set.\" /eniq/log/sw_log/platform_installer/runtime_*.log 2>/dev/null";	
	@Inject
	private Provider<LoadingOperator> provider;
	
	/**
	 * @DESCRIPTION This test case verifies whether JAVA execute permissions are provided without any issues.
	 * @PRE 
	 */
	@TestStep(id = StepIds.VERIFY_RUNTIME_MODULE_12)
	public void verifyRuntimeModule() {
		// get operators from providers
		final LoadingOperator loadingOperator = provider.get();
		
		assertFalse(loadingOperator.executeCommand(CHECK_JAVA_1).isEmpty(), "Unable to verify JAVA execute permissions from runtime logs");
		assertFalse(loadingOperator.executeCommand(CHECK_JAVA_2).isEmpty(), "Unable to verify JAVA execute permissions from runtime logs");
		assertFalse(loadingOperator.executeCommand(CHECK_JAVA_3).isEmpty(), "Unable to verify JAVA execute permissions from runtime logs");
	}

	public static class StepIds {
		public static final String VERIFY_RUNTIME_MODULE_12 = "To Verify JAVA execute permissions are provided without any issues.";

		private StepIds() {
		}
	}
}