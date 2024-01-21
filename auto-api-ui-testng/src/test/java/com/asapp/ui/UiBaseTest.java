package com.asapp.ui;

import com.asapp.BaseTest;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.utils.StringUtil;
import com.asapp.ui.driver.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;

import static com.asapp.TestConstants.END_POINT_BASE_UI_INT;
import static com.asapp.TestConstants.END_POINT_BASE_UI_LIVE;
import static com.asapp.TestConstants.INT_PROFILE;
import static com.asapp.TestConstants.LIVE_PROFILE;
import static com.asapp.ui.actions.asapp.EnvActions.getEnvPassword;
import static com.asapp.ui.actions.asapp.EnvActions.getEnvUsername;

public class UiBaseTest extends BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(UiBaseTest.class);

    public static WebDriver initializerDriver(String moduleName, String testName) {

        WebDriver webDriver;

        try {

            webDriver = WebDriverManager.getWebDriverObject().getWebDriver();
            String browserName = WebDriverManager.getBrowserName();
            String executionUrl = WebDriverManager.getExecutionUrl();

            LOGGER.info("UI Testing in {} Env to Validate - {} - {} using Browser - {} Executing through - {}",
                    getEnv(), testName, moduleName, browserName, executionUrl);

        } catch (final Exception e) {

            LOGGER.error("Driver Initialization Failed with Error - " + e.getMessage() + "\n\n" + StringUtil.getStackTraceTill(e));
            throw e;

        }

        return webDriver;

    }

    @AfterEach
    public void closeDriver() {
        WebDriverManager.destroyDriver();
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

}
