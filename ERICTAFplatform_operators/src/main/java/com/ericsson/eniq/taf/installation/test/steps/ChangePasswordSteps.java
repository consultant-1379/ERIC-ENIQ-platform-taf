package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.installation.test.operators.AdminUISpecialCharacterOperator;
import com.ericsson.eniq.taf.installation.test.operators.ChangePasswordOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class ChangePasswordSteps {
	private static Logger logger = LoggerFactory.getLogger(ChangePasswordSteps.class);

	private static final String STOP_ALL_SERVICES = "echo Yes | bash /eniq/admin/bin/manage_deployment_services.bsh -a stop -s ALL";
	private static final String START_DB_SERVICES = "echo Yes | bash /eniq/admin/bin/manage_eniq_services.bsh -a start -s dwhdb,repdb";
	private static final String CHANGE_DWH_DB_PASSWORD = "cd /eniq/admin/bin/; bash change_db_password.bsh -u dwhrep";
	private static final String CHANGE_ETL_DB_PASSWORD = "cd /eniq/admin/bin/; bash change_db_password.bsh -u etlrep";
	private static final String START_ALL_SERVICES = "echo Yes | bash /eniq/admin/bin/manage_deployment_services.bsh -a start -s ALL";
	private static final String NEW_VALID_PASSWORD = ChangePasswordOperator.generatePassword();
	private static final String CURRENT_DCUSER_PASSWORD = DataHandler.getAttribute("eniq.platform.dcuser.password")
			.toString();
	private static final String CHANGE_DCUSER_PASSWORD = "echo -e \"" + CURRENT_DCUSER_PASSWORD + "\\n"
			+ NEW_VALID_PASSWORD + "\\n" + NEW_VALID_PASSWORD + "\" | passwd";

	@Inject
	private Provider<ChangePasswordOperator> provider;

	/**
	 * @DESCRIPTION The password for dwhrep user cannot be changed through
	 *              change_db_password script (Negative TC)
	 */
	@TestStep(id = StepIds.EQEV_68105_Negative_TC05_Step)
	public void verifyDwhrepPasswordChange() {
		final ChangePasswordOperator operator = provider.get();
		String stopServices = operator.executeCommand(STOP_ALL_SERVICES);
		assertTrue(stopServices.contains("ENIQ services stopped correctly"), "ENIQ services stopped successfully");
		String startDBServices = operator.executeCommand(START_DB_SERVICES);
		assertTrue(startDBServices.contains("ENIQ services started correctly"),
				"ENIQ services started successfully for dwhdb and repdb");
		String changeDwhDBPassword = operator.executeCommand(CHANGE_DWH_DB_PASSWORD);
		assertTrue(changeDwhDBPassword.contains("USER - DWHREP not found"),
				"Password for dwhrep user cannot be changed through change_db_password script");
		String startServices = operator.executeCommand(START_ALL_SERVICES);
		assertTrue(startServices.contains("ENIQ services started correctly"), "ENIQ services started successfully");
	}

	/**
	 * @DESCRIPTION The password for etlrep user cannot be changed through
	 *              change_db_password script (Negative TC)
	 */
	@TestStep(id = StepIds.EQEV_68105_Negative_TC06_Step)
	public void verifyEtlrepPasswordChange() {
		final ChangePasswordOperator operator = provider.get();
		String stopServices = operator.executeCommand(STOP_ALL_SERVICES);
		assertTrue(stopServices.contains("ENIQ services stopped correctly"), "ENIQ services stopped successfully");
		String startDBServices = operator.executeCommand(START_DB_SERVICES);
		assertTrue(startDBServices.contains("ENIQ services started correctly"),
				"ENIQ services started successfully for dwhdb and repdb");
		String changeEtlDBPassword = operator.executeCommand(CHANGE_ETL_DB_PASSWORD);
		assertTrue(changeEtlDBPassword.contains("USER - ETLREP not found"),
				"Password for etlrep user cannot be changed through change_db_password script");
		String startServices = operator.executeCommand(START_ALL_SERVICES);
		assertTrue(startServices.contains("ENIQ services started correctly"), "ENIQ services started successfully");
	}

	/**
	 * @throws ConfigurationException
	 * @DESCRIPTION The password for dcuser user can be changed through
	 *              change_db_password script
	 */
	@TestStep(id = StepIds.EQEV_68105_TC07_Step)
	public void verifyDcUserPasswordChange() throws ConfigurationException {
		final ChangePasswordOperator operator = provider.get();

		String stopServices = operator.executeCommand(STOP_ALL_SERVICES);
		assertTrue(stopServices.contains("ENIQ services stopped correctly"), "ENIQ services stopped successfully");
		String startDBServices = operator.executeCommand(START_DB_SERVICES);
		assertTrue(startDBServices.contains("ENIQ services started correctly"),
				"ENIQ services started successfully for dwhdb and repdb");
		String changeDcUserPwd = operator.executeCommandsDCuser(CHANGE_DCUSER_PASSWORD);
		assertTrue(changeDcUserPwd.contains("all authentication tokens updated successfully."),
				"dcuser password changed successfully");
		PropertiesConfiguration dcUserPwd = new PropertiesConfiguration(
				FileFinder.findFile("platform.properties").get(0));
		dcUserPwd.setProperty("eniq.platform.dcuser.password", NEW_VALID_PASSWORD);
		dcUserPwd.save();
		logger.info("changed passwd: " + dcUserPwd.getProperty("eniq.platform.dcuser.password"));
		String startServices = operator.executeCommand(START_ALL_SERVICES);
		assertTrue(startServices.contains("ENIQ services started correctly"), "ENIQ services started successfully");
	}

	public static class StepIds {
		public static final String EQEV_68105_Negative_TC05_Step = "Verify the password for dwhrep user cannot be changed through change_db_password script (Negative TC)";
		public static final String EQEV_68105_Negative_TC06_Step = "Verify the password for etlrep user cannot be changed through change_db_password script (Negative TC)";
		public static final String EQEV_68105_TC07_Step = "Verify the password can be changed for dcuser user";

		private StepIds() {
		}
	}

}
