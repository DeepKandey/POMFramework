package com.qa.pageObjects;

import com.qa.util.WebInteractUtil;
import org.openqa.selenium.By;

public class SeleniumPracticePageObjects extends WebInteractUtil {

  public SeleniumPracticePageObjects() {
    super();
  }

  protected By dropdownMenuButton = By.id("menu1");
  protected By tutorialList = By.xpath("//ul[@class='dropdown-menu']//li/a");

  protected By herokuapp_BasicAuthLink= By.linkText("Basic Auth");
  protected By herokuapp_BasicAuthText= By.id("content");
}
