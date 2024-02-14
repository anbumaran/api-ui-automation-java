package com.asapp.ui.feature;

import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestUi;
import com.asapp.ui.actions.asapp.LoginActions;
import com.asapp.ui.actions.asapp.MenuActions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

@ExtendWith(MockitoExtension.class)
public class AddAUser extends BaseTestUi {

    @Mock
    ServiceObject serviceObject;

    private static WebDriver driver;
    private static final String TEST_NAME = "Add To Cart";
    private static final String MODULE_NAME = "UI";

    @BeforeEach
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @ParameterizedTest(name = "Test - " + TEST_NAME + " in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Tag("int")
    @Tag("live")
    public void testAddAUserValid(int testInput) {

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
