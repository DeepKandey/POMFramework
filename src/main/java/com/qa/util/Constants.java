package com.qa.util;

public class Constants {

	private Constants() {
	}

	public static final long PAGE_LOAD_TIMEOUT = 40;

	public static final long IMPLICIT_WAIT = 5;

	public static final long EXPLICIT_WAIT = 15;

	public static final String TEST_DATA_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/qa/testData/TestDocument.xlsx";

	public static final String DRIVERPATH_CHROME = "C:/Users/deepa/Downloads/BrowserDrivers/hromedriver.exe";

	public static final String DRIVERPATH_FIREFOX = "C:/Users/deepa/Downloads/BrowserDrivers/geckodriver.exe";

	public static final String DRIVERPATH_EDGE = "C:/Users/deepa/Downloads/BrowserDrivers/msedgedriver.exe";

	public static final String GRID_SERVER_PATH = "C:/Users/deepa/Downloads/BrowserDrivers/selenium-server-4.0.0-alpha-6.jar";

}// End of class Constants