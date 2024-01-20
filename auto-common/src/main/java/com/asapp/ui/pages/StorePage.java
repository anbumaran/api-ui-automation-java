package com.asapp.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class StorePage {

    @FindBy(xpath = "//*[text()='ASAPP Pens']/../../..//span[text()='Add to Cart']")
    private WebElement addToCartPens;

    @FindBy(xpath = "//*[text()='ASAPP Stickers']/../../..//span[text()='Add to Cart']")
    private WebElement addToCartStickers;

    @FindBy(xpath = "//*[text()='ASAPP Water Bottle']/../../..//span[text()='Add to Cart']")
    private WebElement addToCartWaterBottle;

    @FindBy(xpath = "//*[text()='ASAPP Pens']/../../..//span[text()='In Stock!']")
    private WebElement inStockPens;

    @FindBy(xpath = "//*[text()='ASAPP Stickers']/../../..//span[text()='In Stock!']")
    private WebElement inStockStickers;

    @FindBy(xpath = "//*[text()='ASAPP Water Bottle']/../../..//span[text()='In Stock!']")
    private WebElement inStockWaterBottle;

    @FindBy(xpath = "//*[text()='ASAPP Pens']/../../..//div[@role='button']")
    private WebElement qtyDropdownPens;

    @FindBy(xpath = "//*[text()='ASAPP Stickers']/../../..//div[@role='button']")
    private WebElement qtyDropdownStickers;

    @FindBy(xpath = "//*[text()='ASAPP Water Bottle']/../../..//div[@role='button']")
    private WebElement qtyDropdownWaterBottle;

    @FindBy(xpath = "//li[@role='option'][@data-value]")
    private List<WebElement> quantityList;

    @FindBy(xpath = "//span[text()='Close']")
    private WebElement closeProductAddedToCart;

    @FindBy(xpath = "//span[text()='Product Added to Cart']")
    private WebElement productAddedToCart;

    public StorePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getAddToCartPens() {
        return addToCartPens;
    }

    public WebElement getAddToCartStickers() {
        return addToCartStickers;
    }

    public WebElement getAddToCartWaterBottle() {
        return addToCartWaterBottle;
    }

    public WebElement getInStockPens() {
        return inStockPens;
    }

    public WebElement getInStockStickers() {
        return inStockStickers;
    }

    public WebElement getInStockWaterBottle() {
        return inStockWaterBottle;
    }

    public WebElement getQtyDropdownPens() {
        return qtyDropdownPens;
    }

    public WebElement getQtyDropdownStickers() {
        return qtyDropdownStickers;
    }

    public WebElement getQtyDropdownWaterBottle() {
        return qtyDropdownWaterBottle;
    }

    public List<WebElement> getQuantityList() {
        return quantityList;
    }

    public WebElement getCloseProductAddedToCart() {
        return closeProductAddedToCart;
    }

    public WebElement getProductAddedToCart() {
        return productAddedToCart;
    }

    public WebElement getSelectQuantity(int quantity) {
        return getQuantityList().stream().filter(i -> i.getAttribute("data-value")
                .equalsIgnoreCase(String.valueOf(quantity))).findFirst().get();
    }

}
