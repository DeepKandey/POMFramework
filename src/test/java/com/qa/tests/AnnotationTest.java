package com.qa.tests;

import com.qa.base.BaseWebDriverTest;
import com.qa.pageObjects.TrainLineSearchHomePage;
import com.qa.util.LoggerUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AnnotationTest extends BaseWebDriverTest {

  @Test(enabled = false)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Description of the test case - annotationTest")
  @Story("Test case written for Story - annotationTest")
  @Step("Step at test Body level - annotationTest")
  @Link("https://docs.qameta.io/allure/")
  public void verifyTestISEnabledTestAtRunTime() {
    TrainLineSearchHomePage trainLineSearchHomePage = new TrainLineSearchHomePage(getWebDriver());

    LoggerUtil.log("Annotation Transformation Test");
    getWebDriver().get("https://www.thetrainline.com/");
    trainLineSearchHomePage.enterTextInDepartureStn("London");
    assertTrue(true);
  }


}
