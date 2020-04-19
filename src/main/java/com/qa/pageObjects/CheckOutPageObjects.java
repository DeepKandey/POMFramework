package com.qa.pageObjects;

import org.openqa.selenium.By;

public class CheckOutPageObjects {

	protected By emailID = By.id("email");

	protected By password = By.id("password");

	protected By submitBtn = By.xpath("//button[@data-test='login-submit-button']");

	protected By fareOnCheckOut = By.xpath("//span[@data-test='trip-card-total']/span");

	public CheckOutPageObjects() {
	}
}