package com.qa.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

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
    if (Boolean.parseBoolean(properties.getProperty("maximize")))
      chromeOptions.addArguments("--start-maximized");
    if (Boolean.parseBoolean(properties.getProperty("disablePasswordManager"))) {
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("credentials_enable_service", false);
      prefs.put("profile.password_manager_enabled", false);

      chromeOptions.setExperimentalOption("prefs", prefs);
    }
    if (Boolean.parseBoolean(properties.getProperty("autoDownload"))) {
      HashMap<String, String> prefs = new HashMap<>();
      prefs.put("download.default_directory", "/data/FolderToDownload");

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
    // Firefox by default starts maximized

    if (Boolean.parseBoolean(properties.getProperty("headless")))
      firefoxOptions.addArguments("--headless");
    if (Boolean.parseBoolean(properties.getProperty("incognito")))
      firefoxOptions.addArguments("-private");
    if (Boolean.parseBoolean(properties.getProperty("disable-notification"))) {
      firefoxOptions.addPreference("dom.webnotifications.serviceworker.enabled", false);
      firefoxOptions.addPreference("dom.webnotifications.enabled", false);
    }
    if (Boolean.parseBoolean(properties.getProperty("autoDownload"))) {
      FirefoxProfile firefoxProfile = new FirefoxProfile();

      firefoxProfile.setPreference("browser.download.folderList", 2);
      firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
      firefoxProfile.setPreference("browser.download.dir", "/data/WorkArea/FolderToDownload");
      firefoxProfile.setPreference(
          "browser.helperApps.neverAsk.saveToDisk",
          "application/vnd.ms-excel,application/csv,application/vnd.key,application/zip,application/pdf,application/xml");
      firefoxProfile.setPreference("pdfjs.disabled", true);

      firefoxOptions.setProfile(firefoxProfile);
    }
    return firefoxOptions;
  }
} // End of class OptionsManager
