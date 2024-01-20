package com.asapp.common.listener;

import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.ui.driver.WebDriverManager;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Objects;

import static com.asapp.Constants.IGNORE_STACK_TRACE;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);


    @Override
    public void onTestStart(ITestResult iTestResult) {
        testStart(iTestResult);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        testSuccess(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        testSkipped(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        testFailure(iTestResult);
    }

    private static void testStart(ITestResult iTestResult) {

        LOGGER.info("Test - Class: {}, Method: {} & WebDriver: {} - Started", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName(), WebDriverManager.getWebDriver());

        ExtentReportsManager.getExtent().log(Status.INFO, "Test - Class : '<b><i>" + iTestResult.getTestName()
                + "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Started");

    }

    private static void testSuccess(ITestResult iTestResult) {

        LOGGER.info("Test - Class: {}, Method: {} & WebDriver: {} - Passed", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName(), WebDriverManager.getWebDriver());

        ExtentReportsManager.getExtent().log(Status.PASS, "Test - Class : '<b><i>" + iTestResult.getTestName()
                + "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Passed");

    }

    private static void testSkipped(ITestResult iTestResult) {

        LOGGER.info("Test - Class: {}, Method: {} & WebDriver: {} - Skipped", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName(), WebDriverManager.getWebDriver());

        ExtentReportsManager.getExtent().log(Status.SKIP, "Test - Class : '<b><i>" + iTestResult.getTestName()
                + "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Skipped");

    }

    private static void testFailure(ITestResult iTestResult) {

        WebDriver driver = WebDriverManager.getWebDriver();

        LOGGER.info("Test - Class: {}, Method: {} & WebDriver: {} - Failed", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName(), WebDriverManager.getWebDriver());

        String screenShort = ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);

        ExtentReportsManager.getExtent().log(Status.FAIL, "Test - Class : '<b><i>" + iTestResult.getTestName() +
                "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - URL: '<b><i>" +
                driver.getCurrentUrl() + "</i></b>' - Failed", ExtentReportsManager.getExtent()
                .addScreenCaptureFromBase64String(screenShort, "<b><i>" + driver.getTitle() + "</i></b>").getModel()
                .getMedia().get(0));

        String message = iTestResult.getThrowable().getMessage();

        String stackTrace = Arrays.stream(iTestResult.getThrowable().getStackTrace())
                .takeWhile(element -> !element.getClassName().startsWith(IGNORE_STACK_TRACE))
                .map(StackTraceElement::toString)
                .reduce((line1, line2) -> line1 + "\n" + line2)
                .orElse("");

        ExtentReportsManager.getExtent().log(Status.FAIL,
                "<b>Error - Message & StackTrace:</b>\n <pre>" + message + "\n\n" + stackTrace + "</pre>");

    }

}
