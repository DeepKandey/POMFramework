package com.qa.pageActions;

import com.qa.pageObjects.SignInPageObjects;

public class SignInPageActions extends SignInPageObjects {

  /** enter email on SignIn page */
  public void enterEmail() {
    enterText(
        getWebElements(emailInput).stream().filter(a -> a.isDisplayed()).findFirst().get(),
        "proconnect@aisle51.net");
  }

  /** click on Next button */
  public void clickNextBtn() {
    click(getWebElement(nextBtn));
  }
}
