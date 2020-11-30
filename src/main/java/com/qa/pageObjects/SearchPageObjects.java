package com.qa.pageObjects;

import org.openqa.selenium.By;

import com.qa.util.WebInteractUtil;

public class SearchPageObjects extends WebInteractUtil {

	public SearchPageObjects() {
		super();
	}

	protected By departureStn = By.id("from.search");

	protected By destinationStn = By.id("to.search");

	protected By searchButton = By.xpath("//button[@data-test='submit-journey-search-button']");

} // end of class SearchPageObjects