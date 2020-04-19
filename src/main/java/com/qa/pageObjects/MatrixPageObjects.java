package com.qa.pageObjects;

import org.openqa.selenium.By;

public class MatrixPageObjects {

	protected By ticketFareRadioBtn = By.xpath("descendant::input[@type='radio']");

	protected By ticketFareValue1 = By
			.xpath("descendant::input[@type='radio'][5]/parent::div/following-sibling::div/span[2]/span");

	protected By registerLnk = By.linkText("Register");

	protected By quickChckOut = By.xpath("//button[@data-test='cjs-button-quick-buy']");

	public MatrixPageObjects() {

	}
} // end of class MatrixPageObjects