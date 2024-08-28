package com.ericsson.eniq.taf.installation.test.operators;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.ui.Browser;
import com.ericsson.cifwk.taf.ui.BrowserCookie;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.BrowserType;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.sdk.Link;
import com.ericsson.cifwk.taf.ui.sdk.ViewModel;
import com.google.inject.Singleton;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author xsounpk
 */
@Singleton
public class AdminUILinkOperator {
	Logger logger = LoggerFactory.getLogger(AdminUIOperator.class);

	@Inject
	private Provider<GeneralOperator> generalProvider;

	private static boolean debug = false;

	private static void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Browser browser;
	public BrowserTab browserTab;
	public ViewModel viewModel;

	private Host eniqshost;
	private CLICommandHelper handler;

	/**
	 * Generate adminui url from host, port and adminui path from the properties
	 * file
	 */
	public String getAdminUiUrl() {
		String host = DataHandler.getAttribute("host.eniqs.ip").toString();
		String port = DataHandler.getAttribute("eniq.platform.adminui.port").toString();
		String adminUiPath = DataHandler.getAttribute("eniq.platform.adminui.path").toString();
		String adminUiUrl = "https://" + host + ":" + port + adminUiPath;

		return adminUiUrl;
	}

	public String getAlarmUrl() {
		String host = DataHandler.getAttribute("host.eniqs.ip").toString();
		String port = DataHandler.getAttribute("eniq.platform.adminui.port").toString();
		String alarmUiPath = DataHandler.getAttribute("eniq.platform.alarmcfg.path").toString();
		String alarmUiUrl = "https://" + host + ":" + port + alarmUiPath;

		return alarmUiUrl;
	}

	public String getEBSUrl() {
		String host = DataHandler.getAttribute("host.eniqs.ip").toString();
		String port = DataHandler.getAttribute("eniq.platform.adminui.port").toString();
		String ebsUiPath = DataHandler.getAttribute("eniq.platform.ebs.path").toString();
		String ebsUiUrl = "https://" + host + ":" + port + ebsUiPath;

		return ebsUiUrl;
	}

	public String getBusyHourUrl() {
		String host = DataHandler.getAttribute("host.eniqs.ip").toString();
		String port = DataHandler.getAttribute("eniq.platform.adminui.port").toString();
		String busyHourUiPath = DataHandler.getAttribute("eniq.platform.busyhour.path").toString();
		String busyHourUiUrl = "https://" + host + ":" + port + busyHourUiPath;

		return busyHourUiUrl;
	}

	public AdminUILinkOperator() {
		eniqshost = DataHandler.getHostByType(HostType.RC);
		handler = new CLICommandHelper(eniqshost);
	}

	/**
	 * Open adminui and login
	 */
	public void loginAdminUI() {
		String adminUiUrl = getAdminUiUrl();
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();

		// Open AdminUI
		logger.info("Opening adminui " + adminUiUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(adminUiUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		// Login in AdminUI
		logger.info("Logging in with username eniq");
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		if (debug)
			sleep(3);
		this.viewModel.getButton("#submit").click();

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(".systemstatustab"));
		logger.info("Login is successful");
		if (debug)
			sleep(3);
	}

	/**
	 * Logout from adminui
	 */
	public void logoutAdminUI() {
		// Logout from browser
		logger.info("Searching logout link in the page");
		Link logoutLink = null;
		for (Link link : (List<Link>) viewModel.getViewComponents(".menulink", Link.class)) {
			if (link.getText().contains("Logout")) {
				logoutLink = link;
				break;
			}
		}
		if (debug)
			sleep(3);
		assertTrue(logoutLink != null, "Logut link not found in adminui");
		logger.info("logout link found. Clicking logout link");
		logoutLink.click();

		// Check if logout is successful
		browserTab.waitUntilComponentIsDisplayed(viewModel.getViewComponent(".footer"));
		logger.info("logged out successfully");
		if (debug)
			sleep(3);

	}

	/**
	 * open adminui from a second browser parallely and verify the login after
	 * executing session limit script
	 */
	public void verifySessionLimitFromSecondBrowser() {
		String adminUiUrl = getAdminUiUrl();
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		String sessionLimit = DataHandler.getAttribute("eniq.platform.adminui.session.limit").toString();

		logger.info("Opening adminui in second browser " + adminUiUrl);

		Browser browser2 = UI.newBrowser(BrowserType.HEADLESS);
		BrowserTab browserTab2 = browser2.open(adminUiUrl);
		ViewModel viewModel2 = browserTab2.getGenericView();
		if (debug)
			sleep(3);

		// Login
		logger.info("Logging in with username " + username);
		viewModel2.getTextBox("#username").setText(username);
		viewModel2.getTextBox("#password").setText(password);
		if (debug)
			sleep(3);
		viewModel2.getButton("#submit").click();

		// Make sure that the login fails with maximum session limit exceeded
		logger.info("Searching for message 'Maximum session limit for eniq has exceeded' in the page");
		List<UiComponent> components = (List<UiComponent>) viewModel2.getViewComponents("font", UiComponent.class);
		boolean errorMessageFound = false;
		for (UiComponent component : components) {
			if (component.getText().contains("Maximum session limit for eniq has exceeded")) {
				errorMessageFound = true;
				break;
			}
		}
		if (!errorMessageFound && !(sessionLimit.equals("1"))) {
			assertFalse(errorMessageFound, "Login is successful with the updated session limit in second browser");
			logger.info("Verifying whether login is successful");
			this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(".systemstatustab"));
			logger.info("Login is successful");
			if (debug)
				sleep(3);
		} else {
			if (debug)
				sleep(3);
			assertTrue(errorMessageFound,
					"Could not find the message 'Maximum session limit for eniq has exceeded' when logged in from 2nd browser");
			logger.info("Found 'Maximum session limit for eniq has exceeded'");
			logger.info("Closing 2nd browser");
			browser2.close();
			if (debug)
				sleep(3);
		}
	}

	/**
	 * Open adminui,alarmCfg,busyHour,EBS and login/launch
	 */
	public void loginAllUrl() {
		String adminUiUrl = getAdminUiUrl();
		String alarmUrl = getAlarmUrl();
		String busyUrl = getBusyHourUrl();
		String ebsUrl = getEBSUrl();
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();

		// Open AdminUI
		logger.info("Opening adminui " + adminUiUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(adminUiUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		// Login in AdminUI
		logger.info("Logging in with username eniq");
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		if (debug)
			sleep(3);
		this.viewModel.getButton("#submit").click();

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(".systemstatustab"));
		logger.info("Login is successful");
		if (debug)
			sleep(3);
		logoutAdminUI();
		// Open busyHour
		logger.info("Opening busyUrl " + busyUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(busyUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		// Login in busyHour
		logger.info("Logging in with username eniq");
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		if (debug)
			sleep(3);
		this.viewModel.getButton("#submit").click();

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"//*[@id=\"masthead\"]/div[1]/img", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		// Open EBS
		logger.info("Opening ebsUrl " + ebsUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(ebsUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"/html/body/form/center/table/tbody/tr[1]/td/font", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		// Open alarmCfg
		logger.info("Opening alarmUrl " + alarmUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(alarmUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"//*[@id=\"masthead\"]/div[1]/img", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

	}

	/**
	 * Login alarmCfg
	 */
	public void loginAlarmCfg() {
		String alarmUrl = getAlarmUrl();
		String user = DataHandler.getAttribute("eniq.platform.alarmcfg.username").toString();
		String passw = DataHandler.getAttribute("eniq.platform.alarmcfg.password").toString();
		String sys = DataHandler.getAttribute("eniq.platform.alarmcfg.system").toString();
		logger.info("Opening alarmUrl " + alarmUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(alarmUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"//*[@id=\"masthead\"]/div[1]/img", UiComponent.class));
		//logger.info("Login is successful");
		if (debug)
			sleep(3);

		UiComponent system = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(
				SelectorType.XPATH, "/html/body/div/div[3]/form/div/table/tbody/tr[1]/td[2]/input", UiComponent.class));
		system.sendKeys(sys);
		if (debug)
			sleep(3);

		UiComponent userName = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(
				SelectorType.XPATH, "/html/body/div/div[3]/form/div/table/tbody/tr[2]/td[2]/input", UiComponent.class));
		userName.sendKeys(user);
		if (debug)
			sleep(3);

		UiComponent pass = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(
				SelectorType.XPATH, "/html/body/div/div[3]/form/div/table/tbody/tr[3]/td[2]/input", UiComponent.class));
		pass.sendKeys(passw);
		if (debug)
			sleep(3);

		UiComponent login = this.browserTab
				.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
						"/html/body/div/div[3]/form/div/table/tbody/tr[5]/td/center/input", UiComponent.class));
		login.click();
		if (debug)
			sleep(3);

		UiComponent logggedIn = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel
				.getViewComponent(SelectorType.XPATH, "/html/body/div/div[1]/div[2]/div[2]", UiComponent.class));
		//logger.info("Logged in as Eniq_Alarm user");
		assertTrue(logggedIn.getText().contains("ENIQ_ALARM"), "Could not log in as Eniq_Alarm user");
		if (debug)
			sleep(3);

		UiComponent logout = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel
				.getViewComponent(SelectorType.XPATH, "/html/body/div/div[1]/div[2]/div[3]/a", UiComponent.class));
		logout.click();
		//logger.info("Logout is successful");
		if (debug)
			sleep(3);
	}

	/**
	 * Login alarmCfg
	 */
	public void incorrectLoginAlarmCfg() {
		String alarmUrl = getAlarmUrl();
		GeneralOperator generalOperator = generalProvider.get();
		String value = generalOperator.executeCommand("date +%S");
		String sys = DataHandler.getAttribute("eniq.platform.alarmcfg.system").toString();
		logger.info("Opening alarmUrl " + alarmUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(alarmUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"//*[@id=\"masthead\"]/div[1]/img", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		UiComponent system = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(
				SelectorType.XPATH, "/html/body/div/div[3]/form/div/table/tbody/tr[1]/td[2]/input", UiComponent.class));
		system.sendKeys(sys);
		if (debug)
			sleep(3);

		UiComponent userName = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(
				SelectorType.XPATH, "/html/body/div/div[3]/form/div/table/tbody/tr[2]/td[2]/input", UiComponent.class));
		userName.sendKeys(value);
		if (debug)
			sleep(3);

		UiComponent pass = this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(
				SelectorType.XPATH, "/html/body/div/div[3]/form/div/table/tbody/tr[3]/td[2]/input", UiComponent.class));
		pass.sendKeys(value);
		if (debug)
			sleep(3);

		UiComponent login = this.browserTab
				.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
						"/html/body/div/div[3]/form/div/table/tbody/tr[5]/td/center/input", UiComponent.class));
		login.click();
		if (debug)
			sleep(3);

		UiComponent logggedIn = this.browserTab.waitUntilComponentIsDisplayed(
				this.viewModel.getViewComponent(SelectorType.XPATH, "//*[@id=\"main_column\"]/div", UiComponent.class));
		assertTrue(logggedIn.getText().contains("Could not log in"),
				"Username and Password is incorrect, unable to login");
		if (debug)
			sleep(3);
	}

	/**
	 * verify session cookies are set to secured for adminui,alarmCfg,busyHour,EBS
	 */
	public void sessionCookiesAllUrl() {
		String adminUiUrl = getAdminUiUrl();
		String alarmUrl = getAlarmUrl();
		String busyUrl = getBusyHourUrl();
		String ebsUrl = getEBSUrl();
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		String admin = DataHandler.getAttribute("eniq.platform.adminui.path").toString();
		String ebs = DataHandler.getAttribute("eniq.platform.ebs.path").toString();
		String alarm = DataHandler.getAttribute("eniq.platform.alarmcfg.path").toString();

		// Open AdminUI
		logger.info("Opening adminui " + adminUiUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(adminUiUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		// Login in AdminUI
		logger.info("Logging in with username eniq");
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		if (debug)
			sleep(3);

		this.viewModel.getButton("#submit").click();
		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(".systemstatustab"));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		boolean adminSec = true;
		String adminUIPath = null;
		if (this.browserTab.getCookies().isEmpty()) {
			logger.info("adminUI does not contain any session cookies");
		} else {
			for (BrowserCookie adminCookies : this.browserTab.getCookies()) {
				adminSec = adminCookies.isSecure();
				adminUIPath = adminCookies.getPath().toString();
				logger.info("path: " + adminCookies.getPath().toString());
				logger.info("admin secured: " + adminSec);
				if (adminSec) {
					logger.info("admin cookies are secured " + adminSec);
				} else {
					logger.info("admin cookies are not secured " + adminSec);
					break;
				}
			}
			assertTrue(adminSec, "session cookies are secured for alarmCfg url");
			assertTrue(adminUIPath.trim().equals(admin), "session cookies are set to httpOnly for adminUI");
		}
		logoutAdminUI();
		// Open busyHour
		logger.info("Opening busyUrl " + busyUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(busyUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		// Login in busyHour
		logger.info("Logging in with username eniq");
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		if (debug)
			sleep(3);

		this.viewModel.getButton("#submit").click();
		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"//*[@id=\"masthead\"]/div[1]/img", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		boolean busySec = true;
		String busyPath = null;
		if (this.browserTab.getCookies().isEmpty()) {
			logger.info("BusyHour does not contain any session cookies");
		} else {
			for (BrowserCookie busyCookies : this.browserTab.getCookies()) {
				busySec = busyCookies.isSecure();
				busyPath = busyCookies.getPath().toString();
				logger.info("path: " + busyCookies.getPath().toString());
				logger.info("busy secured: " + busySec);
				if (busySec) {
					logger.info("busy cookies are secured " + busySec);
				} else {
					logger.info("busy cookies are not secured " + busySec);
					break;
				}
			}
			assertTrue(busySec, "session cookies are secured for alarmCfg url");
			assertTrue(busyPath.trim().equals(admin), "session cookies are set to httpOnly for busyHour");
		}
		// Open EBS
		logger.info("Opening ebsUrl " + ebsUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(ebsUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"/html/body/form/center/table/tbody/tr[1]/td/font", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		boolean ebsSec = true;
		String ebsPath = null;
		if (this.browserTab.getCookies().isEmpty()) {
			logger.info("EBS does not contain any session cookies");
		} else {
			for (BrowserCookie ebsCookies : this.browserTab.getCookies()) {
				ebsSec = ebsCookies.isSecure();
				ebsPath = ebsCookies.getPath().toString();
				logger.info("path: " + ebsCookies.getPath().toString());
				logger.info("EBS secured: " + ebsSec);
				if (ebsSec) {
					logger.info("EBS cookies are secured " + ebsSec);
				} else {
					logger.info("EBS cookies are not secured " + ebsSec);
					break;
				}
			}
			assertTrue(ebsSec, "session cookies are secured for EBS url");
			assertTrue(ebsPath.trim().equals(ebs), "session cookies are set to httpOnly for EBS");
		}

		// Open alarmCfg
		logger.info("Opening alarmUrl " + alarmUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(alarmUrl);
		this.viewModel = this.browserTab.getGenericView();
		if (debug)
			sleep(3);

		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(SelectorType.XPATH,
				"//*[@id=\"masthead\"]/div[1]/img", UiComponent.class));
		logger.info("Login is successful");
		if (debug)
			sleep(3);

		boolean alarmSec = true;
		String alarmPath = null;
		if (this.browserTab.getCookies().isEmpty()) {
			logger.info("alarmCfg does not contain any session cookies");
		} else {
			for (BrowserCookie alarmCookies : this.browserTab.getCookies()) {
				alarmSec = alarmCookies.isSecure();
				alarmPath = alarmCookies.getPath().toString();
				logger.info("path: " + alarmCookies.getPath().toString());
				logger.info("alarm secured: " + alarmSec);
				if (alarmSec) {
					logger.info("alarmCfg cookies are secured " + alarmSec);
				} else {
					logger.info("alarmCfg cookies are not secured " + ebsSec);
					break;
				}
			}
			assertTrue(alarmSec, "session cookies are secured for alarmCfg url");
			assertTrue(alarmPath.trim().equals(alarm), "session cookies are set to httpOnly for alarmCfg");
		}
	}

}