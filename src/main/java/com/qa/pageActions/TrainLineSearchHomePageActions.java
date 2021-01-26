package com.qa.pageActions;

import com.qa.pageObjects.TrainLineSearchHomePageObjects;

public class TrainLineSearchHomePageActions extends TrainLineSearchHomePageObjects {

  public void enterTextInDepartureStn(String departure) {
    enterText(getWebElement(departureStn), departure);
  }
} // end of SearchHomePageAction
