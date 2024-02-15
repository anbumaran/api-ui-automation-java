package com.asapp.ui;

import com.asapp.common.dto.ProductsDTO;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.actions.blaze.CartActions;
import com.asapp.ui.actions.blaze.StoreActions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.asapp.TestConstants.CART;
import static com.asapp.TestConstants.HOME;

public class BaseTestBlazeShopping extends BaseTestUi {

    public void testBlazeShopping(ServiceObject serviceObject, int testInput, String testName, String moduleName,
                                  WebDriver driver) {
        //Read input Test Data
        setInputServiceAndModule(serviceObject, testInput, testName, moduleName);
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

        List<String> productsActual = cartActions.getListOfProductsInCart(productsExpected.size());

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
