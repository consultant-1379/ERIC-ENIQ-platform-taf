package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;;

/**
 * @author ZJSOLEA
 */
public class CheckFailedETLSets_Steps {

	private static Logger logger = LoggerFactory.getLogger(CheckFailedETLSets_Steps.class);

	private static final String CONSTANT = "";

	@Inject
	private Provider<AdminUiSeleniumOperator> provider;

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify that there are no failed ETL Sets
	 */
	@TestStep(id = StepIds.CheckFailedETLSets_Step)
	public void verify() throws InterruptedException {
		// get operators from providers
		final AdminUiSeleniumOperator operator = provider.get();

		operator.openBrowser();
		operator.login();
		try {
			operator.checkFailedETLSets();
		} catch (Exception e) {
			throw e;
		} finally {
			//operator.logoutAdminUI();
			operator.quitBrowser();
		}
		return;
	}

	public static class StepIds {
		public static final String CheckFailedETLSets_Step = "Verify that there are no failed ETL Sets";

		private StepIds() {
		}
	}
}