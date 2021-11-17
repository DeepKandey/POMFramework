package com.qa.base;

import com.qa.util.LoggerUtil;
import io.qameta.allure.Step;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

  private static final int POLLING = 500;
  protected WebDriver driver;
  private int shortTimeout = 10;
  private int longTimeout = 60;
  private WebDriverWait shortWait;
  private WebDriverWait longWait;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    shortWait =
        new WebDriverWait(driver, Duration.ofSeconds(shortTimeout), Duration.ofMillis(POLLING));
    longWait =
        new WebDriverWait(driver, Duration.ofSeconds(longTimeout), Duration.ofMillis(POLLING));
    PageFactory.initElements(new AjaxElementLocatorFactory(driver, shortTimeout), this);
  }

  protected void setShortTimeout(int newTimeout) {
    shortTimeout = newTimeout;
    shortWait.withTimeout(Duration.ofSeconds(shortTimeout));
  }

  protected void setLongTimeout(int newTimeout) {
    longTimeout = newTimeout;
    longWait.withTimeout(Duration.ofSeconds(longTimeout));
  }

  /**
   * {summary: Method to click on WebElement}
   *
   * @param element click on element
   * @author deepak
   */
  public void elementClick(WebElement element) {
    try {
      Actions actions = new Actions(this.driver);
      actions.moveToElement(element);
    } catch (Exception ignored) {
    }
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    } catch (Exception ignored) {
    }

    boolean clickSuccessful = false;
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
      clickSuccessful = true;
    } catch (Exception ignored) {
    }
    try {
      if (!clickSuccessful) {
        element.click();
      }
    } catch (Exception ignored) {
    }
  }

  /**
   * Method to click on WebElement using Actions class
   *
   * @param element - element for click operation
   * @author deepak
   */
  public void clickWithActions(WebElement element) {
    Actions actions = new Actions(this.driver);
    try {
      actions.moveToElement(element).click().build().perform();
    } catch (Exception e1) {
      LoggerUtil.log("Failed to click element: " + element);
      e1.printStackTrace();
    }
  }

  /**
   * {summary Method to scroll to any WebElement}
   *
   * @param element Webelement
   * @author deepak
   */
  protected void scrollToElement(WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
  } // end of method scrollToElement()

  /**
   * {summary Method to execute JavaScript for any WebElement}
   *
   * @param executionScript,WebElement javascriptToBeExecuted and WebElement
   * @author deepak
   */
  public void executeUsingJavaScript(String executionScript, WebElement element) {
    ((JavascriptExecutor) driver).executeScript(executionScript, element);
  } // end of method executeUsingJavaScript()

  protected void waitForElementToAppear(By locator) {
    shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected void waitForElementToAppear(WebElement element) {
    shortWait.until(ExpectedConditions.visibilityOf(element));
  }

  protected void waitForElementToBeVisible(By locator) {
    WebDriverWait wait = new WebDriverWait(driver, longTimeout, POLLING);
    wait.ignoring(NoSuchElementException.class);
    wait.ignoring(ElementClickInterceptedException.class);
    wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void waitForElementToBeVisible(WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, longTimeout, POLLING);
    wait.ignoring(NoSuchElementException.class);
    wait.ignoring(ElementClickInterceptedException.class);
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  protected boolean isElementClickable(WebElement element) {
    return element.isDisplayed() && element.isEnabled();
  }

  /**
   * {summary Method to set time zone of OS}
   *
   * @param timeZone timezone to be set
   * @return boolean
   * @author deepak
   */
  public boolean setTimeZone(String timeZone) {
    try {
      Process process = new ProcessBuilder("tzutil.exe", "/s", timeZone).start();
      return process.isAlive();
    } catch (IOException e) {
      LoggerUtil.getLogger().info("Failed to set timezone");
      e.printStackTrace();
      return false;
    }
  } // end of method setTimeZone()

  /**
   * {summary Method to wait for page to load}
   *
   * @author deepak
   */
  @Step("Go to URL : {0}")
  public void waitForPageToLoad() {
    longWait.until(
        driver ->
            ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .toString()
                .equals("complete"));
  }
}
