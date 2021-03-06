package com.qa.report;

import com.qa.util.LoggerUtil;
import com.qa.util.TestUtil;
import com.tesults.tesults.Results;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TesultsReportListener implements ITestListener {

  List<Map<String, Object>> testCases = new ArrayList<>();

  public static String getTestMethodName(ITestResult iTestResult) {
    return iTestResult.getMethod().getConstructorOrMethod().getName();
  }

  public static Object[] getTestParams(ITestResult iTestResult) {
    return iTestResult.getParameters();
  }

  public byte[] saveScreenshotPNG(WebDriver driver) {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

  @Override
  public void onTestStart(ITestResult result) {
    LoggerUtil.log("I am in onTestStart method " + getTestMethodName(result) + " start");
  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {
    Map<String, Object> testCase = new HashMap<>();
    testCase.put("name", getTestMethodName(iTestResult));
    testCase.put("suite", "TesultsExample");
    testCase.put("result", "pass");
    testCase.put("params", getTestParams(iTestResult));
    testCases.add(testCase);
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    Map<String, Object> testCase = new HashMap<>();
    testCase.put("name", getTestMethodName(iTestResult));
    testCase.put("suite", "TesultsExample");
    testCase.put("result", "fail");
    testCase.put("params", getTestParams(iTestResult));
    List<String> files = new ArrayList<>();
    try {
      files.add(TestUtil.takeScreenshotAtEndOfTest(getTestMethodName(iTestResult)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    testCase.put("files", files);

    testCases.add(testCase);
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {
    Map<String, Object> testCase = new HashMap<>();
    testCase.put("name", getTestMethodName(iTestResult));
    testCase.put("suite", "TesultsExample");
    testCase.put("result", "skip");
    testCase.put("params", getTestParams(iTestResult));
    List<String> files = new ArrayList<>();
    try {
      files.add(TestUtil.takeScreenshotAtEndOfTest(getTestMethodName(iTestResult)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    testCase.put("files", files);

    testCases.add(testCase);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void onStart(ITestContext context) {
    LoggerUtil.log("I am in onStart method " + context.getName());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onFinish(ITestContext iTestContext) {
    // Map<String, Object> to hold your test results data.
    Map<String, Object> data = new HashMap<>();
    data.put(
        "target",
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjBhN2M3YmZkLTIxZDgtNDNmOS05ZjQ1LTk0OGNhYzFkMzc4Zi0xNTg3NzA3NDIzNzEyIiwiZXhwIjo0MTAyNDQ0ODAwMDAwLCJ2ZXIiOiIwIiwic2VzIjoiNTU4YjU4YmItMzM3OS00ZWExLWJiNzMtZDIxNjVlYjZhNmIzIiwidHlwZSI6InQifQ.fHE2vZxxaAJLKo6vrSlfMPvQf9APIGa5dJ_dQ4gd6g8");

    Map<String, Object> results = new HashMap<>();
    results.put("cases", testCases);
    data.put("results", results);

    // Upload
    Map<String, Object> response = Results.upload(data);
    LoggerUtil.log("success: " + response.get("success"));
    LoggerUtil.log("message: " + response.get("message"));
    LoggerUtil.log("warnings: " + ((List<String>) response.get("warnings")).size());
    LoggerUtil.log("errors: " + ((List<String>) response.get("errors")).size());
  }
}
