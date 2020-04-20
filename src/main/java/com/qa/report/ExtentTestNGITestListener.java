package com.qa.report;

import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.base.DriverFactory;
import com.qa.util.TestUtil;

public class ExtentTestNGITestListener extends DriverFactory implements ITestListener {

	// Extent Report Declarations
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Logger logger;

	public ExtentTestNGITestListener() {
		super(logger);
	}

	@Override
	public void onTestStart(ITestResult result) {
		DriverFactory.getLogger().info((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		DriverFactory.getLogger().info((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test Case Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		DriverFactory.getLogger().info((result.getMethod().getMethodName() + " failed!"));
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
				+ "</font>" + "</b>" + "</summary>" + exceptionMessage.replace(",", "<br>") + "</details>" + " \n");
		// test.get().fail(result.getThrowable());

		String failureLog = "TEST CASE FAILED";
		Markup markup = MarkupHelper.createLabel(failureLog, ExtentColor.RED);
		test.get().fail(markup);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		DriverFactory.getLogger().info((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip("Test Case Skipped");
		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		DriverFactory.getLogger().info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	@Override
	public void onStart(ITestContext context) {
		DriverFactory.getLogger().info("Extent Reports Version 4 Test Suite started! " + context.getOutputDirectory());
	}

	@Override
	public void onFinish(ITestContext context) {
		DriverFactory.getLogger().info("Extent Reports Version 4  Test Suite is ending!");
		DriverFactory.getLogger().info("This is onFinish method. Passed Tests: " + context.getPassedTests());
		DriverFactory.getLogger().info("This is onFinish method. Failed Test: " + context.getFailedTests());
		DriverFactory.getLogger().info("This is onFinish method. Skipped Test: " + context.getSkippedTests());
		extent.getStartedReporters().forEach(a -> a.getReporterName().lines().forEach(b -> System.out.println(b)));
		extent.flush();
		test.remove();
	}
}// End of class ExtentTestNGITestListener