package com.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.DriverFactory;
import com.qa.util.LoggerUtil;
import com.qa.util.TestUtil;
import com.qa.util.WebEventListener;

public class DemoTest {

	public static EventFiringWebDriver eDriver;
	public static WebEventListener eventListener;

	@BeforeMethod
	public void setup() {
		WebDriver driver = TestUtil.webDriverEvents(DriverFactory.getDriver());
		driver.get("https://www.google.com");
	}

	@AfterMethod
	public void tearDown() {
		DriverFactory.removeDriver();
	}

	@Test(alwaysRun = true)
	public void demoTest() {
		LoggerUtil.logMessage("Demo Test Execution");
		Assert.assertEquals(true, false);
	}

	@Test(enabled = false)
	public void annotationTest() {
		LoggerUtil.logMessage("Annotation Transformation Test");
		Assert.assertEquals(false, true);
	}

	@Test()
	public void retryTest() {
		LoggerUtil.logMessage("RetryAnalyzer Test");
		Assert.assertEquals(false, true);
	}
}