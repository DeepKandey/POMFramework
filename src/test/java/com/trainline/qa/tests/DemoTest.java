package com.trainline.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.trainline.qa.base.DriverFactory;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.RetryAnalyzer;
import com.trainline.qa.util.TestUtil;
import com.trainline.qa.util.WebEventListener;

public class DemoTest {

	public static EventFiringWebDriver eDriver;
	public static WebEventListener eventListener;

	@BeforeMethod
	public void setup() {
		DriverFactory.getInstance().setDriver("chrome");
		WebDriver driver = TestUtil.webDriverEvents(DriverFactory.getInstance().getDriver());
		driver.get("https://www.google.com");
	}

	@AfterMethod
	public void tearDown() {
		DriverFactory.getInstance().removeDriver();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, enabled=false)
	public void demoTest() {
		LoggerUtil.logMessage("Demo Test Execution");
		Assert.assertEquals(true, false);
	}
	
	@Test(enabled = false)
	public void AnnotationTtest() {
		LoggerUtil.logMessage("Annotation Transformation Test");
		Assert.assertEquals(true, true);
	}
}