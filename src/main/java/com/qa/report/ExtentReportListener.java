package com.qa.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.base.BaseWebDriverTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Objects;

public class ExtentReportListener extends BaseWebDriverTest implements ITestListener {

  // Extent Report Declarations
  private ExtentReports extent = ExtentManager.createInstance();
  private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

  public static synchronized ExtentTest getExtentTest() {
    try {
      return extentTestThreadLocal.get();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest extentTest =
        extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
    extentTestThreadLocal.set(extentTest);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    extentTestThreadLocal.get().pass("Test Case Passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    Object testClass = result.getInstance();
    WebDriver driver = ((BaseWebDriverTest) testClass).getDriver();

    //        try {
    //            test.get()
    //                    .fail(
    //                            "Test Case Failed",
    //                            MediaEntityBuilder.createScreenCaptureFromPath(
    //
    // testUtil.takeScreenshotAtEndOfTest(result.getName(), driver))
    //                                    .build());
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }

    // Take base64Screenshot screenshot for extent reports
    String base64Screenshot =
        "data:image/png;base64,"
            + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
    // ExtentReports log and screenshot operations for failed tests.
    extentTestThreadLocal.get()
        .log(
            Status.FAIL,
            "Test Failed",
                extentTestThreadLocal.get()
                .addScreenCaptureFromBase64String(base64Screenshot)
                .getModel()
                .getMedia()
                .get(0));

    String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
    extentTestThreadLocal.get()
        .fail(
            "<details>"
                + "<summary>"
                + "<b>"
                + "<font color="
                + "red>"
                + "Exception Occurred:Click to see"
                + "</font>"
                + "</b>"
                + "</summary>"
                + exceptionMessage.replace(",", "<br>")
                + "</details>"
                + " \n");
    // test.get().fail(result.getThrowable());

    String failureLog = "TEST CASE FAILED";
    Markup markup = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
    extentTestThreadLocal.get().fail(markup);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    extentTestThreadLocal.get().skip(result.getThrowable());
    extentTestThreadLocal.get().skip("Test Case Skipped");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  @Override
  public void onStart(ITestContext context) {}

  @Override
  public void onFinish(ITestContext context) {
    extent.flush();
    extentTestThreadLocal.remove();
  }
} // End of class ExtentTestNGITestListener
