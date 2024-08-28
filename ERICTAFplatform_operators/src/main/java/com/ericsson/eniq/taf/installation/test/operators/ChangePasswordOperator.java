package com.ericsson.eniq.taf.installation.test.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.eniq.taf.cli.CLIOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class ChangePasswordOperator extends CLIOperator {

	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";

	private static final int MAX_LENGTH_PWD = 6;

	private static java.util.Random random = new java.util.Random();

	private static final String DIGITS = "23456789";
	private static final String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
	private static final String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
	private static final String VALID_SYMBOLS = "!@#$%^*()_+-=~`{}|:;.[],?";
	private static final String ALL_VALID_PASSWORD = UPCASE_CHARACTERS + LOCASE_CHARACTERS + DIGITS + VALID_SYMBOLS;
	private static final char[] symbolsArray = VALID_SYMBOLS.toCharArray();
	private static final char[] digitsArray = DIGITS.toCharArray();
	private static final char[] allValidPassword = ALL_VALID_PASSWORD.toCharArray();

	private CLICommandHelper handler;
	private Host eniqshost;

	Logger logger = LoggerFactory.getLogger(ChangePasswordOperator.class);

	public ChangePasswordOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public String executeCommandsDCuser(String command) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + command + "'");
		String completeOutput = handler.simpleExec(DCUSER_PROFILE + command + "'");
		logger.info("\nCommand Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput.trim();
	}

	/**
	 * 
	 * @return CommandOutput
	 */
	public String executeCommand(String command) {
		logger.info("\nExecuting : " + command);
		String completeOutput = handler.simpleExec(command);
		logger.info("\nCommand Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		return completeOutput.trim();
	}

	public void retrievePassword(String users) {
		logger.info("\nExecuting : " + DCUSER_PROFILE + "./getPassword.bsh -u " + users + "'");
		String completeOutput = handler
				.simpleExec(DCUSER_PROFILE + "/eniq/sw/installer/getPassword.bsh -u " + users + "'");
		logger.info("\nCommand Output : \n" + completeOutput);
		logger.info("\nExiting shell.. " + handler.simpleExec("exit"));
		if (completeOutput.contains(users.toUpperCase() + " password:")) {
			Assert.assertTrue(true, users + "user password is retrieved successfully");
		} else {
			Assert.assertTrue(false, completeOutput);
		}
	}

	public static String generatePassword() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAX_LENGTH_PWD; i++) {
			sb.append(allValidPassword[random.nextInt(allValidPassword.length)]);
		}
		sb.append(symbolsArray[random.nextInt(symbolsArray.length)]);
		sb.append(digitsArray[random.nextInt(digitsArray.length)]);
		return sb.toString().trim();
	}

}
