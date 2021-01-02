package com.qa.base;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.util.Constants;
import com.qa.util.LoggerUtil;
import com.qa.util.WebInteractUtil;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    // BrowserStack credentials
    public static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_KEY = System.getenv("BROWSERSTACK_ACCESSKEY");
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

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
     * @param properties, methodName
     * @return this method will return driver instance
     * @throws MalformedURLException exception
     */
    public WebDriver initializeDriver(Properties properties, Method methodName) throws MalformedURLException {

        String browserName;
        OptionsManager optionsManager = new OptionsManager(properties);

        if (System.getProperty("browser") == null) {
            browserName = properties.getProperty("browser");
        } else browserName = System.getProperty("browser");

        LoggerUtil.log("Running on ----> " + browserName + " browser");

        if (properties.getProperty("execution").equalsIgnoreCase("local")) {

            if (browserName.equalsIgnoreCase("chrome")) {

                System.setProperty("webdriver.chrome.driver", Constants.DRIVERPATH_CHROME);
                System.setProperty("webdriver.chrome.silentOutput", "true"); // suppress browser logs on console
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

            } else if (browserName.equalsIgnoreCase("firefox")) {

                System.setProperty("webdriver.gecko.driver", Constants.DRIVERPATH_FIREFOX);
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null"); // suppress browser logs on console
                tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

            } else if (browserName.equalsIgnoreCase("Edge")) {

                System.setProperty("webdriver.edge.driver", Constants.DRIVERPATH_EDGE);
                tlDriver.set(new EdgeDriver());

            } else {
                LoggerUtil.log(browserName + " is not found, please pass the right browser name");
            }

        } else if (properties.getProperty("execution").equalsIgnoreCase("browserStack")) {

            DesiredCapabilities caps = new DesiredCapabilities();

            if (browserName.equalsIgnoreCase("chrome")) {
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
                caps.setCapability("browser", "Chrome");
                caps.setCapability("browser_version", "80");
                caps.setCapability("name", methodName.getName());
                tlDriver.set(new RemoteWebDriver(new URL(URL), caps));

            }
        }

        getDriver().manage().window().maximize();
        getDriver().get(properties.getProperty("url"));
        WebInteractUtil.setDriver(getDriver());
        return getDriver();

    }

    /**
     * @return this method returns properties - properties available in config.properties
     */
    public Properties initializeProp() {

        Properties properties = new Properties();
        String path;
        String env;

        try {
            env = System.getProperty("env");
            if (env == null) path = "./src/main/java/com/qa/config/config.properties";
            else path = switch (env) {
                case "qa":
                    yield "./src/main/java/com/qa/config/config.qa.properties";
                case "stg":
                    yield "./src/main/java/com/qa/config/config.stg.properties";
                case "prod":
                    yield "./src/main/java/com/qa/config/config.properties";
                default:
                    LoggerUtil.log("no env is passed");
                    throw new IllegalArgumentException("no env is passed!");
            };

            FileInputStream ip = new FileInputStream(path);
            properties.load(ip);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.log("config file is not found.....");
        }

        return properties;
    }
} // end of class DriverFactory