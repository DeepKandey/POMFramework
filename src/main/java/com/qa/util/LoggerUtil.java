/* */
package com.qa.util;

import com.aventstack.extentreports.Status;
import com.qa.report.AllureReportListener;
import com.qa.report.ExtentReportListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Deepak Rai */
public class LoggerUtil {

  // Initialize Log4j instance
  private static Logger logger = LogManager.getLogger(LoggerUtil.class);

  private LoggerUtil() {}

  // Info Level Logs
  public static void info(String message) {
    logger.info(message);
    if (ExtentReportListener.getExtentTest() != null)
      ExtentReportListener.getExtentTest().log(Status.INFO, message);
    AllureReportListener.saveTextLog(message);
  }
  // Warn Level Logs
  public static void warn(String message) {
    logger.warn(message);
    if (ExtentReportListener.getExtentTest() != null)
      ExtentReportListener.getExtentTest().log(Status.WARNING, message);
  }
  // Error Level Logs
  public static void error(String message) {
    logger.error(message);
    if (ExtentReportListener.getExtentTest() != null)
      ExtentReportListener.getExtentTest().log(Status.FAIL, message);
  }
  // Fatal Level Logs
  public static void fatal(String message) {
    LoggerUtil.fatal(message);
    if (ExtentReportListener.getExtentTest() != null)
      ExtentReportListener.getExtentTest().log(Status.WARNING, message);
  }
  // Debug Level Logs
  public static void debug(String message) {
    LoggerUtil.debug(message);
    if (ExtentReportListener.getExtentTest() != null)
      ExtentReportListener.getExtentTest().log(Status.INFO, message);
  }
  //    /**
  //     * {@summary this method logs messages on console, Allure Report and Extent Report}
  //     *
  //     * @param message logMessage
  //     * @author deepak
  //     */
  //    public static void log(String message) {
  //        getLogger().info(message);
  //        AllureReportListener.saveTextLog(message);
  //    }
} // end of class LoggerUtil
