package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.util.WebInteractUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	// thread local driver object for web driver
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	public static synchronized void removeDriver() {
		tlDriver.get().quit();
		tlDriver.remove();
	}

	/**
	 * This method is used to initialize the WebDriver on the basis of browserName
	 * 
	 * @param browserName
	 * @return this method will return driver instance
	 */
	public WebDriver init_driver(Properties prop) {
		WebInteractUtil interact = new WebInteractUtil();
		String browserName = null;

		if (System.getProperty("browser") == null) {
			browserName = prop.getProperty("browser");
		} else {
			browserName = System.getProperty("browser");
		}

		System.out.println("Running on ----> " + browserName + " browser");

		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:/Users/deepa/Downloads/Browser Drivers/Chrome Drivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.silentOutput", "true"); // To suppress the Chrome logs on console

			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:/Users/deepa/Downloads/Browser Drivers/FireFoxDrivers/geckodriver.exe");

			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null"); // To suppress FF logs on
																						// console

			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		} else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
			tlDriver.set(new SafariDriver());
		} else if (browserName.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver",
					"C:/Users/deepa/Downloads/Browser Drivers/EdgeDriver/msedgedriver.exe");
			tlDriver.set(new EdgeDriver());
		} else {
			System.out.println(browserName + " is not found, please pass the right browser Name");
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		interact.setDriver(getDriver());
		return getDriver();

	}

	/**
	 * 
	 * @return this method returns properties - prop available in config.proerties
	 *         file
	 */
	public Properties init_prop() {
		prop = new Properties();
		String path = null;
		String env = null;

		try {
			env = System.getProperty("env");
			if (env == null) {
				path = "./src/main/java/com/qa/config/config.properties";
			} else {
				switch (env) {
				case "qa":
					path = "./src/main/java/com/qa/config/config.qa.properties";
					break;
				case "stg":
					path = "./src/main/java/com/qa/config/config.stg.properties";
					break;
				case "prod":
					path = "./src/main/java/com/qa/config/config.properties";
					break;
				default:
					System.out.println("no env is passed");
					break;
				}
			}

			FileInputStream ip = new FileInputStream(path);
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("config file is not foubd.....");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
} // end of class DriverFactory