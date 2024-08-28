package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.WebserverRestartOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class WebserverRestartSteps {
	private static Logger logger = LoggerFactory.getLogger(WebserverRestartSteps.class);

	private static final String WEBSERVER_RESTART = "webserver restart";
	private static final String WEBSERVER_STATUS = "webserver status";

	@Inject
	private Provider<WebserverRestartOperator> provider;
	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION Verify whether webserver can be restarted in latest runtime
	 *              module successfully
	 */
	@TestStep(id = StepIds.EQEV_65253_Webserver_Restart_Step)
	public void verifyWebserverRestart() throws InterruptedException {
		/* DB and service checks to check and start engine and dbs. */
		GeneralOperator genoperator = generalOperatorProvider.get();
		
		if (genoperator.executeCommandDcuser("scheduler status").contains("not running"))
			genoperator.executeCommandDcuser("scheduler start");
		if (genoperator.executeCommandDcuser("repdb status").contains("is not running"))
			genoperator.executeCommandDcuser("repdb start");
		if (genoperator.executeCommandDcuser("dwhdb status").contains("not running"))
			genoperator.executeCommandDcuser("dwhdb start");
		if (genoperator.executeCommandDcuser("engine status").contains("not running"))
			genoperator.executeCommandDcuser("engine start");
		if (genoperator.executeCommandDcuser("webserver status").contains("not running"))
			genoperator.executeCommandDcuser("webserver start");
		
		/* the above block can be removed if not needed.*/
		
		final WebserverRestartOperator operator = provider.get();
		String webserver = operator.executeCommandsDCuser(WEBSERVER_RESTART);
		String webserverStatus = operator.executeCommandsDCuser(WEBSERVER_STATUS);
		assertTrue(webserver.contains("Service enabling eniq-webserver"), "Webserver restart failed");
		Thread.sleep(10000);
		assertTrue(webserverStatus.contains("webserver is running OK"), "Webserver status is OK");
	}

	public static class StepIds {
		public static final String EQEV_65253_Webserver_Restart_Step = "Verify whether webserver can be restarted in latest runtime module successfully";

		private StepIds() {
		}
	}

}
