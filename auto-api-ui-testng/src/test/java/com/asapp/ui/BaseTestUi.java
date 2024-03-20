package com.asapp.ui;

import com.asapp.BaseTest;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.model.ServiceObject;
import com.asapp.common.utils.StringUtil;
import com.asapp.ui.driver.WebDriverManager;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;

import static com.asapp.Constants.TEN;
import static com.asapp.TestConstants.BLAZE_PAGES;
import static com.asapp.TestConstants.END_POINT_BASE_UI_INT;
import static com.asapp.TestConstants.END_POINT_BASE_UI_LIVE;
import static com.asapp.TestConstants.INT_PROFILE;
import static com.asapp.TestConstants.LIVE_PROFILE;
import static com.asapp.ui.actions.asapp.EnvActions.getEnvPassword;
import static com.asapp.ui.actions.asapp.EnvActions.getEnvUsername;

public class BaseTestUi extends BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseTestUi.class);

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

    public void openBlazePage(WebDriver driver, String pageName) {

        String blazeURL = BLAZE_PAGES.get(pageName);
        driver.get(blazeURL);
        Waits.fluentWait(driver, ExpectedConditions.urlContains(blazeURL), TEN);
        LOGGER.info("Opened Blaze - Page URL: {}", blazeURL);

    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        WebDriverManager.destroyDriver();
        ExtentReportsManager.clearExtentTest();
    }

    public void gotoHomeURL(ServiceObject serviceObject, WebDriver driver) {

        if (serviceObject.env.equalsIgnoreCase(INT_PROFILE)) {
            //serviceObject.baseEndPoint = isDocker() ? END_POINT_BASE_UI_INT_DOCKER : END_POINT_BASE_UI_INT;
            serviceObject.baseEndPoint = END_POINT_BASE_UI_INT;
            serviceObject.username = getEnvUsername(INT_PROFILE);
            serviceObject.password = getEnvPassword(INT_PROFILE);
        } else if (serviceObject.env.equalsIgnoreCase(LIVE_PROFILE)) {
            //serviceObject.baseEndPoint = isDocker() ? END_POINT_BASE_UI_LIVE_DOCKER : END_POINT_BASE_UI_LIVE;
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
