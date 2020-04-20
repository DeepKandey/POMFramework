package com.qa.util;

import org.apache.logging.log4j.Logger;

/*************************************** PURPOSE **********************************
- This class implements the WebDriverEventListener, which is included under events.
The purpose of implementing this interface is to override all the methods and define certain useful  Log statements 
which would be displayed/logged as the application under test is being run.
Do not call any of these methods, instead these methods will be invoked automatically
as an when the action done (click, findBy etc). 
*/

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.qa.base.DriverFactory;

public class WebEventListener extends DriverFactory implements WebDriverEventListener {

	private static Logger logger;

	public WebEventListener() {
		super(logger);
	}

	public void beforeAlertAccept(WebDriver driver) {
		DriverFactory.getLogger().info("Before accepting alert");
	}

	public void afterAlertAccept(WebDriver driver) {
		DriverFactory.getLogger().info("After accepting alert");
	}

	public void afterAlertDismiss(WebDriver driver) {

	}

	public void beforeAlertDismiss(WebDriver driver) {

	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		DriverFactory.getLogger().info("Before navigating to: '" + url + "'");
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		DriverFactory.getLogger().info("Navigated to:'" + url + "'");
	}

	public void beforeNavigateBack(WebDriver driver) {
		DriverFactory.getLogger().info("Navigating back to previous page");
	}

	public void afterNavigateBack(WebDriver driver) {
		DriverFactory.getLogger().info("Navigated back to previous page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		DriverFactory.getLogger().info("Navigating forward to next page");
	}

	public void afterNavigateForward(WebDriver driver) {
		DriverFactory.getLogger().info("Navigated forward to next page");
	}

	public void beforeNavigateRefresh(WebDriver driver) {

	}

	public void afterNavigateRefresh(WebDriver driver) {

	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		DriverFactory.getLogger().info("Trying to find Element By : " + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		DriverFactory.getLogger().info("Found Element By : " + by.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		DriverFactory.getLogger().info("Trying to click on: " + element.toString());
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		DriverFactory.getLogger().info("Clicked on: " + element.toString());
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		DriverFactory.getLogger().info("Value of the:" + element.toString() + " before any changes made");
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		DriverFactory.getLogger().info("Element value changed to: " + element.toString());
	}

	public void beforeScript(String script, WebDriver driver) {

	}

	public void afterScript(String script, WebDriver driver) {

	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {

	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {

	}

	public void onException(Throwable throwable, WebDriver driver) {
		DriverFactory.getLogger().info("Exception occured: " + throwable);
		/*
		 * try { TestUtil.takeScreenshotAtEndOfTest(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {

	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

	}

	public void beforeGetText(WebElement element, WebDriver driver) {
		DriverFactory.getLogger().info("Before getting text of the element:" + element.toString());
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) {
		DriverFactory.getLogger().info("After getting text of the element:" + element.toString());
	}
}
