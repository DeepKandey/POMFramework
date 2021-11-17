package com.qa.pageObjects;

import com.qa.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.qa.util.HttpConnectUtil.getResponseCode;

public class SeleniumPracticePage extends BasePage {

  @FindBy(id = "menu1")
  private WebElement dropdownMenuButton;

  @FindBy(xpath = "//ul[@class='dropdown-menu']//li/a")
  private List<WebElement> tutorialList;

  @FindBy(tagName = "a")
  private List<WebElement> linksList;

    public SeleniumPracticePage(WebDriver driver) {
        super(driver);
    }

    /** click on DropDownMenuButton */
  @Step("click on DropDownMenu button")
  public void clickDropdownMenuButton() {
    waitForElementToBeVisible(dropdownMenuButton);
    elementClick(dropdownMenuButton);
  }

  /**
   * click on Tutorial link
   *
   * @param tutorial tutorial name
   */
  @Step("click on given tutorial : {0}")
  public void clickTutorialLink(String tutorial) {
    for (WebElement webElement : tutorialList) {
      if (webElement.getAttribute("innerHTML").equals(tutorial)) {
        webElement.click();
        break;
      }
    }
  }

  public long getBrokenLinks() {
    List<Integer> acceptedStatusCodeList = new ArrayList<>();

    Predicate<String> isStatusCodeOK =
        link -> {
          try {
            return acceptedStatusCodeList.contains(getResponseCode(link));
          } catch (IOException e) {
            e.printStackTrace();
          }
          return false;
        };

    Collections.addAll(acceptedStatusCodeList, 200, 301, 302, 403);

    return linksList.stream()
        .parallel()
        .map(element -> element.getAttribute("href"))
        .filter(Objects::nonNull)
        .filter(link -> !link.isEmpty())
        .filter(link -> !link.contains("javascript") && !link.contains("*&"))
        .distinct()
        .filter(isStatusCodeOK.negate())
        .peek(
            link -> {
              try {
                System.out.println("link: " + link + " Response Code: " + getResponseCode(link));
              } catch (IOException e) {
                e.printStackTrace();
              }
            })
        .count();
  }
}
