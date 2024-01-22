package com.asapp.ui.pages.blaze;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class StorePage {

    @FindBy(css = "#tbodyid>div:nth-child(1)")
    private WebElement firstProduct;

    @FindBy(xpath = "//div[@id='tbodyid']//a[text()=.]")
    private List<WebElement> products;

    @FindBy(xpath = "//a[text()='CATEGORIES']/..//a[@id='itemc']")
    private List<WebElement> categories;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private WebElement addToCart;

    @FindBy(css = "#tbodyid>.price-container")
    private WebElement prodPriceInAddToCart;

    @FindBy(xpath = "//a[text()='Cart']")
    private WebElement cart;

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private WebElement home;

    public StorePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getSelectProduct(String productName) {
        return getProducts().stream().filter(i -> i.getText().equalsIgnoreCase(productName)).findFirst().get();
    }

    public WebElement getSelectCategories(String categoryName) {
        return getCategories().stream().filter(i -> i.getText().equalsIgnoreCase(categoryName)).findFirst().get();
    }

}
