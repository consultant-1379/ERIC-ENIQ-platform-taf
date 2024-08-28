package com.ericsson.eniq.taf.installation.test.operators;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.ui.Browser;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.BrowserType;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.cifwk.taf.ui.sdk.Link;
import com.ericsson.cifwk.taf.ui.sdk.Select;
import com.ericsson.cifwk.taf.ui.sdk.ViewModel;
import com.google.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * @author ZJSOLEA
 */
@Singleton
public class AdminUIOperator {
	Logger logger = LoggerFactory.getLogger(AdminUIOperator.class);
	
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
	 * Generate adminui url from host, port and adminui path from the properties file
	 */
	public String getAdminUiUrl() {
		String host = DataHandler.getAttribute("host.eniqs.ip").toString();
		String port = DataHandler.getAttribute("eniq.platform.adminui.port").toString();
		String adminUiPath = DataHandler.getAttribute("eniq.platform.adminui.path").toString();
		String adminUiUrl = "https://" + host + ":" + port + adminUiPath;
		
		return adminUiUrl;
	}

	public AdminUIOperator() {
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
		if (debug) sleep(3);
		
		// Login in AdminUI
		logger.info("Logging in with username eniq");
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		if (debug) sleep(3);
		this.viewModel.getButton("#submit").click();
		
		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(".systemstatustab"));
		logger.info("Login is successful");
		if (debug) sleep(3);
	}
	
	/**
	 * open adminui from a second browser parallelly and make sure that the login fails with
	 * maximum sessio limit exceeded error message
	 */
	public void verifyLoginFromSecondBrowser() {
		String adminUiUrl = getAdminUiUrl();
		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		
		logger.info("Opening adminui in second browser " + adminUiUrl);
		
		//System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		Browser browser2 = UI.newBrowser(BrowserType.HEADLESS);
		BrowserTab browserTab2 = browser2.open(adminUiUrl);
		ViewModel viewModel2 = browserTab2.getGenericView();
		if (debug) sleep(3);

		// Login
		logger.info("Logging in with username " + username);
		viewModel2.getTextBox("#username").setText(username);
		viewModel2.getTextBox("#password").setText(password);
		if (debug) sleep(3);
		viewModel2.getButton("#submit").click();
		
		// Make sure that the login fails with maximum session limit exceeded
		logger.info("Searching for message 'Maximum session limit for eniq has exceeded' in the page");
		List<UiComponent> components = (List<UiComponent>) viewModel2.getViewComponents("font", UiComponent.class);
		boolean errorMessageFound = false;
		for (UiComponent component: components) {
			if (component.getText().contains("Maximum session limit for eniq has exceeded")) {
				errorMessageFound = true;
				break;
			}
		}
		if (debug) sleep(3);
		assertTrue(errorMessageFound, "Could not find the message 'Maximum session limit for eniq has exceeded' when logged in from 2nd browser");
		logger.info("Found 'Maximum session limit for eniq has exceeded'");
		
		logger.info("Closing 2nd browser");
		browser2.close();
		if (debug) sleep(3);
	}
	
	/**
	 * Logout from adminui
	 */
	public void logoutAdminUI() {
		// Logout from browser
		logger.info("Searching logout link in the page");
		Link logoutLink = null;
		for (Link link: (List<Link>) viewModel.getViewComponents(".menulink", Link.class)) {
			if (link.getText().contains("Logout")) {
				logoutLink = link;
				break;
			}
		}		
		if (debug) sleep(3);
		assertTrue(logoutLink != null, "Logut link not found in adminui");
		logger.info("logout link found. Clicking logout link");
		logoutLink.click();
		
		// Check if logout is successful
		browserTab.waitUntilComponentIsDisplayed(viewModel.getViewComponent(".footer"));
		logger.info("logged out successfully");
		if (debug) sleep(3);
		
	}
	
	/**
	 * Creates a new user entry in /eniq/sw/runtime/tomcat/conf/tomcat-users.xml and restarts webserver
	 * 
	 * @param username new username
	 * @param password password for the new user
	 */
	public void addAdminUiUser(String username, String password) {
		logger.info("Adding new user in /eniq/sw/runtime/tomcat/conf/tomcat-users.xml");
		handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; cat /eniq/sw/runtime/tomcat/conf/tomcat-users.xml | sed \"s/<\\/tomcat-users>/  <user username=\\\"" + username + "\\\" password=\\\"" + password + "\\\" roles=\\\"eniq\\\"\\/>\\n<\\/tomcat-users>/g\" > /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.copy'");
		handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; rm /eniq/sw/runtime/tomcat/conf/tomcat-users.xml; mv /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.copy /eniq/sw/runtime/tomcat/conf/tomcat-users.xml'");
		
		this.stopAdminUiServer();
		this.startAdminUiServer();
	}
	
	/**
	 * Removes the user from /eniq/sw/runtime/tomcat/conf/tomcat-users.xml and restarts web server
	 * 
	 * @param username username of the user to be removed
	 */
	public void removeAdminUiUser(String username) {
		logger.info("removing new user from /eniq/sw/runtime/tomcat/conf/tomcat-users.xml");
		handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; cat /eniq/sw/runtime/tomcat/conf/tomcat-users.xml | sed \"s/  <user username=\\\"" + username + "\\\" .* roles=\\\"eniq\\\"\\/>//g\" > /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.copy'");
		handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; rm /eniq/sw/runtime/tomcat/conf/tomcat-users.xml; mv /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.copy /eniq/sw/runtime/tomcat/conf/tomcat-users.xml'");
		
		this.stopAdminUiServer();
		this.startAdminUiServer();
	}
	
	/**
	 * shutsdown adminui webserver
	 */
	public void stopAdminUiServer() {
		logger.info("Stopping webserver");
		String output = handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; /eniq/sw/bin/webserver stop'");
		assertTrue(output.toLowerCase().indexOf("error") == -1, "Errors found when stopping webserver : " + output);

		logger.info("Checking webserver status");
		output = handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; /eniq/sw/bin/webserver status'");
		assertTrue(output.indexOf("webserver is not running") > -1, "Web server didnt stop. Webserver status is : " + output);
		logger.info("Webserver stopped successfully");
	}
	
	/**
	 * Starts adminui webserver
	 */
	public void startAdminUiServer() {
		logger.info("Starting webserver");
		String output = handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; /eniq/sw/bin/webserver start'");
		assertTrue(output.toLowerCase().indexOf("error") == -1, "Errors found when starting webserver : " + output);

		logger.info("Checking webserver status");
		output = handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; /eniq/sw/bin/webserver status'");
		assertTrue(output.indexOf("webserver is running OK") > -1, "Web server didnt start. Webserver status is : " + output);
		logger.info("Web server started successfully");
	}
	
	/**
	 * Login in adminui with provided username and password
	 * 
	 * @param username
	 * @param password
	 */
	public void loginWithNewUser(String username, String password) {
		String adminUiUrl = getAdminUiUrl();
		
		// Open AdminUI
		logger.info("Opening adminui " + adminUiUrl);
		this.browser = UI.newBrowser(BrowserType.FIREFOX);
		this.browserTab = this.browser.open(adminUiUrl);
		this.viewModel = this.browserTab.getGenericView();
		
		// Login in AdminUI
		logger.info("Logging in with username " + username);
		this.viewModel.getTextBox("#username").setText(username);
		this.viewModel.getTextBox("#password").setText(password);
		this.viewModel.getButton("#submit").click();
		
		logger.info("Verifying whether login is successful");
		this.browserTab.waitUntilComponentIsDisplayed(this.viewModel.getViewComponent(".systemstatustab"));
		logger.info("Login is successful");
	}
	
	/**
	 * Change adminui password for given user to the given password
	 * 
	 * @param username
	 * @parma password new password to set for the user
	 */
	public void changeAdminUiPassword(String username, String password) {
		logger.info("Changing adminui password for username eniq to " + password);
		handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; cat /eniq/sw/runtime/tomcat/conf/tomcat-users.xml | sed -z \"s/<user username=\\\"" + username + "\\\" .* roles=\\\"eniq\\\"\\/>/<user username=\\\"" + username + "\\\" password=\\\"" + password + "\\\" roles=\\\"eniq\\\"\\/>/g\" > /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.copy'");
		handler.simpleExec("su - dcuser -c '. /eniq/home/dcuser/.profile; rm /eniq/sw/runtime/tomcat/conf/tomcat-users.xml; mv /eniq/sw/runtime/tomcat/conf/tomcat-users.xml.copy /eniq/sw/runtime/tomcat/conf/tomcat-users.xml'");
		this.stopAdminUiServer();
		this.startAdminUiServer();
	}

	public void openAdminUiInTwoTabs() {
		String adminUiUrl = getAdminUiUrl();
		
		this.loginAdminUI();	
		
		logger.info("Opening adminui in 2nd tab");
		BrowserTab tab2 = this.browser.open(adminUiUrl);
		ViewModel view2 = tab2.getGenericView();
		
		logger.info("Verifying whether the page is logged in 2nd tab");
		tab2.waitUntilComponentIsDisplayed(view2.getViewComponent(".systemstatustab"));
		logger.info("admin ui is loading properly");
		if (debug) sleep(3);
		
		this.logoutAdminUI();
		
		tab2.open(adminUiUrl);
		tab2.waitUntilComponentIsDisplayed(view2.getViewComponent("#login"));
		if (debug) sleep(3);
		logger.info("logged out properly in all tabs");
		
		logger.info("closing browser");
		this.browser.close();
	}
	

	public void verifySessionLogs() {
		int limit = 200;
		
		logger.info("Opening session logs");
		this.viewModel.getLink(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[1]/table/tbody/tr[26]/td/a[1]").click();
		if (debug) sleep(3);
		
		logger.info("Verifying whether the page opened");
		Link sessionLogsLink = this.viewModel.getLink(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/font[1]/a");
		this.browserTab.waitUntilComponentIsDisplayed(sessionLogsLink);
		if (debug) sleep(3);
		assertTrue(sessionLogsLink.getText().equals("Session Logs"), "Session logs page is not opened");
		

		logger.info("Checking whether there are any error messages");
		UiComponent table1 = this.viewModel.getViewComponent(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/table", UiComponent.class);
		assertTrue(table1.getText() .isEmpty(), "The page contains error messages");
		
		logger.info("Finding select box");
		Select selectBox = this.viewModel.getSelect(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[5]/td[2]/select");
		selectBox.selectByValue("0");
		if (debug) sleep(3);
		
		logger.info("Clicking search button");
		this.viewModel.getButton(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[10]/td/input").click();
		
		logger.info("Checking whether there are any logs");
		UiComponent table = this.viewModel.getViewComponent(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/table", UiComponent.class);
		this.browserTab.waitUntilComponentIsDisplayed(table);
		if (table.getChildren().size() == 0) return;
				
		UiComponent tbody = this.viewModel.getViewComponent(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/table/tbody", UiComponent.class);
		int rows = tbody.getChildren().size() - 1;
		logger.info("No.of rows " + rows);
		
		String firstRowText = "";
		if (rows > 0) 
			firstRowText = tbody.getChildren().get(0).getText();
			
		if (rows > limit) assertTrue(firstRowText.indexOf("Row count exceeded " + limit + " -rows") >= 0, "Error message is not displayed properly");
		else assertTrue(firstRowText.indexOf("Row count exceeded") < 0, "Error message is displayed unnecessarily");
		
		if (debug) sleep(3);	
	}

	public void verifyRowLimitExceededErrorMessage() {
		logger.info("Opening monitoring commands page");
		this.viewModel.getLink(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[1]/table/tbody/tr[9]/td/a[1]").click();
		if (debug) sleep(3);
		
		logger.info("Verifying whether the page opened");
		Link breadCombLink = this.viewModel.getLink(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/font[1]/a");
		this.browserTab.waitUntilComponentIsDisplayed(breadCombLink);
		if (debug) sleep(3);
		assertTrue(breadCombLink.getText().equals("Monitoring Commands"), "Monitoring Commands");
		
		logger.info("Finding select box");
		Select selectBox = this.viewModel.getSelect(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[2]/td/font/select");
		selectBox.selectByTitle("Installed modules");
		if (debug) sleep(3);
		
		logger.info("Clicking start button");
		this.viewModel.getButton(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/form/table/tbody/tr[3]/td/font/input").click();
		
		UiComponent listOfModules = this.viewModel.getViewComponent(SelectorType.XPATH, "/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr/td/font", UiComponent.class);
		this.browserTab.waitUntilComponentIsDisplayed(listOfModules);
		if (debug) sleep(3);
		assertTrue(listOfModules.getText().toLowerCase().indexOf("wifi") == -1, "wifi found in list of installed modules");
	}

	public void verifyUserManual() {
		// Find user manual link and click
		logger.info("Tabs : " + this.browser.getAllOpenTabs().size());
		//Link link = this.viewModel.getLink(SelectorType.XPATH, "/html/body/table[1]/tbody/tr/td[1]/table/tbody/tr[46]/td/a");
		Link link = this.getLinkFromText(viewModel, "User Manual");
		assertTrue(link != null, "User Manual link not found");
		logger.info("Clicking user manual link");
		link.click();
		
		// verify that user manual is opening in new tab
		if (debug) sleep(3);
		assertTrue(this.browser.getAllOpenTabs().size() == 2, "User manual didnot open in a new tab");
		
		// get the user manual tab opened
		BrowserTab userManualTab = this.browser.getAllOpenTabs().get(0);
		logger.info("Second tab opened : " + userManualTab.getTitle());
		if (this.browserTab.getTitle().equals(userManualTab.getTitle()))
			userManualTab = this.browser.getAllOpenTabs().get(1);
		
		logger.info("Second tab opened : " + userManualTab.getTitle());
		if (debug) sleep(3);
		logger.info("Checking usermanual tab title");
		assertTrue(userManualTab.getTitle().equalsIgnoreCase("Help: Ericsson Network IQ"), "User manual page did not open correctly");
		userManualTab.markAsClosed();
		logger.info("Usermanual opened successfully");
		
		return;
	}
	
	public Link getLinkFromText(ViewModel model, String text) {
		logger.info("Searching for link '" + text + "' in the page");
		Link link = null;
		for (Link l: (List<Link>) viewModel.getViewComponents("a", Link.class)) {
			if (l.getText().equals(text)) {
				link = l;
				break;
			}
		}		
		if (link != null)
			logger.info("link found.");
		else
			logger.info("link not found");
		return link;
	}
}
