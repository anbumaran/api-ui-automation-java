package com.asapp.ui.feature;

import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTest;
import com.asapp.ui.actions.LoginActions;
import com.asapp.ui.actions.MenuActions;
import com.asapp.ui.driver.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

@ExtendWith(MockitoExtension.class)
public class AddAUser extends BaseTest {

    @Mock
    ServiceObject serviceObject;

    WebDriver driver;

    private static final String TEST_NAME = "Add To Cart";

    private static final String MODULE_NAME = "UI";

    @ParameterizedTest(name = "Test - " + TEST_NAME + " in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Tag("int")
    @Tag("live")
    public void testAddAUserValid(int testInput) {

        driver = new WebDriverManager().getWebDriver();

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

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }

}
