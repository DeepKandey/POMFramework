package com.qa.report;

import static com.qa.util.LoggerUtil.log;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.base.DriverFactory;
import com.qa.util.TestUtil;
import java.io.IOException;
import java.util.Arrays;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener extends DriverFactory implements ITestListener {

  // Extent Report Declarations
  private static ExtentReports extent = ExtentManager.createInstance();
  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

  public static synchronized ExtentTest getExtentTest() {
    try {
      return test.get();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest extentTest =
        extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
    test.set(extentTest);
    log((result.getMethod().getMethodName() + " started!"));
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    log((result.getMethod().getMethodName() + " passed!"));
    test.get().pass("Test Case Passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    log((result.getMethod().getMethodName() + " failed!"));
    try {
      TestUtil.takeScreenshotAtEndOfTest(result.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      test.get()
          .fail(
              "Test Case Failed",
              MediaEntityBuilder.createScreenCaptureFromPath(
                      TestUtil.takeScreenshotAtEndOfTest(result.getName()))
                  .build());
    } catch (IOException e) {
      e.printStackTrace();
    }

    String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
    test.get()
        .fail(
            "<details>"
                + "<summary>"
                + "<b>"
                + "<font color="
                + "red>"
                + "Exception Occured:Click to see"
                + "</font>"
                + "</b>"
                + "</summary>"
                + exceptionMessage.replace(",", "<br>")
                + "</details>"
                + " \n");
    // test.get().fail(result.getThrowable());

    String failureLog = "TEST CASE FAILED";
    Markup markup = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
    test.get().fail(markup);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    log((result.getMethod().getMethodName() + " skipped!"));
    test.get().skip("Test Case Skipped");
    test.get().skip(result.getThrowable());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    log(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
  }

  @Override
  public void onStart(ITestContext context) {
    log("Extent Reports Version 4 Test Suite started! " + context.getOutputDirectory());
  }

  @Override
  public void onFinish(ITestContext context) {
    log("Extent Reports Version 4  Test Suite is ending!");
    extent.flush();
    test.remove();
  }
} // End of class ExtentTestNGITestListener
