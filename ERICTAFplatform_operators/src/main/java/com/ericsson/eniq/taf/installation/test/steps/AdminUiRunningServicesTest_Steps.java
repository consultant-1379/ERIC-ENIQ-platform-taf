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
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author ZJSOLEA
 */
public class AdminUiRunningServicesTest_Steps {

	private static Logger logger = LoggerFactory.getLogger(AdminUiRunningServicesTest_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUiSeleniumOperator> provider;
	
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;
	
	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION Verify that all running services are green
	 */
	@TestStep(id = StepIds.AdminUiRunningServicesTest_Step)
	public void verify() throws InterruptedException {
		// get operators from providers
		final AdminUiSeleniumOperator operator = provider.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		generalOperator.executeCommandDcuser("webserver restart");
		
		operator.openBrowser();
		operator.login();
		try {
			operator.verifyRunningServices();
		} catch (Exception e) {
			throw e;
		} finally {
			//operator.logoutAdminUI();
			operator.quitBrowser();
		}
		return;
	}

	public static class StepIds {
		public static final String AdminUiRunningServicesTest_Step = "Verify that all running services are green";

		private StepIds() {
		}
	}
}