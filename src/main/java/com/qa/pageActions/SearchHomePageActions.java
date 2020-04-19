package com.qa.pageActions;

import com.qa.pageObjects.SearchPageObjects;

public class SearchHomePageActions extends SearchPageObjects {

	
	public void clickOnSearchButton() {
		Click(getWebElement(searchButton), false);
	}

	public void enterTextInDepartureStn(String departure) {
		sendKey(getWebElement(departureStn), departure, true, false);
	}

	public void enterTextInDestinationStn(String destination) {
		sendKey(getWebElement(destinationStn), destination, true, false);
	}

} // end of SearchHomePageActions