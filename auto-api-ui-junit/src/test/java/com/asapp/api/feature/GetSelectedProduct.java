package com.asapp.api.feature;

import com.asapp.api.BaseTestApi;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.model.ServiceObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.asapp.TestConstants.MAX_RETRY;
import static com.asapp.TestConstants.PRODUCT_NAME;
import static com.asapp.TestConstants.PRODUCT_NOT_EXIST;
import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.USER_NAME;
import static com.asapp.TestConstants.USER_NOT_LOGGED_IN;

@ExtendWith(MockitoExtension.class)
public class GetSelectedProduct extends BaseTestApi {

    @Mock
    ServiceObject serviceObject;

    private static final Logger LOGGER = LogManager.getLogger(GetSelectedProduct.class);

    private static final String SERVICE_NAME = "Get Selected Product";
    private static final String MODULE_NAME = "Products";
    private static final String REQUEST_TYPE = "Get";


    @ParameterizedRepeatedIfExceptionsTest(repeats = MAX_RETRY, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1, 2, 3})
    @Tag("int")
    @Tag("live")
    public void testGetSelectedProductValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeSuccess(serviceObject.response);

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


    @ParameterizedRepeatedIfExceptionsTest(repeats = 2, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Negative scenario  {0}")
    @ValueSource(ints = {4})
    @Tag("int")
    @Tag("live")
    public void testGetSelectedProductInvalidProduct(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeFail(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), PRODUCT_NOT_EXIST);

    }

    @ParameterizedRepeatedIfExceptionsTest(repeats = 2, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Negative scenario  {0}")
    @ValueSource(ints = {5, 6, 7})
    @Tag("int")
    @Tag("live")
    public void testGetSelectedProductInvalidUser(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeFail(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), USER_NOT_LOGGED_IN);

    }

    @ParameterizedRepeatedIfExceptionsTest(repeats = 2, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Negative scenario  {0}")
    @ValueSource(ints = {8, 9})
    @Tag("int")
    @Tag("live")
    public void testGetSelectedProductInvalidInput(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeFail(serviceObject.response);

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

}
