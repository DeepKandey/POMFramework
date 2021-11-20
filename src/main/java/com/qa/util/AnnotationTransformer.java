package com.qa.util;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

  public boolean isTestRunning(ITestAnnotation ins) {
    return ins.getAlwaysRun();
  }

  @Override
  public synchronized void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    annotation.setRetryAnalyzer(RetryAnalyzer.class);
  }
} // End of class AnnotationTransformer
