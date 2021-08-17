package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SeleniumPracticePageActions;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

public class ChromeBetaTest {

  Properties prop;
  DriverFactory basePage;

  /**
   * initialise driverFactory and properties classes
   *
   * @param methodName testMethodName
   * @throws MalformedURLException exception
   */
  @BeforeMethod
  public void setup(Method methodName) throws MalformedURLException {
    basePage = new DriverFactory();
    prop = basePage.initializeProp();
    basePage.initializeDriver(prop, methodName);
  }

  /** remove driver */
  @AfterMethod
  public void tearDown() {
    DriverFactory.removeDriver();
  }

  @Test()
  @Severity(SeverityLevel.NORMAL)
  @Description("Description of the test case - ChromeBetaTest")
  @Story("Test case written for Story - ChromeBetaTest")
  @Step("Step at test Body level - ChromeDemoTest")
  @Link("https://docs.qameta.io/allure/")
  public void demoTest() {
    var seleniumPracticePageActions = new SeleniumPracticePageActions();

    seleniumPracticePageActions.clickDropdownMenuButton();
    seleniumPracticePageActions.clickTutorialLink("JavaScript");
    seleniumPracticePageActions.openNewTab();
  }
}
