package com.qa.util;

import org.apache.log4j.Logger;

public abstract class LoggerUtil {
	final static Logger  logger = Logger.getLogger("devpinoyLogger");
	
	 public static void logMessage(String message) {
		 logger.info(message);
	    }
}