package com.qa.pageObjects;

import com.qa.util.WebInteractUtil;
import org.openqa.selenium.By;

public class SignInPageObjects extends WebInteractUtil {

  protected By emailInput = By.xpath("//input[@id='identifierId']");
  protected By nextBtn = By.xpath("//span[text()='Next']/..");
}
