package com.asapp.ui;

import com.asapp.api.util.ServiceUtil;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.utils.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.asapp.TestConstants.END_POINT_BASE_UI_INT;
import static com.asapp.TestConstants.END_POINT_BASE_UI_LIVE;
import static com.asapp.TestConstants.INT_PROFILE;
import static com.asapp.TestConstants.LIVE_PROFILE;
import static com.asapp.TestConstants.REQUEST_FILE_PATH;
import static com.asapp.TestConstants.TEST_DATA;
import static com.asapp.common.Constants.ENV_PROPERTY;
import static com.asapp.ui.actions.EnvActions.getEnvPassword;
import static com.asapp.ui.actions.EnvActions.getEnvUsername;

public class BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    public static void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput,
                                                String serviceName, String moduleName) {

        String testData = TEST_DATA + testDataInput;

        ServiceUtil.setServiceAndModule(serviceObject, serviceName, moduleName);

        setEnv(serviceObject);

        ServiceUtil.setRequestData(serviceObject, REQUEST_FILE_PATH, testData);

    }

    public static void setEnv(ServiceObject serviceObject) {
        serviceObject.env = System.getProperty(ENV_PROPERTY);
    }

    public void gotoHomeURL(ServiceObject serviceObject, WebDriver driver) {

        if (serviceObject.env.equalsIgnoreCase(INT_PROFILE)) {
            serviceObject.baseEndPoint = END_POINT_BASE_UI_INT;
            serviceObject.username = getEnvUsername(INT_PROFILE);
            serviceObject.password = getEnvPassword(INT_PROFILE);
        } else if (serviceObject.env.equalsIgnoreCase(LIVE_PROFILE)) {
            serviceObject.baseEndPoint = END_POINT_BASE_UI_LIVE;
            serviceObject.username = getEnvUsername(LIVE_PROFILE);
            serviceObject.password = getEnvPassword(LIVE_PROFILE);
        } else {
            throw new IllegalArgumentException("Invalid Environment Input : " + serviceObject.env);
        }

        LOGGER.info("Set Username - {} & Password for Env - {}", serviceObject.username, serviceObject.env);
        driver.get(serviceObject.baseEndPoint);

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
