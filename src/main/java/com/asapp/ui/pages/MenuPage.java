package com.asapp.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuPage {

    @FindBy(xpath = "//span[text()='Log Out']")
    private WebElement logout;

    @FindBy(xpath = "//span[text()='Cart']")
    private WebElement cart;

    @FindBy(xpath = "//span[text()='Store']")
    private WebElement store;

    public MenuPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getLogout() {
        return logout;
    }

    public WebElement getCart() {
        return cart;
    }

    public WebElement getStore() {
        return store;
    }

}
