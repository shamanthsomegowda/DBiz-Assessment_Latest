package com.yahoo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.yahoo.utils.GenericWebFunctions;
import com.yahoo.utils.WaitsUtil;

public class FinanceHomePage extends GenericWebFunctions {

	// Page Factory
	@FindBy(xpath = "//input[@id='ybar-sbq']")
	public WebElement searchBarTxt;

	@FindBy(xpath = "//button[@id='ybar-search']")
	public WebElement searchIconBtn;

	String suggestedItemsList_xpath = "//ul/li[@data-type='quotes']";

	static WebElement nthSuggestedRecord;

	List<WebElement> elements;
	WaitsUtil waitsUtil = new WaitsUtil();

	// initializing the page factory
	public FinanceHomePage() {
		log.info("Initializing page factory for " + this.getClass().getSimpleName());
		PageFactory.initElements(driver, this);
	}

	// returning page title
	public String pageTitle() {
		System.out.println("User directed to " + driver.getTitle() + " page.");
		return driver.getTitle();
	}

	public void keyInSearchBar(String searchValue) {
		keyOrTypeValue(searchBarTxt, searchValue);
	}

	// to select the required record based on the list order number
	public String getNthRecordFromSuggestiveText(int nthValue) {
		String nthItemName = "";

		try {
			elements = driver.findElements(By.xpath(suggestedItemsList_xpath));
		} catch (NoSuchElementException e) {
			log.warn("Caught NoSuchElementException " + e.getMessage());
			waitsUtil.fluentWaitForClickOperationBy(By.xpath(suggestedItemsList_xpath), 30);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		if (elements.size() < 1) {
			log.warn("Incorrect Stock Code/Symbol, no entries suggested.");
		} else {
			generateXpath(nthValue);
			nthItemName = getValueByAttribute(nthSuggestedRecord, "title");
			log.info("Item listed in row number " + nthValue + " is: " + nthItemName);
		}
		return nthItemName;
	}

	// Concatenating the order number then creating xpath
	public void generateXpath(int nthValue) {
		String xpathVal = "(//ul/li[@data-type='quotes'])[" + nthValue + "]";
		try {
			nthSuggestedRecord = driver.findElement(By.xpath(xpathVal));
		} catch (NoSuchElementException e) {
			log.warn("Caught NoSuchElementException while trying find element");
			waitsUtil.fluentWaitForClickOperation(nthSuggestedRecord, 30);
		} catch (Exception e) {
			log.error("Failed to generate xpath");
			e.printStackTrace();
		}
	}

	// click operation on select record
	public void selectNthRecord(int nthValue) {
		generateXpath(nthValue);
		clickOnElement(nthSuggestedRecord);
	}

	// click operation on search magnifier
	public void clickOnSearchBar() {
		searchIconBtn.click();
	}

}
