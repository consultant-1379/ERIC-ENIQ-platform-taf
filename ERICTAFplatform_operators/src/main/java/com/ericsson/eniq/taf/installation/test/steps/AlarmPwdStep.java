package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;;

/**
 * @author XARUNHA
 */
public class AlarmPwdStep {

	private static Logger logger = LoggerFactory.getLogger(AlarmPwdStep.class);

	private final String ENIQ_SW_BIN = DataHandler.getAttribute("platform.install.lm.dir").toString();
	private final String ALARM_PASSWORD = DataHandler.getAttribute("eniq.platform.alarm.pass").toString();
	private static final String CD = "cd";
	private static final String PIPE = " | ";
	private static final String SPACE = " ";
	private static final String SEMI_CO = ";";
	private static final String ALARM_PWD_CHANGE_SCRIPT = "change_alarm_password.bsh";
	//private static final String EXPECTED = "BUILD SUCCESSFUL";
	private static final String EXPECTED ="Alarm password changed successfully";
	@Inject
	private Provider<GeneralOperator> provider;

	/**
	 * @throws InterruptedException
	 * @DESCRIPTION Verify that there are no failed ETL Sets
	 */
	@TestStep(id = StepIds.ALLOWED_PWD)
	public void verifyAlarmPwdWithAllowed() {
		final GeneralOperator generalOperator = provider.get();
		String output = generalOperator.executeCommandDcuser(CD + SPACE + ENIQ_SW_BIN + SEMI_CO + " echo -e " + '"'
				+ ALARM_PASSWORD + '"' + PIPE + ALARM_PWD_CHANGE_SCRIPT).trim();
		assertTrue(output.contains(EXPECTED), "\n Expected : " + EXPECTED + " \nbut found : " + output);
	}

	/**
	 * @DESCRIPTION Verify that there are no failed ETL Sets
	 */
	@TestStep(id = StepIds.NOT_ALLOWED_PWD)
	public void verifyAlarmPwdWithNotAllowed(@Input(Parameters.PASSWORD) String key) {
		final GeneralOperator generalOperator = provider.get();
		

		if (key.contains("$") || key.contains("`") || key.contains("?") || key.contains("\"") || key.contains("\\")) {
			
			// Escape the characters for use in bash
			String[] charToEscapeArray = { "\"", "$", "`", "\\"};
			List<String> charToEscape = Arrays.asList(charToEscapeArray);
			for (String c: charToEscape) {
				key = key.replace(c, "\\" + c);
			}
			
			String output = generalOperator.executeCommandDcuser(
					CD + SPACE + ENIQ_SW_BIN + SEMI_CO + " echo -e " + '"' + key + '"' + PIPE + ALARM_PWD_CHANGE_SCRIPT)
					.trim();
			assertTrue(output.contains("The new password entered is not compliant with the Password Policies"),
					"\n  Password verification failed for  : " + key + " \nExpected : "
							+ "The new password entered is not compliant with the Password Policies" + " \nbut found : "
							+ output);

		} /*else if(key.contains("`") | key.contains("\"\"\"")){
			
		}*/else {
			String output = generalOperator.executeCommandDcuserDiff(
					CD + SPACE + ENIQ_SW_BIN + SEMI_CO + " echo -e '" + key + "'" + PIPE + ALARM_PWD_CHANGE_SCRIPT)
					.trim();
			assertTrue(output.contains("The new password entered is not compliant with the Password Policies"),
					"\n  Password verification failed for  : " + key + " \nExpected : "
							+ "The new password entered is not compliant with the Password Policies" + " \nbut found : "
							+ output);
		}
	}

	public static class StepIds {
		public static final String ALLOWED_PWD = "Verify changing the pwd with allowed characters";
		public static final String NOT_ALLOWED_PWD = "Verify changing the pwd with the characters which are not allowed.";

		private StepIds() {
		}
	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class Parameters {
		public static final String PASSWORD = "pass";

		private Parameters() {
		}

	}

}