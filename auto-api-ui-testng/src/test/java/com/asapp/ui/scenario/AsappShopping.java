package com.asapp.ui.scenario;

import com.asapp.TestConstants;
import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestUi;
import com.asapp.ui.actions.asapp.CartActions;
import com.asapp.ui.actions.asapp.LoginActions;
import com.asapp.ui.actions.asapp.MenuActions;
import com.asapp.ui.actions.asapp.StoreActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static com.asapp.TestConstants.EMPTY_CART;
import static com.asapp.TestConstants.FOUR;
import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;

@Test(testName = "AsappShopping")
public class AsappShopping extends BaseTestUi {

    ServiceObject serviceObject = new ServiceObject();

    private static WebDriver driver;
    private static final String TEST_NAME = "ASAPP Shopping";
    private static final String MODULE_NAME = "UI";
    private static int index = 0;


    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        ExtentReportsManager.startExtentTest(MODULE_NAME + " - " + TEST_NAME + " - " + ++index);
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @Test(groups = {INT, LIVE}, dataProvider = FOUR, dataProviderClass = TestConstants.class,
            retryAnalyzer = Retry.class)
    public void testAsappShopping(int testInput) {

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

}
