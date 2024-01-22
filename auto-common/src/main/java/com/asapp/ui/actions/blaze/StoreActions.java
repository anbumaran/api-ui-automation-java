package com.asapp.ui.actions.blaze;

import com.asapp.ui.pages.blaze.StorePage;
import com.asapp.ui.pageutils.RetryActions;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.asapp.common.Constants.FIFTEEN;


public class StoreActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    StorePage storePage;
    Alert alert;

    private static final Logger LOGGER = LogManager.getLogger(StoreActions.class);

    public StoreActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        storePage = new StorePage(driver);
    }

    public void filterCategories(String categories) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getSelectCategories(categories))).click();
    }

    public double getProductPrice(String product) {
        By productBy = By.xpath("//div[@id='tbodyid']//a[text()='" + product + "']/../../..//h5[text()=.]");
        RetryActions.retryVisibility(driver.findElement(productBy), driver, 10);

        try {
            return NumberFormat.getCurrencyInstance(Locale.US).parse(driver.findElement(productBy).getText()).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public void selectProduct(String product) {
        RetryActions.retryVisibility(storePage.getFirstProduct(), driver, 5);
        RetryActions.retryActionClickOrSendKeys(storePage.getSelectProduct(product), driver, 5);
    }

    public double getProdPriceInAddToCart() {
        return Double.parseDouble(
                webDriverWait.until(ExpectedConditions.visibilityOf(storePage.getProdPriceInAddToCart())).getText());
    }

    public void clickAddToCart() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getAddToCart())).click();
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

}
