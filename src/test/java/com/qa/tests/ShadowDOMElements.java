package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.PointsVilleLoginPageActions;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShadowDOMElements {

  Properties properties;
  DriverFactory driverFactory;

  /**
   * initialise driverFactory and properties classes
   *
   * @param methodName testMethodName
   * @throws MalformedURLException
   */
  @BeforeMethod
  public void setup(Method methodName) throws MalformedURLException {
    driverFactory = new DriverFactory();
    properties = driverFactory.initializeProp();
    driverFactory.initializeDriver(properties, methodName);
  }

  /** remove WebDriver */
  @AfterMethod
  public void tearDown() {
    DriverFactory.removeDriver();
  }

  /** verify shadow OOM elements and perform actions */
  @Test
  public void ShadowDOMElementsTest() {
    var pointsVilleLoginPageActions = new PointsVilleLoginPageActions();

    pointsVilleLoginPageActions.enterUserCredentials();
    pointsVilleLoginPageActions.clickOnResetPwdLink();
  }
}
