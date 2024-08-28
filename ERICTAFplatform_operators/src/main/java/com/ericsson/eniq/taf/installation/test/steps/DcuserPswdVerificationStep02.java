package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.RepdbOperator;

/**
 * @author ZJSOLEA
 */
public class DcuserPswdVerificationStep02 {

	private static Logger logger = LoggerFactory.getLogger(DcuserPswdVerificationStep02.class);

	@Inject
	private Provider<GeneralOperator> provider;
	@Inject
	private Provider<RepdbOperator> repdbprovider;
	
	/**
	 * @DESCRIPTION Verify whether all the services are up and running after restarting the services
	 */
	@TestStep(id = StepIds.DCUSER_PSWD_VERIFICATION_STEP_02)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		logger.info("Restarting services");
		String output = operator.executeCommand("cd /eniq/admin/bin; echo Yes | /usr/bin/bash ./manage_deployment_services.bsh -a restart -s ALL");
		assertTrue(output.contains("ENIQ services started correctly on eniqs"), "Services didnt restart correctly :\n" + output);
		
		logger.info("Checking if all services are active");
		output = operator.executeCommand("services -s eniq");
		assertTrue(!output.contains("inactive"), "All services are not active : \n" + output);
		
		return;
	}

	public static class StepIds {
		public static final String DCUSER_PSWD_VERIFICATION_STEP_02="Verify whether all the services are up and running after restarting the services";

		private StepIds() {
		}
	}
}