package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;

public class ClickAllLinks {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClickAllLinks.class);
	private WebDriver browser;
	private Host eniqHost;

	public void openBrowser() throws InterruptedException {

		try {

			TimeUnit.SECONDS.sleep(3);
			System.setProperty("webdriver.gecko.driver", "/usr/bin/firefox");
			// System.setProperty("webdriver.chrome.driver",
			// "src\\main\\resources\\data\\chromedriver.exe");
			browser = new FirefoxDriver();
			// browser = new ChromeDriver();
			browser.manage().window().maximize();
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser = new FirefoxDriver(); threw an exception.");
			e.printStackTrace();
		}
	}

	private String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("https://%s", eniqHost.getIp() + ":8443/adminui/");
	}

	public boolean login() throws InterruptedException {
		try {
			String url = getWebAppUrl();
			browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			TimeUnit.SECONDS.sleep(1);
			browser.get(url);
			browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			TimeUnit.SECONDS.sleep(1);
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser.get() : ADMIN UI URL navigation threw an exception.");
			e.printStackTrace();
		}
		LOGGER.info("\n\n After naviagting AdminUI Login URL (Page Source): \n\n" + browser.getPageSource().toString());
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		browser.findElement(By.name("userName")).sendKeys("eniq");
		TimeUnit.SECONDS.sleep(1);
		browser.findElement(By.name("userPassword")).sendKeys("eniq");
		TimeUnit.SECONDS.sleep(1);
		browser.findElement(By.name("submit")).click();
		LOGGER.info("Clicked on 'Login' button");
		TimeUnit.SECONDS.sleep(1);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();
		boolean result = browser.getPageSource().contains("Host Information");
		return result;
	}

	public void closeBrowser() throws InterruptedException {
		try {
			browser.quit();
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser.quit(); threw an exception.");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return String
	 * @throws InterruptedException
	 */
	public boolean verifyAllAdminUIlinks(String eachLink, String expected) throws InterruptedException {
		try {

			LOGGER.info("Opening AdminUI Link : " + eachLink);
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText(eachLink))).click();
			browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			TimeUnit.SECONDS.sleep(3);
		} catch (UnreachableBrowserException e) {
			System.out.println(
					"\n ExpectedConditions.elementToBeClickable(By.linkText(eachLink))).click() threw an exception.");
			e.printStackTrace();
		}
		String pageSource = browser.findElement(By.tagName("body")).getText();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		LOGGER.info("Page Source : " + pageSource);
		boolean result = browser.getPageSource().contains(expected);
		return result;
	}

	/**
	 * 
	 * @return String
	 * @throws InterruptedException
	 */
	public boolean logoutAdminUI() throws InterruptedException {
		try {
			String url = getWebAppUrl();
			browser.get(url);
			TimeUnit.SECONDS.sleep(2);

			LOGGER.info("\nLogging out AdminUI");
			boolean hasQuit = browser.toString().contains("(null)");
			LOGGER.info("Browser Status (true means browser is closed) : " + hasQuit);

			browser.manage().window().maximize();
			LOGGER.info("LogOut page source : " + browser.getPageSource());
			browser.findElement(By.linkText("Logout")).click();
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser.findElement(By.linkText('Logout')).click(); threw an exception.");
			e.printStackTrace();
		}
		boolean result = browser.getPageSource().contains("You have logged out");
		return result;
	}

	/**
	 * 
	 * @return String
	 * @throws InterruptedException
	 */
	public boolean verifyTriggerSetsInAdminUI() throws InterruptedException {
		// login();
		LOGGER.info("Opening AdminUI...");
		LOGGER.info("\nTriggering Sets in AdminUI");
		WebDriverWait wait = new WebDriverWait(browser, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("ETLC Set Scheduling"))).click();
		Select element_settype = new Select(browser.findElement(By.name("settype")));
		element_settype.selectByValue("Maintenance");
		TimeUnit.SECONDS.sleep(2);
		Select element_package = new Select(browser.findElement(By.name("packageSets")));
		element_package.selectByValue("DWH_MONITOR");
		TimeUnit.SECONDS.sleep(2);

		List<WebElement> allCountrymvalue = browser.findElements(By.cssSelector("tbody>tr>td>table#ttable>tbody>tr"));
		List<WebElement> alllinks = browser.findElements(By.cssSelector("tbody>tr>td>table#ttable>tbody>tr>td>font>a"));
		for (int i1 = 1; i1 < allCountrymvalue.size(); i1++) {
			System.out.println("Value are : " + allCountrymvalue.get(i1).getText() + "== Corresponding link is : "
					+ alllinks.get(i1 - 1).getText() + "\n");

			if (allCountrymvalue.get(i1).getText().contains("DeltaViewCreation")) {
				LOGGER.info("Starting DeltaViewCreation set...\n");
				alllinks.get(i1 - 1).click();
				LOGGER.info("Triggering set Started...\n");
				break;
			} else {
				LOGGER.info(
						"ERROR: DeltaViewCreation Set corresponding link 'Start' link is worng or Unable to start the Adapter");
			}
		}

		TimeUnit.SECONDS.sleep(3);

		boolean result = browser.getPageSource().contains("DeltaViewCreation");
		return result;
	}

	public boolean verifyLicenseAdminUI() throws InterruptedException {
		boolean licenseStatus = browser.getPageSource().contains("is online");
		return licenseStatus;
	}

	public boolean validateLicenseLogInAdminUI() throws InterruptedException {
		LOGGER.info("Opening AdminUI...");
		try {
			browser.findElement(By.linkText("Show license logs")).click();
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser.findElement(By.linkText('Show license logs')).click(); threw an exception.");
			e.printStackTrace();
		}
		TimeUnit.SECONDS.sleep(2);
		try {
			browser.findElement(By.cssSelector("input[value='Search']")).click();
			LOGGER.info("\n Page Source after clicking on Search button:  " + browser.getPageSource());
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser.findElement(Search.click(); threw an exception.");
			e.printStackTrace();
		}

		int rowCount = browser.findElements(By.xpath("/html/body/table/tbody/tr/td[2]/table/tbody/tr")).size();
		ArrayList<String> rowData = new ArrayList<String>();
		for (int i = 1; i <= rowCount; i++) {
			String output = browser
					.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table/tbody/tr[" + i + "]/td[2]")).getText();

			rowData.add(output);
			i++;
		}
		List<String> search = Arrays.asList("SEVERE", "WARNING", "ERROR");

		for (String str : search) {

			if (rowData.contains(str)) {
				LOGGER.info("\nFound Error");
				return false;
			}
		}
		return true;
	}

	public boolean verifyLicenseStatusAdminUI() throws InterruptedException {
		login();
		LOGGER.info("Opening AdminUI...");
		String yellowColor = "ALT=" + '"' + "Yellow" + '"';
		String RedColor = "ALT=" + '"' + "Red" + '"';
		String pageSource = browser.getPageSource();
		if (pageSource.contains(yellowColor) || pageSource.contains(RedColor)) {
			return false;
		} else {
			return true;
		}
	}

	public void closeBrowsers() {
		browser.close();
	}

	public void quitBrowsers() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		browser.quit();
		TimeUnit.SECONDS.sleep(1);
	}

	private String getAlarmCFGurl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("https://%s", eniqHost.getIp() + ":8443/alarmcfg/");
	}

	public boolean loginAlarmCFG() throws InterruptedException {
		try {
			String url = getAlarmCFGurl();
			browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			TimeUnit.SECONDS.sleep(1);
			browser.get(url);
			browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			TimeUnit.SECONDS.sleep(1);
		} catch (UnreachableBrowserException e) {
			System.out.println("\n browser.get() : ADMIN CFG UI URL navigation threw an exception.");
			e.printStackTrace();
		}
		LOGGER.info("\n\n After naviagting AdminUI Login URL (Page Source): \n\n" + browser.getPageSource().toString());
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		boolean result = browser.getPageSource().contains("Login");
		return result;
	}

	//

	public void clickMultipleTimes(String link) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(browser, 300);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(link))).click();
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public boolean isAdminUisLoggedIn() {

		return browser.getCurrentUrl().contains("adminui/servlet/LoaderStatusServlet");
	}

	public boolean isDbUsersAvailable() {
		// TODO Auto-generated method stub
		return browser.getPageSource().contains("Customized Database User Details");

	}

}