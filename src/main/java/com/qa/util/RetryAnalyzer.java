package com.qa.util;

import com.qa.base.BaseWebDriverTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

  private static final int MAX_RETRY_COUNT = 2;
  private int count = MAX_RETRY_COUNT;
  LoggerUtil loggerUtil = new LoggerUtil(RetryAnalyzer.class);

  /*
   * This method decides how many times a test needs to be rerun. TestNg will call
   * this method every time a test fails. So we can put some code in here to
   * decide when to rerun the test.
   *
   * Note: This method will return true if a tests needs to be retried and false
   * it not.
   *
   */

  @Override
  public boolean retry(ITestResult result) {
    boolean retryAgain = false;
    if (count > 0) {
      loggerUtil.info(
          "Going to retry test case: "
              + result.getMethod()
              + ", "
              + (((MAX_RETRY_COUNT - count) + 1))
              + " out of "
              + MAX_RETRY_COUNT);
      retryAgain = true;
      --count;
      result.setStatus(ITestResult.FAILURE);
      loggerUtil.info(result.getInstanceName());
    }
    return retryAgain;
  }

  public void extendReportsFailOperations(ITestResult iTestResult) {
    Object testClass = iTestResult.getInstance();
    WebDriver webDriver = ((BaseWebDriverTest) testClass).getDriver();
    String base64Screenshot =
        "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
//    Objects.requireNonNull(ExtentReportListener.getExtentTest())
//        .log(
//            Status.FAIL,
//            "Test Failed",
//            ExtentReportListener.getExtentTest()
//                .addScreenCaptureFromBase64String(base64Screenshot)
//                .getModel()
//                .getMedia()
//                .get(0));
  }
}
