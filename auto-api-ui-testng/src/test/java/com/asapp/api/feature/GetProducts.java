package com.asapp.api.feature;

import com.asapp.api.BaseTestApi;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.GET;
import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.INVALID;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.USER_NAME;
import static com.asapp.TestConstants.VALID;

public class GetProducts extends BaseTestApi {


    ServiceObject serviceObject = new ServiceObject();

    private static final Logger LOGGER = LogManager.getLogger(GetProducts.class);

    private static final String SERVICE_NAME = "Get Products";
    private static final String MODULE_NAME = "Products";
    private static final String REQUEST_TYPE = GET;
    private static int index = 0;


    @BeforeMethod(alwaysRun = true)
    public void initializeApi() {
        ExtentReportsManager.startExtentApiTest(SERVICE_NAME + " - " + MODULE_NAME + " - " + ++index);
        initializeApi(MODULE_NAME, SERVICE_NAME, REQUEST_TYPE);
    }

    @Test(groups = {INT, LIVE}, dataProvider = VALID, retryAnalyzer = Retry.class)
    public void testGetProductsValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @Test(groups = {INT, LIVE}, dataProvider = INVALID, retryAnalyzer = Retry.class)
    public void testGetProductsInvalid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify invalid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeFail(serviceObject.response);

    }

    private void setRequestAndHitService(ServiceObject serviceObject, int testInput) {

        LOGGER.info("Given The input - testData{} for the Service - '{}' in the module - '{}'",
                testInput, SERVICE_NAME, MODULE_NAME);

        setInputServiceAndModule(serviceObject, testInput, SERVICE_NAME, MODULE_NAME, REQUEST_TYPE);

        LOGGER.info("When Prepare the Service request for the Service - '{}' in the module - '{}'",
                SERVICE_NAME, MODULE_NAME);

        ServiceUtil.setEndPointParameter(serviceObject, USER_NAME);

        LOGGER.info("And Hit Service with '{}' request and get the response", REQUEST_TYPE);

        ServiceUtil.hitService(serviceObject);

    }

    @DataProvider(name = VALID)
    public static Object[][] valid() {
        return new Object[][]{{1}};
    }

    @DataProvider(name = INVALID)
    public static Object[][] invalid() {
        return new Object[][]{{2}, {3}};
    }

}
