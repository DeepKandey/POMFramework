package com.qa.pageObjects;

import com.qa.util.WebInteractUtil;
import org.openqa.selenium.By;

public class TrainLineSearchHomePage extends WebInteractUtil {

  protected By departureStn = By.id("from.search");
  protected By destinationStn = By.id("to.search");
  protected By searchButton = By.xpath("//button[@data-test='submit-journey-search-button']");

  public TrainLineSearchHomePage() {
    super();
  }

  public void enterTextInDepartureStn(String departure) {
    enterText(getWebElement(departureStn), departure);
  }
} // end of class SearchPageObjects
