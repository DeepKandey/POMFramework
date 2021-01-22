package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.TrainLIneSearchHomePageActions;
import com.qa.util.LoggerUtil;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

import static org.testng.Assert.assertTrue;

public class DemoTest {

  Properties prop;
  DriverFactory basePage;

  @BeforeMethod
  public void setup(Method methodName) throws MalformedURLException {
    basePage = new DriverFactory();
    prop = basePage.initializeProp();
    basePage.initializeDriver(prop, methodName);
    // TestUtil.webDriverEvents(basePage.init_driver(prop));
  }

  @AfterMethod
  public void tearDown() {
    basePage.removeDriver();
  }

  @Test()
  @Severity(SeverityLevel.NORMAL)
  @Description("Description of the test case - DemoTest")
  @Story("Test case written for Story - DemoTest")
  @Step("Step at test Body level - DemoTest")
  @Link("https://docs.qameta.io/allure/")
  public void demoTest() {
    TrainLIneSearchHomePageActions searchHomePageActions = new TrainLIneSearchHomePageActions();

    LoggerUtil.log("Demo Test Execution");
    searchHomePageActions.enterTextInDepartureStn("London");
    assertTrue(true);
  }

  @Test(enabled = false)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Description of the test case - annotationTest")
  @Story("Test case written for Story - annotationTest")
  @Step("Step at test Body level - annotationTest")
  @Link("https://docs.qameta.io/allure/")
  public void annotationTest() {
    TrainLIneSearchHomePageActions searchHomePageActions = new TrainLIneSearchHomePageActions();

    LoggerUtil.log("Annotation Transformation Test");
    searchHomePageActions.enterTextInDepartureStn("London");
    assertTrue(true);
  }

  @Test()
  @Severity(SeverityLevel.MINOR)
  @Description("Description of the test case - retryTest")
  @Story("Test case written for Story - retryTest")
  @Step("Step at test Body level - retryTest")
  @Link("https://docs.qameta.io/allure/")
  public void retryTest() {
    TrainLIneSearchHomePageActions searchHomePageActions = new TrainLIneSearchHomePageActions();

    LoggerUtil.log("RetryAnalyzer Test");
    searchHomePageActions.enterTextInDepartureStn("London");
    assertTrue(true);
  }
}
