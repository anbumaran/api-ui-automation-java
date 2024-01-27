package com.asapp.ui.actions.blaze;

import com.asapp.ui.pages.blaze.CartPage;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import static com.asapp.Constants.FIFTEEN;

public class CartActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    CartPage cartPage;

    private static final Logger LOGGER = LogManager.getLogger(CartActions.class);

    public CartActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        cartPage = new CartPage(driver);
    }

    public void removeProductFromCart(String productName) {
        WebElement productPrice = driver.findElement(
                By.xpath("//*[text()='Products']/..//tr/td[text()='" + productName + "']/../td[4]"));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(productPrice)).click();
        LOGGER.info("Product - {} - Removed from Cart", productName);
    }

    public double getTotalPrice() {
        double totalPrice = Double.parseDouble(webDriverWait.until(
                ExpectedConditions.visibilityOf(cartPage.getTotal())).getText());
        LOGGER.info("Cart - Total Price - {}", totalPrice);
        return totalPrice;

    }

    public List<String> getListOfProductsInCart(int expectedNoOfProd) {
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(cartPage.getProductNamesBy(), expectedNoOfProd));
        List<String> productsInCart =
                cartPage.getProductNameList().stream().map(WebElement::getText).collect(Collectors.toList());
        LOGGER.info("Products in Cart - {}", productsInCart);
        return productsInCart;
    }

    public double getTotalPriceFromListOfProductsInCart() {
        webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getProductPriceList().get(0)));
        double totalPrice = cartPage.getProductPriceList().stream().map(WebElement::getText)
                .mapToDouble(Double::parseDouble).sum();
        LOGGER.info("Cart - Calculated Total Price - {}", totalPrice);
        return totalPrice;
    }

    public void clearCart() {
        cartPage.getProductDeleteList().forEach(i ->
                webDriverWait.until(ExpectedConditions.elementToBeClickable(i)).click());
        LOGGER.info("All Products - Removed from Cart");
    }

}
