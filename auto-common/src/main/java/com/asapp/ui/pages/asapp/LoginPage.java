package com.asapp.ui.pages.asapp;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPage {

    @FindBy(xpath = "//h2[text()='Please Login']/../../..//span[text()='Register']")
    WebElement openRegister;

    @FindBy(css = "#register-username")
    private WebElement registerUsername;

    @FindBy(css = "#register-password")
    private WebElement registerPassword;

    @FindBy(xpath = "//p[text()='Please insert Username and Password']/../..//span[text()='Register']")
    private WebElement register;

    @FindBy(css = "#username")
    private WebElement username;

    @FindBy(css = "#password")
    private WebElement password;

    @FindBy(xpath = "//span[text()='Log In']")
    private WebElement logIn;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

}
