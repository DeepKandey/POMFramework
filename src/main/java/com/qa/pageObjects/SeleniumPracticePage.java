package com.qa.pageObjects;

import com.qa.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.qa.util.HttpConnectUtil.getResponseCode;
import static org.testng.Assert.assertTrue;

public class SeleniumPracticePage extends BasePage {

  @FindBy(id = "menu1")
  private WebElement dropdownMenuButton;

  @FindBy(xpath = "//ul[@class='dropdown-menu']//li/a")
  private List<WebElement> tutorialList;

  @FindBy(tagName = "a")
  private List<WebElement> linksList;

  @FindBy(id = "column-a")
  private WebElement columnA;

  @FindBy(id = "column-b")
  private WebElement columnB;

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

  @Step("find broken links count")
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

  public String performDragAndDropColumns() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(
        "function createEvent(typeOfEvent) {\n"
            + "var event =document.createEvent(\"CustomEvent\");\n"
            + "event.initCustomEvent(typeOfEvent,true, true, null);\n"
            + "event.dataTransfer = {\n"
            + "data: {},\n"
            + "setData: function (key, value) {\n"
            + "this.data[key] = value;\n"
            + "},\n"
            + "getData: function (key) {\n"
            + "return this.data[key];\n"
            + "}\n"
            + "};\n"
            + "return event;\n"
            + "}\n"
            + "\n"
            + "function dispatchEvent(element, event,transferData) {\n"
            + "if (transferData !== undefined) {\n"
            + "event.dataTransfer = transferData;\n"
            + "}\n"
            + "if (element.dispatchEvent) {\n"
            + "element.dispatchEvent(event);\n"
            + "} else if (element.fireEvent) {\n"
            + "element.fireEvent(\"on\" + event.type, event);\n"
            + "}\n"
            + "}\n"
            + "\n"
            + "function simulateHTML5DragAndDrop(element, destination) {\n"
            + "var dragStartEvent =createEvent('dragstart');\n"
            + "dispatchEvent(element, dragStartEvent);\n"
            + "var dropEvent = createEvent('drop');\n"
            + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
            + "var dragEndEvent = createEvent('dragend');\n"
            + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n"
            + "}\n"
            + "\n"
            + "var source = arguments[0];\n"
            + "var destination = arguments[1];\n"
            + "simulateHTML5DragAndDrop(source,destination);",
        columnA,
        columnB);

    return columnB.getText();
  }
}
