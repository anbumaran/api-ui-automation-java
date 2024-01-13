package com.asapp.ui.actions;

import com.asapp.ui.pages.CartPage;
import com.asapp.ui.pages.MenuPage;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.asapp.common.Constants.FIFTEEN;

public class CartActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    CartPage cartPage;
    MenuPage menuPage;

    private static final Logger LOGGER = LogManager.getLogger(CartActions.class);

    public CartActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        cartPage = new CartPage(driver);
        menuPage = new MenuPage(driver);
    }

    public int getQtyInCart(String product) {

        int qtyInCart;

        switch (product.toUpperCase()) {

            case "ASAPP PENS":
                webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getPensQtyInCart()));
                qtyInCart = Integer.parseInt(cartPage.getPensQtyInCart().getText());
                printQtyInCart(product, qtyInCart);
                return qtyInCart;

            case "ASAPP STICKERS":
                webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getStickersQtyInCart()));
                qtyInCart = Integer.parseInt(cartPage.getStickersQtyInCart().getText());
                printQtyInCart(product, qtyInCart);
                return qtyInCart;

            case "ASAPP WATER BOTTLE":
                webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getWaterBottleQtyInCart()));
                qtyInCart = Integer.parseInt(cartPage.getWaterBottleQtyInCart().getText());
                printQtyInCart(product, qtyInCart);
                return qtyInCart;

            default:
                throw new IllegalArgumentException("Get Quantity In Cart - Invalid Product : " + product);

        }

    }

    public void removeFromCart(String product) {

        switch (product.toUpperCase()) {

            case "ASAPP PENS":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(cartPage.getRemovePensFromCart()));
                cartPage.getRemovePensFromCart().click();
                printRemoveFromCart(product);
                break;

            case "ASAPP STICKERS":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(cartPage.getRemoveStickersFromCart()));
                cartPage.getRemoveStickersFromCart().click();
                printRemoveFromCart(product);
                break;

            case "ASAPP WATER BOTTLE":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(cartPage.getRemoveWaterBottleFromCart()));
                cartPage.getRemoveWaterBottleFromCart().click();
                printRemoveFromCart(product);
                break;

            default:
                throw new IllegalArgumentException("Invalid Product : " + product);

        }

    }

    public String getEmptyCartMessage() {
        return webDriverWait.until(ExpectedConditions.visibilityOf(cartPage.getEmptyCart())).getText();
    }

    private void printQtyInCart(String product, int quantity) {
        LOGGER.info("{} Product - {} Quantity available in Cart", product, quantity);
    }

    private void printRemoveFromCart(String product) {
        LOGGER.info("{} Product - Remove from Cart - Clicked", product);
    }


}
