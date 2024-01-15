package com.asapp.ui.actions;

import com.asapp.ui.pages.MenuPage;
import com.asapp.ui.pages.StorePage;
import com.asapp.ui.pageutils.RetryActions;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.stream.IntStream;

import static com.asapp.common.Constants.FIFTEEN;
import static com.asapp.common.Constants.FIVE;


public class StoreActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    StorePage storePage;
    MenuPage menuPage;

    private static final Logger LOGGER = LogManager.getLogger(StoreActions.class);

    public StoreActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        storePage = new StorePage(driver);
        menuPage = new MenuPage(driver);
    }

    public boolean addProductToCart(String product, int quantity) {

        boolean addProductToCart = false;

        switch (product.toUpperCase()) {

            case "ASAPP PENS":

                if (RetryActions.retryVisibility(storePage.getInStockPens(), driver, FIVE)) {
                    webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getQtyDropdownPens())).click();
                    addProductToCart = addToCart(product, quantity);
                } else {
                    printOutOfStock(product);
                }

                break;

            case "ASAPP STICKERS":

                if (RetryActions.retryVisibility(storePage.getInStockStickers(), driver, FIVE)) {
                    webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getQtyDropdownStickers())).click();
                    addProductToCart = addToCart(product, quantity);
                } else {
                    printOutOfStock(product);
                }

                break;

            case "ASAPP WATER BOTTLE":

                if (RetryActions.retryVisibility(storePage.getInStockWaterBottle(), driver, FIVE)) {
                    webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getQtyDropdownWaterBottle())).click();
                    addProductToCart = addToCart(product, quantity);
                } else {
                    printOutOfStock(product);
                }

                break;

            default:
                throw new IllegalArgumentException("Add Product to Cart - Invalid Product : " + product);

        }

        return addProductToCart;

    }

    private boolean addToCart(String product, int quantity) {

        boolean isValidSelect = getSelectQuantity(product, quantity);

        if (isValidSelect) {

            clickAddToCart(product);
            RetryActions.retryActionClickOrSendKeys(storePage.getCloseProductAddedToCart(), driver, FIVE);
            printAddToCart(product, quantity);
            return true;

        } else {

            RetryActions.retryActionClickOrSendKeys(menuPage.getStore(), driver, FIVE);
            return false;

        }

    }

    public boolean getSelectQuantity(String product, int quantity) {

        int availableQty = storePage.getQuantityList().size() - 1;

        if (availableQty >= quantity) {

            Actions actions = new Actions(driver);
            IntStream.range(0, quantity).forEach(i -> actions.sendKeys(Keys.ARROW_DOWN).perform());
            RetryActions.retryActionClickOrSendKeys(storePage.getSelectQuantity(quantity), driver, FIVE);
            printSelectProductQty(product, quantity);
            return true;

        } else {

            printOutOfQuantity(product, quantity, availableQty);
            return false;

        }

    }

    public void clickAddToCart(String product) {

        switch (product.toUpperCase()) {

            case "ASAPP PENS":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getAddToCartPens())).click();
                break;

            case "ASAPP STICKERS":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getAddToCartStickers())).click();
                break;

            case "ASAPP WATER BOTTLE":
                webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getAddToCartWaterBottle())).click();
                break;

            default:
                throw new IllegalArgumentException("Click Add Product to Cart - Invalid Product : " + product);

        }

    }

    public boolean isProductAddedToCartMsgDisplayed() {
        WebDriverWait webDriverWait = Waits.getExplicitWait(driver, FIVE);
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(storePage.getProductAddedToCart()));
            LOGGER.info("Product Added to Cart - Message is Visible");
            return true;
        } catch (WebDriverException e) {
            LOGGER.info("Product Added to Cart - Message is Not Visible");
            return false;
        }
    }

    private void printOutOfStock(String product) {
        LOGGER.info("{} - Product - Out of Stock", product);
    }

    private void printOutOfQuantity(String product, int quantity, int availableQty) {
        LOGGER.info("{} - Product & {} - Quantity - Not available, Available Quantity - {} only ",
                product, quantity, availableQty);
    }

    private void printSelectProductQty(String product, int quantity) {
        LOGGER.info("{} - Product & {} - Quantity - Selected ", product, quantity);
    }

    private void printAddToCart(String product, int quantity) {
        LOGGER.info("{} - Product & {} - Quantity - Added to Cart ", product, quantity);
    }

}
