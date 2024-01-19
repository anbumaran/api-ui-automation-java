package com.asapp.ui.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.asapp.common.Constants.BROWSER_NAME;
import static com.asapp.common.Constants.EXECUTION_URL;

public class MyWebDriver {

    WebDriver webDriver;
    String browserName;
    String executionUrl;

    private static final Logger LOGGER = LogManager.getLogger(MyWebDriver.class);

    public MyWebDriver() {

        this.browserName = System.getProperty(BROWSER_NAME).toLowerCase();
        this.executionUrl = System.getProperty(EXECUTION_URL);

        switch (browserName) {
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setAcceptInsecureCerts(true);
                edgeOptions.addArguments("--remote-allow-origins=*");

                if (executionUrl.startsWith("http")) {
                    try {
                        this.webDriver = new RemoteWebDriver(new URL(executionUrl), edgeOptions);

                    } catch (MalformedURLException e) {
                        LOGGER.info("Initializing Web Driver using - {} Failed with Error {} ", executionUrl, e);
                    }
                } else {
                    this.webDriver = new EdgeDriver(edgeOptions);
                }
                break;

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(true);
                chromeOptions.addArguments("--remote-allow-origins=*");

                if (executionUrl.startsWith("http")) {
                    try {
                        this.webDriver = new RemoteWebDriver(new URL(executionUrl), chromeOptions);

                    } catch (MalformedURLException e) {
                        LOGGER.info("Initializing Web Driver using - {} Failed with Error {} ", executionUrl, e);
                    }
                } else {
                    this.webDriver = new ChromeDriver(chromeOptions);
                }
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true);
                firefoxOptions.addArguments("--remote-allow-origins=*");

                if (executionUrl.startsWith("http")) {
                    try {
                        this.webDriver = new RemoteWebDriver(new URL(executionUrl), firefoxOptions);

                    } catch (MalformedURLException e) {
                        LOGGER.info("Initializing Web Driver using - {} Failed with Error {} ", executionUrl, e);
                    }
                } else {
                    this.webDriver = new FirefoxDriver(firefoxOptions);
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported Browser Type");
        }

        if (executionUrl.startsWith("http")) {
            LOGGER.info("Initialized {} - WebDriver with Server URL - {} ", browserName, executionUrl);
        } else {
            LOGGER.info("Initialized {} - WebDriver Locally ", browserName);
        }

        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().maximize();

    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

}
