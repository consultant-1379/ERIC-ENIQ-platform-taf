package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.LoadingOperator;

/**
 * @author ZJSOLEA
 */
public class RuntimeModule06Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);

	private static final String CHECK_JDK = "ls /eniq/sw/runtime 2>/dev/null | grep \"^jdk$\"";
	private static final String CHECK_ANT = "ls /eniq/sw/runtime 2>/dev/null | grep \"^ant$\"";
	private static final String CHECK_JAVA = "ls /eniq/sw/runtime 2>/dev/null | grep \"^java$\"";
	private static final String CHECK_TOMCAT = "ls /eniq/sw/runtime 2>/dev/null | grep \"^tomcat$\"";
	private static final String CHECK_APACHE_ANT = "ls /eniq/sw/runtime 2>/dev/null | grep \"^apache-ant\"";
	private static final String CHECK_APACHE_TOMCAT = "ls /eniq/sw/runtime 2>/dev/null | grep \"^apache-tomcat-\"";
	private static final String CHECK_JDK1 = "ls /eniq/sw/runtime 2>/dev/null | grep \"^jdk1\"";
	private static final String CHECK_SSL = "ls /eniq/sw/runtime 2>/dev/null | grep \"^ssl$\"";

	@Inject
	private Provider<LoadingOperator> provider;

	/**
	 * @DESCRIPTION This test case checks the runtime package is extracted under
	 *              /eniq/sw/runtime.
	 * @PRE
	 */
	@TestStep(id = StepIds.VERIFY_RUNTIME_MODULE_06)
	public void verifyRuntimeModule() {
		// get operators from providers
		final LoadingOperator loadingOperator = provider.get();

		assertFalse(loadingOperator.executeCommand(CHECK_JAVA).isEmpty(), "java not found under runtime");
		assertFalse(loadingOperator.executeCommand(CHECK_JDK).isEmpty(), "jdk not found under runtime");
		assertFalse(loadingOperator.executeCommand(CHECK_ANT).isEmpty(), "ant not found under runtime");
		assertFalse(loadingOperator.executeCommand(CHECK_TOMCAT).isEmpty(), "tomcat not found under runtime");
		assertFalse(loadingOperator.executeCommand(CHECK_APACHE_ANT).isEmpty(), "apache-ant not found under runtime");
		assertFalse(loadingOperator.executeCommand(CHECK_APACHE_TOMCAT).isEmpty(),
				"apache-tomcat not found under runtime");
		assertFalse(loadingOperator.executeCommand(CHECK_JDK1).isEmpty(), "jdk1 not found under runtime");

		// String apacheVersion = loadingOperator.executeCommand("cd
		// /eniq/sw/runtime/;ls | grep apache-tomcat").trim();
		String output2 = loadingOperator.executeCommand("ls /eniq/sw/runtime/tomcat | grep ssl");

		assertTrue(output2.contains("ssl"), "ssl not found under /eniq/sw/runtime/" + output2);
	}

	public static class StepIds {
		public static final String VERIFY_RUNTIME_MODULE_06 = "To Check the runtime package is extracted under /eniq/sw/runtime";

		private StepIds() {
		}
	}
}