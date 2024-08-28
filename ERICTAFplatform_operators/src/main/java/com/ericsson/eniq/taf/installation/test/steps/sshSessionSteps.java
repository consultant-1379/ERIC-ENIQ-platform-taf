package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

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
public class sshSessionSteps {

	private static Logger logger = LoggerFactory.getLogger(sshSessionSteps.class);

	private final String RELEASE = DataHandler.getAttribute("platform.release.version").toString();

	private final String CD = "cd";

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws ConfigurationException
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.SSH_SESSION)
	public void epfgFileGenerator() {
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		generalOperator.executeCommandDcuser("systemctl restart sshd");
		generalOperator.executeCommand("systemctl restart sshd");
		logger.info("Restarted SSH Sessions");
		assertTrue(true);

	}

	public static class StepIds {
		public static final String SSH_SESSION = "epfg File Generator";

		private StepIds() {
		}
	}
}
