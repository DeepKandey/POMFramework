package com.trainline.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.trainline.qa.util.LoggerUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static Properties prop;

	private static DriverFactory instance = null;
	// thread local driver object for web driver
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	private DriverFactory() {
	}

	public static DriverFactory getInstance() {
		if (instance == null) {
			instance = new DriverFactory();
			prop = new Properties();
			File file = new File(
					System.getProperty("user.dir") + "/src/main/java/com/trainline/qa/config/config.properties");
			try {
				FileInputStream fis = new FileInputStream(file);
				prop.load(fis);
			} catch (Exception e) {
				LoggerUtil.logMessage("Exception occured: " + e);
			}
		}
		return instance;
	}

	public final void setDriver(String browser) {
		// DesiredCapabilities caps = null;
		switch (browser.toUpperCase()) {
		case "CHROME":
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			break;

		case "FF":
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
			break;

		case "IE":
			WebDriverManager.iedriver().setup();
			driver.set(new InternetExplorerDriver());
			break;

		default:
			LoggerUtil.logMessage("Please provide correct browsername");
			break;
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
	}

	public WebDriver getDriver() {
		return driver.get();

	}

	public void removeDriver() {
		getDriver().quit();
		driver.remove();
	}

	public Properties getProperties() {
		return DriverFactory.prop;
	}
} // end of class DriverFactory