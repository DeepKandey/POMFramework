package com.trainline.qa.report;

import java.io.IOException;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.TestUtil;

public class ExtentTestNGITestListener implements ITestListener {

	// Extent Report Declarations
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		LoggerUtil.logMessage((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
	}

	public void onTestSuccess(ITestResult result) {
		LoggerUtil.logMessage((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test Case Passed");
	}

	public void onTestFailure(ITestResult result) {
		LoggerUtil.logMessage((result.getMethod().getMethodName() + " failed!"));
		try {
			TestUtil.takeScreenshotAtEndOfTest(result.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			test.get().fail("Test Case Failed", MediaEntityBuilder
					.createScreenCaptureFromPath(TestUtil.takeScreenshotAtEndOfTest(result.getName())).build());
		} catch (IOException e) {
			e.printStackTrace();
		}

		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		test.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>" + " \n");
		// test.get().fail(result.getThrowable());

		String failureLog = "TEST CASE FAILED";
		Markup markup = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
		test.get().fail(markup);
	}

	public void onTestSkipped(ITestResult result) {
		LoggerUtil.logMessage((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip("Test Case Skipped");
		test.get().skip(result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		LoggerUtil.logMessage(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	public void onStart(ITestContext context) {
		LoggerUtil.logMessage("Extent Reports Version 4 Test Suite started! " + context.getOutputDirectory());
	}

	public void onFinish(ITestContext context) {
		LoggerUtil.logMessage("Extent Reports Version 4  Test Suite is ending!");
		LoggerUtil.logMessage("This is onFinish method. Passed Tests: " + context.getPassedTests());
		LoggerUtil.logMessage("This is onFinish method. Failed Test: " + context.getFailedTests());
		LoggerUtil.logMessage("This is onFinish method. Skipped Test: " + context.getSkippedTests());
		extent.flush();
	}
}