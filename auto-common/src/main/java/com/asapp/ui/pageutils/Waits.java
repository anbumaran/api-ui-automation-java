package com.asapp.ui.pageutils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.asapp.Constants.ONE;
import static com.asapp.Constants.TWO_HUNDRED;

public class Waits {

    /**
     * Get Explicit Wait for the given Web Driver and Duration in Seconds
     *
     * @param webDriver         - Web Driver
     * @param durationInSeconds - Duration in Seconds
     * @return - WebDriverWait instance
     */
    public static WebDriverWait getExplicitWait(WebDriver webDriver, int durationInSeconds) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(durationInSeconds));
    }

    /**
     * Set Implicit Wait
     *
     * @param webDriver         - Web Driver
     * @param durationInSeconds - Duration in Seconds
     */
    public static void setImplicitWait(WebDriver webDriver, int durationInSeconds) {
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(durationInSeconds));
    }

    public static long fluentWait(WebDriver driver, ExpectedCondition<?> expectedCondition) {
        return fluentWait(driver, expectedCondition, Duration.ofMillis(TWO_HUNDRED), Duration.ofSeconds(ONE));
    }

    public static long fluentWait(WebDriver driver, ExpectedCondition<?> expectedCondition, int timeoutInSeconds) {
        return fluentWait(driver, expectedCondition, Duration.ofMillis(TWO_HUNDRED), Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Fluent Wait till the Expected Condition is met for the below inputs and provide time taken to complete
     *
     * @param driver            - WebDriver
     * @param expectedCondition - Expected Conditions
     * @param interval          - Polling Interval
     * @param timeout           - Maximum Timeout
     * @return - Duration Taken to meet the Expected Condition
     */
    public static long fluentWait(WebDriver driver, ExpectedCondition<?> expectedCondition, Duration interval,
                                  Duration timeout) {

        long startTime = System.currentTimeMillis();

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .pollingEvery(interval)
                .withTimeout(timeout)
                .ignoring(WebDriverException.class);

        wait.until(expectedCondition);

        return System.currentTimeMillis() - startTime;

    }

}
