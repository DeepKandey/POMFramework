package com.qa.base;

import com.qa.util.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static com.qa.util.LoggerUtil.log;

/** DriverFactory class to manage driver related methods */
public class BaseWebDriverTest {

  // BrowserStack credentials
  private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
  private static final String AUTOMATE_KEY = System.getenv("BROWSERSTACK_ACCESSKEY");
  private static final String URL =
      "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

  private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

  /** @return WebDriver webDriver */
  public static synchronized WebDriver getWebDriver() {
    return tlDriver.get();
  }

  /** method to remove driver once test completes */
  @AfterMethod
  public static synchronized void removeWebDriver() {
    tlDriver.get().quit();
    tlDriver.remove();
  }

  /**
   * This method is used to initialize the WebDriver on the basis of browserName
   * @throws MalformedURLException exception
   */
  @BeforeMethod
  public void initializeDriver(ITestContext testContext)
          throws IOException {
    String runType;
   Properties properties= new Properties();
   properties.load(this.getClass().getResourceAsStream("/maven.properties"));
   runType = properties.getProperty("runType");

    if (runType.equalsIgnoreCase("local")) {
      createLocalDriver();

     } else if (properties.getProperty("execution").equalsIgnoreCase("browserStack")) {
    createBrowserStackDriver(testContext);
    }

    getWebDriver().manage().window().maximize();
  }

  public void createLocalDriver() throws IOException {
    Properties properties = new Properties();
    properties.load(this.getClass().getResourceAsStream("/maven.properties"));
    String browser = properties.getProperty("browser");

    OptionsManager optionsManager = new OptionsManager(properties);

    switch (browser.toUpperCase()) {
      case "CHROME" -> {
        System.setProperty("webdriver.chrome.driver", Constants.DRIVERPATH_CHROME);
        System.setProperty(
                "webdriver.chrome.silentOutput", "true"); // suppress browser logs on console
        tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
      }
      case "FIREFOX" -> {
        System.setProperty("webdriver.gecko.driver", Constants.DRIVERPATH_FIREFOX);
        System.setProperty(
                FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                "FFBrowserLogs"); // suppress browser logs on console
        tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
      }
      case "EDGE" -> {
        System.setProperty("webdriver.edge.driver", Constants.DRIVERPATH_EDGE);
        tlDriver.set(new EdgeDriver());
      }
      default -> log(browser + " is not found, please pass the right browser name");
    }
  }

  public void createBrowserStackDriver(ITestContext testContext) throws IOException {
    Properties prop = new Properties();
    prop.load(this.getClass().getResourceAsStream("/maven.properties"));
    String browserName= prop.getProperty("browser");

    DesiredCapabilities caps = new DesiredCapabilities();

    if (browserName.equalsIgnoreCase("CHROME")) {
      caps.setCapability("os", "Windows");
      caps.setCapability("os_version", "10");
      caps.setCapability("browser", "Chrome");
      caps.setCapability("browser_version", "latest");
      caps.setCapability("name", testContext.getName());
      tlDriver.set(new RemoteWebDriver(new URL(URL), caps));
    }
  }
} // end of class DriverFactory
