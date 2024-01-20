package com.asapp.ui.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

@Getter
@AllArgsConstructor
public class WebDriverObject {

    private WebDriver webDriver;
    private String browserName;
    private String executionUrl;
    private boolean isRemote;

}
