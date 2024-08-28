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
public class RuntimeModule10Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);
	

	private static final String CHECK_SSL_1 =  "grep -i \"Configuring Tomcat for SSL\" /eniq/log/sw_log/platform_installer/runtime_*.log 2>/dev/null";
	private static final String CHECK_SSL_2 =  "grep -i \"Tomcat is configured for xml\\|Tomcat is already configured for xml\" /eniq/log/sw_log/platform_installer/runtime_*.log 2>/dev/null";
	
	@Inject
	private Provider<LoadingOperator> provider;
	
	/**
	 * @DESCRIPTION This test case verifies whether SSL is configured as part of tomcat installation.
	 * @PRE 
	 */
	@TestStep(id = StepIds.VERIFY_RUNTIME_MODULE_10)
	public void verifyRuntimeModule() {
		// get operators from providers
		final LoadingOperator loadingOperator = provider.get();
		
		assertFalse(loadingOperator.executeCommand(CHECK_SSL_1).isEmpty(), "tomcat ssl configuration not found in runtime logs");
		assertFalse(loadingOperator.executeCommand(CHECK_SSL_1).isEmpty(), "tomcat ssl configuration not found in runtime logs");
	}

	public static class StepIds {
		public static final String VERIFY_RUNTIME_MODULE_10 = "To verify whether SSL is configured as part of tomcat installation";

		private StepIds() {
		}
	}
}