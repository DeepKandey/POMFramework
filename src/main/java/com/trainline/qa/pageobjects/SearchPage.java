package com.trainline.qa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.trainline.qa.base.DriverFactory;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.TestUtil;

public class SearchPage {
	@FindBy(id = "from.text")
	WebElement departureStn;

	@FindBy(id = "to.text")
	private WebElement destinationStn;

	@FindBy(xpath = "//button[@data-test='submit-journey-search-button']")
	private WebElement searchButton;

	public SearchPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void enterJourneyDetails() {
		String destination= "Manchester";
		departureStn.sendKeys("London");
		destinationStn.sendKeys(destination);
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
		String scriptForDestination = "return document.getElementById(\"to.text\").value;";

		while (!js.executeScript(scriptForDestination).equals(destination)) {
			destinationStn.clear();
			destinationStn.sendKeys(destination);
		}
		DriverFactory.getInstance().getDriver().findElement(By.id("to.text")).sendKeys(Keys.ENTER);
	}

	public void clickOnSearchBtn() {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), TestUtil.EXPLICIT_WAIT);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
		while (!DriverFactory.getInstance().getDriver()
				.findElements(By.xpath("//button[@data-test='submit-journey-search-button']")).isEmpty()) {
			LoggerUtil.logMessage("search button is still present after first Click");
			searchButton.click();
		}
		//return new MatrixPage(DriverFactory.getInstance().getDriver());
	}
}