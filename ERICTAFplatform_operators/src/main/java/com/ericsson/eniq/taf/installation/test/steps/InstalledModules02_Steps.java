package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.flows.AdminUI_Flows;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author ZJSOLEA
 */
public class InstalledModules02_Steps {

	private static Logger logger = LoggerFactory.getLogger(InstalledModules02_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUiSeleniumOperator> provider;
	
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;
	
	@Inject
	AdminUI_Flows wepAppTestFlow;
	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION Verify installed modules duplicates
	 */
	@TestStep(id = StepIds.InstalledModules_Step02)
	public void verify() throws InterruptedException {
		// get operators from providers
		final AdminUiSeleniumOperator operator = provider.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();

		generalOperator.executeCommandDcuser("webserver restart");
		operator.openBrowser();
		operator.login();
		wepAppTestFlow.basicTest();
		operator.verifyInstalledModules();
		//operator.logoutAdminUI();
		operator.quitBrowser();
		
		return;
	}

	public static class StepIds {
		public static final String InstalledModules_Step02 = "Verify installed modules duplicates";

		private StepIds() {
		}
	}
}