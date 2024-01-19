package com.asapp.ui.actions;

import com.asapp.ui.pages.MenuPage;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.asapp.common.Constants.FIFTEEN;

public class MenuActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    MenuPage menuPage;

    private static final Logger LOGGER = LogManager.getLogger(LoginActions.class);

    public MenuActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        menuPage = new MenuPage(driver);
    }

    public void clickStore() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(menuPage.getStore())).click();
        LOGGER.info("Clicked - Store");
    }

    public void clickCart() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(menuPage.getCart())).click();
        LOGGER.info("Clicked - Cart");
    }

    public void clickLogout() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(menuPage.getLogout())).click();
        LOGGER.info("Clicked - Logout");
    }

}
