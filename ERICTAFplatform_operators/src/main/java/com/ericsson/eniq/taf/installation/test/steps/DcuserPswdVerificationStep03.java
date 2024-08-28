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
public class DcuserPswdVerificationStep03 {

	private static Logger logger = LoggerFactory.getLogger(DcuserPswdVerificationStep03.class);
	private static final String CURRENT_PASSWORD = "Q!@#$%^&*(){}\"_+-=:;";
	private static final String CHANGE_DCUSER_PASSWORD = ". /eniq/home/dcuser/.profile; echo -e '\"'\"'" + CURRENT_PASSWORD + "\\nQ!@#$%^&*(){}\"_+-=:;\\nQ!@#$%^&*(){}\"_+-=:;'\"'\"' | passwd";

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION Verify by providing the pwd length less than 8 and greater than 30 characters
	 */
	@TestStep(id = StepIds.DCUSER_PSWD_VERIFICATION_STEP_03)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
	
		logger.info("Changing password");
		String output = operator.executeCommandDcuser(CHANGE_DCUSER_PASSWORD);
		assertTrue(output.contains("The new password length should not be less than 8 characters."), "Error - the script should not accept passwords less than 8 characters\n" + output);
		
		return;
	}

	public static class StepIds {
		public static final String DCUSER_PSWD_VERIFICATION_STEP_03="Verify by providing the pwd length less than 8 and greater than 30 characters";

		private StepIds() {
		}
	}
}