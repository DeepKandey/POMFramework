package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.qa.base.DriverFactory;

public class TestUtil extends DriverFactory {

	public static String takeScreenshotAtEndOfTest(String methodName) throws IOException {
		File scrFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");

		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
		String path = currentDir + "/screenshots/" + methodName + "_" + dateformat.format(date) + ".png";
		FileUtils.copyFile(scrFile,
				new File(currentDir + "/screenshots/" + methodName + "_" + dateformat.format(date) + ".png"));
		return path;
	}

	// Return data from the excel
	public static String[][] getExcelData(String filePath, String sheetName) throws IOException {
		Sheet sheet = null;
		String[][] arrayData = null;
		
		try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));) {

			sheet = workbook.getSheet(sheetName);
			int totalNoRows = sheet.getPhysicalNumberOfRows();
			int totalNoCols = sheet.getRow(0).getPhysicalNumberOfCells();
			arrayData = new String[totalNoRows][totalNoCols];

			for (int i = 0; i < totalNoRows; i++) {
				for (int j = 0; j < totalNoCols; j++) {
					arrayData[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}
		} catch (Exception e) {
			LoggerUtil.log(e.getMessage());
		}
		return arrayData;
	}

	public static EventFiringWebDriver webDriverEvents(WebDriver driver) {
		EventFiringWebDriver eDriver = null;
		WebEventListener eventListener = null;

		eDriver = new EventFiringWebDriver(driver);
		// Now create object of EventListenerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		eDriver.register(eventListener);
		eDriver.manage().deleteAllCookies();
		eDriver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		return eDriver;
	}
}// End of class TestUtil