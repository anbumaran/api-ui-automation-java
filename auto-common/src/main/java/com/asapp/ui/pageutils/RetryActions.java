package com.asapp.ui.pageutils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.asapp.common.Constants.TWO;
import static com.asapp.common.Constants.ZERO;

public class RetryActions {

    public static void retryActionClickOrSendKeys(WebElement webElement, WebDriver webDriver, int maxRetry, CharSequence... inputValue) {

        Actions action = new Actions(webDriver);
        WebDriverWait webDriverWait = Waits.getExplicitWait(webDriver, TWO);

        for (int i = ZERO; i < maxRetry; i++) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
                webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
                if (inputValue.length == 0) {
                    action.moveToElement(webElement).click().build().perform();
                } else {
                    action.moveToElement(webElement).sendKeys(inputValue).build().perform();
                }
                break;
            } catch (WebDriverException e) {
                if (i == maxRetry) {
                    throw new IllegalStateException("Action Click Filed with Error" + e);
                }
            }

        }

    }

    public static boolean retryVisibility(WebElement webElement, WebDriver webDriver, int maxRetry) {

        WebDriverWait webDriverWait = Waits.getExplicitWait(webDriver, TWO);

        for (int i = ZERO; i < maxRetry; i++) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
                return true;
            } catch (WebDriverException e) {
                if (i == maxRetry) {
                    throw new IllegalStateException("Action Click Filed with Error" + e);
                }
            }
        }

        return false;

    }

}
