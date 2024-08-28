package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author ZJSOLEA
 */
public class EQEV57943Steps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);

	private static final String LOGFILE_PATH = "";

	@Inject
	private Provider<GeneralOperator> provider;

	/**
	 * @DESCRIPTION This test case covers verification of EQEV57943.
	 * @PRE
	 */
	@TestStep(id = StepIds.VERIFY_EQEV57943_1)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		/* DB and service checks to check and start engine and dbs. */
		
		if (operator.executeCommandDcuser("scheduler status").contains("not running"))
			operator.executeCommandDcuser("scheduler start");
		if (operator.executeCommandDcuser("repdb status").contains("is not running"))
			operator.executeCommandDcuser("repdb start");
		if (operator.executeCommandDcuser("dwhdb status").contains("not running"))
			operator.executeCommandDcuser("dwhdb start");
		if (operator.executeCommandDcuser("engine status").contains("not running"))
			operator.executeCommandDcuser("engine start");
		
		/* the above block can be removed if not needed.*/

		// Check if engine status is normal before starting the test
		String output = operator.executeCommandDcuser("engine status");
		assertTrue(output.indexOf("Current Profile: Normal") >= 0,
				"Engine is not in normal state. Cannot perform the test");

		// change engine profile to NoLoads
		output = operator.executeCommandDcuser("engine -e changeProfile NoLoads");
		assertTrue(output.indexOf("Change profile requested successfully") >= 0,
				"Error in changing engine profile to No loads");

		// verify that engine status is changed
		output = operator.executeCommandDcuser("sleep 2s && engine status");
		assertTrue(output.indexOf("Current Profile: NoLoads") >= 0, "Engine profile didnt change");

		// change engine status back to normal
		output = operator.executeCommandDcuser("engine -e changeProfile Normal");
		assertTrue(output.indexOf("Change profile requested successfully") >= 0,
				"Error in changing engine profile to Normal");
		output = operator.executeCommandDcuser("sleep 2s && engine status");
		assertTrue(output.indexOf("Current Profile: Normal") >= 0, "Engine did not change to normal state.");

		// stop dwhdb
		output = operator.executeCommandDcuser("dwhdb stop");
		output = operator.executeCommandDcuser("sleep 180s && engine status");
		assertTrue(output.indexOf("Current Profile: NoLoads") >= 0, "Engine profile didnt change");

	}

	/**
	 * @DESCRIPTION This test case covers verification of EQEV57943.
	 * @PRE
	 */
	@TestStep(id = StepIds.VERIFY_EQEV57943_2)
	public void startDwhdb() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		String output = null;
		// start dwhdb
		output = operator.executeCommandDcuser("dwhdb start");
		//output = operator.executeCommandDcuser("sleep 5s && engine status");
		output = operator.executeCommandDcuser("sleep 180s && engine status");
		assertTrue(output.indexOf("Current Profile: Normal") >= 0, "Engine did not change to normal state.");
	}

	public static class StepIds {
		public static final String VERIFY_EQEV57943_1 = "To verify EQEV57943";
		public static final String VERIFY_EQEV57943_2 = "To verify EQEV57943 start dwhdb";

		private StepIds() {
		}
	}
}