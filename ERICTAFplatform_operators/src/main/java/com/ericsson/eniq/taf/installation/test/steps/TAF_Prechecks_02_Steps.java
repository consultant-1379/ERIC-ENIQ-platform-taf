package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
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
public class TAF_Prechecks_02_Steps {

	private static Logger logger = LoggerFactory.getLogger(TAF_Prechecks_02_Steps.class);

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION all services should be in active state
	 */
	@TestStep(id = StepIds.TAF_Prechecks_02_STEP)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		String output = operator.executeCommand("/eniq/installation/core_install/bin/list_services.bsh -s eniq");
		assertTrue(!output.toLowerCase().contains("not found"), "services command not executed properly : " + output);
		assertTrue(!output.toLowerCase().contains("inactive"), "Inactive services found : \n" + output + "\n");

		return;
	}

	public static class StepIds {
		public static final String TAF_Prechecks_02_STEP = "all services should be in active state";

		private StepIds() {
		}
	}
}