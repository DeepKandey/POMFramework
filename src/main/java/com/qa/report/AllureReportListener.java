package com.qa.report;

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
  private static String saveTextLog(String message) {
    return message;
  }

  // HTML attachments for Allure
  @Attachment(value = "{0}", type = "text/html")
  private static String attachHtml(String html) {
    return html;
  }

  // Image attachment for Allure
  @Attachment(value = "Page screenshot", type = "image/png")
  private byte[] saveScreenshotPNG(WebDriver driver) {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

  @Override
  public void onStart(ITestContext iTestContext) {}

  @Override
  public void onFinish(ITestContext iTestContext) {}

  @Override
  public void onTestStart(ITestResult iTestResult) {}

  @Override
  public void onTestSuccess(ITestResult iTestResult) {}

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    // Allure ScreenShot and SaveTestLog
    Object testClass = iTestResult.getInstance();
    WebDriver driver = ((BaseWebDriverTest) testClass).getDriver();
    if (driver != null) {
      saveScreenshotPNG(driver);
    }
    // Save a log on allure.
    saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {}

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}
}
