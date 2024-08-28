package com.ericsson.eniq.taf.installation.test.steps;

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
public class EQEV68105_TC02_Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV68105_TC02_Steps.class);
	
	private static final String NEW_PASSWORD="Password@123";

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * @DESCRIPTION change the password of database user dcbo
	 */
	@TestStep(id = StepIds.EQEV68105_TC02_STEP01)
	public void verify() {
		/* NOTE!!!
		 * This test case must be run in the end of the suite as it changes the
		 * default password and other test cases might be depend on the default password.
		 */
		
		// get operators from providers
		final GeneralOperator operator = provider.get();

		try {
			logger.info("Getting current dcbo password");
			String output = operator.executeCommandDcuser("cd /eniq/sw/installer/; ./getPassword.bsh -u dcbo | grep \"DCBO password:\"");
			assertTrue(!output.isEmpty(), "Unable to retrieve current password for dcbo");
			String currentPassword = output.split(":")[1].trim();
			logger.info("Retrieved current password : " + currentPassword);
			
			logger.info("Stopping all eniq services");
			output = operator.executeCommand("echo Yes | bash /eniq/admin/bin/manage_deployment_services.bsh -a stop -s ALL");
			assertTrue(output.contains("ENIQ services stopped correctly"), "Unable to stop all eniq services");
			logger.info("Stopped all services");
			
			logger.info("Starting dwhdb and repdb services");
			output = operator.executeCommand("echo Yes | bash /eniq/admin/bin/manage_eniq_services.bsh -a start -s dwhdb,repdb");
			assertTrue(output.contains("ENIQ services started correctly"), "Unable to start dwhdb and repdb services");
			logger.info("Started dwhdb and repdb services");
			
			logger.info("Changing password");
			String newpswd = NEW_PASSWORD;
			output = operator.executeCommand("echo -e \"" + currentPassword + "\\n" + newpswd + "\\n" + newpswd + "\\nYes\" | bash /eniq/admin/bin/change_db_password.bsh -u dcbo");
			assertTrue(output.contains("PASSWORD CHANGE SUCCESSFUL"), "Unable to change Password :\n" + output);
			logger.info("Password changed");
		} catch (Exception e) {
			throw e;
		} finally {
			String output = operator.executeCommand("echo Yes | bash /eniq/admin/bin/manage_deployment_services.bsh -a start -s ALL");
			assertTrue(output.contains("ENIQ services started correctly on eniqs"), "Unable to start all services");
		}
		return;
	}

	public static class StepIds {
		public static final String EQEV68105_TC02_STEP01 = "change the password of database user dcbo";

		private StepIds() {
		}
	}
}