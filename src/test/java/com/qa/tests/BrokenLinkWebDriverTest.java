package com.qa.tests;

import com.qa.base.BaseWebDriverTest;
import com.qa.pageObjects.SeleniumPracticePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class BrokenLinkWebDriverTest extends BaseWebDriverTest {

  @Test
  public void brokenLinksTest() {
    SeleniumPracticePage seleniumPracticePage = new SeleniumPracticePage(getDriver());

    getDriver().get("https://www.swtestacademy.com/find-broken-links-selenium-java-streams/");
    assertTrue(seleniumPracticePage.getBrokenLinks() > 0, "No broken links found");
  }
}
