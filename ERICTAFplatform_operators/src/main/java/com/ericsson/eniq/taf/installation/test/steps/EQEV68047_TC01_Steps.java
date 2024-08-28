package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;


/**
 * @author ZJSOLEA
 */
public class EQEV68047_TC01_Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV68047_TC01_Steps.class);

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION Verify the script to retrieve password of the user dc
	 */
	@TestStep(id = StepIds.EQEV68047_TC01_STEP_01)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		logger.info("fetching password using getPassword.bsh");
		String output = operator.executeCommandDcuser("cd /eniq/sw/installer/; ./getPassword.bsh -u dc | grep \"DC password: \"");
		assertTrue(!output.isEmpty(), "Unable to retrieve password for user dc");
		logger.info("Password retrieved successfully");

		return;
	}

	public static class StepIds {
		public static final String EQEV68047_TC01_STEP_01 = "Verify the script to retrieve password of the user dc";

		private StepIds() {
		}
	}
}