/** */
package com.qa.util;

import com.aventstack.extentreports.Status;
import com.qa.report.AllureReportListener;
import com.qa.report.ExtentReportListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Deepak Rai */
public class LoggerUtil {

  private static Logger logger;

  private LoggerUtil() {}

  public static Logger getLogger() {
    if (logger == null) return LogManager.getLogger();
    return logger;
  }

  /**
   * {@summary this method logs messages on console, Allure Report and Extent Report}
   *
   * @param message
   * @return void
   * @author deepak
   */
  public static void log(String message) {
    getLogger().info(message);
    AllureReportListener.saveTextLog(message);
    if (ExtentReportListener.getExtentTest() != null) {
      ExtentReportListener.getExtentTest().log(Status.INFO, message);
    }
  }
} // end of class LoggerUtil
