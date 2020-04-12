package com.qa.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebInteractUtil {

	private static WebDriver driver;

	public void Click(WebElement element, Boolean isClickWithJavaScriptExecutor) {

		Actions action = new Actions(driver);
		try {
			if (Boolean.TRUE.equals(isClickWithJavaScriptExecutor)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} else
				element.click();
		} catch (Exception e) {
			try {
				action.click().build().perform();
			} catch (Exception e1) {
				try {
					action.moveToElement(element).click().build().perform();
				} catch (Exception e2) {

					System.out.println("Failed to click element: " + element);
					e2.printStackTrace();
				}
			}
		}
	}// end of method Click()

	public void sendKey(WebElement element, String value, Boolean clear, Boolean tab) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(element));

			if (element.isDisplayed()) {
				Click(element, false);
			}

			// Use clear=false
			if (Boolean.TRUE.equals(clear))
				element.clear();
			element.sendKeys(value);

			if (Boolean.TRUE.equals(tab))
				element.sendKeys(Keys.TAB);
		} catch (Exception e) {
			System.out.println("Failed to enter value: " + value);
			e.printStackTrace();
		}
	}// end of method sendKey()

	public void scrollToElement(WebElement element, int x, int y) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);

		if (x != 0 && y != 0) {
			((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(" + x + "," + y + ")", element);

			try {
				if (element.isDisplayed())
					System.out.println("Scrolled to element");
			} catch (Exception e) {
				System.out.println("Failed to Scroll to element");
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
			System.out.println("Successfully verified that element is enabled");
		else
			System.out.println("Failed to verify that element is enabled");
	}// end of method isEnabled()

	public void clear(WebElement element) {
		Click(element, null);
		element.clear();
	} // end of method clear()

	public void isSelected(WebElement element) {
		if (element.isEnabled())
			System.out.println("Successfully verified that element is selected");
		else
			System.out.println("Failed to verify that element is selected");
	}// end of method isSelected()

	public void isDisplayed(WebElement element) {
		try {
			if (element.isDisplayed()) {
				System.out.println("Successfully verified element is displayed");
			}
		} catch (Exception e) {
			System.out.println("Failed to verify element is displayed");
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
			System.out.println("Failed to set timezone");
			e.printStackTrace();
			return false;
		}
	}// end of method setTimeZone()

	public WebElement getWebElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator);
	} // end of method getWebElement()
}