package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;


/**
 * @author ZJSOLEA
 */
public class EQEV71745_Loader_Delimitter_Cron_Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV71745_Loader_Delimitter_Cron_Steps.class);
	

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION Verify that loader_delimiter cron entry is not  removed from dcuser cron
	 */
	@TestStep(id = StepIds.EQEV71745_Loader_Delimitter_Cron_STEP01)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();

		String result = operator.executeCommand("cd /var/spool/cron/; cat dcuser | grep -i loader_delimiter");
		assertFalse(result.trim().isEmpty(), "loader_delimiter.bsh should be listed");

		return;
	}

	public static class StepIds {
		public static final String EQEV71745_Loader_Delimitter_Cron_STEP01 = "Verify that loader_delimiter cron entry is removed from dcuser cron";

		private StepIds() {
		}
	}
}