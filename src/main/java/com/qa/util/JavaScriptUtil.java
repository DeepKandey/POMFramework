package com.qa.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
  private WebDriver driver;

  public JavaScriptUtil(WebDriver driver) {
    this.driver = driver;
  }

  public void flash(WebElement element) {
    String bgColor = element.getCssValue("backgroundColor");
    for (int i = 0; i < 20; i++) {
      changeColor("rgb(0,200,0)", element); // 1
      changeColor(bgColor, element); // 2
    }
  }

  private void changeColor(String color, WebElement element) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void drawBorder(WebElement element) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("arguments[0].style.border='3px solid red'", element);
  }

  public void generateAlert(String message) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("alert('" + message + "')");
  }

  public void clickElementByJS(WebElement element) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("arguments[0].click();", element);
  }

  public void refreshBrowserByJS() {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("history.go(0)");
  }

  public String getTitleByJS() {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    return js.executeScript("return document.title;").toString();
  }

  public String getPageInnerText() {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    return js.executeScript("return document.documentElement.innerText;").toString();
  }

  public void scrollPageDown() {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
  }

  public void scrollIntoView(WebElement element) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("arguments[0].scrollIntoView(true);", element);
  }

  public String getBrowserInfo() {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    return js.executeScript("return navigator.userAgent;").toString();
  }

  public void sendKeysUsingJSWithId(String id, String value) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
  }

  public void sendKeysUsingJSWithName(String name, String value) {
    JavascriptExecutor js = ((JavascriptExecutor) driver);
    js.executeScript("document.getElementByName('" + name + "').value='" + value + "'");
  }
}
