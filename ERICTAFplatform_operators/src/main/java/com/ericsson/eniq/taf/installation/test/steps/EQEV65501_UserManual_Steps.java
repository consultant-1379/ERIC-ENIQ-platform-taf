package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.AdminUIOperator;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;

/**
 * @author ZJSOLEA
 */
public class EQEV65501_UserManual_Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV65501_UserManual_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUiSeleniumOperator> provider;
	
	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION  launch User manual from AdminUi
	 */
	@TestStep(id = StepIds.EQEV65501_UserManual_STEP_01)
	public void verify() throws InterruptedException {
		
		// get operators from providers
		final AdminUiSeleniumOperator operator = provider.get();
		
		operator.openBrowser();
		operator.login();
		try {
			operator.verifyUserManual();
		} catch (Exception e) {
			throw e;
		} finally {
			operator.logoutAdminUI();
			operator.quitBrowser();
		}
		return;
		/*
		// get operators from providers
		final AdminUIOperator operator = provider.get();
		
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();


		operator.loginWithNewUser(username, password);
		//try {
			operator.verifyUserManual();
		//} finally {
			operator.logoutAdminUI();
		//}

		return;*/
	}

	public static class StepIds {
		public static final String EQEV65501_UserManual_STEP_01 = "launch User manual from AdminUi";

		private StepIds() {
		}
	}
}