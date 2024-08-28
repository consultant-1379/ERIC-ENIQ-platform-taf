package com.ericsson.eniq.taf.installation.test.operators;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.cli.CLIOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class AdminUISpecialCharacterOperator extends CLIOperator {

	private final String DCUSER_PROFILE = "su - dcuser -c '. /eniq/home/dcuser/.profile; ";

	private static final int MAX_LENGTH_PWD = 5;
	private static final int MAX_LENGTH = 4;

	private static java.util.Random random = new java.util.Random();

	private static final String DIGITS = "23456789";
	private static final String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
	private static final String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
	private static final String SYMBOLS = "&<>'\"/\\";
	private static final String VALID_SYMBOLS = "!@#$%^*()_+-=~`{}|:;.[],?";
	private static final String ALL_PASSWORD = UPCASE_CHARACTERS + LOCASE_CHARACTERS + DIGITS + SYMBOLS;
	private static final String ALL_USER = UPCASE_CHARACTERS + LOCASE_CHARACTERS + DIGITS;
	private static final String ALL_VALID_PASSWORD = UPCASE_CHARACTERS + LOCASE_CHARACTERS + DIGITS + VALID_SYMBOLS;
	private static final char[] symbolsArray = SYMBOLS.toCharArray();
	private static final char[] digitsArray = DIGITS.toCharArray();
	private static final char[] allPassword = ALL_PASSWORD.toCharArray();
	private static final char[] allUsers = ALL_USER.toCharArray();
	private static final char[] allValidPassword = ALL_VALID_PASSWORD.toCharArray();

	private CLICommandHelper handler;
	private Host eniqshost;
	private WebDriver browser;

	Logger logger = LoggerFactory.getLogger(AdminUISpecialCharacterOperator.class);

	public AdminUISpecialCharacterOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);

	}

	private String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("https://%s", eniqHost.getIp() + ":8443/adminui/");
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

	public static String generatePassword() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAX_LENGTH_PWD; i++) {
			sb.append(allPassword[random.nextInt(allPassword.length)]);
		}
		sb.append(symbolsArray[random.nextInt(symbolsArray.length)]);
		return sb.toString().trim();
	}

	public static String generateNewUser() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAX_LENGTH; i++) {
			sb.append(allUsers[random.nextInt(allUsers.length)]);
		}
		sb.append(digitsArray[random.nextInt(digitsArray.length)]);
		return sb.toString().trim();
	}

	public static String generateValidPassword() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAX_LENGTH_PWD; i++) {
			sb.append(allValidPassword[random.nextInt(allValidPassword.length)]);
		}
		sb.append(digitsArray[random.nextInt(digitsArray.length)]);
		
		String new_password = sb.toString().trim();
		
		return new_password;
	}

	public void openBrowser() {
		System.setProperty("webdriver.gecko.driver", "/usr/bin/firefox");
		browser = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",
		// "src\\main\\resources\\data\\chromedriver.exe");
		// browser = new ChromeDriver();
	}

	public boolean login(String password) throws InterruptedException {
		String url = getWebAppUrl();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		
		browser.findElement(By.id("username")).sendKeys(username);
		browser.findElement(By.id("password")).sendKeys(password);
		browser.findElement(By.id("submit")).click();
		logger.info("Clicked on 'Login' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();
		String finalHost = hostName1.substring(0, hostName1.indexOf("."));
		boolean result = browser.getPageSource().contains(finalHost);
		return result;
	}

	public void logoutUI() {
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("window.scrollBy(0,250)", "");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String log = browser.findElement(By.linkText("Logout")).getText();
		assertTrue(log.contains("Logout"), "Logout Link is found");
		WebElement logout = browser.findElement(By.linkText("Logout"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logout.click();
	}

	public void closeBrowser() {
		browser.close();
	}

	public void updateAdminuiPasswordInProperties(String new_password) throws ConfigurationException {
		// TODO Auto-generated method stub
		PropertiesConfiguration update = new PropertiesConfiguration(
				FileFinder.findFile("platform.properties").get(0));
		update.setProperty("eniq.platform.adminui.password", new_password);
		update.save();
		logger.info("Updated adminui password in platform.properties " + new_password);
	}

}
