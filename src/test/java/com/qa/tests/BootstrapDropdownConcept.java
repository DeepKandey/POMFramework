package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SeleniumPracticePageActions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

public class BootstrapDropdownConcept {
  Properties prop;
  DriverFactory basePage;

  @BeforeMethod
  public void setup(Method methodName) throws MalformedURLException {
    basePage = new DriverFactory();
    prop = basePage.initializeProp();
    basePage.initializeDriver(prop, methodName);
  }

  /**
   * booStrapDropDownTest
   */
  @Test
  public void bootStrapDropDownTest(){
    var seleniumPracticePageActions= new SeleniumPracticePageActions();

    seleniumPracticePageActions.clickDropdownMenuButton();
    seleniumPracticePageActions.clickTutorialLink("JavaScript");
  }


  /**
   * booStrapDropDownTest
   */
  @Test
  public void bootStrapClickTest(){
    var seleniumPracticePageActions= new SeleniumPracticePageActions();

    seleniumPracticePageActions.clickDropdownMenuButton();
    seleniumPracticePageActions.clickTutorialLink("JavaScript");
  }

  @AfterMethod
  public void tearDown() {
    DriverFactory.removeDriver();
  }
}
