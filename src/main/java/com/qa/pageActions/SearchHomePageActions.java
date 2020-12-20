package com.qa.pageActions;

import com.qa.pageObjects.SearchPageObjects;

public class SearchHomePageActions extends SearchPageObjects {


    public void clickOnSearchButton() {
        click(getWebElement(searchButton));
    }

    public void enterTextInDepartureStn(String departure) {
        sendKey(getWebElement(departureStn), departure);
    }

    public void enterTextInDestinationStn(String destination) {
        sendKey(getWebElement(destinationStn), destination);
    }

} // end of SearchHomePageActions