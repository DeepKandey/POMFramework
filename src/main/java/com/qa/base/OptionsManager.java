package com.qa.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

  Properties properties;

  public OptionsManager(Properties properties) {
    this.properties = properties;
  }

  /**
   * summary set ChromeOptions
   *
   * @return ChromeOptions
   * @author deepak rai
   */
  public ChromeOptions getChromeOptions() {
    final ChromeOptions chromeOptions = new ChromeOptions();

    if (Boolean.parseBoolean(properties.getProperty("headless")))
      chromeOptions.addArguments("--headless");
    if (Boolean.parseBoolean(properties.getProperty("incognito")))
      chromeOptions.addArguments("--incognito");
    if (Boolean.parseBoolean(properties.getProperty("disablePasswordManager"))) {
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("credentials_enable_service", false);
      prefs.put("profile.password_manager_enabled", false);
      chromeOptions.setExperimentalOption("prefs", prefs);
    }
    return chromeOptions;
  }

  /**
   * {summary set FirefoxOptions}
   *
   * @return FirefoxOptions
   * @author deepak rai
   */
  public FirefoxOptions getFirefoxOptions() {
    final FirefoxOptions firefoxOptions = new FirefoxOptions();

    if (Boolean.parseBoolean(properties.getProperty("headless")))
      firefoxOptions.addArguments("--headless");
    if (Boolean.parseBoolean(properties.getProperty("incognito")))
      firefoxOptions.addArguments("-private");
    return firefoxOptions;
  }
} // End of class OptionsManager
