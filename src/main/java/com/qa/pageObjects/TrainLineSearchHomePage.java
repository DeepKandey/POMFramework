package com.qa.pageObjects;

import com.qa.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TrainLineSearchHomePage extends BasePage {

  @FindBy(id = "from.search")
  private WebElement departureStn;

  @FindBy(id = "to.search")
  private WebElement destinationStn;

  @FindBy(id = "//button[@data-test='submit-journey-search-button']")
  private WebElement searchButton;

  public TrainLineSearchHomePage(WebDriver driver) {
    super(driver);
  }

  @Step("enter departure station: {0}")
  public void enterTextInDepartureStn(String departure) {
    waitForElementToBeVisible(departureStn);
    departureStn.sendKeys(departure);
  }
} // end of class SearchPageObjects
