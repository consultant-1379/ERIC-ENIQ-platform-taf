package com.ericsson.eniq.taf.installation.test.steps;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;
import com.ericsson.eniq.taf.installation.test.steps.WepAppTestStep.Parameters;
import com.ericsson.eniq.taf.installation.test.steps.WepAppTestStep.StepIds;;

/**
 * @author ZJSOLEA
 */
public class VerifyHelpLinksSteps {

	private static Logger logger = LoggerFactory.getLogger(VerifyHelpLinksSteps.class);
	private static final String CONSTANT = "";

	@Inject
	private AdminUiSeleniumOperator operator;
	
	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.VERIFY_HELP_LINKS_STEP)
	public void verify(@Input("links") String linkName, @Input("title") String title) throws InterruptedException {
		// get operators from providers
		//final AdminUiSeleniumOperator operator = provider.get();
		
		//String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		//String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		
		//String title = "Checking System Status";
		
		operator.verifyHelpLink(linkName, title);

		//return;
	}
	
	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.OPEN_FIREFOX)
	public void open() throws InterruptedException {
		operator.openBrowser();
		operator.login();

	}

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify whether all the help links are working in adminui
	 */
	@TestStep(id = StepIds.CLOSE_FIREFOX)
	public void close() {
		//operator.logoutAdminUI();
		operator.quitBrowser();

	}

	public static class StepIds {
		public static final String OPEN_FIREFOX = "Open Firefox Browser";
		public static final String VERIFY_HELP_LINKS_STEP = "verify whether all the help links are working";
		public static final String CLOSE_FIREFOX = "Close Firefox Browser";

		private StepIds() {
		}
	}
	
}