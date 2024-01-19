package com.asapp.common.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static com.asapp.Constants.MAX_RETRY;

public class Retry implements IRetryAnalyzer {

    private int counter = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (counter < MAX_RETRY) {
            counter++;
            return true;
        }
        return false;
    }

}
