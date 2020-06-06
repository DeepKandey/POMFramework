package com.qa.tests;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SearchHomePageActions;
import com.qa.util.LoggerUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.apache.logging.log4j.Logger;

public class DemoTest {

	Properties prop;
	DriverFactory basePage;
	Logger logger;

	@BeforeMethod
	public void setup() {
		basePage = new DriverFactory();
		prop = basePage.init_prop();
		basePage.init_driver(prop);
		// TestUtil.webDriverEvents(basePage.init_driver(prop));
	}

	@AfterMethod
	public void tearDown() {
		DriverFactory.removeDriver();
	}

	@Test()
	@Severity(SeverityLevel.NORMAL)
	@Description("Description of the test case - DemoTest")
	@Story("Test case written for Story - DemoTest")
	@Step("Step at test Body level - DemoTest")
	@Link("https://docs.qameta.io/allure/")
	public void demoTest() {
		SearchHomePageActions searchHomePageActions = new SearchHomePageActions();

		LoggerUtil.log("Demo Test Execution");
		searchHomePageActions.enterTextInDepartureStn("London");
		Assert.assertEquals(true, true);
	}

	@Test(enabled = false)
	@Severity(SeverityLevel.BLOCKER)
	@Description("Description of the test case - annotationTest")
	@Story("Test case written for Story - annotationTest")
	@Step("Step at test Body level - annotationTest")
	@Link("https://docs.qameta.io/allure/")
	public void annotationTest() {
		SearchHomePageActions searchHomePageActions = new SearchHomePageActions();
		LoggerUtil.log("Annotation Transformation Test");
		searchHomePageActions.enterTextInDepartureStn("London");
		Assert.assertEquals(true, true);
	}

	@Test()
	@Severity(SeverityLevel.MINOR)
	@Description("Description of the test case - retryTest")
	@Story("Test case written for Story - retryTest")
	@Step("Step at test Body level - retryTest")
	@Link("https://docs.qameta.io/allure/")
	public void retryTest() {
		SearchHomePageActions searchHomePageActions = new SearchHomePageActions();
		LoggerUtil.log("RetryAnalyzer Test");
		searchHomePageActions.enterTextInDepartureStn("London");
		Assert.assertEquals(true, true);
	}
}