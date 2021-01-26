package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SeleniumPracticePageActions;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BootstrapDropdown {
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

  /** booStrapDropDownTest */
  @Test
  public void bootStrapDropDownTest() {
    var seleniumPracticePageActions = new SeleniumPracticePageActions();

    seleniumPracticePageActions.clickDropdownMenuButton();
    seleniumPracticePageActions.clickTutorialLink("JavaScript");
  }

  /** remove driver */
  @AfterMethod
  public void tearDown() {
    DriverFactory.removeDriver();
  }
}
