package com.qa.util;

import com.qa.base.BaseWebDriverTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil extends BaseWebDriverTest {

    public String takeScreenshotForFailedTest(String methodName, WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        String path =
                currentDir + "/src/main/java/com/qa/report/screenshots/" + methodName + "_" + dateFormat.format(date) + ".png";
        FileUtils.copyFile(
                scrFile,
                new File(
                        currentDir + "/src/main/java/com/qa/report/screenshots/" + methodName + "_" + dateFormat.format(date) + ".png"));
        return path;
    }

} // End of class TestUtil
