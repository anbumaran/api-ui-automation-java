package com.asapp.ui.pageutils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptActions {


    /**
     * Scroll Up or Down by the given distance
     *
     * @param webDriver        - WebDriver
     * @param distanceToScroll - distance To Scroll
     * @param isScrollDown     - Empty or True - Scroll Down, Otherwise - Scroll Up
     */
    public static void scrollBy(WebDriver webDriver, int distanceToScroll, boolean... isScrollDown) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        if (isScrollDown.length == 0 || isScrollDown[0]) {
            javascriptExecutor.executeScript("window.scrollBy(0," + distanceToScroll + ")", "");
        } else {
            javascriptExecutor.executeScript("window.scrollBy(0," + -distanceToScroll + ")", "");
        }
    }

    /**
     * Scroll Into WebElement
     *
     * @param webElement - Web Element
     * @param webDriver  - Web Driver
     */
    public static void scrollIntoWebElement(WebElement webElement, WebDriver webDriver) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
    }

}
