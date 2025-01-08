package com.yahoo.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.yahoo.drivers.Driver;
import com.yahoo.pages.FinanceHomePage;
import com.yahoo.pages.FinanceQuotePage;
import com.yahoo.testdata.ApplicationConstants;
import com.yahoo.testdata.ExcelDataManipulator;

public class ValidateTeslaStock extends Driver {

	FinanceHomePage financeHomePage;
	FinanceQuotePage financeQuotePage;

	@Test
	public void validate() {
		extentTest = extentReports.createTest("Validate Tesla Stock Details");
		financeHomePage = new FinanceHomePage();
		ApplicationConstants.testClassName = this.getClass().getSimpleName();

		// Step 1: Search for Tesla (TSLA):
		log.info("Step 1: Search for Tesla (TSLA)");
		if (financeHomePage.pageTitle()
				.equalsIgnoreCase("Yahoo Finance - Stock Market Live, Quotes, Business & Finance News")) {
			extentTest.log(Status.PASS, "User directed to the right webpage");
		} else {
			extentTest.log(Status.FAIL, "User directed to incorrect webpage");
		}
		financeHomePage.keyInSearchBar("TSLA");

		// Step 2: Verify Autosuggest
		log.info("Step 2: Verify Autosuggest");

		String suggestedEntry = financeHomePage.getNthRecordFromSuggestiveText(1);
		if (suggestedEntry.equalsIgnoreCase(ExcelDataManipulator.getTestData("TeslaSearch", "StockFullName"))) {
			extentTest.log(Status.FAIL, "Valid entry listed in the suggestive textbox.");
		} else {
			extentTest.log(Status.PASS,
					"Incorrect Entry Name listed in the suggestive textbox. This is behaving as expected, it is a Negative scenario");
		}

		// Step 3: Click on First Entry
		log.info("Step 3: Click on First Entry");
		financeHomePage.selectNthRecord(1);
		financeQuotePage = new FinanceQuotePage();
		financeQuotePage.pageTitle();
		boolean isDirectedTorightPage = financeQuotePage.verifyStockName("TSLA");
		if (isDirectedTorightPage) {
			extentTest.log(Status.PASS, "Valid entry retrieved.");
		} else {
			extentTest.log(Status.FAIL, "Incorrect Stock Entry details retrieved.");
		}

		// Step 4: Verify Stock Price
		log.info("Step 4: Verify Stock Price");
		double stockPrice = Double.parseDouble(financeQuotePage.readStockPrice());
		System.out.println("Stock Price value: " + stockPrice);
		if (stockPrice > 200.00) {
			extentTest.log(Status.INFO, "Stock value is " + stockPrice);
			extentTest.log(Status.PASS, "Stock value is greater than 200 USD.");
		} else {
			extentTest.log(Status.INFO, "Stock value is " + stockPrice);
			extentTest.log(Status.FAIL, "Stock value is lesser than 200 USD.");
		}

		// Step 5: Capture Additional Data
		log.info("Step 5: Capture Additional Data");
		HashMap<String, String> stockDetailsMap = financeQuotePage.displayStockDetails();
		for (Map.Entry<String, String> entry : stockDetailsMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
			extentTest.log(Status.INFO, entry.getKey() + " : " + entry.getValue());
		}

	}

}
