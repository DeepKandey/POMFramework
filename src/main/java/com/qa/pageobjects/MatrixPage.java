package com.qa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.DriverFactory;
import com.qa.util.Constants;
import com.qa.util.LoggerUtil;

public class MatrixPage {

	private String fareOnMatrixPage = null;

	@FindBy(xpath = "descendant::input[@type='radio']")
	private List<WebElement> ticketFareRadioBtn;

	@FindBy(xpath = "descendant::input[@type='radio'][5]/parent::div/following-sibling::div/span[2]/span")
	private WebElement ticketFareValue1;

	@FindBy(linkText = "Register")
	private WebElement registerLnk;

	@FindBy(xpath = "//button[@data-test='cjs-button-quick-buy']")
	private WebElement quickChckOut;

	public void clickOnFirstClassOption() throws InterruptedException {
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Constants.EXPLICIT_WAIT);
		wait.until(ExpectedConditions.elementToBeClickable(
				DriverFactory.getInstance().getDriver().findElement(By.xpath("//button[@class='_1vwjm4ai']"))));
		Thread.sleep(1000);
		List<WebElement> radioBtnList = DriverFactory.getInstance().getDriver()
				.findElements(By.xpath("descendant::input[@type='radio']"));
		String beforexPath = "descendant::input[@type='radio'][ ";
		String afterxPath = "]/parent::div/following-sibling::div/span[2]/span";
		try {
			if (radioBtnList.size() < 5) {
				wait.until(ExpectedConditions.elementToBeClickable(radioBtnList.get(2)));
				radioBtnList.get(2).click();

				String fareXpath = beforexPath + 3 + afterxPath;
				fareOnMatrixPage = DriverFactory.getInstance().getDriver().findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage("Fare on Matrix Page when size is less than 5 : " + fareOnMatrixPage);

			} else {
				wait.until(ExpectedConditions.elementToBeClickable(radioBtnList.get(4)));
				radioBtnList.get(4).click();

				String fareXpath = beforexPath + 5 + afterxPath;
				fareOnMatrixPage = DriverFactory.getInstance().getDriver().findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage("Fare on Matrix Page when size is greater than 5 : " + fareOnMatrixPage);
			}
		} catch (StaleElementReferenceException e) {
			List<WebElement> radioBtnList1 = DriverFactory.getInstance().getDriver()
					.findElements(By.xpath("descendant::input[@type='radio']"));
			if (radioBtnList1.size() < 5) {
				radioBtnList1.get(2).click();

				String fareXpath = beforexPath + 3 + afterxPath;
				fareOnMatrixPage = DriverFactory.getInstance().getDriver().findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage("Fare on Matrix Page: " + fareOnMatrixPage);

			} else {
				wait.until(ExpectedConditions.visibilityOf(radioBtnList1.get(4)));
				radioBtnList1.get(4).click();

				String fareXpath = beforexPath + 5 + afterxPath;
				fareOnMatrixPage = DriverFactory.getInstance().getDriver().findElement(By.xpath(fareXpath)).getText();
				LoggerUtil.logMessage("Fare on Matrix Page: " + fareOnMatrixPage);
			}
		}
	}

	public String getFareOnMatrixPage() {
		return fareOnMatrixPage;
	}

	public void clickOnRegisterLink() {
		registerLnk.click();
	}

	public void clickOnCheckOut() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), Constants.EXPLICIT_WAIT);
		try {
			WebElement continueBtn1 = DriverFactory.getInstance().getDriver()
					.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.elementToBeClickable(continueBtn1));
			LoggerUtil.logMessage("Trying to click on First Continue");
			continueBtn1.click();
			LoggerUtil.logMessage("Clicked on First Continue");
			Thread.sleep(2000);

			WebElement continueBtn2 = DriverFactory.getInstance().getDriver()
					.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.visibilityOf(continueBtn2));
			LoggerUtil.logMessage("Trying to click on Second Continue");
			continueBtn2.click();
			LoggerUtil.logMessage("Clicked on Second Continue");
			Thread.sleep(2000);

			WebElement continueBtn3 = DriverFactory.getInstance().getDriver()
					.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.visibilityOf(continueBtn2));
			LoggerUtil.logMessage("Trying to click on Third Continue");
			continueBtn3.click();
			LoggerUtil.logMessage("Clicked on Third Continue");
		} catch (Exception e) {
			LoggerUtil.logMessage("Exception occured: " + e.getMessage());
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOf(
					DriverFactory.getInstance().getDriver().findElement(By.xpath("//span[@class='_eu1pe2']/span"))));
			WebElement continueBtn2 = DriverFactory.getInstance().getDriver()
					.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
			wait.until(ExpectedConditions.elementToBeClickable(continueBtn2));
			LoggerUtil.logMessage("Trying to click on Second Continue");
			continueBtn2.click();
			LoggerUtil.logMessage("Clicked on Second Continue");
			Thread.sleep(2000);

			if (!DriverFactory.getInstance().getDriver().findElements(By.xpath("//span[@class='_eu1pe2']/span"))
					.isEmpty()) {
				WebElement continueBtn3 = DriverFactory.getInstance().getDriver()
						.findElement(By.xpath("//span[@class='_eu1pe2']/span"));
				wait.until(ExpectedConditions.elementToBeClickable(continueBtn2));
				LoggerUtil.logMessage("Trying to click on Third Continue");
				Thread.sleep(2000);
				continueBtn3.click();
				LoggerUtil.logMessage("Clicked on Third Continue");
			}

		}
	}

	public MatrixPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}