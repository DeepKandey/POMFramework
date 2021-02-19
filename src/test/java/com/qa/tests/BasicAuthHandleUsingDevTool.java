package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SeleniumPracticePageActions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

public class BasicAuthHandleUsingDevTool {

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
  }

  @Test
  public void BasicAuthHandleTest() {

    SeleniumPracticePageActions seleniumPracticePageActions = new SeleniumPracticePageActions();

    if (seleniumPracticePageActions.getLoginMessageForBasicAuth().contains("Congratulations! ")) {
      System.out.println("Login successful");
    } else {
      System.out.println("Login failed");
    }
  }

  /** remove driver */
  @AfterMethod
  public void tearDown() {
    DriverFactory.removeDriver();
  }
}
