package com.asapp.ui.pageutils;

import org.openqa.selenium.WebDriver;
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
     * @param driver     - Web Driver
     * @param maxRetry   - Max Retry
     * @param inputValue - Input Value in case of Send Keys otherwise Click Button
     */
    public static void retryClickOrSendKeys(WebElement webElement, WebDriver driver, int maxRetry,
                                            CharSequence... inputValue) {

        Actions action = new Actions(driver);
        WebDriverWait webDriverWait = Waits.getExplicitWait(driver, TWO);

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
            } catch (Exception e) {
                try {
                    Waits.setImplicitWait(driver, TWO);
                    Waits.fluentWait(driver, ExpectedConditions.elementToBeClickable(webElement));
                } catch (Exception ignored) {
                }
                if (i == maxRetry) {
                    throw new IllegalStateException("Retry Click Till Expected Condition Filed with Error " + e);
                }
            }
        }

    }

    /**
     * Retry Action Click Or SendKeys till Expected Conditions for the given
     * Web Element, Web Driver, Max Retry & Input Value in case of Send Keys
     *
     * @param webElement        - Web Element
     * @param driver            - Web Driver
     * @param expectedCondition - Expected Condition
     * @param maxRetry          - Max Retry
     * @param inputValue        - Input Value in case of Send Keys otherwise Click Button
     */
    public static void retryClickOrSendKeysTillExpCond(WebElement webElement, WebDriver driver,
                                                       ExpectedCondition<?> expectedCondition, int maxRetry,
                                                       CharSequence... inputValue) {

        Actions action = new Actions(driver);
        WebDriverWait webDriverWait = Waits.getExplicitWait(driver, TWO);

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
            } catch (Exception e) {
                try {
                    Waits.setImplicitWait(driver, TWO);
                    Waits.fluentWait(driver, expectedCondition);
                } catch (Exception ignored) {
                }
                if (i == maxRetry) {
                    throw new IllegalStateException("Retry Click or Send Keys Filed with Error " + e);
                }
            }
        }

    }

    /**
     * Retry Visibility for the given Web Element, Web Driver, Max Retry
     *
     * @param webElement - Web Element
     * @param driver     - Web Driver
     * @param maxRetry   - Max Retry
     * @return - return true if Visible otherwise false
     */
    public static boolean retryVisibility(WebElement webElement, WebDriver driver, int maxRetry) {

        WebDriverWait webDriverWait = Waits.getExplicitWait(driver, TWO);

        for (int i = ZERO; i <= maxRetry; i++) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
                return true;
            } catch (Exception e) {
                try {
                    Waits.setImplicitWait(driver, TWO);
                    Waits.fluentWait(driver, ExpectedConditions.visibilityOf(webElement));
                } catch (Exception ignored) {
                }
                if (i == maxRetry) {
                    throw new IllegalStateException("Action Click Filed with Error" + e);
                }
            }
        }
        return false;

    }

    /**
     * Retry Visibility till Expected Conditions for the given
     * Web Element, Web Driver, Max Retry & Input Value in case of Send Keys
     *
     * @param webElement        - Web Element
     * @param driver            - Web Driver
     * @param expectedCondition - Expected Condition
     * @param maxRetry          - Max Retry
     * @return - return true if Visible otherwise false
     */
    public static boolean retryVisibilityTillExpCond(WebElement webElement, WebDriver driver,
                                                     ExpectedCondition<?> expectedCondition, int maxRetry) {

        WebDriverWait webDriverWait = Waits.getExplicitWait(driver, TWO);

        for (int i = ZERO; i <= maxRetry; i++) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
                webDriverWait.until(expectedCondition);
                return true;
            } catch (Exception e) {
                try {
                    Waits.setImplicitWait(driver, TWO);
                    Waits.fluentWait(driver, expectedCondition);
                } catch (Exception ignored) {
                }
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
                try {
                    Waits.setImplicitWait(driver, TWO);
                    Waits.fluentWait(driver, ExpectedConditions.visibilityOf(webElement));
                } catch (Exception ignored) {
                }
                if (i == maxRetry)
                    throw new IllegalStateException("Retry Web Element Click & Send Keys Failed with error - " + e);
            }
        }

    }

}
