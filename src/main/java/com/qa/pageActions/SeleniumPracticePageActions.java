package com.qa.pageActions;

import com.qa.base.DriverFactory;
import com.qa.pageObjects.SeleniumPracticePageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.qameta.allure.Step;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v86.network.Network;
import org.openqa.selenium.devtools.v86.network.model.Headers;

public class SeleniumPracticePageActions extends SeleniumPracticePageObjects {

  private static final String USERNAME = "admin";
  private static final String PASSWORD = "admin";

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
        System.out.println(webElement.getAttribute("innerHTML"));
        webElement.click();
        break;
      }
    }
  }

  @Step("click on Basic Auth link and handle authentication using Chrome Dev Tool")
  public String getLoginMessageForBasicAuth() {

    DevTools chromeDevTools = ((ChromeDriver) DriverFactory.getDriver()).getDevTools();
    chromeDevTools.createSession();
    chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    // Open website
    DriverFactory.getDriver().get("https://the-internet.herokuapp.com/");

    // Send authorization header
    Map<String, Object> headers = new HashMap<>();
    String basicAuth =
        "Basic "
            + new String(
                new Base64().encode(String.format("%s:%s", USERNAME, PASSWORD).getBytes()));
    headers.put("Authorization", basicAuth);
    chromeDevTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

    click(getWebElement(herokuapp_BasicAuthLink));

    return getText(getWebElement(herokuapp_BasicAuthText));
  }

  public void openNewTab(){

  }
}
