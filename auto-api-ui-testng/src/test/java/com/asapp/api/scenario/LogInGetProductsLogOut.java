package com.asapp.api.scenario;

import com.asapp.TestConstants;
import com.asapp.api.BaseTestApi;
import com.asapp.api.feature.GetSelectedProduct;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.GET;
import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.LOGIN_SUCCESS;
import static com.asapp.TestConstants.LOGOUT_SUCCESS;
import static com.asapp.TestConstants.ONE;
import static com.asapp.TestConstants.POST;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.USER_NAME;
import static com.asapp.TestConstants.USER_NOT_LOGGED_IN;

public class LogInGetProductsLogOut extends BaseTestApi {

    ServiceObject serviceObject = new ServiceObject();

    private static final Logger LOGGER = LogManager.getLogger(GetSelectedProduct.class);

    private static final String MODULE_NAME_AUTH = "Auth";
    private static final String MODULE_NAME_PRODUCTS = "Products";
    private static final String SERVICE_NAME_LOGIN = "Login";
    private static final String SERVICE_NAME_LOGOUT = "Logout";
    private static final String SERVICE_NAME_GET_PROD = "Get Products";
    private static final String SERVICE_NAME = SERVICE_NAME_LOGIN + " - " + SERVICE_NAME_LOGOUT + " - " +
            SERVICE_NAME_GET_PROD;
    private static final String MODULE_NAME = MODULE_NAME_AUTH + " - " + MODULE_NAME_PRODUCTS;
    private static final String REQUEST_TYPE = GET + " - " + POST;
    private static int index = 0;


    @BeforeMethod(alwaysRun = true)
    public void initializeApi() {
        ExtentReportsManager.startExtentApiTest(SERVICE_NAME + " - " + MODULE_NAME + " - " + ++index);
        initializeApi(MODULE_NAME, SERVICE_NAME, REQUEST_TYPE);
    }

    @Test(groups = {INT, LIVE}, dataProvider = ONE, dataProviderClass = TestConstants.class,
            priority = 1, retryAnalyzer = Retry.class)
    public void testUserLoginValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_LOGIN, MODULE_NAME_AUTH, POST);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_LOGIN, MODULE_NAME_AUTH);

        assetStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGIN_SUCCESS);

    }

    @Test(groups = {INT, LIVE}, dataProvider = ONE, dataProviderClass = TestConstants.class,
            priority = 2, retryAnalyzer = Retry.class)
    public void testGetProductsValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_GET_PROD, MODULE_NAME_PRODUCTS, GET);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_GET_PROD, MODULE_NAME_PRODUCTS);

        assetStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @Test(groups = {INT, LIVE}, dataProvider = ONE, dataProviderClass = TestConstants.class,
            priority = 3, retryAnalyzer = Retry.class)
    public void testUserLogoutValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_LOGOUT, MODULE_NAME_AUTH, POST);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_LOGOUT, MODULE_NAME_AUTH);

        assetStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGOUT_SUCCESS);

    }

    @Test(groups = {INT, LIVE}, dataProvider = ONE, dataProviderClass = TestConstants.class,
            priority = 4, retryAnalyzer = Retry.class)
    public void testGetProductsInvalid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_GET_PROD, MODULE_NAME_PRODUCTS, GET);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_GET_PROD, MODULE_NAME_PRODUCTS);

        assetStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), USER_NOT_LOGGED_IN);

    }

    private void setRequestAndHitService(ServiceObject serviceObject, int testInput, String serviceName,
                                         String moduleName, String requestType) {

        LOGGER.info("Given The input - testData{} for the Service - '{}' in the module - '{}'",
                testInput, serviceName, moduleName);

        setInputServiceAndModule(serviceObject, testInput, serviceName, moduleName, requestType);

        LOGGER.info("When Prepare the Service request for the Service - '{}' in the module - '{}'",
                serviceName, moduleName);

        switch (serviceName) {
            case SERVICE_NAME_LOGIN:
            case SERVICE_NAME_LOGOUT:
                ServiceUtil.setRequestBody(serviceObject);
                break;
            case SERVICE_NAME_GET_PROD:
                ServiceUtil.setEndPointParameter(serviceObject, USER_NAME);
                break;
            default:
                throw new IllegalArgumentException("Invalid Service Name : " + serviceName);
        }

        LOGGER.info("And Hit Service with '{}' request and get the response", requestType);

        ServiceUtil.hitService(serviceObject);

    }

}
