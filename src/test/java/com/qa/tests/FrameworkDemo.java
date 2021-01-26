package com.qa.tests;

import static org.testng.Assert.assertTrue;

import com.qa.base.DriverFactory;
import com.qa.pageActions.TrainLineSearchHomePageActions;
import com.qa.util.LoggerUtil;
import io.qameta.allure.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FrameworkDemo {

  Properties prop;
  DriverFactory basePage;

  /**
   * initialise driverFactory and properties classes
   *
   * @param methodName testMethodName
   * @throws MalformedURLException
   */
  @BeforeMethod
  public void setup(Method methodName) throws MalformedURLException {
    basePage = new DriverFactory();
    prop = basePage.initializeProp();
    basePage.initializeDriver(prop, methodName);
    // TestUtil.webDriverEvents(basePage.init_driver(prop));
  }

  /** remove driver */
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
    TrainLineSearchHomePageActions searchHomePageActions = new TrainLineSearchHomePageActions();

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
    TrainLineSearchHomePageActions searchHomePageActions = new TrainLineSearchHomePageActions();

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
    TrainLineSearchHomePageActions searchHomePageActions = new TrainLineSearchHomePageActions();

    LoggerUtil.log("RetryAnalyzer Test");
    searchHomePageActions.enterTextInDepartureStn("London");
    assertTrue(true);
  }
}
