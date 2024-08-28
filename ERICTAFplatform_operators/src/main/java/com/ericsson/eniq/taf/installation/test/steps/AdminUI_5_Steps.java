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
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * @author ZJSOLEA
 */
public class AdminUI_5_Steps {

	private static Logger logger = LoggerFactory.getLogger(AdminUI_5_Steps.class);

	private static final String NEW_USERNAME = "newuser01";
	private static final String NEW_PASSWORD = "newuser01";

	@Inject
	private Provider<AdminUIOperator> provider;
	
	/**
	 * @DESCRIPTION Launching of adminui after creating the new user
	 */
	@TestStep(id = StepIds.ADMINUI_5_STEP_01)
	public void verify() {
		// get operators from providers
		final AdminUIOperator operator = provider.get();
		
		String username = NEW_USERNAME;
		String password = NEW_PASSWORD;

		operator.addAdminUiUser(username, password);
		try {
			operator.loginWithNewUser(username, password);
			operator.logoutAdminUI();
		} catch (Exception e) {
			throw e;
		}  finally {
			operator.removeAdminUiUser(username);
		}

		return;
	}

	public static class StepIds {
		public static final String ADMINUI_5_STEP_01 = "Launching of adminui after creating the new user";

		private StepIds() {
		}
	}
}