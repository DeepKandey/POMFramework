package com.qa.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.qa.base.DriverFactory;

public class RetryAnalyzer extends DriverFactory implements IRetryAnalyzer {

	/*
	 * private int counter = 0; private static final int retryLimit = 1;
	 */

	private static final int MAX_RETRY_COUNT = 2;
	private int count = MAX_RETRY_COUNT;

	/*
	 * This method decides how many times a test needs to be rerun. TestNg will call
	 * this method every time a test fails. So we can put some code in here to
	 * decide when to rerun the test.
	 * 
	 * Note: This method will return true if a tests needs to be retried and false
	 * it not.
	 *
	 */

	/*
	 * public boolean retry(ITestResult result) { if (counter < retryLimit) {
	 * counter++; return true; } return false; }
	 */

	@Override
	public boolean retry(ITestResult result) {
		boolean retryAgain = false;
		if (count > 0) {
			LoggerUtil.log("Going to retry test case: " + result.getMethod() + ", " + (((MAX_RETRY_COUNT - count) + 1))
					+ " out of " + MAX_RETRY_COUNT);
			retryAgain = true;
			--count;
			result.setStatus(ITestResult.FAILURE);
			LoggerUtil.getLogger().info(result.getInstanceName());
		}
		return retryAgain;
	}
}