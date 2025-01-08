package com.yahoo.utils;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yahoo.drivers.Driver;

public class WaitsUtil extends Driver {

	static FluentWait<WebDriver> fluentWait;
	static WebDriverWait wait;

	public void exlicitlyWaitForClickOperation(long seconds, WebElement webElement) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void exlicitlyWaitForAlert(long seconds, WebElement webElement) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void exlicitlyWaitForVisibility(long seconds, WebElement webElement) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public void fluentWaitForClickOperation(WebElement webElement, long seconds) {
		fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
		fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void fluentWaitForAlert(long seconds, WebElement webElement) {
		fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
		fluentWait.until(ExpectedConditions.alertIsPresent());
	}

	public void fluentWaitForVisibility(long seconds, WebElement webElement) {
		fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
		fluentWait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public void fluentWaitForClickOperationBy(By xpath, int seconds) {
		fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
		fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath));
	}

}
