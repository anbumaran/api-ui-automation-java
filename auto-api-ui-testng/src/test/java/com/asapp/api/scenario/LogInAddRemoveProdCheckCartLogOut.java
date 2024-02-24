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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.GET;
import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.LOGIN_SUCCESS;
import static com.asapp.TestConstants.LOGOUT_SUCCESS;
import static com.asapp.TestConstants.ONE;
import static com.asapp.TestConstants.POST;
import static com.asapp.TestConstants.PRODUCT_NAME;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.THREE;
import static com.asapp.TestConstants.USER_NAME;

public class LogInAddRemoveProdCheckCartLogOut extends BaseTestApi {

    ServiceObject serviceObject = new ServiceObject();

    private static final Logger LOGGER = LogManager.getLogger(GetSelectedProduct.class);

    private static final String MODULE_NAME_AUTH = "Auth";
    private static final String MODULE_NAME_PRODUCTS = "Products";
    private static final String SERVICE_NAME_LOGIN = "Login";
    private static final String SERVICE_NAME_LOGOUT = "Logout";
    private static final String SERVICE_NAME_ADD_PROD = "Add Product";
    private static final String SERVICE_NAME_REMOVE_PROD = "Remove Product";
    private static final String SERVICE_NAME_GET_CART = "Get Cart";
    private static final String SERVICE_NAME = "LogInAddRemoveProdCheckCartLogOut";
    private static final String MODULE_NAME = MODULE_NAME_AUTH + " - " + MODULE_NAME_PRODUCTS;
    private static final String REQUEST_TYPE = GET + " - " + POST;
    private static final String PROD_VALID = "ProductValid";
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

        assertStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGIN_SUCCESS);

    }

    @Test(groups = {INT, LIVE}, dataProvider = THREE, dataProviderClass = TestConstants.class,
            priority = 2, retryAnalyzer = Retry.class)
    public void testAddProductValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_ADD_PROD, MODULE_NAME_PRODUCTS, POST);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_ADD_PROD, MODULE_NAME_PRODUCTS);

        assertStatusCodeSuccess(serviceObject.response);

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(serviceObject.response.asPrettyString(), serviceObject.expectedRespData.asText());

    }

    @Test(groups = {INT, LIVE}, dataProvider = ONE, dataProviderClass = TestConstants.class,
            priority = 3, retryAnalyzer = Retry.class)
    public void testGetCartAfterAddProductValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS, GET);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS);

        assertStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @Test(groups = {INT, LIVE}, dataProvider = THREE, dataProviderClass = TestConstants.class,
            priority = 4, retryAnalyzer = Retry.class)
    public void testRemoveProductValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_REMOVE_PROD, MODULE_NAME_PRODUCTS, POST);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_REMOVE_PROD, MODULE_NAME_PRODUCTS);

        assertStatusCodeSuccess(serviceObject.response);

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(serviceObject.response.asPrettyString(), serviceObject.expectedRespData.asText());

    }

    @Test(groups = {INT, LIVE}, dataProvider = PROD_VALID, priority = 5, retryAnalyzer = Retry.class)
    public void testGetCartAfterRemoveProductValid(int testInput) {

        final String requestType = "Get";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS);

        assertStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @Test(groups = {INT, LIVE}, dataProvider = ONE, dataProviderClass = TestConstants.class,
            priority = 6, retryAnalyzer = Retry.class)
    public void testUserLogoutValid(int testInput) {

        final String requestType = "Post";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_LOGOUT, MODULE_NAME_AUTH, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_LOGOUT, MODULE_NAME_AUTH);

        assertStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGOUT_SUCCESS);

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
            case SERVICE_NAME_ADD_PROD:
                ServiceUtil.setEndPointParameter(serviceObject, USER_NAME, PRODUCT_NAME);
                ServiceUtil.setRequestBody(serviceObject);
                break;
            case SERVICE_NAME_REMOVE_PROD:
                ServiceUtil.setEndPointParameter(serviceObject, USER_NAME, PRODUCT_NAME);
                break;
            case SERVICE_NAME_GET_CART:
                ServiceUtil.setEndPointParameter(serviceObject, USER_NAME);
                break;
            default:
                throw new IllegalArgumentException("Invalid Service Name : " + serviceName);
        }

        LOGGER.info("And Hit Service with '{}' request and get the response", requestType);

        ServiceUtil.hitService(serviceObject);

    }

    @DataProvider(name = PROD_VALID)
    public static Object[][] valid() {
        return new Object[][]{{2}};
    }

}
