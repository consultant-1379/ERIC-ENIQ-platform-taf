package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;


import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.RuntimeOperator;

/**
 * @author ZJSOLEA
 */
public class RuntimeModule04Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);

	private static final String CHECK_JDK_VERSION =  "ls /eniq/sw/runtime -l | grep \"jdk -> jdk1.8.0_281\"";
	private static final String CHECK_JAVA_VERSION =  "ls /eniq/sw/runtime -l | grep \"java -> jdk1.8.0_281\"";
	private static final String CHECK_TOMCAT_VERSION =  "ls /eniq/sw/runtime -l | grep \"tomcat -> /eniq/sw/runtime/apache-tomcat-8.5.61\"";
	
	@Inject
	private Provider<RuntimeOperator> provider;
	
	/**
	 * @DESCRIPTION This test case covers runtime module java and tomcat version check.
	 * @PRE 
	 */
	@TestStep(id = StepIds.VERIFY_RUNTIME_MODULE_04)
	public void verifyRuntimeModule() {
		// get operators from providers
		final RuntimeOperator runtimeOperator = provider.get();
		
		String output1 = runtimeOperator.executeCommand(CHECK_JAVA_VERSION);
		String output2 = runtimeOperator.executeCommand(CHECK_TOMCAT_VERSION);
		String output3 = runtimeOperator.executeCommand(CHECK_JDK_VERSION);
		
		assertFalse(output1.isEmpty(), "java version is not the latest");
		assertFalse(output2.isEmpty(), "tomcat version is not the latest");
		assertFalse(output3.isEmpty(), "jdk version is not the latest");
		
	}

	public static class StepIds {
		public static final String VERIFY_RUNTIME_MODULE_04 = "To verify runtime module java and tomact versions";

		private StepIds() {
		}
	}
}