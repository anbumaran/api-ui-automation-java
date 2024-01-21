package com.asapp.ui.pages.asapp;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
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

}
