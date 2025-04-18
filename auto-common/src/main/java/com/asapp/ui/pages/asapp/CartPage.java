package com.asapp.ui.pages.asapp;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CartPage {

    @FindBy(xpath = "//*[text()='ASAPP Pens']/following-sibling::td[1]")
    private WebElement pensQtyInCart;

    @FindBy(xpath = "//*[text()='ASAPP Pens']/..//span[text()='x']")
    private WebElement removePensFromCart;

    @FindBy(xpath = "//*[text()='ASAPP Stickers']/following-sibling::td[1]")
    private WebElement stickersQtyInCart;

    @FindBy(xpath = "//*[text()='ASAPP Stickers']/..//span[text()='x']")
    private WebElement removeStickersFromCart;

    @FindBy(xpath = "//*[text()='ASAPP Water Bottle']/following-sibling::td[1]")
    private WebElement waterBottleQtyInCart;

    @FindBy(xpath = "//*[text()='ASAPP Water Bottle']/..//span[text()='x']")
    private WebElement removeWaterBottleFromCart;

    @FindBy(xpath = "//span[text()='OH NO YOUR CART IS EMPTY']")
    private WebElement emptyCart;

    public CartPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

}
