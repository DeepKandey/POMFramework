package com.qa.util;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v95.emulation.Emulation;
import org.openqa.selenium.devtools.v95.log.Log;
import org.openqa.selenium.devtools.v95.network.Network;
import org.openqa.selenium.devtools.v95.network.model.ConnectionType;
import org.openqa.selenium.devtools.v95.network.model.Headers;
import org.openqa.selenium.devtools.v95.performance.Performance;
import org.openqa.selenium.devtools.v95.performance.model.Metric;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.*;
import java.util.stream.Collectors;

public class ChromeDevToolsUtil {

  private DevTools devTools;
  private WebDriver driver;
  private String browser;

  public ChromeDevToolsUtil(WebDriver driver, String browser) {
    this.driver = driver;
    this.browser = browser;
    getBrowserDevTools();
    createDevToolsSession();
  }

  /** create dev tools session */
  public void createDevToolsSession() {
    devTools.createSession();
  }

  /** disable dev tools network */
  public void disableNetwork() {
    devTools.send(Network.disable());
  }

  /** disable dev tools network */
  private void getBrowserDevTools() {
    switch (browser) {
      case "chrome" -> devTools = ((ChromeDriver) driver).getDevTools();
      case "edge" -> devTools = ((EdgeDriver) driver).getDevTools();
    }
  }

  /** capture dev tools network http requests */
  public void getNetworkHTTPRequest() {
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    devTools.addListener(
        Network.requestWillBeSent(),
        e -> {
          System.out.println((e.getRequest().getUrl()));
          System.out.println(e.getRequest().getPostData());
          System.out.println(e.getRequest().getMethod());
        });
  }

  /**
   * Simulating device mode
   *
   * @param deviceMetrics device metrics
   */
  public void simulateDeviceMode(Map<String, Object> deviceMetrics) {
    switch (browser) {
      case "chrome" -> ((ChromiumDriver) driver)
              .executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
      case "edge" -> ((EdgeDriver) driver)
              .executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
    }
  }

  /**
   * Simulating Network Speed Parameters: offline * latency downloadThroughput uploadThroughput
   * connectionType
   *
   * @param offline
   * @param latency
   * @param downloadThroughput
   * @param uploadThroughput
   * @param connectionType
   */
  public void simulateNetworkSpeed(
      boolean offline,
      Number latency,
      Number downloadThroughput,
      Number uploadThroughput,
      String connectionType) {
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    if (connectionType.equalsIgnoreCase("2G")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.CELLULAR2G)));
    } else if (connectionType.equalsIgnoreCase("3G")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.CELLULAR3G)));
    } else if (connectionType.equalsIgnoreCase("4G")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.CELLULAR4G)));
    } else if (connectionType.equalsIgnoreCase("WIFI")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.WIFI)));
    } else if (connectionType.equalsIgnoreCase("BLUETOOTH")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.BLUETOOTH)));
    } else if (connectionType.equalsIgnoreCase("WIMAX")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.WIMAX)));
    } else if (connectionType.equalsIgnoreCase("ETHERNET")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.ETHERNET)));
    } else if (connectionType.equalsIgnoreCase("NONE")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.NONE)));
    } else if (connectionType.equalsIgnoreCase("OTHER")) {
      devTools.send(
          Network.emulateNetworkConditions(
              false,
              latency,
              downloadThroughput,
              uploadThroughput,
              Optional.of(ConnectionType.OTHER)));
    }
    // (false, 20, 20, 50, Optional.of(ConnectionType.OTHER)));
  }

  /**
   * set the geolocation
   *
   * @param latitude
   * @param longitude
   * @param accuracy
   */
  public void setGeoLocation(double latitude, double longitude, double accuracy) {
    devTools.send(
        Emulation.setGeolocationOverride(
            Optional.of(latitude), Optional.of(longitude), Optional.of(accuracy)));
  }

  /** get console logs */
  public void getConsoleLogs() {
    devTools.send(Log.enable());
    devTools.addListener(
        Log.entryAdded(),
        logEntry -> {
          System.out.println("log: " + logEntry.getText());
          System.out.println("level: " + logEntry.getLevel());
        });
  }

  /** capturing performance data(metrics) */
  public void getPerformanceMetrics() {
    devTools.send(Performance.enable(Optional.empty()));
    List<Metric> metrics = devTools.send(Performance.getMetrics());
    List<String> metricNames = metrics.stream().map(o -> o.getName()).collect(Collectors.toList());

    devTools.send(Performance.disable());

    List<String> metricsToCheck =
        Arrays.asList(
            "Timestamp",
            "Documents",
            "Frames",
            "JSEventListeners",
            "LayoutObjects",
            "MediaKeySessions",
            "Nodes",
            "Resources",
            "DomContentLoaded",
            "NavigationStart");

    metricsToCheck.forEach(
        metric ->
            System.out.println(
                metric + " is : " + metrics.get(metricNames.indexOf(metric)).getValue()));
  }

  /**
   * login with basic auth
   *
   * @param username
   * @param password
   * @param url
   */
  public void loginWithBasicAuth(String username, String password, String url) {
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    Map<String, Object> headers = new HashMap<>();
    String basicAuth =
        "Basic "
            + new String(
                new Base64().encode(String.format("%s:%s", username, password).getBytes()));
    headers.put("Authorization", basicAuth);
    devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
    driver.get(url);
  }
}
