package com.asapp;

import com.asapp.api.util.ServiceUtil;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.validations.Assertions;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.asapp.TestConstants.REQUEST_FILE_PATH;
import static com.asapp.TestConstants.TEST_DATA;
import static com.asapp.common.Constants.ENV_PROPERTY;
import static com.asapp.common.extentreport.ExtentReportsManager.getExtent;

public class BaseTest extends Assertions {

    public static void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput,
                                                String serviceName, String moduleName) {

        String testData = TEST_DATA + testDataInput;

        ServiceUtil.setServiceAndModule(serviceObject, serviceName, moduleName);

        setEnv(serviceObject);

        ServiceUtil.setRequestData(serviceObject, REQUEST_FILE_PATH, testData);

    }

    public static void setEnv(ServiceObject serviceObject) {
        serviceObject.env = getEnv();
    }

    public static String getEnv() {
        return System.getProperty(ENV_PROPERTY);
    }

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        ExtentReportsManager.initializeExtentReports();
    }

    @AfterSuite(alwaysRun = true)
    public void teardownSuite() {
        ExtentReportsManager.finishExtentTest();
    }

    public void assertEqual(Object actual, Object expected) {

        try {
            super.assertEqual(actual, expected);
            getExtent().log(Status.PASS, "Actual - Object - " + actual + " - matches Expected - Object - " + expected);
        } catch (final AssertionError e) {
            getExtent().log(Status.WARNING, "Actual - Object - " + actual + " - NOT matches Expected - Object - " + expected);
            throw e;
        }

    }

    public void assertStatusCodeSuccess(Response response) {

        try {
            super.assertStatusCodeSuccess(response);
            getExtent().log(Status.PASS, "Actual - Response Code - '" + response.getStatusCode()
                    + "' - matches Expected - Response Code is 200");
        } catch (final AssertionError e) {
            getExtent().log(Status.WARNING, "Actual - Response Code - '" + response.getStatusCode()
                    + "' - NOT matches Expected - Response Code is 200");
            throw e;
        }

    }

    public void assertStatusCodeFail(Response response) {

        try {
            super.assertStatusCodeFail(response);
            getExtent().log(Status.PASS, "Actual - Response Code - '" + response.getStatusCode()
                    + "' - matches Expected - Response Code is NOT 200");
        } catch (final AssertionError e) {
            getExtent().log(Status.WARNING, "Actual - Response Code - '" + response.getStatusCode()
                    + "' - NOT matches Expected - Response Code");
            throw e;
        }

    }

}
