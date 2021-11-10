package com.qa.report;

import static com.qa.util.LoggerUtil.log;

import com.qa.base.BaseWebDriverTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureReportListener extends BaseWebDriverTest implements ITestListener {

  private static String getTestMethodName(ITestResult iTestResult) {
    return iTestResult.getMethod().getConstructorOrMethod().getName();
  }

  // Text attachments for Allure
  @Attachment(value = "{0}", type = "text/plain")
  public static String saveTextLog(String message) {
    return message;
  }

  // HTML attachments for Allure
  @Attachment(value = "{0}", type = "text/html")
  public static String attachHtml(String html) {
    return html;
  }

  // Image attachment for Allure
  @Attachment(value = "Page screenshot", type = "image/png")
  public byte[] saveScreenshotPNG(WebDriver driver) {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

  @Override
  public void onStart(ITestContext iTestContext) {
    log("I am in onStart method " + iTestContext.getName());
  }

  @Override
  public void onFinish(ITestContext iTestContext) {
    log("I am in onFinish method " + iTestContext.getName());
  }

  @Override
  public void onTestStart(ITestResult iTestResult) {
    log("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {
    log("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    log("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
    WebDriver driver = BaseWebDriverTest.getWebDriver();
    // Allure ScreenShotRobot and SaveTestLog
    if (driver != null) {
      log("Screenshot captured for test case:" + getTestMethodName(iTestResult));
      saveScreenshotPNG(driver);
    }
    // Save a log on allure.
    saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {
    log("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    log("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
  }
}
