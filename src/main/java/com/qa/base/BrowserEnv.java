package com.qa.base;

/**
 * browser names as enums
 *
 * @author deepak Rai
 */
public enum BrowserEnv {
  CHROME("chrome"),
  FIREFOX("firefox"),
  IE("internet explorer"),
  SAFARI("safari");

  private String browser;

  BrowserEnv(String browserName) {
    this.browser = browserName;
  }
}
