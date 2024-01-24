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

    public void deleteProductFromCart(String productName) {
        WebElement productPrice = driver.findElement(
                By.xpath("//*[text()='Products']/..//tr/td[text()='" + productName + "']/../td[4]"));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(productPrice)).click();
    }

    public double getTotalPrice() {
        return Double.parseDouble(webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getTotal())).getText());
    }

    public boolean isCartHaveListOfProdOnly(List<String> products) {
        return getListOfProductsInCart().equals(products);
    }

    public List<String> getListOfProductsInCart() {
        Waits.setImplicitWait(driver,2);
        webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getProductNameList().get(0)));
        return cartPage.getProductNameList().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public double getTotalPriceFromListOfProductsInCart() {
        webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getProductPriceList().get(0)));
        return cartPage.getProductPriceList().stream().map(WebElement::getText)
                .mapToDouble(Double::parseDouble).sum();
    }

    public void clearCart() {
        cartPage.getProductDeleteList().forEach(i ->
                webDriverWait.until(ExpectedConditions.elementToBeClickable(i)).click());
    }

}
