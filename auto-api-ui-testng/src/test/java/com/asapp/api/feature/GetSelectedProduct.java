package com.asapp.api.feature;

import com.asapp.api.BaseTestApi;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.GET;
import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.PRODUCT_NAME;
import static com.asapp.TestConstants.PRODUCT_NOT_EXIST;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.USER_NAME;
import static com.asapp.TestConstants.USER_NOT_LOGGED_IN;
import static com.asapp.TestConstants.VALID;

public class GetSelectedProduct extends BaseTestApi {

    ServiceObject serviceObject = new ServiceObject();

    private static final Logger LOGGER = LogManager.getLogger(GetSelectedProduct.class);

    private static final String SERVICE_NAME = "Get Selected Product";
    private static final String MODULE_NAME = "Products";
    private static final String REQUEST_TYPE = GET;
    private static final String INVALID_PROD = "InvalidProduct";
    private static final String INVALID_USER = "InvalidUser";
    private static final String INVALID_INPUT = "InvalidInput";
    private static int index = 0;

    @BeforeMethod(alwaysRun = true)
    public void initializeApi() {
        ExtentReportsManager.startExtentApiTest(SERVICE_NAME + " - " + MODULE_NAME + " - " + ++index);
        initializeApi(MODULE_NAME, SERVICE_NAME, REQUEST_TYPE);
    }

    @Test(groups = {INT, LIVE}, dataProvider = VALID, retryAnalyzer = Retry.class)
    public void testGetSelectedProductValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ProductsDTO productsDTOActual = new ObjectMapper().convertValue(
                jsonNode, new TypeReference<>() {
                });
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);
        ProductsDTO productsDTOExpected = new ObjectMapper().convertValue(
                serviceObject.expectedRespData, new TypeReference<>() {
                });

        assertEqual(productsDTOActual.getProductQty(), productsDTOExpected.getProductQty());

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }


    @Test(groups = {INT, LIVE}, dataProvider = INVALID_PROD, retryAnalyzer = Retry.class)
    public void testGetSelectedProductInvalidProduct(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeFail(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), PRODUCT_NOT_EXIST);

    }

    @Test(groups = {INT, LIVE}, dataProvider = INVALID_USER, retryAnalyzer = Retry.class)
    public void testGetSelectedProductInvalidUser(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeFail(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), USER_NOT_LOGGED_IN);

    }

    @Test(groups = {INT, LIVE}, dataProvider = INVALID_INPUT, retryAnalyzer = Retry.class)
    public void testGetSelectedProductInvalidInput(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeFail(serviceObject.response);

    }

    private void setRequestAndHitService(ServiceObject serviceObject, int testInput) {

        LOGGER.info("Given The input - testData{} for the Service - '{}' in the module - '{}'",
                testInput, SERVICE_NAME, MODULE_NAME);

        setInputServiceAndModule(serviceObject, testInput, SERVICE_NAME, MODULE_NAME, REQUEST_TYPE);

        LOGGER.info("When Prepare the Service request for the Service - '{}' in the module - '{}'",
                SERVICE_NAME, MODULE_NAME);

        ServiceUtil.setEndPointParameter(serviceObject, USER_NAME, PRODUCT_NAME);

        LOGGER.info("And Hit Service with '{}' request and get the response", REQUEST_TYPE);

        ServiceUtil.hitService(serviceObject);

    }

    @DataProvider(name = VALID)
    public static Object[][] valid() {
        return new Object[][]{{1}, {2}, {3}};
    }

    @DataProvider(name = INVALID_PROD)
    public static Object[][] invalidProduct() {
        return new Object[][]{{4}};
    }

    @DataProvider(name = INVALID_USER)
    public static Object[][] invalidUser() {
        return new Object[][]{{5}, {6}, {7}};
    }

    @DataProvider(name = INVALID_INPUT)
    public static Object[][] invalidInput() {
        return new Object[][]{{8}, {9}};
    }

}
