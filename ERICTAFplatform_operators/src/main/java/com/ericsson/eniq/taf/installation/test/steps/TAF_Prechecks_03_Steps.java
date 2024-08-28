package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.DwhdbOperator;


/**
 * @author ZJSOLEA
 */
public class TAF_Prechecks_03_Steps {

	private static Logger logger = LoggerFactory.getLogger(TAF_Prechecks_03_Steps.class);

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION Repdb and dwhdb should be up and running
	 */
	@TestStep(id = StepIds.TAF_Prechecks_03_STEP)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();

		String output = operator.executeCommandDcuser("repdb status");
		assertTrue("repdb is running OK".equals(output), "repdb is not running");
		
		output = operator.executeCommandDcuser("dwhdb status");
		assertTrue("dwhdb is running OK".equals(output), "dwhdb is not running");

		return;
	}

	public static class StepIds {
		public static final String TAF_Prechecks_03_STEP = "Repdb and dwhdb should be up and running";

		private StepIds() {
		}
	}
}