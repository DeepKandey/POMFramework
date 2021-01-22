package com.qa.pageActions;

import com.qa.pageObjects.SeleniumPracticePageObjects;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SeleniumPracticePageActions extends SeleniumPracticePageObjects {

  /** click on DropDownMenuButton */
  public void clickDropdownMenuButton() {
    click(getWebElement(dropdownMenuButton));
  }

  /**
   * click on Tutorial link
   *
   * @param tutorial tutorial name
   */
  public void clickTutorialLink(String tutorial) {
    List<WebElement> listOfTutorials = getWebElements(tutorialList);
    for (WebElement webElement : listOfTutorials) {
      if (webElement.getAttribute("innerHTML").equals(tutorial)) {
        System.out.println(webElement.getAttribute("innerHTML"));
        webElement.click();
        break;
      }
    }
  }
}
