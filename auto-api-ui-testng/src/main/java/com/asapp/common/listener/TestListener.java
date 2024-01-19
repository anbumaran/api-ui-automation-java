package com.asapp.common.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    }

    private static void testStart(ITestResult iTestResult) {

    }

    private static void testSuccess(ITestResult iTestResult) {

    }

    private static void testFailure(ITestResult iTestResult) {

    }

    private static void testSkipped(ITestResult iTestResult) {

    }

}
