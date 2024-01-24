package com.asapp.ui.actions.blaze;

import com.asapp.ui.pages.blaze.StorePage;
import com.asapp.ui.pageutils.RetryActions;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.asapp.common.Constants.FIFTEEN;
import static com.asapp.common.Constants.FIVE;


public class StoreActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    StorePage storePage;

    private static final Logger LOGGER = LogManager.getLogger(StoreActions.class);

    public StoreActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        storePage = new StorePage(driver);
    }

    public void filterCategories(String categories) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getSelectCategories(categories))).click();
        Waits.setImplicitWait(driver, 2);
        LOGGER.info("Category - {} - Applied", categories);
    }

    public void selectProduct(String product) {
        RetryActions.retryVisibility(storePage.getFirstProduct(), driver, FIVE);
        RetryActions.retryActionClickOrSendKeys(storePage.getSelectProduct(product), driver, FIVE);
        LOGGER.info("Product - {} - Selected", product);
    }

    public double getProdPriceInAddToCart() {
        RetryActions.retryVisibility(storePage.getProdPriceInAddToCart(), driver, FIVE);
        String price = webDriverWait.until(ExpectedConditions.visibilityOf(storePage.getProdPriceInAddToCart())).getText();
        LOGGER.info("Price of the Product is - {}", price);
        try {
            return NumberFormat.getCurrencyInstance(Locale.US).parse(price).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickAddToCart() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getAddToCart())).click();
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        LOGGER.info("Product is added to the Cart");
    }

}
