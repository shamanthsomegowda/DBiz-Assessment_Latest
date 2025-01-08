package com.yahoo.pages;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.yahoo.utils.GenericWebFunctions;
import com.yahoo.utils.WaitsUtil;

public class FinanceQuotePage extends GenericWebFunctions {

	// Page Factory
	WaitsUtil waitsUtil = new WaitsUtil();
	static String stockPrice;

	@FindBy(xpath = "//h1[@class='yf-xxbei9']")
	WebElement stockNameHdr;

	@FindBy(xpath = "(//section[@class='yf-1tejb6'][1]//span)[1]")
	WebElement stockValueTxt;

	@FindBy(xpath = "//span[@data-testid='qsp-price']")
	WebElement stockValueTxt_alternate;

	@FindBy(xpath = "//span[text()='Previous Close']/following-sibling::span/fin-streamer")
	WebElement previousCloseVlaueTxt;

	@FindBy(xpath = "//fin-streamer[@data-field='regularMarketVolume']")
	WebElement stockVolumeTxt;

	// Initializing page factory
	public FinanceQuotePage() {
		log.info("Initializing page factory for " + this.getClass().getSimpleName());
		PageFactory.initElements(driver, this);
	}

	// Retrieve Page title
	public String pageTitle() {
		waitsUtil.exlicitlyWaitForClickOperation(implicitWaitInSeconds, stockNameHdr);
		System.out.println("User directed to " + driver.getTitle() + " page.");
		return driver.getTitle();
	}

	// TO check the stockname displayed in the page
	public boolean verifyStockName(String expValue) {
		String actualValue = stockNameHdr.getText();
		if (actualValue.contains(expValue)) {
			System.out.println("Retrieved stock entry name is, " + actualValue);
			log.info("verifyStockName operation SUCCESS!");
			return true;
		} else {
			System.out.println("Incorrect! Retrieved stock entry name is, " + actualValue
					+ ". While the expected name is " + expValue + ".");
			log.info("verifyStockName operation Failure!");
			return true;
		}
	}

	// To read and return the selected stock price
	public String readStockPrice() {
		try {
			waitsUtil.exlicitlyWaitForVisibility(30, stockValueTxt);
			stockPrice = stockValueTxt.getText();
		} catch (NoSuchElementException e) {
			log.error("driver trying to identify stockValueTxt element using alternate option");
			waitsUtil.fluentWaitForVisibility(30, stockValueTxt_alternate);
			stockPrice = stockValueTxt_alternate.getText();
		} catch (Exception e) {
			log.error("readStockPrice() Failure!");
			e.printStackTrace();
		}
		return stockValueTxt.getText();
	}

	public HashMap<String, String> displayStockDetails() {
		HashMap<String, String> stockDetailsMap = new HashMap<String, String>();
		moveToOrMouseHover(stockVolumeTxt);
		stockDetailsMap.put("Volume", stockVolumeTxt.getText());
		stockDetailsMap.put("Previous Close", previousCloseVlaueTxt.getText());
		return stockDetailsMap;
	}

}
