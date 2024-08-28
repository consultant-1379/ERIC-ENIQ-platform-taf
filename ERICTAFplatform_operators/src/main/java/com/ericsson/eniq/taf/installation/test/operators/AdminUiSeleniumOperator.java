package com.ericsson.eniq.taf.installation.test.operators;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;

public class AdminUiSeleniumOperator {
	private static final Logger logger = LoggerFactory.getLogger(AdminUiSeleniumOperator.class);
	private WebDriver browser;
	private Host eniqHost;
	private Host eniqshost;
	private CLICommandHelper handler;

	public AdminUiSeleniumOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	public void openBrowser() {
		 //System.setProperty("webdriver.chrome.driver",
		 //"src\\main\\resources\\data\\chromedriver.exe");
		 //browser = new ChromeDriver();

		System.setProperty("webdriver.gecko.driver", "/usr/bin/firefox");
		browser = new FirefoxDriver();
		browser.manage().window().maximize();
	}

	private String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("https://%s", eniqHost.getIp() + ":8443/adminui/");
	}

	public boolean login() throws InterruptedException {
		String url = getWebAppUrl();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		
		browser.findElement(By.id("username")).sendKeys(username);
		browser.findElement(By.id("password")).sendKeys(password);
		browser.findElement(By.id("submit")).click();
		logger.info("Clicked on 'Login' button");
		
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();
		boolean result = browser.getPageSource().contains("Host Information");

		return result;
	}

	public void closeBrowser() {
		browser.close();
	}

	public void quitBrowser() {
		browser.quit();
	}

	/**
	 * 
	 * @return String
	 */
	public boolean logoutAdminUI() {
		logger.info("\nLogging out AdminUI");
		browser.findElement(By.linkText("Logout")).click();
		boolean result = browser.getPageSource().contains("You have logged out");
		return result;

	}

	public void verifyHelpLink(String linkName, String title) throws InterruptedException {
		// this.openBrowser();
		// this.login();
		TimeUnit.SECONDS.sleep(1);
		String mainWindow = browser.getWindowHandle();

		logger.info("Finding link '" + linkName + "'");
		browser.findElement(By.linkText(linkName)).findElement(By.xpath("following-sibling::*")).click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Assert.assertEquals("Help page didnt open in new tab", browser.getWindowHandles().size(), 2);

		String helpWindow = null;
		for (String handle : browser.getWindowHandles()) {
			if (!handle.equals(mainWindow))
				helpWindow = handle;
		}

		Assert.assertNotNull("Unable to find the opened browser tab", helpWindow);

		browser.switchTo().window(helpWindow);

		logger.info("Verifying that opened help section is " + title);
		browser.switchTo().frame("hlexmain");
		browser.switchTo().frame("hlexdummy");
		browser.switchTo().frame("hlexdoc");

		WebElement chapLink = browser.findElement(By.className("CHAPLINK"));

		Assert.assertEquals(title, chapLink.getText());

		// logger.info("Closing the help page");
		// browser.close();

		browser.switchTo().window(mainWindow);
		// this.logoutAdminUI();
		// .quitBrowser();
	}

	public void verifyRunningServices() throws InterruptedException {
		logger.info("Finding the running services in page using XPATH");
		WebElement td = this.browser.findElement(
				By.xpath("/html/body/table[1]/tbody/tr/td[2]/table[1]/tbody[2]/tr/td[3]/table/tbody/tr[3]/td"));

		logger.info("Verifying services");
		for (WebElement element : td.findElements(By.tagName("font"))) {
			logger.info("Verifying service " + element.getText());
			Assert.assertEquals("Service is not green", "green", element.getAttribute("color"));

		}
	}

	public void checkFailedETLSets() {
		WebElement etlLink = this.browser.findElement(By.linkText("ETLC Monitoring"));
		etlLink.click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement failedTable = this.browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table[4]/tbody"));
		int failedEtlSetsCount = failedTable.findElements(By.tagName("tr")).size() - 1;
		Assert.assertEquals("" + failedEtlSetsCount + " Failed ETL sets are present" + failedTable.toString(), 0,
				failedEtlSetsCount);
	}

	public void verifyDuplicateInstalledModules() {
		WebElement installedModulesLink = this.browser.findElement(By.linkText("Monitoring Commands"));
		installedModulesLink.click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Select select = new Select(this.browser.findElement(By.tagName("select")));
		select.selectByVisibleText("Installed modules");

		WebElement submitButton = this.browser.findElement(By.tagName("input"));
		submitButton.click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		String modules = this.browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr/td/font"))
				.getText();
		Map<String, List<String>> modulesMap = new HashMap<String, List<String>>();
		for (String item : modules.trim().split("\n")) {
			if (item.equalsIgnoreCase("Installed platform modules:"))
				continue;
			String module = item.split("\\.")[1].split("=")[0];
			String rstate = item.split("\\.")[1].split("=")[1];

			if (!modulesMap.containsKey(module))
				modulesMap.put(module, new LinkedList<String>());
			modulesMap.get(module).add(rstate);
		}

		String duplicates = "";
		for (String module : modulesMap.keySet()) {
			if (modulesMap.get(module).size() > 1) {
				logger.info("duplicate entries found for module '" + module + "' Rstates=" + modulesMap.get(module));
				duplicates = duplicates + "\n" + module;
			}
		}

		Assert.assertTrue("Duplicate entries found for certain modules :- " + duplicates, duplicates.isEmpty());
	}

	public void verifyInstalledModules() {
		logger.info("Clicking link Monitoring commands");
		WebElement installedModulesLink = this.browser.findElement(By.linkText("Monitoring Commands"));
		installedModulesLink.click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		logger.info("Selecting Installed Modules from dropdown");
		Select select = new Select(this.browser.findElement(By.tagName("select")));
		select.selectByVisibleText("Installed modules");

		logger.info("Clicking start button");
		WebElement submitButton = this.browser.findElement(By.tagName("input"));
		submitButton.click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		logger.info("Ëxtracting modules from page");
		String modules = this.browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr/td/font"))
				.getText();
		Map<String, String> modulesMap = new HashMap<String, String>();
		for (String item : modules.trim().split("\n")) {
			if (item.equalsIgnoreCase("Installed platform modules:"))
				continue;
			String module = item.split("\\.")[1].split("=")[0];
			String rstate = item.split("\\.")[1].split("=")[1];

			modulesMap.put(module, rstate);
		}
		//
		// logger.info("Getting modules from versiondb.properties file");
		// String[] output = handler.simpleExec("cat
		// /eniq/sw/installer/versiondb.properties").split("\n");
		// for (String entry: output) {
		// if (entry.trim().isEmpty() || entry.startsWith("#"))
		// continue;
		// String module = entry.split("\\.")[1].split("=")[0];
		// String rstate = entry.split("\\.")[1].split("=")[1].trim();
		//
		// logger.info("Verifying rstate for module " + module);
		// logger.info("Module=" + module + ", Rstate In Versiondb.properties="
		// + rstate + ", Rstate in AdminUi=" + modulesMap.get(module));
		// Assert.assertEquals("rstate mismatch in adminUi for module " +
		// module, rstate, modulesMap.get(module));
		// }

		// Get modules from platform path
		String[] output = handler.simpleExec("ls /eniq/sw/platform/ | cat").split("\n");
		for (String entry : output) {
			String[] entries = entry.split("-");
			String module = entries[0];
			String rstate = entries[entries.length - 1].trim();
			Assert.assertEquals("rstate mismatch in adminUi for module " + module, rstate, modulesMap.get(module));
		}
	}

	public void verifyHostInformation() {
		logger.info("Extracting host information from page");
		WebElement hostInfo = this.browser.findElement(
				By.xpath("/html/body/table[1]/tbody/tr/td[2]/table[1]/tbody[2]/tr/td[3]/table/tbody/tr[2]/td"));
		String[] hostInfos = hostInfo.getText().split("\n");

		String hostName = hostInfos[0].split(":")[1].trim().split("\\s")[0];
		String osVersion = hostInfos[1].split(":")[1].trim();
		String arch = hostInfos[2].split(":")[1].trim();
		String uptime = hostInfos[3].replaceFirst("Uptime:", "").trim();

		logger.info(":" + hostName + ":" + osVersion + ":" + arch + ":" + uptime + ":");

		logger.info("Getting host info from cli");
		Map<String, String> cliHostInfo = new HashMap<String, String>();
		String output = handler.simpleExec("hostnamectl");
		logger.info("Output : " + output);
		for (String entry : output.split("\n")) {
			String property = entry.split(":")[0].trim();
			String value = entry.split(":")[1].trim();
			cliHostInfo.put(property, value);
		}

		logger.info("Host info from cli : " + cliHostInfo.toString());

		String cliUptime = handler.simpleExec("uptime").trim();
		String actualHostName = handler.simpleExec("hostname").trim();
		String actualArch = handler.simpleExec("uname -i").trim();

		Assert.assertEquals("Hostname mismatch", actualHostName, hostName);
		Assert.assertEquals("Architecture mismatch", actualArch, arch);
		Assert.assertEquals("uptime mismatch", cliUptime.replace(" ", ""), uptime.replace(" ", ""));
	}

	public void verifyUserManual() {
		// TODO Auto-generated method stub
		String mainWindow = browser.getWindowHandle();

		logger.info("Finding link '" + "User Manual" + "'");
		browser.findElement(By.linkText("User Manual")).click();
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Assert.assertEquals("Help page didnt open in new tab", browser.getWindowHandles().size(), 2);

		String helpWindow = null;
		for (String handle : browser.getWindowHandles()) {
			if (!handle.equals(mainWindow))
				helpWindow = handle;
		}

		Assert.assertNotNull("Unable to find the opened browser tab", helpWindow);

		browser.switchTo().window(helpWindow);

		logger.info("Verifying that opened help page : " + browser.getTitle());
		Assert.assertEquals(browser.getTitle(), "Help: Ericsson Network IQ");
		logger.info("Closing the help page");
		browser.close();

		browser.switchTo().window(mainWindow);
	}

	public boolean loginWith(String username, String password) throws InterruptedException {
		String url = getWebAppUrl();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		browser.findElement(By.id("username")).sendKeys(username);
		Thread.sleep(4000);
		browser.findElement(By.id("password")).sendKeys(password);
		Thread.sleep(4000);
		browser.findElement(By.id("submit")).click();
		
		logger.info("Clicked on 'Login' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();
		boolean result = browser.getPageSource().contains("Host Information");

		return result;
	}
}
