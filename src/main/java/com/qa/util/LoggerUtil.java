/* */
package com.qa.util;

import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Deepak Rai
 */
public class LoggerUtil {

  private final Logger logger;

  public LoggerUtil(Class className) {
    logger = LogManager.getLogger(className);
  }

  // Text attachments for Allure
  @Attachment(value = "{0}", type = "text/plain")
  private static String saveTextLog(String message) {
    return message;
  }

  public void info(String message) {
    logger.info(message);
    // ExtentReportListener.getExtentTest().log(Status.INFO, message);
    saveTextLog(message);
  }

  public void warn(String message) {
    logger.warn(message);
    // ExtentReportListener.getExtentTest().log(Status.WARNING, message);
    saveTextLog(message);
  }

  public void error(String message) {
    logger.error(message);
    // ExtentReportListener.getExtentTest().log(Status.FAIL, message);
    saveTextLog(message);
  }

  public void fatal(String message) {
    logger.fatal(message);
    // ExtentReportListener.getExtentTest().log(Status.WARNING, message);
    saveTextLog(message);
  }

  public void debug(String message) {
    logger.debug(message);
    // ExtentReportListener.getExtentTest().log(Status.INFO, message);
    saveTextLog(message);
  }
} // end of class LoggerUtil
