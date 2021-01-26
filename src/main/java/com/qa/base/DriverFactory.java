package com.qa.base;

import static com.qa.util.LoggerUtil.log;

import com.qa.util.Constants;
import com.qa.util.LoggerUtil;
import com.qa.util.WebInteractUtil;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/** DriverFactory class to manage driver related methods */
public class DriverFactory {

  // BrowserStack credentials
  private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
  private static final String AUTOMATE_KEY = System.getenv("BROWSERSTACK_ACCESSKEY");
  private static final String URL =
      "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

  private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

  /** @return WebDriver webDriver */
  protected static synchronized WebDriver getDriver() {
    return tlDriver.get();
  }

  /** method to remove driver once test completes */
  public static synchronized void removeDriver() {
    tlDriver.get().quit();
    tlDriver.remove();
  }

  /**
   * This method is used to initialize the WebDriver on the basis of browserName
   *
   * @param properties,methodName Properties instance and test method Name
   * @throws MalformedURLException exception
   */
  public void initializeDriver(Properties properties, Method methodName)
      throws MalformedURLException {

    String browserName;
    OptionsManager optionsManager = new OptionsManager(properties);

    if (System.getProperty("browser") == null) {
      browserName = properties.getProperty("browser");
    } else browserName = System.getProperty("browser");

    if (properties.getProperty("execution").equalsIgnoreCase("local")) {

      if (browserName.equalsIgnoreCase("chrome")) {

        System.setProperty("webdriver.chrome.driver", Constants.DRIVERPATH_CHROME);
        System.setProperty(
            "webdriver.chrome.silentOutput", "true"); // suppress browser logs on console
        tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

      } else if (browserName.equalsIgnoreCase("firefox")) {

        System.setProperty("webdriver.gecko.driver", Constants.DRIVERPATH_FIREFOX);
        System.setProperty(
            FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
            "FFBrowserLogs"); // suppress browser logs on console
        tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

      } else if (browserName.equalsIgnoreCase("Edge")) {

        System.setProperty("webdriver.edge.driver", Constants.DRIVERPATH_EDGE);
        tlDriver.set(new EdgeDriver());

      } else {
        log(browserName + " is not found, please pass the right browser name");
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
    getDriver()
        .manage()
        .timeouts()
        .pageLoadTimeout(Duration.ofSeconds(Constants.PAGE_LOAD_TIMEOUT));
    WebInteractUtil.setDriver(getDriver());
  }

  /** @return this method returns properties - properties available in config.properties */
  public Properties initializeProp() {

    Properties properties = new Properties();
    String path;
    String env;

    try {
      env = System.getProperty("env");
      if (env == null) path = "./src/main/java/com/qa/config/config.properties";
      else
        path =
            switch (env) {
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
      log("config file is not found.....");
    }
    return properties;
  }
} // end of class DriverFactory
