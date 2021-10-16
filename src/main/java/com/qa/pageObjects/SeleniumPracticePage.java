package com.qa.pageObjects;

import com.qa.util.WebInteractUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SeleniumPracticePage extends WebInteractUtil {

  private static final String USERNAME = "admin";
  private static final String PASSWORD = "admin";

  private By dropdownMenuButton = By.id("menu1");
  private By tutorialList = By.xpath("//ul[@class='dropdown-menu']//li/a");

  /** click on DropDownMenuButton */
  @Step("click on DropDownMenu button")
  public void clickDropdownMenuButton() {
    click(getWebElement(dropdownMenuButton));
  }

  /**
   * click on Tutorial link
   *
   * @param tutorial tutorial name
   */
  @Step("click on given tutorial : {0}")
  public void clickTutorialLink(String tutorial) {
    List<WebElement> listOfTutorials = getWebElements(tutorialList);
    for (WebElement webElement : listOfTutorials) {
      if (webElement.getAttribute("innerHTML").equals(tutorial)) {
        webElement.click();
        break;
      }
    }
  }
}
