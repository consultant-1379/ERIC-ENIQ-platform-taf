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
public class RuntimeModule15Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);
	

	private static final String CHECK_SERVER_STATUS =  "sudo systemctl status eniq-webserver.service 2>/dev/null | grep \"Active: active (running)\"";

	@Inject
	private Provider<LoadingOperator> provider;
	
	/**
	 * @DESCRIPTION This test case verifies whether JAVA execute permissions are provided without any issues.
	 * @PRE 
	 */
	@TestStep(id = StepIds.VERIFY_RUNTIME_MODULE_15)
	public void verifyRuntimeModule() {
		// get operators from providers
		final LoadingOperator loadingOperator = provider.get();
		
		assertFalse(loadingOperator.executeCommand(CHECK_SERVER_STATUS).isEmpty(), "Web server status is not active and running");
	}

	public static class StepIds {
		public static final String VERIFY_RUNTIME_MODULE_15 = "To Verify the web server status.";

		private StepIds() {
		}
	}
}