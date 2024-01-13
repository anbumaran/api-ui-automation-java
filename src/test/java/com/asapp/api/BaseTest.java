package com.asapp.api;

import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.utils.StringUtil;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;

import static com.asapp.TestConstants.END_POINT_BASE_API_INT;
import static com.asapp.TestConstants.END_POINT_BASE_API_LIVE;
import static com.asapp.TestConstants.END_POINT_FILE;
import static com.asapp.TestConstants.INT_PROFILE;
import static com.asapp.TestConstants.LIVE_PROFILE;
import static com.asapp.TestConstants.REQUEST_FILE_PATH;
import static com.asapp.TestConstants.TEST_DATA;
import static com.asapp.common.Constants.ENV_PROPERTY;

public class BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    public static void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput,
                                                String serviceName, String moduleName, String requestType) {

        String testData = TEST_DATA + testDataInput;

        ServiceUtil.setServiceAndModule(serviceObject, serviceName, moduleName);

        serviceObject.env = System.getProperty(ENV_PROPERTY);

        String baseEndPoint;

        if (serviceObject.env.equalsIgnoreCase(INT_PROFILE)) {
            baseEndPoint = END_POINT_BASE_API_INT;
        } else if (serviceObject.env.equalsIgnoreCase(LIVE_PROFILE)) {
            baseEndPoint = END_POINT_BASE_API_LIVE;
        } else {
            throw new IllegalArgumentException("Invalid Environment Input : " + serviceObject.env);
        }

        ServiceUtil.setEndPoint(serviceObject, END_POINT_FILE, baseEndPoint);

        ServiceUtil.setRequestData(serviceObject, REQUEST_FILE_PATH, testData);

        ServiceUtil.setRequestType(serviceObject, requestType);

    }

    public void assetStatusCodeSuccess(Response response) {

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
        LOGGER.info("Actual - Response Code - '{}' matches Expected - Response Code is 200 ", response.getStatusCode());

    }

    public void assetStatusCodeFail(Response response) {

        Assertions.assertThat(response.getStatusCode()).isNotEqualTo(200);
        LOGGER.info("Actual - Response Code - '{}' matches Expected - Response Code is NOT 200", response.getStatusCode());

    }

    public void assertEqual(Object actual, Object expected) {

        LOGGER.info("Actual Object : ");
        StringUtil.printObject(actual);

        LOGGER.info("Expected Object : ");
        StringUtil.printObject(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
        LOGGER.info("Actual - Object matches Expected - Object");

    }


}
