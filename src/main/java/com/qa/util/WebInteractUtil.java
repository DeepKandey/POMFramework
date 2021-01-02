package com.qa.util;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebInteractUtil {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static Actions action;

	/**
	 * {@summary Method to set WebDriver and initialize wait and action class}
	 * 
	 * @param driver
	 * @return void
	 * @author deepak
	 */
	public static void setDriver(WebDriver driver) {
		WebInteractUtil.driver = driver;
		wait = new WebDriverWait(WebInteractUtil.driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
		action = new Actions(WebInteractUtil.driver);
	}

	/**
	 * {@summary Method to click on WebElement}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void click(WebElement element) {
		try {
			element.click();
		} catch (Exception e2) {
			LoggerUtil.log("Failed to click element: " + element);
			e2.printStackTrace();
		}
	}// end of method Click()

	/**
	 * {@summary Method to click on WebElement using JavaScript}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void clickWithJavaScript(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			LoggerUtil.log("Failed to click element: " + element);
			e.printStackTrace();
		}
	}

	/**
	 * {@summary Method to click on WebElement using Actions class}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void clickWithActions(WebElement element) {
		try {
			action.click().build().perform();
		} catch (Exception e1) {
			try {
				action.moveToElement(element).click().build().perform();
			} catch (Exception e2) {
				LoggerUtil.log("Failed to click element: " + element);
				e2.printStackTrace();
			}
		}
	}

	/**
	 * {@summary Method to enter text}
	 * 
	 * @param element, String
	 * @return void
	 * @author deepak
	 */
	public void sendKey(WebElement element, String value) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			if (element.isDisplayed()) {
				click(element);
			}
		} catch (Exception e) {
			LoggerUtil.log("Failed to enter value: " + value);
			e.printStackTrace();
		}
	}// end of method sendKey()

	/**
	 * {@summary Method to scroll to any WebElement using coordinates}
	 * 
	 * @param element, integer,integer
	 * @return void
	 * @author deepak
	 */
	public void scrollToElement(WebElement element, int x, int y) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);

		if (x != 0 && y != 0) {
			((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(" + x + "," + y + ")", element);

			try {
				if (element.isDisplayed())
					LoggerUtil.getLogger().info("Scrolled to element");
			} catch (Exception e) {
				LoggerUtil.getLogger().info("Failed to Scroll to element");
				e.printStackTrace();
				RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
				remoteDriver.getCapabilities().getBrowserName();
			}
		}
	}// end of method scrollToElement()

	/**
	 * {@summary Method to get browser name}
	 * 
	 * @param
	 * @return void
	 * @author deepak
	 */
	public void getBrowserName() {
		RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
		remoteDriver.getCapabilities().getBrowserName();
	}// end of method getBrowserName()

	/**
	 * {@summary Method to execute JavaScript for any WebElement}
	 * 
	 * @param executionScript,WebElement
	 * @return void
	 * @author deepak
	 */
	public void executeUsingJavaScript(String executionScript, WebElement element) {
		((JavascriptExecutor) driver).executeScript(executionScript, element);
	}// end of method executeUsingJavaScript()

	/**
	 * {@summary Method to verify if WebElement is enabled}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void isEnabled(WebElement element) {
		if (element.isEnabled())
			LoggerUtil.getLogger().info("Successfully verified that element is enabled");
		else
			LoggerUtil.getLogger().info("Failed to verify that element is enabled");
	}// end of method isEnabled()

	/**
	 * {@summary Method to clear text on any input box}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void clear(WebElement element) {
		click(element);
		element.clear();
	} // end of method clear()

	/**
	 * {@summary Method to verify if WebElement is selected}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void isSelected(WebElement element) {
		if (element.isEnabled())
			LoggerUtil.getLogger().info("Successfully verified that element is selected");
		else
			LoggerUtil.getLogger().info("Failed to verify that element is selected");
	}// end of method isSelected()

	/**
	 * {@summary Method to verify if WebElement is displayed}
	 * 
	 * @param element
	 * @return void
	 * @author deepak
	 */
	public void isDisplayed(WebElement element) {
		try {
			if (element.isDisplayed()) {
				LoggerUtil.getLogger().info("Successfully verified element is displayed");
			}
		} catch (Exception e) {
			LoggerUtil.getLogger().info("Failed to verify element is displayed");
		}
	}// end of method isDisplayed()

	/**
	 * {@summary Method to get text attribute on WebElement}
	 * 
	 * @param element
	 * @return String
	 * @author deepak
	 */
	public String getText(WebElement element) {
		return element.getText().trim();
	}// end of method getText()

	/**
	 * {@summary Method to get text attribute for list of WebElements}
	 * 
	 * @param listOfWebElements
	 * @return List of Strings
	 * @author deepak
	 */
	public List<String> getText(List<WebElement> list) {
		List<String> listOfString = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			listOfString.add(getText(list.get(i)).toLowerCase());
		}
		return listOfString;
	}// end of method getText()

	/**
	 * {@summary Method to get count of list of WebElements}
	 * 
	 * @param listOfWebElements
	 * @return integer
	 * @author deepak
	 */
	public int getCount(List<WebElement> listOfElement) {
		return listOfElement.size();
	} // end of method getCount()

	/**
	 * {@summary Method to get attribute value of WebElement}
	 * 
	 * @param element, String
	 * @return String
	 * @author deepak
	 */
	public String getAttributeValue(WebElement element, String attributeName) {
		return element.getAttribute(attributeName).trim();
	} // end of method getAttributeValue()

	/**
	 * {@summary Method to get CSS value of WebElement}
	 * 
	 * @param element, String
	 * @return String
	 * @author deepak
	 */
	public String getCSSValue(WebElement element, String propertyName) {
		return element.getCssValue(propertyName);
	}// end of method getCSSValue()

	/**
	 * {@summary Method to get Windows/Tabs Ids}
	 * 
	 * @param
	 * @return Set<Strings?
	 * @author deepak
	 */
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	} // end of method getWindowHandles()

	/**
	 * {@summary Method to set time zone of OS}
	 * 
	 * @param
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
	}// end of method setTimeZone()

	/**
	 * {@summary Method to get WebElement}
	 * 
	 * @param locator
	 * @return WebElement
	 * @author deepak
	 */
	public WebElement getWebElement(By locator) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator);
	} // end of method getWebElement()

	/**
	 * {@summary Method to get list of WebElements}
	 * 
	 * @param locator
	 * @return list of WebElements
	 * @author deepak
	 */
	public List<WebElement> getWebElements(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElements(locator);
	} // end of method getWebElement()
}