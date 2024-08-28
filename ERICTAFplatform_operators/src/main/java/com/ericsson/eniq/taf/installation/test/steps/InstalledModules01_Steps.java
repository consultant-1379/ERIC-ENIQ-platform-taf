package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author ZJSOLEA
 */
public class InstalledModules01_Steps {

	private static Logger logger = LoggerFactory.getLogger(InstalledModules01_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUiSeleniumOperator> provider;

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify installed modules
	 */
	@TestStep(id = StepIds.InstalledModules_Step01)
	public void verify() throws InterruptedException {
		// get operators from providers
		final AdminUiSeleniumOperator operator = provider.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();
		generalOperator.executeCommandDcuser("webserver restart");
		operator.openBrowser();
		operator.login();
		operator.verifyInstalledModules();
		// operator.logoutAdminUI();
		operator.quitBrowser();

		return;
	}

	public static class StepIds {
		public static final String InstalledModules_Step01 = "Verify installed modules";

		private StepIds() {
		}
	}
}