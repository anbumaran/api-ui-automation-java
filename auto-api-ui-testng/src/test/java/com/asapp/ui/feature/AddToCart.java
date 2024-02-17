package com.asapp.ui.feature;

import com.asapp.TestConstants;
import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestUi;
import com.asapp.ui.actions.asapp.LoginActions;
import com.asapp.ui.actions.asapp.MenuActions;
import com.asapp.ui.actions.asapp.StoreActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.THREE;

@Test(testName = "AddToCart")
public class AddToCart extends BaseTestUi {


    ServiceObject serviceObject = new ServiceObject();

    private static WebDriver driver;
    private static final String TEST_NAME = "Add To Cart";
    private static final String MODULE_NAME = "UI";

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @Test(groups = {INT, LIVE}, dataProvider = THREE, dataProviderClass = TestConstants.class,
            retryAnalyzer = Retry.class)
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
