package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.AdminUiSeleniumOperator;

import com.ericsson.cifwk.taf.data.DataHandler;


/**
 * @author ZJSOLEA
 */
public class EQEV67891_SpecialCharacters01_Steps {
	private static Logger logger = LoggerFactory.getLogger(EQEV67891_SpecialCharacters01_Steps.class);
	
	// Constants
	private static final int COMBINATION_COUNT = 5;

	@Inject
	private Provider<GeneralOperator> provider;
	
	@Inject
	private Provider<AdminUiSeleniumOperator> uiOperatorProvider;
	
	/**
	 * Shuffle the given string randomly
	 * @return random password with required special characters
	 */
	String getRandomPassword() {
		String s = "~!@#$%^*()_+-=`{}|[]?,.;:0nx";
		List<Character> list = new ArrayList<Character>();
		for (char c: s.toCharArray())
			list.add(c);
		
		Collections.shuffle(list);
		
		StringBuilder newString = new StringBuilder();
		for (char c: list)
			newString.append(c);
		return newString.toString();
	}

	/**
	 * Counter used to generate usernames
	 */
	private static int userCount = 1;
	
	/**
	 * Generate a new username
	 */
	String getUsername() {
		String username = "testuser" + String.format("%03d", userCount++);
		return username;
	}

	/**
	 * @throws Exception 
	 * @DESCRIPTION Verify adding new adminui user with special character in password and 
	 * New AdminUI Users able to login to AdminUI and password is encrypted
	 */
	@TestStep(id = StepIds.ADD_USER_WITH_PASSWORD)
	public void verify() throws Exception {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		final AdminUiSeleniumOperator uiOperator = uiOperatorProvider.get();
		
		try {
			// backing up tomcat-users.xml
			operator.executeCommandDcuser("cp /eniq/sw/runtime/tomcat/conf/tomcat-users.xml /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.bkp");			
			
			for (int i = 0; i < this.COMBINATION_COUNT; i++) {
					
				String password = getRandomPassword();
				String username = getUsername();
				String output = operator.executeCommandDcuser("echo -e '\"'\"'" + username 
						+ "\\n" + password + "'\"'\"' | /eniq/sw/installer/manage_tomcat_user.bsh -A ADD_USER");	
				assertTrue(output.contains("Password updated successfully for user"), "User addition is not successful : " + output);
							
				uiOperator.openBrowser();
				assertTrue(uiOperator.loginWith(username, password), "Failed to login with username " + username + " and password=" + password);
				uiOperator.logoutAdminUI();
				uiOperator.closeBrowser();
				uiOperator.quitBrowser();
				
				output = operator.executeCommandDcuser("cat /eniq/sw/runtime/tomcat/conf/tomcat-users.xml | grep -i " + username);
				assertTrue(!output.contains(password), "Password is not encrypted in tomcat-users.xml file");
				
			}
			// restore tomcat-users.xml file
			operator.executeCommandDcuser("mv /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.bkp /eniq/sw/runtime/tomcat/conf/tomcat-users.xml");
			operator.executeCommandDcuser("/eniq/sw/bin/webserver restart");
		} catch (Exception e) {
			// restore tomcat-users.xml file
			operator.executeCommandDcuser("mv /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.bkp /eniq/sw/runtime/tomcat/conf/tomcat-users.xml");
			operator.executeCommandDcuser("/eniq/sw/bin/webserver restart");
			throw e;
		} catch (Error e) {
			// restore tomcat-users.xml file
			operator.executeCommandDcuser("mv /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.bkp /eniq/sw/runtime/tomcat/conf/tomcat-users.xml");
			operator.executeCommandDcuser("/eniq/sw/bin/webserver restart");
			throw e;
		}
		return;
	}


	public static class StepIds {
		public static final String ADD_USER_WITH_PASSWORD = "Adding New AdminUI Users";
		
		private StepIds() {
		}
	}
}