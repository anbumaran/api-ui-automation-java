package com.asapp.api.feature;

import com.asapp.api.BaseTestApi;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.asapp.TestConstants.DEFAULT_PORT_NUMBER_ASAPP;
import static com.asapp.TestConstants.LOGIN_FAIL;
import static com.asapp.TestConstants.LOGIN_SUCCESS;
import static com.asapp.TestConstants.MAX_RETRY;

@ExtendWith(MockitoExtension.class)
public class UserLogin extends BaseTestApi {

    @Mock
    ServiceObject serviceObject;

    private static final Logger LOGGER = LogManager.getLogger(UserLogin.class);

    private static final String SERVICE_NAME = "Login";
    private static final String MODULE_NAME = "Auth";
    private static final String REQUEST_TYPE = "Post";

    @ParameterizedRepeatedIfExceptionsTest(repeats = MAX_RETRY, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Tag("int")
    @Tag("live")
    public void testUserLoginValid(int testInput) {

        setRequestAndHitService(serviceObject, testInput);

        LOGGER.info("Then Verify valid service response for the Service - '{}' in the Module - '{}' ",
                SERVICE_NAME, MODULE_NAME);

        assertStatusCodeSuccess(serviceObject.response);

        assertEqual(serviceObject.response.asPrettyString(), LOGIN_SUCCESS);

    }

    @ParameterizedRepeatedIfExceptionsTest(repeats = 2, name =
            "Test - " + SERVICE_NAME + " Service in - " + MODULE_NAME + " Module - Negative scenario  {0}")
    @ValueSource(ints = {2, 3, 4, 5})
    @Tag("int")
    @Tag("live")
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

        setInputServiceAndModule(serviceObject, testInput, SERVICE_NAME, MODULE_NAME, DEFAULT_PORT_NUMBER_ASAPP, REQUEST_TYPE);

        LOGGER.info("When Prepare the Service request for the Service - '{}' in the module - '{}'",
                SERVICE_NAME, MODULE_NAME);

        ServiceUtil.setRequestBody(serviceObject);

        LOGGER.info("And Hit Service with '{}' request and get the response", REQUEST_TYPE);

        ServiceUtil.hitService(serviceObject);

    }

}
