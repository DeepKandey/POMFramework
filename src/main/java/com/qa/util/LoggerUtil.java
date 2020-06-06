/**
 * 
 */
package com.qa.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.report.AllureReportListener;

/**
 * @author Deepak Rai
 *
 */
public class LoggerUtil {

	private static Logger logger;

	private LoggerUtil() {
	}

	public static Logger getLogger() {
		if (logger == null)
			return LogManager.getLogger();
		return logger;
	}

	/**
	 * 
	 * {@summary this method logs messages on console, Allure Report and Extent
	 * Report}
	 * 
	 * @param message
	 * @return void
	 * @author deepak
	 */
	public static void log(String message) {
		getLogger().info(message);
		AllureReportListener.saveTextLog(message);
	}
} // end of class LoggerUtil