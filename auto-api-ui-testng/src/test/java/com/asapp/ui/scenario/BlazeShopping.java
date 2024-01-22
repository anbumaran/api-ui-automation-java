package com.asapp.ui.scenario;

import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.UiBaseTest;
import com.asapp.ui.actions.blaze.CartActions;
import com.asapp.ui.actions.blaze.StoreActions;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.asapp.TestConstants.BLAZE_PAGES;
import static com.asapp.TestConstants.CART;
import static com.asapp.TestConstants.HOME;

@ExtendWith(MockitoExtension.class)
public class BlazeShopping extends UiBaseTest {

    @Mock
    ServiceObject serviceObject;

    private static WebDriver driver;
    private static final String TEST_NAME = "Blaze Shopping";
    private static final String MODULE_NAME = "UI";

    @BeforeEach
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @ParameterizedTest(
            name = "Test - " + TEST_NAME + " in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1})
    @Tag("int")
    @Tag("live")
    public void testBlazeShopping(int testInput) {

        //Read input Test Data
        setInputServiceAndModule(serviceObject, testInput, TEST_NAME, MODULE_NAME);
        List<ProductsDTO> productsDTOList = new ObjectMapper().convertValue(serviceObject.requestData,
                new TypeReference<>() {
                });

        //Go To Home Page
        driver.get(BLAZE_PAGES.get(HOME));

        List<Double> prices = new ArrayList<>();
        List<String> products = new ArrayList<>();

        //Select Filter & Product in Blaze Shopping Site
        StoreActions storeActions = new StoreActions(driver);
        CartActions cartActions = new CartActions(driver);

        productsDTOList.forEach(i -> {

            storeActions.filterCategories(i.getProductCategory());
            storeActions.selectProduct(i.getProductName());

            IntStream.range(0, i.getProductQty()).forEach(j -> {
                prices.add(storeActions.getProductPrice(i.getProductName()));
                products.add(i.getProductName());
                storeActions.clickAddToCart();
            });

        });

        //Go To Cart Page
        driver.get(BLAZE_PAGES.get(CART));

        //Assert Car wrt Products added in Shopping page
        assertEqual(products, cartActions.getListOfProductsInCart());
        assertEqual(prices, cartActions.getTotalPriceFromListOfProductsInCart());
        assertEqual(prices, cartActions.getTotalPrice());

        cartActions.clearCart();

    }

}
