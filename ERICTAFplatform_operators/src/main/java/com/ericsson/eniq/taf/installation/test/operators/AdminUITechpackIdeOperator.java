package com.ericsson.eniq.taf.installation.test.operators;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;

public class AdminUITechpackIdeOperator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminUITechpackIdeOperator.class);

	private final String ADMINUI_TP = DataHandler.getAttribute("eniq.platform.adminui.teckpackide.path").toString();
	private final String ADMINUI_TP_PORT = DataHandler.getAttribute("eniq.platform.adminui.teckpackide.port")
			.toString();
	private final String ADMINUI_TP_USR = DataHandler.getAttribute("eniq.platform.adminui.teckpackide.username")
			.toString();
	private final String ADMINUI_TP_PWD = DataHandler.getAttribute("eniq.platform.adminui.teckpackide.password")
			.toString();

	private static String downloadPath = "\\seliisfile\\home\\xarunha\\WDP\\Downloads";

	private WebDriver browser;
	private Host eniqHost;

	public void openBrowser() {

		 System.setProperty("webdriver.gecko.driver", "/usr/bin/firefox");
		//System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\data\\chromedriver.exe");
		 browser = new FirefoxDriver();
		
		/*//System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        String downloadFilepath = "\\seliisfile\\home\\xarunha\\WDP\\Downloads";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("safebrowsing.enabled", "false"); 
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
       
		*/
		//browser = new ChromeDriver(cap);
		//browser = new ChromeDriver();
	}

	private String getWebAppUrl() {
		Host eniqHost = DataHandler.getHostByName("eniqs");
		return String.format("http://%s", eniqHost.getIp() + ":" + ADMINUI_TP_PORT + ADMINUI_TP);
	}

	public boolean login() throws InterruptedException {
		String url = getWebAppUrl();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		LOGGER.info(browser.getPageSource().toString());
		// Double click the button to launch an alertbox
		Actions action = new Actions(browser);
		browser.findElement(By.name("start")).click();
		Assert.assertTrue(isFileDownloaded(downloadPath, "techpackide.jnlp"), "Failed to download Expected document");
		return true;
	}

	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag = true;
		}

		return flag;
	}

	public void closeBrowser() {
		browser.close();
	}
}
