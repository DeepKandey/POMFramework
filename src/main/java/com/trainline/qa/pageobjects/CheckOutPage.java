package com.trainline.qa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.trainline.qa.base.DriverFactory;
import com.trainline.qa.util.TestUtil;

public class CheckOutPage {
	RegisterPage registerPage;

	@FindBy(id = "email")
	private WebElement emailID;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(xpath = "//button[@data-test='login-submit-button']")
	private WebElement submitBtn;

	@FindBy(xpath = "//span[@data-test='trip-card-total']/span")
	private WebElement fareOnCheckOut;

	public String getFareOnChckOutPage() {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), TestUtil.EXPLICIT_WAIT);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-test='trip-card-total']")));
		return fareOnCheckOut.getAttribute("innerHTML");
	}

	public CheckOutPage(WebDriver driver) {
		registerPage = new RegisterPage(driver);
		PageFactory.initElements(driver, this);
	}
}