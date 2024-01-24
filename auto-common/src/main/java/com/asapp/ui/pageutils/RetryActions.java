package com.asapp.ui.pageutils;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.asapp.Constants.TWO;
import static com.asapp.Constants.ZERO;
import static com.asapp.ui.pageutils.JavaScriptActions.scrollIntoWebElement;

public class RetryActions {

    /**
     * Retry Action Click Or SendKeys for the given Web Element, Web Driver, Max Retry & Input Value in case of Send Keys
     *
     * @param webElement - Web Element
     * @param webDriver  - Web Driver
     * @param maxRetry   - Max Retry
     * @param inputValue - Input Value in case of Send Keys otherwise Click Button
     */
    public static void retryClickOrSendKeys(WebElement webElement, WebDriver webDriver, int maxRetry,
                                            CharSequence... inputValue) {

        Actions action = new Actions(webDriver);
        WebDriverWait webDriverWait = Waits.getExplicitWait(webDriver, TWO);

        for (int i = ZERO; i <= maxRetry; i++) {
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

    /**
     * Retry Action Click Or SendKeys till Expected Conditions for the given
     * Web Element, Web Driver, Max Retry & Input Value in case of Send Keys
     *
     * @param webElement        - Web Element
     * @param webDriver         - Web Driver
     * @param expectedCondition - Expected Condition
     * @param maxRetry          - Max Retry
     * @param inputValue        - Input Value in case of Send Keys otherwise Click Button
     */
    public static void retryClickOrSendKeysTillExpCond(WebElement webElement, WebDriver webDriver,
                                                       ExpectedCondition<?> expectedCondition, int maxRetry,
                                                       CharSequence... inputValue) {

        Actions action = new Actions(webDriver);
        WebDriverWait webDriverWait = Waits.getExplicitWait(webDriver, TWO);

        for (int i = ZERO; i <= maxRetry; i++) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
                webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
                if (inputValue.length == 0) {
                    action.moveToElement(webElement).click().build().perform();
                } else {
                    action.moveToElement(webElement).sendKeys(inputValue).build().perform();
                }
                webDriverWait.until(expectedCondition);
                break;
            } catch (final StaleElementReferenceException e) {
                if (i == maxRetry) {
                    throw new IllegalStateException("Retry Click Till Expected Condition Filed with Error" + e);
                }
                webDriver.get(webDriver.getCurrentUrl());
                throw e;
            } catch (Exception e) {
                if (i == maxRetry) {
                    throw new IllegalStateException("Retry Click Till Expected Condition Filed with Error" + e);
                }
            }
        }

    }

    /**
     * Retry Action Visibility for the given Web Element, Web Driver, Max Retry
     *
     * @param webElement - Web Element
     * @param webDriver  - Web Driver
     * @param maxRetry   - Max Retry
     * @return - return true if Visible otherwise false
     */
    public static boolean retryVisibility(WebElement webElement, WebDriver webDriver, int maxRetry) {

        WebDriverWait webDriverWait = Waits.getExplicitWait(webDriver, TWO);

        for (int i = ZERO; i <= maxRetry; i++) {
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


    /**
     * Retry Scroll Into Web Element
     *
     * @param webElement - Web Element
     * @param driver     - Web Driver
     * @param maxRetry   - Max Retry
     */
    public static void retryScrollIntoWebElement(WebElement webElement, WebDriver driver, int maxRetry) {

        for (int i = ZERO; i <= maxRetry; i++) {
            try {
                scrollIntoWebElement(webElement, driver);
                break;
            } catch (Exception e) {
                if (i == maxRetry)
                    throw new IllegalStateException("Retry Web Element Click & Send Keys Failed with error - " + e);
            }
        }

    }

}
