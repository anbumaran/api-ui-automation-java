package com.asapp.api;

import com.asapp.BaseTest;
import com.asapp.api.util.ServiceUtil;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestUi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;

import static com.asapp.TestConstants.END_POINT_BASE_API_INT;
import static com.asapp.TestConstants.END_POINT_BASE_API_LIVE;
import static com.asapp.TestConstants.END_POINT_FILE;
import static com.asapp.TestConstants.INT_PROFILE;
import static com.asapp.TestConstants.LIVE_PROFILE;

public class BaseTestApi extends BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseTestUi.class);

    public static void initializeApi(String moduleName, String serviceName, String requestType) {

        LOGGER.info("API Testing in {} Env to Validate - {} - {} - {}",
                getEnv(), serviceName, moduleName, requestType);

    }

    @AfterClass(alwaysRun = true)
    public void closeApi() {
        ExtentReportsManager.clearExtentTest();
    }

    public static void setInputServiceAndModule(ServiceObject serviceObject, int testDataInput,
                                                String serviceName, String moduleName, String requestType) {

        setInputServiceAndModule(serviceObject, testDataInput, serviceName, moduleName);

        String baseEndPoint;

        if (serviceObject.env.equalsIgnoreCase(INT_PROFILE)) {
            baseEndPoint = END_POINT_BASE_API_INT;
        } else if (serviceObject.env.equalsIgnoreCase(LIVE_PROFILE)) {
            baseEndPoint = END_POINT_BASE_API_LIVE;
        } else {
            throw new IllegalArgumentException("Invalid Environment Input : " + serviceObject.env);
        }

        ServiceUtil.setEndPoint(serviceObject, END_POINT_FILE, baseEndPoint);

        ServiceUtil.setRequestType(serviceObject, requestType);

    }

}
