package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.PointsVilleLoginPageActions;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShadowDomTest {

  Properties properties;
  DriverFactory driverFactory;

  @BeforeMethod
  public void setup(Method methodName) throws MalformedURLException {
    driverFactory = new DriverFactory();
    properties = driverFactory.initializeProp();
    driverFactory.initializeDriver(properties, methodName);
  }

  @AfterMethod
  public void tearDown() {
    DriverFactory.removeDriver();
  }

  @Test
  public void verifyShadowDomElements() {

    var pointsVilleLoginPageActions = new PointsVilleLoginPageActions();

    pointsVilleLoginPageActions.enterUserCredentials();
    pointsVilleLoginPageActions.clickOnResetPwdLink();
  }
}
