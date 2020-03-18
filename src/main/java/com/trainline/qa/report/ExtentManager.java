package com.trainline.qa.report;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.Platform;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.trainline.qa.util.LoggerUtil;

public class ExtentManager {

	private static ExtentReports extent;
	private static Platform platform;
	private static Date date = new Date();
	private static String reportFileName = "Extent_" + date.toString().replaceAll(":", "_") + ".html";
	private static String windowsPath = System.getProperty("user.dir") + "\\test-output";
	// private static String macPath = System.getProperty("user.dir")+
	// "/TestReport";
	private static String winReportFileLoc = windowsPath + "\\" + reportFileName;
	// private static String macReportFileLoc = macPath + "/" + reportFileName;

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	} // End of method getInstance

	public static ExtentReports createInstance() {
		platform = getCurrentPlatform();
		String fileName = getReportFileLocation(platform);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Automation Tester", "Deepak Rai");
		extent.setSystemInfo("Organization", "Deepak Automation Labs");
		extent.setSystemInfo("Build Number", "Deepak 1.0");

		return extent;
	} // End of method createInstance

	// Select the extent report file location based on platform
	private static String getReportFileLocation(Platform platform) {
		String reportFileLocation = null;
		switch (platform) {
		/*
		 * case MAC: reportFileLocation = macReportFileLoc; createReportPath(macPath);
		 * System.out.println("ExtentReport Path for MAC: " + macPath + "\n"); break;
		 */
		case WIN10:
			reportFileLocation = winReportFileLoc;
			createReportPath(windowsPath);
			LoggerUtil.logMessage("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
			break;
		default:
			LoggerUtil.logMessage("ExtentReport path has not been set! There is a problem!\n");
			break;
		}
		return reportFileLocation;
	} // End of method getReportFileLocation

	// Create the report path if it does not exist
	private static void createReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				LoggerUtil.logMessage("Directory: " + path + " is created!");
			} else {
				LoggerUtil.logMessage("Failed to create directory: " + path);
			}
		} else {
			LoggerUtil.logMessage("Directory already exists: " + path);
		}
	}// End of method createReportPath

	// Get current platform
	private static Platform getCurrentPlatform() {
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WIN10;
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX;
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
			}
		}
		return platform;
	} // End of method getCurrentPlatform
} // End of class ExtentManager