package com.qa.tests;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SearchHomePageActions;
import org.apache.logging.log4j.Logger;

public class DemoTest {

	Properties prop;
	DriverFactory basePage;
	Logger logger;

	@BeforeMethod
	public void setup() {
		basePage = new DriverFactory(logger);
		prop = basePage.init_prop();
		basePage.init_driver(prop);
		// TestUtil.webDriverEvents(basePage.init_driver(prop));

	}

	@AfterMethod
	public void tearDown() {
		DriverFactory.removeDriver();
	}

	@Test()
	public void demoTest() {
		SearchHomePageActions searchHomePageActions = new SearchHomePageActions();
		
		DriverFactory.getLogger().info("Demo Test Execution");
		searchHomePageActions.enterTextInDepartureStn("London");
		Assert.assertEquals(true, true);
	}

	@Test(enabled = false)
	public void annotationTest() {
		SearchHomePageActions searchHomePageActions = new SearchHomePageActions();
		DriverFactory.getLogger().info("Annotation Transformation Test");
		searchHomePageActions.enterTextInDepartureStn("London");
		Assert.assertEquals(true, true);
	}

	@Test()
	public void retryTest() {
		SearchHomePageActions searchHomePageActions = new SearchHomePageActions();
		DriverFactory.getLogger().info("RetryAnalyzer Test");
		searchHomePageActions.enterTextInDepartureStn("London");
		Assert.assertEquals(true, true);
	}
}