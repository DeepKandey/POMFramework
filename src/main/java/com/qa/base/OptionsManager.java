package com.qa.base;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	Properties prop;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	/**
	 * 
		 * {@summary set FirefoxChromeOptionsOptions}
		 * @param
		 * @return FirefoxOptions
		 * @author deepak rai
	 */
	public ChromeOptions getChromeOptions() {
		final ChromeOptions co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))
			co.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito")))
			co.addArguments("--incognito");
		return co;
	}

	/**
	 * 
		 * {@summary set FirefoxOptions}
		 * @param
		 * @return FirefoxOptions
		 * @author deepak rai
	 */
	public FirefoxOptions getFirefoxOptions() {
		final FirefoxOptions fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless")))
			fo.addArguments("--headless");
		return fo;
	}
}// End of class OptionManager