package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.qa.base.DriverFactory;

public class TestUtil {

	private static FileInputStream fis = null;
	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;
	private static EventFiringWebDriver eDriver = null;
	private static WebEventListener eventListener = null;

	public static String takeScreenshotAtEndOfTest(String methodName) throws IOException {
		File scrFile = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
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
		String[][] arrayData = null;
		try {
			fis = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);

			int totalNoRows = sheet.getLastRowNum();
			int totalNoCols = sheet.getRow(0).getLastCellNum();
			arrayData = new String[totalNoRows][totalNoCols];

			for (int i = 0; i < totalNoRows; i++) {
				for (int j = 0; j < totalNoCols; j++) {
					arrayData[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}
		} catch (Exception e) {
			LoggerUtil.logMessage(e.getMessage());
		} finally {
			// workbook.close();
			// fis.close();
		}
		return arrayData;
	}

	public static EventFiringWebDriver webDriverEvents(WebDriver driver) {
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