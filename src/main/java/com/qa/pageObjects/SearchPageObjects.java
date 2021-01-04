package com.qa.pageObjects;

import com.qa.util.WebInteractUtil;
import org.openqa.selenium.By;

public class SearchPageObjects extends WebInteractUtil {

  public SearchPageObjects() {
    super();
  }

  protected By departureStn = By.id("from.search");

  protected By destinationStn = By.id("to.search");

  protected By searchButton = By.xpath("//button[@data-test='submit-journey-search-button']");
} // end of class SearchPageObjects
