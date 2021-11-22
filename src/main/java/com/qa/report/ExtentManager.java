package com.qa.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.util.LoggerUtil;
import org.openqa.selenium.Platform;

import java.io.File;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extentReport;
    private static Platform platform;
    private static final Date date = new Date();
    private static final String reportFileName =
            "Extent_" + date.toString().replaceAll(":", "_") + ".html";
    private static final String windowsPath = System.getProperty("user.dir") + "/test-output";
    private static final String linuxPath = System.getProperty("user.dir") + "/test-output";
    private static final String macPath = System.getProperty("user.dir") + "/TestReport";
    private static final String winReportFileLoc = windowsPath + "/" + reportFileName;
    private static final String linuxReportFileLoc = linuxPath + "/" + reportFileName;
    private static final String macReportFileLoc = macPath + "/" + reportFileName;

    public static ExtentReports getInstance() {
        if (extentReport == null) {
            createInstance();
        }
        return extentReport;
    } // End of method getInstance

    public static ExtentReports createInstance() {
        new ExtentManager();
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Sample Extent Report");

        extentReport = new ExtentReports();
        extentReport.attachReporter(htmlReporter);

        extentReport.setSystemInfo("Automation Tester", "Deepak Rai");
        extentReport.setSystemInfo("Organization", "Deepak Automation Labs");
        extentReport.setSystemInfo("Build Number", "Deepak 1.0");

        return extentReport;
    } // End of method createInstance

    // Select the extent report file location based on platform
    private static String getReportFileLocation(Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case MAC -> {
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
            }
            case WIN10 -> {
                reportFileLocation = winReportFileLoc;
                createReportPath(windowsPath);
                System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
            }
            case LINUX -> {
                reportFileLocation = linuxReportFileLoc;
                createReportPath(linuxPath);
                System.out.println("ExtentReport Path for LINUX: " + linuxPath + "\n");
            }
            default -> System.out.println("ExtentReport path has not been set! There is a problem!\n");
        }
        return reportFileLocation;
    } // End of method getReportFileLocation

    // Create the report path if it does not exist
    private static void createReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                LoggerUtil.info("Directory: " + path + " is created!");
            } else {
                LoggerUtil.info("Failed to create directory: " + path);
            }
        } else {
            LoggerUtil.info("Directory already exists: " + path);
        }
    } // End of method createReportPath

    // Get current platform
    private static Platform getCurrentPlatform() {
        if (platform == null) {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.contains("win")) {
                platform = Platform.WIN10;
            } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                platform = Platform.LINUX;
            } else if (OS.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    } // End of method getCurrentPlatform
} // End of class ExtentManager
