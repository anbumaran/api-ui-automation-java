package com.asapp.ui.actions.blaze;

import com.asapp.ui.pages.blaze.CartPage;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.asapp.common.Constants.FIFTEEN;

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

    public double getProductPrice() {
        //div[@id='tbodyid']//a[text()=.]/../../..//h5[text()=.]
        return 0;
    }

}
