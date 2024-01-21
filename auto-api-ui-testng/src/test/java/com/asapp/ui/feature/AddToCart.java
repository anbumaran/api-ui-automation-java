package com.asapp.ui.feature;

import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.UiBaseTest;
import com.asapp.ui.actions.asapp.LoginActions;
import com.asapp.ui.actions.asapp.MenuActions;
import com.asapp.ui.actions.asapp.StoreActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AddToCart extends UiBaseTest {

    @Mock
    ServiceObject serviceObject;

    private static WebDriver driver;
    private static final String TEST_NAME = "Add To Cart";
    private static final String MODULE_NAME = "UI";


    @BeforeEach
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @ParameterizedTest(
            name = "Test - " + TEST_NAME + " in - " + MODULE_NAME + " Module - Negative scenario  {0}")
    @ValueSource(ints = {1, 2, 3})
    @Tag("int")
    @Tag("live")
    public void testAddToCartInValid(int testInput) {

        //Read input Test Data
        setInputServiceAndModule(serviceObject, testInput, TEST_NAME, MODULE_NAME);
        List<ProductsDTO> productsDTOList = new ObjectMapper().convertValue(serviceObject.requestData,
                new TypeReference<>() {
                });

        //Go To Home Page
        gotoHomeURL(serviceObject, driver);

        //Login ASAPP Shopping Site
        LoginActions loginActions = new LoginActions(driver);
        loginActions.login(serviceObject.username, serviceObject.password);

        //Click Add to Product without selecting the Quantity
        StoreActions storeActions = new StoreActions(driver);
        productsDTOList.forEach(i -> storeActions.clickAddToCart(i.getProductName()));

        //Assert Product Added to Cart - Message is Not Visible
        assertEqual(storeActions.isProductAddedToCartMsgDisplayed(), false);

        MenuActions menuActions = new MenuActions(driver);
        //Logout ASAPP Shopping Site
        menuActions.clickLogout();

    }

}
