package com.qa.tests;

import com.qa.base.BaseWebDriverTest;
import com.qa.pageObjects.TrainLineSearchHomePage;
import com.qa.util.LoggerUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class RetryForFailedTest extends BaseWebDriverTest {

    @Test()
    @Severity(SeverityLevel.MINOR)
    @Description("Description of the test case - retryTest")
    @Story("Test case written for Story - retryTest")
    @Step("Step at test Body level - retryTest")
    @Link("https://docs.qameta.io/allure/")
    public void verifyTestIsRetriedWhenFailed() {
        TrainLineSearchHomePage trainLineSearchHomePage = new TrainLineSearchHomePage(getDriver());

        LoggerUtil.info("RetryAnalyzer Test");
        getDriver().get("https://www.thetrainline.com/");
        trainLineSearchHomePage.enterTextInDepartureStn("London");
        assertTrue(false);
    }
}
