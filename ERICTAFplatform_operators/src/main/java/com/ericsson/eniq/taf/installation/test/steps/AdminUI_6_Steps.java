package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.ui.Browser;
import com.ericsson.cifwk.taf.ui.BrowserOS;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.BrowserType;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.sdk.Link;
import com.ericsson.cifwk.taf.ui.sdk.ViewModel;
import com.ericsson.eniq.taf.installation.test.operators.AdminUIOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author ZJSOLEA
 */
public class AdminUI_6_Steps {

	private static Logger logger = LoggerFactory.getLogger(AdminUI_6_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUIOperator> provider;
	
	/**
	 * @DESCRIPTION Launching of adminui after changing the password
	 */
	@TestStep(id = StepIds.ADMINUI_6_STEP_01)
	public void verify() {
		// get operators from providers
		final AdminUIOperator operator = provider.get();
		
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String oldPassword = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		String newpassword = "neweniqpassword";
		
		logger.info("Changing adminui password for " + username);
		operator.changeAdminUiPassword(username, newpassword);
		try {
			operator.loginWithNewUser(username, newpassword);
			operator.logoutAdminUI();
		} catch (Exception e) {
			throw e;
		} finally {
			logger.info("Changing adminui password for " + username + " back");
			operator.changeAdminUiPassword(username, oldPassword);
		}

		return;
	}

	public static class StepIds {
		public static final String ADMINUI_6_STEP_01 = "Launching of adminui after changing the password";

		private StepIds() {
		}
	}
}