package com.trainline.qa.tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trainline.qa.base.DriverFactory;
import com.trainline.qa.pageobjects.CheckOutPage;
import com.trainline.qa.pageobjects.MatrixPage;
import com.trainline.qa.pageobjects.RegisterPage;
import com.trainline.qa.pageobjects.SearchPage;
import com.trainline.qa.util.Constants;
import com.trainline.qa.util.ExcelReader;
import com.trainline.qa.util.LoggerUtil;
import com.trainline.qa.util.TestUtil;

public class TicketFareTest {

	public SearchPage searchPage;
	public MatrixPage matrixPage;
	public RegisterPage registerPage;
	public CheckOutPage checkOutPage;

	@BeforeMethod
	public void setUp() {
		DriverFactory.getInstance().setDriver("chrome");
		WebDriver driver = TestUtil.webDriverEvents(DriverFactory.getInstance().getDriver());
		driver.get(DriverFactory.getInstance().getProperties().getProperty("url"));

		searchPage = new SearchPage(driver);
		matrixPage = new MatrixPage(driver);
		registerPage = new RegisterPage(driver);
		checkOutPage = new CheckOutPage(driver);
	}

	@AfterTest
	public void tearDown() {
		DriverFactory.getInstance().removeDriver();
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		ExcelReader excelReader = new ExcelReader(Constants.TEST_DATA_PATH);
		Object userData[][] = excelReader.getExcelData("Sheet2");
		return userData;
	}

	// @Test(retryAnalyzer=RetryAnalyzer.class)
	@Test(dataProvider = "getData")
	public void verifyTicketFare(String addressData1, String addressData2) throws InterruptedException {
		// Enter Journey Details
		searchPage.enterJourneyDetails();
		// Click on Search
		searchPage.clickOnSearchBtn();
		// Click On First Class Option
		matrixPage.clickOnFirstClassOption();
		// Click on Register Link
		matrixPage.clickOnRegisterLink();
		// Enter Registration Details
		registerPage.enterRegistrationDetails(addressData1, addressData2);
		// Click on Register button
		registerPage.clickOnRegisterBtn();
		// Click on Search button
		searchPage.clickOnSearchBtn();
		// Click on First Class
		matrixPage.clickOnFirstClassOption();
		// Fetch fare on MAatrix Page
		String fareOnMatrixPage = matrixPage.getFareOnMatrixPage();
		Thread.sleep(3000);
		// Click on CheckOut
		matrixPage.clickOnCheckOut();
		// checkOutPage.enterDetailsForCheckOut();
		// Fetch fare on CheckOut
		String fareOnCheckOutPage = checkOutPage.getFareOnChckOutPage();
		// Validate Fare
		if (fareOnCheckOutPage.equals(fareOnMatrixPage)) {
			LoggerUtil.logMessage("Ticket fares are matching");
		}
		Assert.assertEquals(fareOnCheckOutPage, fareOnMatrixPage, "Fares do not match");
	}
}