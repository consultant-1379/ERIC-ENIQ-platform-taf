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
public class TAF_Prechecks_01_Steps {

	private static Logger logger = LoggerFactory.getLogger(TAF_Prechecks_01_Steps.class);

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION Engine profile should be in ‘Normal’
	 */
	@TestStep(id = StepIds.TAF_Prechecks_01_STEP)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		String output = operator.executeCommandDcuser("engine -e status | grep 'Current Profile: Normal'");
		assertTrue(!output.isEmpty(), "Engine profile is not Normal");

		return;
	}

	public static class StepIds {
		public static final String TAF_Prechecks_01_STEP = "Engine profile should be in ‘Normal’";

		private StepIds() {
		}
	}
}