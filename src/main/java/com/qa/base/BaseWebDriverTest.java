package com.qa.base;

import com.qa.util.LoggerUtil;
import com.qa.util.browserRelatedUtils.BrowserConfigUtility;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class BaseWebDriverTest {
    protected WebDriver driver;
    private String browserVersion;

    protected ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected LoggerUtil logger = new LoggerUtil(BaseWebDriverTest.class);

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod
    protected void createDriver(ITestContext testContext) throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));

        String runType = properties.getProperty("runType");
        switch (runType.toUpperCase()) {
            case "LOCAL" -> createLocalDriver(false);
            case "DOCKER" -> createLocalDriver(true);
        }
    }

    protected MutableCapabilities getBrowserOptionsAndSetVersion(String browser) throws IOException, ParseException {
        BrowserConfigUtility browserConfigUtility = new BrowserConfigUtility(browser);
        browserVersion = browserConfigUtility.getBrowserVersion();
        return browserConfigUtility.getOptions();
    }

    protected void createLocalDriver(Boolean runOnGrid) throws IOException, ParseException {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));
        String host= "localhost";
        String browser = properties.getProperty("localBrowser");
        if (System.getProperty("HUB_HOST") != null) {
            host = System.getProperty("HUB_HOST");
        }

        String OS = System.getProperty("os.name").toLowerCase();
        String operatingSystem;
        if (OS.contains("win")) {
            operatingSystem = "Windows";
        } else {
            operatingSystem = "Mac";
        }

        switch (browser.toUpperCase()) {
            case "CHROME" -> {
                ChromeOptions chromeOptions = (ChromeOptions) getBrowserOptionsAndSetVersion(browser);
                if(!runOnGrid)
                driver = new ChromeDriver(chromeOptions);
                else {
                    driver = new RemoteWebDriver(new URL("http://"+host+":4444/"),chromeOptions);
                }
            }
            case "FIREFOX" -> {
                FirefoxOptions firefoxOptions = (FirefoxOptions) getBrowserOptionsAndSetVersion(browser);
                if(!runOnGrid)
                    driver = new FirefoxDriver(firefoxOptions);
                else
                    driver = new RemoteWebDriver(new URL("http://"+host+":4444/"),firefoxOptions);
            }
            case "SAFARI" -> {
                SafariOptions safariOptions = (SafariOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new SafariDriver(safariOptions);
            }
            case "EDGE" -> {
                EdgeOptions edgeOptions = (EdgeOptions) getBrowserOptionsAndSetVersion(browser);
                if(!runOnGrid)
                    driver = new EdgeDriver(edgeOptions);
                else
                    driver = new RemoteWebDriver(new URL("http://"+host+":4444/"),edgeOptions);
            }
            case "IE" -> {
                InternetExplorerOptions internetExplorerOptions = (InternetExplorerOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new InternetExplorerDriver(internetExplorerOptions);
            }
        }
        driverThreadLocal.set(driver);
        String browserConfigString = operatingSystem + "_" + browser + "_" + browserVersion;
        logger.info(browserConfigString);
    }

    @AfterMethod
    protected void removeWebDriver() {
        if (driverThreadLocal != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
