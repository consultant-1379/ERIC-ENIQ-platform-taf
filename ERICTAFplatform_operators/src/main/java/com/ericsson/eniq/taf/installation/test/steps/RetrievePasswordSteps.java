package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.ChangePasswordOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class RetrievePasswordSteps {
	private static Logger logger = LoggerFactory.getLogger(RetrievePasswordSteps.class);

	@Inject
	private Provider<ChangePasswordOperator> provider;

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify the script to retrieve password of the user
	 *              dcpublic,dwhrep,etlrep,dba
	 */
	@TestStep(id = StepIds.VERIFY_PASSWORD_RETRIEVE)
	public void verify(@Input("userslist") String user) throws InterruptedException {

		final ChangePasswordOperator operator = provider.get();
		operator.retrievePassword(user);

	}

	public static class StepIds {
		public static final String VERIFY_PASSWORD_RETRIEVE = "Verify the script to retrieve password of the user dcpublic,dwhrep,etlrep,dba";

		private StepIds() {
		}
	}

}
