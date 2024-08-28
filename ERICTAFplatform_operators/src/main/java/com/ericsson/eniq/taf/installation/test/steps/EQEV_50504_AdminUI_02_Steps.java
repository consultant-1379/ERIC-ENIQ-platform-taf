package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.AdminUIOperator;

/**
 * @author ZJSOLEA
 */
public class EQEV_50504_AdminUI_02_Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV_50504_AdminUI_02_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUIOperator> provider;
	
	/**
	 * @DESCRIPTION Verify session logs not displaying error "Row count exceeded $toomany -rows"
	 */
	@TestStep(id = StepIds.EQEV_50504_AdminUI_02_STEP_01)
	public void verify() {
		// get operators from providers
		final AdminUIOperator operator = provider.get();
		
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();


		operator.loginWithNewUser(username, password);
		try {
			operator.verifySessionLogs();
		} finally {
			operator.logoutAdminUI();
		}

		return;
	}

	public static class StepIds {
		public static final String EQEV_50504_AdminUI_02_STEP_01 = " Verify session logs not displaying error 'Row count exceeded $toomany -rows'";

		private StepIds() {
		}
	}
}