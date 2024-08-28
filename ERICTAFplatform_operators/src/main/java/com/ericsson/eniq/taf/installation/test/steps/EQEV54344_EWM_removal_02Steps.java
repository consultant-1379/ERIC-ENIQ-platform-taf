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
public class EQEV54344_EWM_removal_02Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV54344_EWM_removal_02Steps.class);

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION pmdata_wifi directory should not be created under /eniq/data/
	 */
	@TestStep(id = StepIds.EQEV54344_EWM_REMOVAL_02_STEP)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		String output = operator.executeCommand(" ls /eniq/data");
		assertTrue(!output.contains("pmdata_wifi"), "pmdata_wifi should not be present under /eniq/data");

		return;
	}

	public static class StepIds {
		public static final String EQEV54344_EWM_REMOVAL_02_STEP = "verification of EQEV-54344_EWM removal_02";

		private StepIds() {
		}
	}
}