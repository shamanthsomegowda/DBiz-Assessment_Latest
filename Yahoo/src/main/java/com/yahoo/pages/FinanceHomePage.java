package com.yahoo.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.yahoo.utils.GenericWebFunctions;

public class FinanceHomePage extends GenericWebFunctions {

	// Page Factory
	@FindBy(xpath = "//input[@id='ybar-sbq']")
	public WebElement searchBarTxt;

	@FindBy(xpath = "//button[@id='ybar-search']")
	public WebElement searchIconBtn;

	@FindAll(@FindBy(xpath = "//ul/li[@data-type='quotes']"))
	public List<WebElement> suggestedItemsList;

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

		if (suggestedItemsList.size() < 1) {
			log.warn("Incorrect Stock Code/Symbol, no entries suggested.");
		} else {
			nthItemName = getValueByAttribute(suggestedItemsList.get(nthValue - 1), "title");
			log.info("Item listed in row number " + nthValue + " is: " + nthItemName);
		}
		return nthItemName;
	}

	// click operation on select record
	public void selectNthRecord(int nthValue) {
		if (suggestedItemsList.size() < 1) {
			log.warn("Incorrect Stock Code/Symbol, no entries suggested.");
		} else {
			clickOnElement(suggestedItemsList.get(nthValue - 1));
		}
	}

	// click operation on search magnifier
	public void clickOnSearchBar() {
		searchIconBtn.click();
	}

}
