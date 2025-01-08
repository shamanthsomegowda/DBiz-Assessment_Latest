package com.yahoo.utils;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.yahoo.drivers.Driver;

public class GenericWebFunctions extends Driver {

	WaitsUtil waitsUtil = new WaitsUtil();

	// To perform click operation on desired web element
	public void clickOnElement(WebElement webElement) {
		log.debug("performing click operation");
		waitsUtil.fluentWaitForClickOperation(webElement, 30);
		try {
			webElement.click();
		} catch (NoSuchElementException e) {
			log.warn("Caught NoSuchElementException while trying perform click");
			waitsUtil.fluentWaitForClickOperation(webElement, 30);
			webElement.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To key value in a text field
	public void keyOrTypeValue(WebElement webElement, String textValue) {
		try {
			webElement.click();
		} catch (NoSuchElementException e) {
			log.warn("Caught NoSuchElementException while trying perform sendkeys");
			waitsUtil.fluentWaitForClickOperation(webElement, 30);
			webElement.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		webElement.clear();
		webElement.sendKeys(textValue);
	}

	// To get value from the node base on attribute
	public String getValueByAttribute(WebElement webElement, String attributeName) {
		String strVal = null;
		try {
			strVal = webElement.getDomAttribute(attributeName);
		} catch (NoSuchElementException e) {
			log.warn("Caught NoSuchElementException while trying perform sendkeys");
			waitsUtil.fluentWaitForVisibility(30, webElement);
			strVal = webElement.getDomAttribute(attributeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strVal;
	}

	// Move to or hover an element
	public void moveToOrMouseHover(WebElement webElement) {
		Actions actions = new Actions(driver);
		try {
			actions.scrollToElement(webElement);
			actions.moveToElement(webElement);
		} catch (NoSuchElementException e) {
			log.warn("NoSuchElementException exception encountered in moveToOrMouseHover ()");
			waitsUtil.fluentWaitForClickOperation(webElement, 30);
		} catch (ElementNotInteractableException e) {
			log.warn("ElementNotInteractableException exception encountered in moveToOrMouseHover ()");
			waitsUtil.fluentWaitForClickOperation(webElement, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
