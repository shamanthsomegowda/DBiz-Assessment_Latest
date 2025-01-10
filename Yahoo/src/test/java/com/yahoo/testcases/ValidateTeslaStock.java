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
	public void validateStcok() {
		extentTest = extentReports.createTest("Validate Tesla Stock Details");
		financeHomePage = new FinanceHomePage();
		ApplicationConstants.testClassName = this.getClass().getSimpleName();
		financeQuotePage = new FinanceQuotePage();

		try {
			// Step 1: Search for Tesla (TSLA):
			log.info("Step 1: Search for Tesla (TSLA)");
			if (financeHomePage.pageTitle()
					.equalsIgnoreCase("Yahoo Finance - Stock Market Live, Quotes, Business & Finance News")) {
				extentTest.log(Status.PASS, "User directed to the right webpage");
			} else {
				extentTest.log(Status.FAIL, "User directed to incorrect webpage");
			}
			financeHomePage.keyInSearchBar(ExcelDataManipulator.getTestData("TeslaSearch", "StockShortName"));

			// Step 2: Verify Autosuggest
			log.info("Step 2: Verify Autosuggest");
			int nthValue = Integer.parseInt(ExcelDataManipulator.getTestData("TeslaSearch", "ItemNumber"));

			String suggestedEntry = financeHomePage.getNthRecordFromSuggestiveText(nthValue);
			if (suggestedEntry.equalsIgnoreCase(ExcelDataManipulator.getTestData("TeslaSearch", "StockFullName"))) {
				extentTest.log(Status.FAIL, "Valid entry listed in the suggestive textbox.");
			} else {
				extentTest.log(Status.PASS,
						"Incorrect Entry Name listed in the suggestive textbox. This is behaving as expected, it is a Negative scenario");
			}

			// Step 3: Click on First Entry
			log.info("Step 3: Click on First Entry");
			financeHomePage.selectNthRecord(nthValue);
			financeQuotePage.pageTitle();
			boolean isDirectedTorightPage = financeQuotePage
					.verifyStockName(ExcelDataManipulator.getTestData("TeslaSearch", "StockShortName"));
			if (isDirectedTorightPage) {
				extentTest.log(Status.PASS, "Valid entry retrieved.");
			} else {
				extentTest.log(Status.FAIL, "Incorrect Stock Entry details retrieved.");
			}

			// Step 4: Verify Stock Price
			log.info("Step 4: Verify Stock Price");
			double actualStockPrice = Double.parseDouble(financeQuotePage.readStockPrice());
			double expectedStockPrice = Double
					.parseDouble(ExcelDataManipulator.getTestData("TeslaSearch", "PriceExceedBy"));
			if (actualStockPrice > expectedStockPrice) {
				extentTest.log(Status.INFO, "Stock value is " + actualStockPrice);
				extentTest.log(Status.PASS, "Stock value is greater than " + expectedStockPrice);
			} else {
				extentTest.log(Status.INFO, "Stock value is " + actualStockPrice);
				extentTest.log(Status.FAIL, "Stock value is lesser than " + expectedStockPrice);
			}

			// Step 5: Capture Additional Data
			log.info("Step 5: Capture Additional Data");
			HashMap<String, String> stockDetailsMap = financeQuotePage.displayStockDetails();
			for (Map.Entry<String, String> entry : stockDetailsMap.entrySet()) {
				extentTest.log(Status.INFO, entry.getKey() + " : " + entry.getValue());
			}
		} catch (AssertionError e) {
			extentTest.log(Status.FAIL, "Assertion Failure while validating method validateStcok", e, null);
			log.error("Assertion Failure! - Method Name: validateStcok");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			extentTest.log(Status.FAIL, "Failure while during data conversion", e, null);
			log.error("Conversion Failure! - Method Name: validateStcok");
			e.printStackTrace();
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Unknown Failure while validating method validateStcok", e, null);
			log.fatal("UNKNOWN FAILURE! - Method Name: validateStcok");
			e.printStackTrace();
		}

	}

}
