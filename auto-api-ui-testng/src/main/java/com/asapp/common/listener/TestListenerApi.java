package com.asapp.common.listener;

import com.asapp.common.extentreport.ExtentReportsManager;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

import static com.asapp.Constants.IGNORE_STACK_TRACE;

public class TestListenerApi implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestListenerApi.class);


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

        LOGGER.info("Test - Class: {}, Method: {} - Started", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName());

        ExtentReportsManager.getExtent().log(Status.INFO, "Test - Class : '<b><i>" + iTestResult.getTestName()
                + "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Started");

    }

    private static void testSuccess(ITestResult iTestResult) {

        LOGGER.info("Test - Class: {}, Method: {} - Passed", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName());

        ExtentReportsManager.getExtent().log(Status.PASS, "Test - Class : '<b><i>" + iTestResult.getTestName()
                + "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Passed");

    }

    private static void testSkipped(ITestResult iTestResult) {

        LOGGER.info("Test - Class: {}, Method: {} - Skipped", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName());

        ExtentReportsManager.getExtent().log(Status.SKIP, "Test - Class : '<b><i>" + iTestResult.getTestName()
                + "</i></b>' - Method: '<b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Skipped");

    }

    private static void testFailure(ITestResult iTestResult) {

        LOGGER.info("Test - Class: {}, Method: {} - Failed", iTestResult.getTestClass().getTestName(),
                iTestResult.getMethod().getMethodName());

        ExtentReportsManager.getExtent().log(Status.FAIL, "Test - Class : '<b><i>" + iTestResult.getTestName() +
                "</i></b> - Method: <b><i>" + iTestResult.getMethod().getMethodName() + "</i></b>' - Failed");

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
