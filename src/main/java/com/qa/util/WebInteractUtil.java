package com.qa.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.DriverFactory;

public class WebInteractUtil {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static Actions action;
	private static Logger logger;

	public static void setDriver(WebDriver driver, Logger logger) {
		WebInteractUtil.driver = driver;
		WebInteractUtil.logger= logger;
		wait = new WebDriverWait(WebInteractUtil.driver, Constants.EXPLICIT_WAIT);
		action = new Actions(WebInteractUtil.driver);
	}

	public void Click(WebElement element) {
		try {
			element.click();
		} catch (Exception e2) {
			DriverFactory.getLogger().info("Failed to click element: " + element);
			e2.printStackTrace();
		}
	}// end of method Click()

	public void clickWithJavaScript(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			DriverFactory.getLogger().info("Failed to click element: " + element);
			e.printStackTrace();
		}
	}

	public void clickWithActions(WebElement element) {
		try {
			action.click().build().perform();
		} catch (Exception e1) {
			try {
				action.moveToElement(element).click().build().perform();
			} catch (Exception e2) {

				DriverFactory.getLogger().info("Failed to click element: " + element);
				e2.printStackTrace();
			}
		}
	}

	public void sendKey(WebElement element, String value) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			if (element.isDisplayed()) {
				Click(element);
			}
		} catch (Exception e) {
			DriverFactory.getLogger().info("Failed to enter value: " + value);
			e.printStackTrace();
		}
	}// end of method sendKey()

	public void scrollToElement(WebElement element, int x, int y) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);

		if (x != 0 && y != 0) {
			((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(" + x + "," + y + ")", element);

			try {
				if (element.isDisplayed())
					DriverFactory.getLogger().info("Scrolled to element");
			} catch (Exception e) {
				DriverFactory.getLogger().info("Failed to Scroll to element");
				e.printStackTrace();
				RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
				remoteDriver.getCapabilities().getBrowserName();
			}
		}
	}// end of method scrollToElement()

	public void getBrowserName() {
		RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
		remoteDriver.getCapabilities().getBrowserName();
	}// end of method getBrowserName()

	public void executeUsingJavaScript(String executionScript, WebElement element) {
		((JavascriptExecutor) driver).executeScript(executionScript, element);
	}// end of method executeUsingJavaScript()

	public void isEnabled(WebElement element) {
		if (element.isEnabled())
			DriverFactory.getLogger().info("Successfully verified that element is enabled");
		else
			DriverFactory.getLogger().info("Failed to verify that element is enabled");
	}// end of method isEnabled()

	public void clear(WebElement element) {
		Click(element);
		element.clear();
	} // end of method clear()

	public void isSelected(WebElement element) {
		if (element.isEnabled())
			DriverFactory.getLogger().info("Successfully verified that element is selected");
		else
			DriverFactory.getLogger().info("Failed to verify that element is selected");
	}// end of method isSelected()

	public void isDisplayed(WebElement element) {
		try {
			if (element.isDisplayed()) {
				DriverFactory.getLogger().info("Successfully verified element is displayed");
			}
		} catch (Exception e) {
			DriverFactory.getLogger().info("Failed to verify element is displayed");
		}
	}// end of method isDisplayed()

	public String getText(WebElement element) {
		return element.getText().trim();
	}// end of method getText()

	public List<String> getText(List<WebElement> list) {
		List<String> listOfString = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			listOfString.add(getText(list.get(i)).toLowerCase());
		}
		return listOfString;
	}// end of method getText()

	public int getCount(List<WebElement> listOfelement) {
		return listOfelement.size();
	} // end of method getCount()

	public String getAttributeValue(WebElement element, String attributeName) {
		return element.getAttribute(attributeName).trim();
	} // end of method getAttributeValue()

	public String getCSSValue(WebElement element, String propertyName) {
		return element.getCssValue(propertyName);
	}// end of method getCSSValue()

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	} // end of method getWindowHandles()

	public boolean setTimeZone(String timeZone) {
		try {
			Process process = new ProcessBuilder("tzutil.exe", "/s", timeZone).start();
			return process.isAlive();
		} catch (IOException e) {
			DriverFactory.getLogger().info("Failed to set timezone");
			e.printStackTrace();
			return false;
		}
	}// end of method setTimeZone()

	public WebElement getWebElement(By locator) {
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator);
	} // end of method getWebElement()

	public List<WebElement> getWebElements(By locator) {
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElements(locator);
	} // end of method getWebElement()
}