package com.ericsson.eniq.taf.installation.test.operators;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;

/**
 * 
 * @author xsounpk
 *
 */
public class GUIOperator {
	private static final Logger LOGGER = LoggerFactory.getLogger(GUIOperator.class);
	private WebDriver browser;
	private Host eniqHost;

	@Inject
	private Provider<GeneralOperator> generalProvider;

	public void openBrowser() {
		System.setProperty("webdriver.gecko.driver", "/usr/bin/firefox");
		browser = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\xsounpk\\chromedriver.exe");
		// browser = new ChromeDriver();
	}

	private String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("https://%s", eniqHost.getIp() + ":8443/adminui/");
	}

	private String getServerIpUrl() {
		GeneralOperator generalOperator = generalProvider.get();
		String serverIp = generalOperator.executeCommand(
				"host " + DataHandler.getAttribute("host.eniqs.ip").toString() + " | awk '{print $(NF)}'");
		return String.format("https://%s", serverIp + ":8443/adminui/");

	}

	public boolean login() throws InterruptedException {
		String url = getWebAppUrl();
		browser.get(url);
		loginCredentials();
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();
		String finalHost = hostName1.substring(0, hostName1.indexOf("."));
		boolean result = browser.getPageSource().contains(finalHost);
		return result;
	}

	private void loginCredentials() {
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		browser.findElement(By.id("username")).sendKeys(username);
		browser.findElement(By.id("password")).sendKeys(password);
		browser.findElement(By.id("submit")).click();
		LOGGER.info("Clicked on 'Login' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public boolean portLogin() throws InterruptedException {
		String url = getWebAppUrl().replace("https", "http").replace("8443", "8080");
		LOGGER.info("url: " + url);
		browser.get(url);
		loginCredentials();
		String actualUrl = browser.getCurrentUrl();
		LOGGER.info("actualURL: " + actualUrl);
		String expectedUrl[] = actualUrl.split(":");
		LOGGER.info(expectedUrl[2].toString());
		String port[] = expectedUrl[2].split("/");
		LOGGER.info(port[0].toString());
		String actualPort = port[0];
		boolean result = actualUrl.contains(actualPort);
		return result;
	}

	public boolean incorrectLogin() throws InterruptedException {
		GeneralOperator generalOperator = generalProvider.get();
		String value = generalOperator.executeCommand("date +%S");
		String url = getWebAppUrl();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		browser.findElement(By.id("username")).sendKeys(value);
		browser.findElement(By.id("password")).sendKeys(value);
		browser.findElement(By.id("submit")).click();
		LOGGER.info("Logged in with wrong username & password");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		boolean result = browser.getPageSource().contains("Login failed, please check your username and password.");
		return result;
	}

	public boolean serverIpLogin() throws InterruptedException {
		String url = getServerIpUrl();
		browser.get(url);
		loginCredentials();
		LOGGER.info("Logged in via serverIp address");
		String hostName1 = DataHandler.getHostByName("eniqs").getIp();
		String finalHost = hostName1.substring(0, hostName1.indexOf("."));
		boolean result = browser.getPageSource().contains(finalHost);
		return result;
	}

	public boolean linkDataVerification() throws InterruptedException {
		List<String> inter = new ArrayList<String>();
		List<String> mainInterface = new ArrayList<String>();
		List<String> optValue = new ArrayList<String>();
		GeneralOperator generalOperator = generalProvider.get();
		String url = getWebAppUrl();
		browser.get(url);
		loginCredentials();
		WebElement monitoring = browser.findElement(By.linkText("Monitoring Commands"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		monitoring.click();
		eniqSWVersion(generalOperator);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		installedTechPack(generalOperator);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		installedFeatures(generalOperator);
		WebElement logging = browser.findElement(By.linkText("Logging Configuration"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logging.click();
		loggingConfig(inter, mainInterface);
		Select dropdown_interface_option = new Select(
				browser.findElement(By.name("logLevel:" + (mainInterface.get(mainInterface.size() - 1)))));
		WebElement selectedValue = dropdown_interface_option.getFirstSelectedOption();
		String curentValue = selectedValue.getText();
		LOGGER.info("curentValue after changing: " + curentValue);
		for (String str : mainInterface) {
			Select dropdown_interface_value = new Select(browser.findElement(By.name("logLevel:" + str)));
			WebElement allValue = dropdown_interface_value.getFirstSelectedOption();
			String actualSelectedValue = allValue.getText();
			optValue.add(actualSelectedValue);
		}
		String finalval = mainInterface.get(mainInterface.size() - 1) + ":" + optValue.get(optValue.size() - 1);
		LOGGER.info("finalval: " + finalval);
		boolean result = finalval.contains(curentValue);
		return result;
	}

	private void loggingConfig(List<String> inter, List<String> mainInterface) {
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("window.scrollBy(0,250)", "");
		List<WebElement> activeColumns = browser.findElements(
				By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/font[2]/table[3]/tbody/tr/td[@class='basic']"));
		for (WebElement interfaces : activeColumns) {
			inter.add(interfaces.getText());
		}
		for (String actualInterface : inter) {
			if (actualInterface.startsWith("INTF")) {
				mainInterface.add(actualInterface);
			}
		}
		Select dropdown_interface = new Select(
				browser.findElement(By.name("logLevel:" + (mainInterface.get(mainInterface.size() - 1)))));
		WebElement select = dropdown_interface.getFirstSelectedOption();
		String select1 = select.getText();
		LOGGER.info("cuurentValue before changing: " + select1);
		dropdown_interface.selectByVisibleText("FINEST");
		WebElement save = browser.findElement(By.name("save"));
		save.click();
	}

	private void installedFeatures(GeneralOperator generalOperator) {
		List<String> install = new ArrayList<String>();
		List<String> feature = new ArrayList<String>();
		Select dropdown_setType2 = new Select(browser.findElement(By.name("command")));
		dropdown_setType2.selectByVisibleText("Installed Features");
		WebElement start2 = browser.findElement(By.tagName("input"));
		start2.click();
		LOGGER.info("Clicked on 'start' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String installedFeatures = generalOperator
				.executeCommandDcuser("cd /eniq/sw/installer/; cat installed_features");
		String features = browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr/td"))
				.getText();
		String[] inst = installedFeatures.trim().split("\\r?\\n");
		for (int i = 0; i < inst.length; i++) {
			install.add(inst[i]);
		}
		LOGGER.info("install: " + install.toString() + "instsize " + install.size());
		String[] instFeatures = features.trim().split("\\r?\\n");
		for (int i = 0; i < instFeatures.length; i++) {
			feature.add(instFeatures[i]);
		}
		LOGGER.info("feature: " + feature.toString() + "size " + feature.size());
		boolean inst_feat = false;
		for (String inststat : install) {
			if (feature.contains(inststat.trim())) {
				inst_feat = true;
			} else {
				inst_feat = false;
			}
		}
		assertTrue(inst_feat, "Installed Features are verified");
	}

	private void installedTechPack(GeneralOperator generalOperator) {
		String techpacks = null;
		String product = null;
		String rstate = null;
		List<String> actualTechpacks = new ArrayList<String>();
		List<String> productNum = new ArrayList<String>();
		List<String> actualRstate = new ArrayList<String>();
		List<String> cmdTechpacks = new ArrayList<String>();
		List<String> cmdProductNum = new ArrayList<String>();
		List<String> cmdRstate = new ArrayList<String>();
		List<String> uiActualRstate = new ArrayList<String>();
		boolean rstateCheck = false;
		boolean productCheck = false;
		boolean techCheck = false;
		Select dropdown_setType1 = new Select(browser.findElement(By.name("command")));
		dropdown_setType1.selectByVisibleText("Installed tech packs");
		WebElement start1 = browser.findElement(By.tagName("input"));
		start1.click();
		LOGGER.info("Clicked on 'start' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		for (int i = 3;; i++) {
			techpacks = browser
					.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[" + i + "]/td[1]"))
					.getText();
			if (!techpacks.trim().isEmpty()) {
				actualTechpacks.add(techpacks);
				product = browser
						.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[" + i + "]/td[2]"))
						.getText();
				productNum.add(product);
				rstate = browser
						.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[" + i + "]/td[3]"))
						.getText();
				actualRstate.add(rstate);

			} else {
				break;
			}
		}
		LOGGER.info("techpacks: " + actualTechpacks.toString());
		LOGGER.info("productNum: " + productNum.toString());
		LOGGER.info("actualRstate: " + actualRstate.toString());
		String rState_cmd = generalOperator.executeCommandDcuser("cd /eniq/sw/installer/; ./installed_techpacks -v");
		String techPack_cmd = generalOperator.executeCommandDcuser("cd /eniq/sw/installer/; ./installed_techpacks -s");
		String productNum_cmd = generalOperator
				.executeCommandDcuser("cd /eniq/sw/installer/; ./installed_techpacks -p");
		String[] rstat = rState_cmd.trim().split("\\r?\\n");
		for (int i = 0; i < rstat.length; i++) {
			cmdRstate.add(rstat[i]);
		}
		LOGGER.info("install: " + cmdRstate.toString() + "cmdRstate " + cmdRstate.size());
		String[] tech = techPack_cmd.trim().split("\\r?\\n");
		for (int i = 0; i < tech.length; i++) {
			cmdTechpacks.add(tech[i]);
		}
		LOGGER.info("cmdTechpacks: " + cmdTechpacks.toString() + "cmdTechpacks " + cmdTechpacks.size());
		String[] productnumber = productNum_cmd.trim().split("\\r?\\n");
		for (int i = 0; i < productnumber.length; i++) {
			cmdProductNum.add(productnumber[i]);
		}
		LOGGER.info("install: " + cmdProductNum.toString() + "cmdProductNum " + cmdProductNum.size());
		for (String expectedRstate : actualRstate) {
			String[] splitRstate = expectedRstate.split("_");
			uiActualRstate.add(splitRstate[0]);
		}
		LOGGER.info("uiActualRstate: " + uiActualRstate.toString() + "uiActualRstate " + uiActualRstate.size());
		for (String rstatenum : cmdRstate) {
			if (uiActualRstate.contains(rstatenum.trim())) {
				rstateCheck = true;
			} else {
				rstateCheck = false;
			}
		}
		assertTrue(rstateCheck, "rstate is verified");
		for (String techName : cmdTechpacks) {
			if (actualTechpacks.contains(techName.trim())) {
				techCheck = true;
			} else {
				techCheck = false;
			}
		}
		assertTrue(techCheck, " TechPacks are verified");
		for (String prodNum : cmdProductNum) {
			if (productNum.contains(prodNum.trim())) {
				productCheck = true;
			} else {
				productCheck = false;
			}
		}
		assertTrue(productCheck, "ProductNumbers are verified");
	}

	private void eniqSWVersion(GeneralOperator generalOperator) {
		List<String> statuslist = new ArrayList<String>();
		List<String> valuelist = new ArrayList<String>();
		boolean check = false;
		Select dropdown_setType = new Select(browser.findElement(By.name("command")));
		dropdown_setType.selectByVisibleText("ENIQ software version");
		WebElement start = browser.findElement(By.tagName("input"));
		start.click();
		LOGGER.info("Clicked on 'start' button");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String values = browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr/td")).getText();
		String status = generalOperator.executeCommandDcuser("cat /eniq/admin/version/eniq_status");
		String[] stat = status.trim().split("\\r?\\n");
		for (int i = 0; i < stat.length; i++) {
			statuslist.add(stat[i]);
		}
		LOGGER.info("statuslist: " + statuslist.toString());
		String[] val = values.trim().split("\\r?\\n");
		for (int i = 0; i < val.length; i++) {
			valuelist.add(val[i]);
		}
		LOGGER.info("valuelist: " + valuelist.toString());
		for (String str : statuslist) {
			if (valuelist.contains(str.trim())) {
				check = true;
			} else {
				check = false;
			}
		}
		assertTrue(check, " ENIQ SW Version is verified");
	}

	public void eniqMonitorServicesDWHDB() {
		List<String> script = new ArrayList<String>();
		List<String> variables = new ArrayList<String>();
		List<String> dataLoad = new ArrayList<String>();
		List<String> actualDataLoad = new ArrayList<String>();
		String url = getWebAppUrl();
		browser.get(url);
		loginCredentials();
		WebElement monitor = browser.findElement(By.linkText("Eniq Monitoring Services"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		monitor.click();
		Select dropdown_monitor = new Select(browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[2]/td[1]/font/select")));
		dropdown_monitor.selectByVisibleText("DWHDBConn");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement row = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[2]/input"));
		row.clear();
		row.sendKeys("2");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement getInfo = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[1]/input[2]"));
		getInfo.click();
		LOGGER.info("Clicked on 'Get Information' button");

		String details = browser.getPageSource().toString();
		String[] splitScript = details.trim().split("\\r?\\n");
		for (int i = 0; i < splitScript.length; i++) {
			script.add(splitScript[i]);
		}
		for (String var : script) {
			if (var.contains("var x=")) {
				variables.add(var.trim());
			}
		}
		for (String val : variables) {
			String[] valSplit = val.split("=");
			dataLoad.add(valSplit[1].replace(";", "").trim());
		}
		for (String dataVal : dataLoad) {
			try {
				Integer.parseInt(dataVal);
				actualDataLoad.add(dataVal);
			} catch (NumberFormatException e) {
			}
		}
		LOGGER.info("dataLoad1: " + actualDataLoad.toString());
		String newDataval = browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/p[2]")).getText();
		String[] actualData = newDataval.trim().split("\\s+");
		Integer expectedData = Integer.parseInt(actualData[2]);
		LOGGER.info("expectedData: " + expectedData);
		for (String val2 : actualDataLoad) {
			Integer dataConn = Integer.parseInt(val2);
			if (dataConn <= 0) {
				LOGGER.info("No Connection exists for DWHDBConn");
			} else {
				assertFalse(dataConn >= expectedData, "Max limit exceeded for DWHDBConn");
			}
		}
	}

	public void eniqMonitorServicesRepDB() {
		List<String> script = new ArrayList<String>();
		List<String> variables = new ArrayList<String>();
		List<String> dataLoad = new ArrayList<String>();
		List<String> actualDataLoad = new ArrayList<String>();
		String url = getWebAppUrl();
		browser.get(url);
		loginCredentials();
		WebElement monitor = browser.findElement(By.linkText("Eniq Monitoring Services"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		monitor.click();
		Select dropdown_monitor = new Select(browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[2]/td[1]/font/select")));
		dropdown_monitor.selectByVisibleText("RepDBConn");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement row = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[2]/input"));
		row.clear();
		row.sendKeys("2");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement getInfo = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[1]/input[2]"));
		getInfo.click();
		LOGGER.info("Clicked on 'Get Information' button");

		String details = browser.getPageSource().toString();
		String[] splitScript = details.trim().split("\\r?\\n");
		for (int i = 0; i < splitScript.length; i++) {
			script.add(splitScript[i]);
		}
		for (String var : script) {
			if (var.contains("var x=")) {
				variables.add(var.trim());
			}
		}
		for (String val : variables) {
			String[] valSplit = val.split("=");
			dataLoad.add(valSplit[1].replace(";", "").trim());
		}
		for (String dataVal : dataLoad) {
			try {
				Integer.parseInt(dataVal);
				actualDataLoad.add(dataVal);
			} catch (NumberFormatException e) {
			}
		}
		LOGGER.info("dataLoad1: " + actualDataLoad.toString());
		String newDataval = browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/p[2]")).getText();
		String[] actualData = newDataval.trim().split("\\s+");
		Integer expectedData = Integer.parseInt(actualData[2]);
		LOGGER.info("expectedData: " + expectedData);
		for (String val2 : actualDataLoad) {
			Integer dataConn = Integer.parseInt(val2);
			if (dataConn <= 0) {
				LOGGER.info("No Connection exists for RepDBConn");
			} else {
				assertFalse(dataConn >= expectedData, "Max limit exceeded for RepDBConn");
			}
		}
	}

	public void eniqMonitorServicesSchedulerHeap() {
		List<String> script = new ArrayList<String>();
		List<String> variables = new ArrayList<String>();
		List<String> dataLoad = new ArrayList<String>();
		List<String> actualDataLoad = new ArrayList<String>();
		String url = getWebAppUrl();
		browser.get(url);
		loginCredentials();
		WebElement monitor = browser.findElement(By.linkText("Eniq Monitoring Services"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		monitor.click();
		Select dropdown_monitor = new Select(browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[2]/td[1]/font/select")));
		dropdown_monitor.selectByVisibleText("SchedulerHeap");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement row = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[2]/input"));
		row.clear();
		row.sendKeys("2");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement getInfo = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[1]/input[2]"));
		getInfo.click();
		LOGGER.info("Clicked on 'Get Information' button");

		String details = browser.getPageSource().toString();
		String[] splitScript = details.trim().split("\\r?\\n");
		for (int i = 0; i < splitScript.length; i++) {
			script.add(splitScript[i]);
		}
		for (String var : script) {
			if (var.contains("var x=")) {
				variables.add(var.trim());
			}
		}
		for (String val : variables) {
			String[] valSplit = val.split("=");
			dataLoad.add(valSplit[1].replace(";", "").trim());
		}
		for (String dataVal : dataLoad) {
			try {
				Integer.parseInt(dataVal);
				actualDataLoad.add(dataVal);
			} catch (NumberFormatException e) {
			}
		}
		LOGGER.info("dataLoad1: " + actualDataLoad.toString());
		String newDataval = browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/p[2]")).getText();
		String[] actualData = newDataval.trim().split("\\s+");
		String schHeap = actualData[4].replace("MB", "");
		Integer heapVal = Integer.parseInt(schHeap.trim());
		LOGGER.info("heapVal: " + heapVal);
		LOGGER.info("expectedData: " + heapVal);
		for (String val2 : actualDataLoad) {
			Integer dataConn = Integer.parseInt(val2);
			if (dataConn <= 0) {
				LOGGER.info("No Connection exists for SchedulerHeap");
			} else {
				assertFalse(dataConn >= heapVal, "Max limit exceeded for SchedulerHeap");
			}
		}
	}

	public void eniqMonitorServicesEngineHeap() {
		List<String> script = new ArrayList<String>();
		List<String> variables = new ArrayList<String>();
		List<String> dataLoad = new ArrayList<String>();
		List<String> actualDataLoad = new ArrayList<String>();
		String url = getWebAppUrl();
		browser.get(url);
		loginCredentials();
		WebElement monitor = browser.findElement(By.linkText("Eniq Monitoring Services"));
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		monitor.click();
		Select dropdown_monitor = new Select(browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[2]/td[1]/font/select")));
		dropdown_monitor.selectByVisibleText("EngineHeap");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement row = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[2]/input"));
		row.clear();
		row.sendKeys("2");
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement getInfo = browser
				.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td[1]/input[2]"));
		getInfo.click();
		LOGGER.info("Clicked on 'Get Information' button");

		String details = browser.getPageSource().toString();
		String[] splitScript = details.trim().split("\\r?\\n");
		for (int i = 0; i < splitScript.length; i++) {
			script.add(splitScript[i]);
		}
		for (String var : script) {
			if (var.contains("var x=")) {
				variables.add(var.trim());
			}
		}
		for (String val : variables) {
			String[] valSplit = val.split("=");
			dataLoad.add(valSplit[1].replace(";", "").trim());
		}
		for (String dataVal : dataLoad) {
			try {
				Integer.parseInt(dataVal);
				actualDataLoad.add(dataVal);
			} catch (NumberFormatException e) {
			}
		}
		LOGGER.info("dataLoad1: " + actualDataLoad.toString());
		String newDataval = browser.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/p[2]")).getText();
		String[] actualData = newDataval.trim().split("\\s+");
		String engineHeap = actualData[4].replace("M", "");
		Integer heapVal = Integer.parseInt(engineHeap.trim());
		LOGGER.info("heapVal: " + heapVal);
		LOGGER.info("expectedData: " + heapVal);
		for (String val2 : actualDataLoad) {
			Integer dataConn = Integer.parseInt(val2);
			if (dataConn <= 0) {
				LOGGER.info("No Connection exists for EngineHeap");
			} else {
				assertFalse(dataConn >= heapVal, "Max limit exceeded for EngineHeap");
			}
		}

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

}