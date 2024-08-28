package com.ericsson.eniq.taf.installation.test.operators;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;

public class WebAppUiOperator {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppUiOperator.class);
	private WebDriver browser;
	private Host eniqHost;

	public void openBrowser() {
		System.setProperty("webdriver.gecko.driver", "/usr/bin/firefox");
		//System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\data\\chromedriver.exe");
		 browser = new FirefoxDriver();
		//browser = new ChromeDriver();
	}

	private String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("https://%s", eniqHost.getIp() + ":8443/adminui/");
	}

	public boolean login() throws InterruptedException {
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		
		String url = getWebAppUrl();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		browser.findElement(By.id("username")).sendKeys(username);
		browser.findElement(By.id("password")).sendKeys(password);
		browser.findElement(By.id("submit")).click();
		LOGGER.info("Clicked on 'Login' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();

		boolean result = browser.getPageSource().contains("Host Information");
		return result;
	}

	public void closeBrowser() {
		browser.close();
	}

	/**
	 * 
	 * @return String
	 * @throws InterruptedException
	 */
	public String verifyAllAdminUIlinks(String eachLink) throws InterruptedException {
		LOGGER.info("Opening AdminUI Link : " + eachLink);
		// browser.findElement(By.linkText(eachLink)).click();
		WebDriverWait wait = new WebDriverWait(browser, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(eachLink))).click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// TimeUnit.SECONDS.sleep(3);
		String pageSource = browser.findElement(By.tagName("body")).getText();
		// String result = browser.getPageSource().toString();
		LOGGER.info("Page Source : " + pageSource);
		return pageSource;

	}

	/**
	 * 
	 * @return String
	 */
	public boolean logoutAdminUI() {
		LOGGER.info("\nLogging out AdminUI");
		browser.findElement(By.linkText("Logout")).click();
		boolean result = browser.getPageSource().contains("You have logged out");
		browser.close();
		return result;

	}
}
