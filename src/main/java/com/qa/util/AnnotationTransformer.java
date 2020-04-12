package com.qa.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {

	public boolean isTestRunning(ITestAnnotation ins) {
		if (ins.getAlwaysRun()) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public synchronized void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		try {
			Object[][] object = TestUtil.getExcelData(Constants.TEST_DATA_PATH, "TestScripts");

			for (int i = 0; i < object.length; i++) {
				if (object[i][0].toString().trim().equals(testMethod.getName())) {
					annotation.setEnabled(Boolean.parseBoolean(object[i][1].toString()));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isTestRunning(annotation)) {
			annotation.setEnabled(false);
		}

		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
} // End of class AnnotationTransformer