package com.asapp.ui.pageutils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {

    /**
     * Provides Web Driver Wait for the given number of Seconds
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

    /**
     * Fluent Wait till the Expected Condition is met for the below inputs and provide time taken to complete
     *
     * @param driver            - WebDriver
     * @param expectedCondition - Expected Conditions
     * @param timeout           - Maximum Timeout
     * @param interval          - Polling Interval
     * @return - Duration Taken to meet the Expected Condition
     */
    public static long fluentWait(WebDriver driver, ExpectedCondition<?> expectedCondition, Duration timeout,
                                  Duration interval) {

        long startTime = System.currentTimeMillis();

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(interval)
                .ignoring(WebDriverException.class);

        wait.until(expectedCondition);

        return System.currentTimeMillis() - startTime;

    }
}
