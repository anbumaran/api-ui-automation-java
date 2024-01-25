package com.asapp.ui.scenario;

import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestUi;
import com.asapp.ui.actions.blaze.CartActions;
import com.asapp.ui.actions.blaze.StoreActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static com.asapp.TestConstants.CART;
import static com.asapp.TestConstants.HOME;

@ExtendWith(MockitoExtension.class)
public class BlazeShopping extends BaseTestUi {

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

        List<Double> prices = new ArrayList<>();
        List<String> productsExpected = new ArrayList<>();


        //Select Filter & Product in Blaze Shopping Site
        StoreActions storeActions = new StoreActions(driver);
        CartActions cartActions = new CartActions(driver);

        productsDTOList.forEach(productsDTO -> {

            openBlazePage(driver, HOME);
            storeActions.filterCategories(productsDTO.getProductCategory());
            storeActions.selectProduct(productsDTO.getProductName());

            IntStream.range(0, productsDTO.getProductQty()).forEach(j -> {
                prices.add(storeActions.getProdPriceInAddToCart());
                productsExpected.add(productsDTO.getProductName());
                storeActions.clickAddToCart();
            });

        });

        //Go To Cart Page
        openBlazePage(driver, CART);

        List<String> productsActual = cartActions.getListOfProductsInCart();

        productsExpected.sort(String.CASE_INSENSITIVE_ORDER);
        productsActual.sort(String.CASE_INSENSITIVE_ORDER);

        double totalPrice = prices.stream().mapToDouble(d -> d).sum();

        //Assert Car wrt Products added in Shopping page
        assertEqual(productsActual, productsExpected);
        assertEqual(totalPrice, cartActions.getTotalPriceFromListOfProductsInCart());
        assertEqual(totalPrice, cartActions.getTotalPrice());

        //Clear Cart
        cartActions.clearCart();

    }

}
