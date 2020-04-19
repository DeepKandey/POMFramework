package com.qa.tests;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.DriverFactory;
import com.qa.util.Constants;
import com.qa.util.ExcelUtil;

public class TicketFareTest {

	@BeforeMethod
	public void setUp() {

	}

	@AfterTest
	public void tearDown() {
		DriverFactory.removeDriver();
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		ExcelUtil excelReader = new ExcelUtil(Constants.TEST_DATA_PATH);
		Object userData[][] = excelReader.getExcelData("Sheet2");
		return userData;
	}

	@Test(dataProvider = "getData")
	public void verifyTicketFare() throws InterruptedException {
		// Enter Journey Details

		// Click on Search

		// Click On First Class Option

		// Click on Register Link

		// Enter Registration Details

		// Click on Register button

		// Click on Search button

		// Click on First Class

		// Fetch fare on Maatrix Page

		// Click on CheckOut

		// Fetch fare on CheckOut

	}
}