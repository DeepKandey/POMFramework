package com.trainline.qa.util;

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
			if (isClickWithJavaScriptExecutor) {
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
			if (clear)
				element.clear();

			element.sendKeys(value);

			if (tab)
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
	
	
}