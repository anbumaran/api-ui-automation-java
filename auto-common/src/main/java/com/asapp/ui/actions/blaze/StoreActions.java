package com.asapp.ui.actions.blaze;

import com.asapp.ui.pages.blaze.StorePage;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        WebElement productPrice= driver.findElement(
                By.xpath("//div[@id='tbodyid']//a[text()='"+product+"']/../../..//h5[text()=.]"));
        return Double.parseDouble(webDriverWait.until(ExpectedConditions.visibilityOf(productPrice)).getText());
    }

    public void selectProduct(String product) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getSelectProduct(product))).click();
    }

    public double getProdPriceInAddToCart(){
        return Double.parseDouble(
                webDriverWait.until(ExpectedConditions.visibilityOf(storePage.getProdPriceInAddToCart())).getText());
    }

    public void clickAddToCart() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(storePage.getAddToCart())).click();
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

}
