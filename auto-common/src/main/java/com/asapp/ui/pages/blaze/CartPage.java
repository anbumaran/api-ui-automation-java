package com.asapp.ui.pages.blaze;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class CartPage {

    @FindBy(xpath = "//*[text()='Products']/..//tr/td[2]")
    private List<WebElement> productNameList;

    @FindBy(xpath = "//*[text()='Products']/..//tr/td[3]")
    private List<WebElement> productPriceList;

    @FindBy(xpath = "//*[text()='Products']/..//tr/td[4]")
    private List<WebElement> productDeleteList;

    @FindBy(css = "#totalp")
    private WebElement total;

    public CartPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getProductInCart(String productName) {
        return getProductNameList().stream().filter(i -> i.getText().equalsIgnoreCase(productName)).findFirst().get();
    }

}
