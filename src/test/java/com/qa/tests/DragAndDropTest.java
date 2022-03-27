package com.qa.tests;

import com.qa.base.BaseWebDriverTest;
import com.qa.pageObjects.SeleniumPracticePage;
import com.qa.util.LoggerUtil;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DragAndDropTest extends BaseWebDriverTest {

  @Test
  @Description("Verify drag and drop functionality")
  public void dragAndDropTest() throws NoSuchMethodException {
    SeleniumPracticePage seleniumPracticePage = new SeleniumPracticePage(getDriver());
    LoggerUtil loggerUtil = new LoggerUtil(DragAndDropTest.class);

    getDriver().get("https://the-internet.herokuapp.com/drag_and_drop");
    assertEquals(
        seleniumPracticePage.performDragAndDropColumns(), "A", "Drag and drop was not successful");
    loggerUtil.info(
        DragAndDropTest.class.getDeclaredMethod("dragAndDropTest", null).getName() + "  Passed");
  }
}
