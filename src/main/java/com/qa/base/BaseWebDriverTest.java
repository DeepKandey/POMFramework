package com.qa.base;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.gson.Gson;
import com.qa.util.browserRelatedUtils.BrowserConfigUtility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class BaseWebDriverTest {
    protected WebDriver driver;
    private String runType;
    private String browserConfigString = "";
    private String browserVersion;
    protected JSONObject jsonObject;
    protected Gson gson = new Gson();

    protected ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod
    protected void createDriver(ITestContext testContext) throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));

        runType = properties.getProperty("runType");
        switch (runType.toUpperCase()) {
            case "LOCAL" -> createLocalDriver();
            case "BROWSERSTACK" -> createBrowserStackDriver(testContext);
        }
    }

    protected MutableCapabilities getBrowserOptionsAndSetVersion(String browser) throws IOException, ParseException {
        BrowserConfigUtility browserConfigUtility = new BrowserConfigUtility(browser);
        browserVersion = browserConfigUtility.getBrowserVersion();
        return browserConfigUtility.getOptions();
    }

    protected void createLocalDriver() throws IOException, ParseException {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));
        String browser = properties.getProperty("localBrowser");

        String operatingSystem = System.getProperty("os.name").toLowerCase();
        String operatingSyst;
        if (operatingSystem.contains("win")) {
            operatingSyst = "Windows";
        } else {
            operatingSyst = "Mac";
        }

        switch (browser.toUpperCase()) {
            case "CHROME" -> {
                ChromeOptions chromeOptions = (ChromeOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new ChromeDriver(chromeOptions);
            }
            case "FIREFOX" -> {
                FirefoxOptions firefoxOptions = (FirefoxOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "SAFARI" -> {
                SafariOptions safariOptions = (SafariOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new SafariDriver(safariOptions);
            }
            case "EDGE" -> {
                EdgeOptions edgeOptions = (EdgeOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new EdgeDriver(edgeOptions);
            }
            case "IE" -> {
                InternetExplorerOptions internetExplorerOptions = (InternetExplorerOptions) getBrowserOptionsAndSetVersion(browser);
                driver = new InternetExplorerDriver(internetExplorerOptions);
            }
        }
        driverThreadLocal.set(driver);
        browserConfigString = operatingSyst + "_" + browser + "_" + browserVersion;
    }

    protected void createBrowserStackDriver(ITestContext testContext) throws IOException, ParseException {
        JSONObject BSConfig;
        JSONParser jsonParser = new JSONParser();
        boolean desktopSession = false;

        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));
        String browserStackEnvironment = properties.getProperty("browserStackEnvironment");
        if (browserStackEnvironment == null) {
            browserStackEnvironment = "0";
        }

        String browserStackConfigSet = properties.getProperty("browserStackConfigSet");
        if (browserStackConfigSet == null) {
            browserStackConfigSet = "CORE";
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream jsonInputFile = switch (browserStackConfigSet.toUpperCase()) {
            case "CORE":
                yield classLoader.getResourceAsStream("coreBrowserstackConfig.json");
            case "SECONDARY":
                yield classLoader.getResourceAsStream("secondaryBrowserStack.json");
            case "LOCAL": {
                String currentDirectory = System.getProperty("user.dir");
                yield new FileInputStream(currentDirectory + "/src/test/resources/browserStackConfig.json");
            }
            default:
                throw new IllegalStateException("Unexpected value");
        };

        BSConfig = (JSONObject) jsonParser.parse(new InputStreamReader(jsonInputFile, StandardCharsets.UTF_8));
        JSONArray environments = (JSONArray) BSConfig.get("environments");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        capabilities.setCapability("safariAllowPopUps", "true");
        capabilities.setCapability("autoAcceptAlerts", true);
        ChromeOptions options = new ChromeOptions();

        if (!isNullOrEmpty(properties.getProperty("disableGeoLocation"))) {
            if (properties.getProperty("disableGeoLocation").equalsIgnoreCase("true")) {
                Map<String, Object> prefs = new HashMap<>();
                Map<String, Object> profile = new HashMap<>();
                Map<String, Object> contentSettings = new HashMap<>();
                // SET CHROME OPTIONS
                // 0- Default, 1- Allow, 2- Block
                contentSettings.put("geolocation", 2);
                profile.put("manage_default_content_settings", contentSettings);
                prefs.put("profile", profile);
                options.setExperimentalOption("prefs", prefs);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            }
        }

        Map<String, String> envCapabilities = (Map<String, String>) environments.get(Integer.parseInt(browserStackEnvironment));
        Iterator iterator = envCapabilities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            if (pair.getKey().toString().equalsIgnoreCase("os")) {
                desktopSession = true;
            }
        }

        Map<String, String> commonCapabilities = (Map<String, String>) BSConfig.get("capabilities");
        iterator = commonCapabilities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        capabilities.setCapability("name", testContext.getName());
        String username = System.getenv("BROWSERSTACK_USER_ACCOUNT");
        String accessKey = System.getenv("BROWSERSTACK_API_KEY");
        String server = (String) BSConfig.get("server");

        if (!isNullOrEmpty(properties.getProperty("browserstackSimulateHW"))) {
            if (properties.getProperty("browserstackSimulateHW").equalsIgnoreCase("true")) {
                // Configure ChromeOptions to pass fake media stream
                options = new ChromeOptions();
                options.addArguments("use-fake-device-for-media-stream");
                options.addArguments("use-fake-ui-for-media-stream");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            }
        }

        try {
            if (desktopSession) {
                driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + server), capabilities);
                driverThreadLocal.set(driver);
            }
        } catch (WebDriverException webDriverException) {
            webDriverException.printStackTrace();
        }

        if (desktopSession) {
            driver.manage().window().maximize();
        }

        String browser = envCapabilities.get("browser");
        String browserVersion = envCapabilities.get("browser_version");
        String os = envCapabilities.get("os");
        String operatingSys = "";
        if (os == null) {
            String device = envCapabilities.get("device");
            String version = envCapabilities.get("os_version");
            browserConfigString = device + "_" + version;
        } else {
            if (os.equalsIgnoreCase("Windows")) {
                operatingSys = "Windows";
            } else if (os.equalsIgnoreCase("OS X")) {
                operatingSys = "Mac";
            }
            browserConfigString = operatingSys + "_" + browser + "_" + browserVersion;
        }
    }

    @AfterMethod
    protected void removeWebDriver() {
        if (driverThreadLocal != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
