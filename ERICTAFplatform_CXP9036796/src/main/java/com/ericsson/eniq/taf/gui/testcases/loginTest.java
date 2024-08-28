package com.ericsson.eniq.taf.gui.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.data.DataHandler;

public class loginTest {
	@Test
	public void login() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/data/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://atvts3340.athtem.eei.ericsson.se:8443/adminui/");

		String username = DataHandler.getAttribute("eniq.platform.adminui.username").toString();
		String password = DataHandler.getAttribute("eniq.platform.adminui.password").toString();
		
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("submit")).click();

		String actualUrl = "https://atvts3358.athtem.eei.ericsson.se:8443/adminui";
		String expectedUrl = driver.getCurrentUrl();
		driver.quit();
		Assert.assertEquals(expectedUrl, actualUrl);
		
	}
}