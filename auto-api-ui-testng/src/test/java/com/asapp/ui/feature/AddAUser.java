package com.asapp.ui.feature;

import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestUi;
import com.asapp.ui.actions.asapp.LoginActions;
import com.asapp.ui.actions.asapp.MenuActions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(testName = "AddAUser")
public class AddAUser extends BaseTestUi {

    ServiceObject serviceObject = new ServiceObject();

    private static WebDriver driver;
    private static final String TEST_NAME = "Add To Cart";
    private static final String MODULE_NAME = "UI";

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @Test(groups = {"int", "live"}, retryAnalyzer = Retry.class)
    public void testAddAUserValid() {

        //Set Environment
        setEnv(serviceObject);

        //Go To Home Page
        gotoHomeURL(serviceObject, driver);

        //Register User
        LoginActions loginActions = new LoginActions(driver);
        loginActions.clickOpenRegister();
        loginActions.registerUser(serviceObject.username, serviceObject.password);

        //Login ASAPP Shopping Site
        loginActions.login(serviceObject.username, serviceObject.password);

        MenuActions menuActions = new MenuActions(driver);
        //Logout ASAPP Shopping Site
        menuActions.clickLogout();

    }

}
