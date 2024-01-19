package com.asapp.api.scenario;

import com.asapp.TestConstants;
import com.asapp.api.BaseTest;
import com.asapp.api.feature.GetSelectedProduct;
import com.asapp.common.model.ServiceObject;
import com.asapp.api.util.ServiceUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.LOGIN_SUCCESS;
import static com.asapp.TestConstants.LOGOUT_SUCCESS;
import static com.asapp.TestConstants.PRODUCT_NAME;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.USER_NAME;

@Test(testName = "LogInAddRemoveProdCheckCartLogOut")
public class LogInAddRemoveProdCheckCartLogOut extends BaseTest {

    ServiceObject serviceObject = new ServiceObject();

    private static final Logger LOGGER = LogManager.getLogger(GetSelectedProduct.class);

    private static final String MODULE_NAME_AUTH = "Auth";
    private static final String MODULE_NAME_PRODUCTS = "Products";
    private static final String SERVICE_NAME_LOGIN = "Login";
    private static final String SERVICE_NAME_LOGOUT = "Logout";
    private static final String SERVICE_NAME_ADD_PROD = "Add Product";
    private static final String SERVICE_NAME_REMOVE_PROD = "Remove Product";

    private static final String SERVICE_NAME_GET_CART = "Get Cart";


    @ParameterizedTest(name = "Test - " + SERVICE_NAME_LOGIN + " Service in - " + MODULE_NAME_AUTH + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Order(1)
    @Tag("int")
    @Tag("live")
    @Test(groups = {"int", "live"},
    dataProvider = "one", dataProviderClass = TestConstants.class)
    public void testUserLoginValid(int testInput) {

        final String requestType = "Post";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_LOGIN, MODULE_NAME_AUTH, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_LOGIN, MODULE_NAME_AUTH);

        assetStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGIN_SUCCESS);

    }

    @ParameterizedTest(name = "Test - " + SERVICE_NAME_ADD_PROD +
            " Service in - " + MODULE_NAME_PRODUCTS + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1, 2, 3})
    @Order(2)
    @Tag("int")
    @Tag("live")
    public void testAddProductValid(int testInput) {

        final String requestType = "Post";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_ADD_PROD, MODULE_NAME_PRODUCTS, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_ADD_PROD, MODULE_NAME_PRODUCTS);

        assetStatusCodeSuccess(serviceObject.response);

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(serviceObject.response.asPrettyString(), serviceObject.expectedRespData.asText());

    }

    @ParameterizedTest(name = "Test - " + SERVICE_NAME_GET_CART +
            " Service in - " + MODULE_NAME_PRODUCTS + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Order(3)
    @Tag("int")
    @Tag("live")
    public void testGetCartAfterAddProductValid(int testInput) {

        final String requestType = "Get";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS);

        assetStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @ParameterizedTest(name = "Test - " + SERVICE_NAME_REMOVE_PROD +
            " Service in - " + MODULE_NAME_PRODUCTS + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1, 2, 3})
    @Order(4)
    @Tag("int")
    @Tag("live")
    public void testRemoveProductValid(int testInput) {

        final String requestType = "Post";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_REMOVE_PROD, MODULE_NAME_PRODUCTS, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_REMOVE_PROD, MODULE_NAME_PRODUCTS);

        assetStatusCodeSuccess(serviceObject.response);

        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(serviceObject.response.asPrettyString(), serviceObject.expectedRespData.asText());

    }

    @ParameterizedTest(name = "Test - " + SERVICE_NAME_GET_CART +
            " Service in - " + MODULE_NAME_PRODUCTS + " Module - Positive scenario  {0}")
    @ValueSource(ints = {2})
    @Order(5)
    @Tag("int")
    @Tag("live")
    public void testGetCartAfterRemoveProductValid(int testInput) {

        final String requestType = "Get";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_GET_CART, MODULE_NAME_PRODUCTS);

        assetStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @ParameterizedTest(name = "Test - " + SERVICE_NAME_LOGOUT + " Service in - " + MODULE_NAME_AUTH + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Order(6)
    @Tag("int")
    @Tag("live")
    public void testUserLogoutValid(int testInput) {

        final String requestType = "Post";

        setRequestAndHitService(serviceObject, testInput, SERVICE_NAME_LOGOUT, MODULE_NAME_AUTH, requestType);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME_LOGOUT, MODULE_NAME_AUTH);

        assetStatusCodeSuccess(serviceObject.response);

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
            case SERVICE_NAME_REMOVE_PROD:
                ServiceUtil.setEndPointParameter(serviceObject, USER_NAME, PRODUCT_NAME);
                ServiceUtil.setRequestBody(serviceObject);
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

}
