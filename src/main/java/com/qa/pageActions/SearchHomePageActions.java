package com.qa.pageActions;

import com.qa.pageObjects.SearchPageObjects;

public class SearchHomePageActions extends SearchPageObjects {

    public void enterTextInDepartureStn(String departure) {
        enterText(getWebElement(departureStn), departure);
    }

} // end of SearchHomePageAction