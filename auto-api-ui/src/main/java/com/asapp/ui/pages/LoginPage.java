package com.asapp.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public WebElement getOpenRegister() {
        return openRegister;
    }

    public WebElement getRegisterUsername() {
        return registerUsername;
    }

    public WebElement getRegisterPassword() {
        return registerPassword;
    }

    public WebElement getRegister() {
        return register;
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getLogIn() {
        return logIn;
    }

}
