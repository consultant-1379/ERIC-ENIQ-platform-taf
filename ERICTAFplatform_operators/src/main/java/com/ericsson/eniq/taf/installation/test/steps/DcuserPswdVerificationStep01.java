package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.eniq.taf.db.EniqDBOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.RepdbOperator;


/**
 * @author ZJSOLEA
 */
public class DcuserPswdVerificationStep01 {

	private static Logger logger = LoggerFactory.getLogger(DcuserPswdVerificationStep01.class);
	
	// Unescaped command :
	// echo -e '"'"'dcuser\nQ!@#$%^&*(){}\\"_+-=:;\nQ!@#$%^&*(){}\\"_+-=:;'"'"' | passwd
	private static final String CURRENT_PASSWORD = "dcuser";
	private static final String CHANGE_DCUSER_PASSWORD = ". /eniq/home/dcuser/.profile; echo -e '\"'\"'" + CURRENT_PASSWORD + "\\nQ!@#$%^&*(){}\"_+-=:;\\nQ!@#$%^&*(){}\"_+-=:;'\"'\"' | passwd";// "echo -e '\"'\"'" + CURRENT_PASSWORD + "\\nQ!@#$%^&*(){}\\\\\"_+-=:;\\nQ!@#$%^&*(){}\\\\\"_+-=:;'\"'\"' | passwd";
	private static final String NEW_PASSWORD_ENCRYPTED = "USFAIyQlXiYqKCl7fSJfKy09Ojs=";
	private static final String DCUSER_PASSWORDS_FROM_REPDB = "pm/dcuserpasswords.sql";
	// Unescaped command :
	// cd /eniq/sw/platform/repository-R*/bin; chmod +x ChangeUserPasswordsInRepdb; echo -e '"'"'dcuser\nQ!@#$%^&*(){}\\"_+-=:;\nQ!@#$%^&*(){}\\"_+-=:;'"'"' | ./ChangeUserPasswordsInRepdb
	private static final String CHANGE_DCUSER_PASSWORD_IN_REPDB = "cd /eniq/sw/platform/repository-R*/bin; chmod +x ChangeUserPasswordsInRepdb; echo -e '\"'\"'" + CURRENT_PASSWORD + "\\nQ!@#$%^&*(){}\"_+-=:;\\nQ!@#$%^&*(){}\"_+-=:;'\"'\"' | ./ChangeUserPasswordsInRepdb -u dcuser";
	

	@Inject
	private Provider<GeneralOperator> provider;
	@Inject
	private Provider<EniqDBOperator> dbprovider;
	
	/**
	 * @DESCRIPTION Verify by providing the pwd with Special characters (all in one go).
	 */
	@TestStep(id = StepIds.DCUSER_PSWD_VERIFICATION_STEP_01)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		String output;
		
		logger.info("Changing password");
		output = operator.executeCommandDcuser(CHANGE_DCUSER_PASSWORD);
		assertTrue(output.contains("all authentication tokens updated successfully"), "Failed to change the password : \n" + output);
		
		logger.info("Changing password in db");
		
		output = operator.executeCommandDcuser(CHANGE_DCUSER_PASSWORD_IN_REPDB);
		assertTrue(output.contains(" rows affected."), "Failed to change password in repdb : \n" + output);

		logger.info("Verifying password in db");
		List<Object[]> queryResults = ExecuteQueryFileInRepdb(DCUSER_PASSWORDS_FROM_REPDB);
		assertTrue(queryResults.size() == 1, "All passwords in the meta_database table for the user dcuser are not same");
		assertTrue(NEW_PASSWORD_ENCRYPTED.equals(queryResults.get(0)[0].toString()), "Password is db is not matching");
		
		return;
	}
	
	public List<Object[]> ExecuteQueryFileInRepdb(String queryfile) {
		final EniqDBOperator dbOperator = dbprovider.get();
		dbOperator.setupETLREP();
		List<Object[]> resultList = new ArrayList<Object[]>();
		
		logger.info("Executing Database Query File : " + queryfile);

		// Execute the query and get result
		TestDataSource<DataRecord> resultOutput = dbOperator.executeQuery(queryfile, new HashMap<String, String>());
		
		// Store the results in a list
		if (resultOutput.iterator().hasNext()) {
			for (DataRecord record: resultOutput) {
				resultList.add(record.getAllFields().values().toArray());
				logger.info("RESULT : " + record.getAllFields());
			}
		}
		
		return resultList;
	}

	public static class StepIds {
		public static final String DCUSER_PSWD_VERIFICATION_STEP_01="Verify by providing the pwd with Special characters (all in one go).";

		private StepIds() {
		}
	}
}