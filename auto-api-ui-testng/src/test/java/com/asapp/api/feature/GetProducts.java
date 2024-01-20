package com.asapp.api.feature;

import com.asapp.api.ApiBaseTest;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.asapp.TestConstants.RESPONSE_FILE_PATH;
import static com.asapp.TestConstants.USER_NAME;

@ExtendWith(MockitoExtension.class)
public class GetProducts extends ApiBaseTest {

    @Mock
    ServiceObject serviceObject;

    private static final Logger LOGGER = LogManager.getLogger(GetProducts.class);

    private static final String SERVICE_NAME = "Get Products";
    private static final String MODULE_NAME = "Products";
    private static final String REQUEST_TYPE = "Get";

    @ParameterizedRepeatedIfExceptionsTest(repeats = 2, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Tag("int")
    @Tag("live")
    public void testGetProductsValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assetStatusCodeSuccess(serviceObject.response);

        JsonNode jsonNode = serviceObject.response.as(JsonNode.class);
        ServiceUtil.setExpectedNode(serviceObject, RESPONSE_FILE_PATH);

        assertEqual(jsonNode, serviceObject.expectedRespData);

    }

    @ParameterizedRepeatedIfExceptionsTest(repeats = 2, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Negative scenario  {0}")
    @ValueSource(ints = {2, 3})
    @Tag("int")
    @Tag("live")
    public void testGetProductsInvalid(int testInput) {

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

        ServiceUtil.setEndPointParameter(serviceObject, USER_NAME);

        LOGGER.info("And Hit Service with '{}' request and get the response", REQUEST_TYPE);

        ServiceUtil.hitService(serviceObject);

    }

}
