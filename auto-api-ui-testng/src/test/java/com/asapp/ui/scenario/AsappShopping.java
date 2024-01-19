package com.asapp.ui.scenario;

import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTest;
import com.asapp.ui.actions.CartActions;
import com.asapp.ui.actions.LoginActions;
import com.asapp.ui.actions.MenuActions;
import com.asapp.ui.actions.StoreActions;
import com.asapp.ui.driver.WebDriverManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;

import static com.asapp.TestConstants.EMPTY_CART;

@ExtendWith(MockitoExtension.class)
public class AsappShopping extends BaseTest {

    @Mock
    ServiceObject serviceObject;

    WebDriver driver;

    private static final String TEST_NAME = "ASAPP Shopping";

    private static final String MODULE_NAME = "UI";

    @ParameterizedTest(
            name = "Test - " + TEST_NAME + " in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1, 2, 3, 4})
    @Tag("int")
    @Tag("live")
    public void testAsappShopping(int testInput) {

        driver = new WebDriverManager().getWebDriver();

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

        //Hash Map to Store Add Product Success or not, will be used to verify Cart status
        HashMap<String, Boolean> addToProductStatus = new HashMap<>();

        //Add to Cart - input Product and Quantity
        StoreActions storeActions = new StoreActions(driver);
        productsDTOList.forEach(i -> addToProductStatus.put(i.getProductName(),
                storeActions.addProductToCart(i.getProductName(), i.getProductQty())));

        //Go to Cart
        MenuActions menuActions = new MenuActions(driver);
        menuActions.clickCart();

        //Assert Add to Cart from Store Page is available in Cart Page
        CartActions cartActions = new CartActions(driver);
        productsDTOList.forEach(i -> {
            if (addToProductStatus.get(i.getProductName())) {
                assertEqual(cartActions.getQtyInCart(i.getProductName()), i.getProductQty());
            }
        });

        //Remove All Products from Cart
        productsDTOList.forEach(i -> {
            if (addToProductStatus.get(i.getProductName())) {
                cartActions.removeFromCart(i.getProductName());
            }
        });

        //Assert Empty Cart
        assertEqual(cartActions.getEmptyCartMessage(), EMPTY_CART);

        //Logout ASAPP Shopping Site
        menuActions.clickLogout();

    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }

}
