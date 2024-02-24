package com.asapp.api.feature;

import com.asapp.api.BaseTestApi;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.INVALID;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.LOGIN_FAIL;
import static com.asapp.TestConstants.LOGIN_SUCCESS;
import static com.asapp.TestConstants.POST;
import static com.asapp.TestConstants.VALID;

public class UserLogin extends BaseTestApi {

    ServiceObject serviceObject = new ServiceObject();

    private static final Logger LOGGER = LogManager.getLogger(UserLogin.class);

    private static final String SERVICE_NAME = "Login";
    private static final String MODULE_NAME = "Auth";
    private static final String REQUEST_TYPE = POST;
    private static int index = 0;


    @BeforeMethod(alwaysRun = true)
    public void initializeApi() {
        ExtentReportsManager.startExtentApiTest(SERVICE_NAME + " - " + MODULE_NAME + " - " + ++index);
        initializeApi(MODULE_NAME, SERVICE_NAME, REQUEST_TYPE);
    }

    @Test(groups = {INT, LIVE}, dataProvider = VALID, retryAnalyzer = Retry.class)
    public void testUserLoginValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGIN_SUCCESS);

    }

    @Test(groups = {INT, LIVE}, dataProvider = INVALID, retryAnalyzer = Retry.class)
    public void testUserLoginInvalid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeFail(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGIN_FAIL);

    }

    private void setRequestAndHitService(ServiceObject serviceObject, int testInput) {

        LOGGER.info("Given The input - testData{} for the Service - '{}' in the module - '{}'",
                testInput, SERVICE_NAME, MODULE_NAME);

        setInputServiceAndModule(serviceObject, testInput, SERVICE_NAME, MODULE_NAME, REQUEST_TYPE);

        LOGGER.info("When Prepare the Service request for the Service - '{}' in the module - '{}'",
                SERVICE_NAME, MODULE_NAME);

        ServiceUtil.setRequestBody(serviceObject);

        LOGGER.info("And Hit Service with '{}' request and get the response", REQUEST_TYPE);

        ServiceUtil.hitService(serviceObject);

    }

    @DataProvider(name = VALID)
    public static Object[][] valid() {
        return new Object[][]{{1}};
    }

    @DataProvider(name = INVALID)
    public static Object[][] invalid() {
        return new Object[][]{{2}, {3}, {4}, {5}};
    }

}
