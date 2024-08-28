package com.ericsson.eniq.taf.installation.test.steps;


import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.AdminUIOperator;

/**
 * @author ZJSOLEA
 */
public class AdminUI_4_Steps {

	private static Logger logger = LoggerFactory.getLogger(AdminUI_4_Steps.class);

	@Inject
	private Provider<AdminUIOperator> provider;
	
	/**
	 * @DESCRIPTION Launching of adminui of same server in one more browser
	 */
	@TestStep(id = StepIds.ADMINUI_4_STEP_01)
	public void verify() {
		// get operators from providers
		final AdminUIOperator operator = provider.get();
		
		operator.loginAdminUI();
		operator.verifyLoginFromSecondBrowser();
		operator.logoutAdminUI();

		return;
	}

	public static class StepIds {
		public static final String ADMINUI_4_STEP_01 = "Launching of adminui of same server in one more browser";

		private StepIds() {
		}
	}
}