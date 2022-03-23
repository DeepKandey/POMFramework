package com.qa.tests;

import com.qa.base.BaseWebDriverTest;
import com.qa.pageObjects.SeleniumPracticePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DragAndDropTest extends BaseWebDriverTest {

  @Test
  public void dragAndDropTest() {
    SeleniumPracticePage seleniumPracticePage = new SeleniumPracticePage(getDriver());

    getDriver().get("https://the-internet.herokuapp.com/drag_and_drop");
    assertTrue(
        seleniumPracticePage.performDragAndDropColumns().equals("A"),
        "Drag and drop was not successful");
  }
}
