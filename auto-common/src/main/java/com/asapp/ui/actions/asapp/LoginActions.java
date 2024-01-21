package com.asapp.ui.actions.asapp;

import com.asapp.ui.pages.asapp.LoginPage;
import com.asapp.ui.pageutils.RetryActions;
import com.asapp.ui.pageutils.Waits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.asapp.common.Constants.FIFTEEN;
import static com.asapp.common.Constants.FIVE;

public class LoginActions {

    WebDriver driver;
    WebDriverWait webDriverWait;
    LoginPage loginPage;

    private static final Logger LOGGER = LogManager.getLogger(LoginActions.class);

    public LoginActions(WebDriver driver) {
        this.driver = driver;
        webDriverWait = Waits.getExplicitWait(driver, FIFTEEN);
        loginPage = new LoginPage(driver);
    }

    public void clickOpenRegister() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginPage.getOpenRegister()));
        RetryActions.retryActionClickOrSendKeys(loginPage.getOpenRegister(), driver, FIVE);
    }

    public void registerUsername(String username) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginPage.getRegisterUsername()));
        loginPage.getRegisterUsername().click();
        RetryActions.retryActionClickOrSendKeys(loginPage.getRegisterUsername(), driver, FIVE, username);
        LOGGER.info("Sent Key - Register Username : {}", username);
    }

    public void registerPassword(String password) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginPage.getRegisterPassword()));
        loginPage.getRegisterPassword().click();
        loginPage.getRegisterPassword().sendKeys(password);
        LOGGER.info("Sent Key - Register Password");
    }

    public void clickRegister() {
        RetryActions.retryActionClickOrSendKeys(loginPage.getRegister(), driver, FIVE);
        webDriverWait.until(ExpectedConditions.invisibilityOf(loginPage.getRegister()));
    }

    public void sendUsername(String username) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginPage.getUsername()));
        loginPage.getUsername().click();
        RetryActions.retryActionClickOrSendKeys(loginPage.getUsername(), driver, FIVE, username);
        LOGGER.info("Sent Key - Username : {}", username);
    }

    public void sendPassword(String password) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginPage.getPassword()));
        loginPage.getPassword().click();
        loginPage.getPassword().sendKeys(password);
        LOGGER.info("Sent Key - Password");
    }

    public void clickLogin() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginPage.getLogIn()));
        loginPage.getLogIn().click();
    }

    public void registerUser(String username, String password) {
        clickOpenRegister();
        registerUsername(username);
        registerPassword(password);
        clickRegister();
        LOGGER.info("Username and Password Registered for user : {} ", username);
    }

    public void login(String username, String password) {
        sendUsername(username);
        sendPassword(password);
        clickLogin();
        LOGGER.info("Login Clicked for user : {} ", username);
    }

}
