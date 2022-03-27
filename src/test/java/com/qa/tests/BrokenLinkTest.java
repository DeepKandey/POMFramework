package com.qa.tests;

import com.qa.base.BaseWebDriverTest;
import com.qa.pageObjects.SeleniumPracticePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class BrokenLinkTest extends BaseWebDriverTest {

  @Test
  @Description("Test to find broken links count")
  @Severity(SeverityLevel.NORMAL)
  public void brokenLinksTest() {
    SeleniumPracticePage seleniumPracticePage = new SeleniumPracticePage(getDriver());

    getDriver().get("https://www.swtestacademy.com/find-broken-links-selenium-java-streams/");
    assertTrue(seleniumPracticePage.getBrokenLinks() > 0, "No broken links found");
  }
}
