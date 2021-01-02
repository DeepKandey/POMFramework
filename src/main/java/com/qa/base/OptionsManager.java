package com.qa.base;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

    Properties properties;

    public OptionsManager(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return ChromeOptions
     * @summary set ChromeOptions
     * @author deepak rai
     */
    public ChromeOptions getChromeOptions() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        if (Boolean.parseBoolean(properties.getProperty("headless")))
            chromeOptions.addArguments("--headless");
        if (Boolean.parseBoolean(properties.getProperty("incognito")))
            chromeOptions.addArguments("--incognito");
        return chromeOptions;
    }

    /**
     * {@summary set FirefoxOptions}
     *
     * @return FirefoxOptions
     * @author deepak rai
     */
    public FirefoxOptions getFirefoxOptions() {
        final FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (Boolean.parseBoolean(properties.getProperty("headless")))
            firefoxOptions.addArguments("--headless");
        return firefoxOptions;
    }
}// End of class OptionsManager