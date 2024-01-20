package com.asapp;

import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.validations.Assertions;

import static com.asapp.TestConstants.REQUEST_FILE_PATH;
import static com.asapp.TestConstants.TEST_DATA;
import static com.asapp.common.Constants.ENV_PROPERTY;

public class BaseTest extends Assertions {

    public static void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput,
                                                String serviceName, String moduleName) {

        String testData = TEST_DATA + testDataInput;

        ServiceUtil.setServiceAndModule(serviceObject, serviceName, moduleName);

        setEnv(serviceObject);

        ServiceUtil.setRequestData(serviceObject, REQUEST_FILE_PATH, testData);

    }

    public static void setEnv(ServiceObject serviceObject) {
        serviceObject.env = getEnv();
    }

    public static String getEnv() {
        return System.getProperty(ENV_PROPERTY);
    }

}
