package com.asapp.ui.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.asapp.common.Constants.BROWSER_NAME;
import static com.asapp.common.Constants.EXECUTION_URL;

public class WebDriverManager {

    private static final ThreadLocal<WebDriverObject> webDriverThreadLocal = new ThreadLocal<>();
    private static final Set<WebDriver> webDriverStorage = ConcurrentHashMap.newKeySet();

    private static final Logger LOGGER = LogManager.getLogger(WebDriverManager.class);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(WebDriverManager::killAllDriverInstances));
    }

    public WebDriverObject getWebDriverManager() {

        ClassLoader classLoader = WebDriverManager.class.getClassLoader();

        String browserName = System.getProperty(BROWSER_NAME).toLowerCase();
        String executionUrl = System.getProperty(EXECUTION_URL);
        boolean isRemote = false;
        WebDriver webDriver = null;

        EdgeOptions edgeOptions;
        ChromeOptions chromeOptions;
        FirefoxOptions firefoxOptions;

        if (!isDriverExists()) {
            switch (browserName) {

                case "edge":

                    edgeOptions = new EdgeOptions();
                    edgeOptions.setAcceptInsecureCerts(true);
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    edgeOptions.addArguments("--windows-size=1920, 1080");
                    edgeOptions.addExtensions(
                            new File(Objects.requireNonNull(classLoader.getResource("modheader.crx")).getFile()));

                    if (executionUrl.startsWith("http")) {
                        try {
                            webDriver = new RemoteWebDriver(new URL(executionUrl), edgeOptions);
                            isRemote = true;
                        } catch (MalformedURLException e) {
                            LOGGER.info("Initializing Web Driver using - {} Failed with Error {} ", executionUrl, e);
                        }
                    } else {
                        webDriver = new EdgeDriver(edgeOptions);
                    }

                    break;

                case "chrome":

                    chromeOptions = new ChromeOptions();
                    chromeOptions.setAcceptInsecureCerts(true);
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--windows-size=1920, 1080");
                    chromeOptions.addExtensions(
                            new File(Objects.requireNonNull(classLoader.getResource("modheader.crx")).getFile()));

                    if (executionUrl.startsWith("http")) {
                        try {
                            webDriver = new RemoteWebDriver(new URL(executionUrl), chromeOptions);
                            isRemote = true;
                        } catch (MalformedURLException e) {
                            LOGGER.info("Initializing Web Driver using - {} Failed with Error {} ", executionUrl, e);
                        }
                    } else {
                        webDriver = new ChromeDriver(chromeOptions);
                    }

                    break;

                case "firefox":

                    firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setAcceptInsecureCerts(true);
                    firefoxOptions.addArguments("--remote-allow-origins=*");

                    if (executionUrl.startsWith("http")) {
                        try {
                            webDriver = new RemoteWebDriver(new URL(executionUrl), firefoxOptions);
                            isRemote = true;
                        } catch (MalformedURLException e) {
                            LOGGER.info("Initializing Web Driver using - {} Failed with Error {} ", executionUrl, e);
                        }
                    } else {
                        webDriver = new FirefoxDriver(firefoxOptions);
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

            WebDriverObject webDriverObject = new WebDriverObject(webDriver, browserName, executionUrl, isRemote);
            webDriverThreadLocal.set(webDriverObject);
            webDriverStorage.add(webDriverThreadLocal.get().getWebDriver());

        }

        return webDriverThreadLocal.get();

    }

    public static WebDriver getWebDriver() {
        return webDriverThreadLocal.get().getWebDriver();
    }

    public static String getBrowserName() {
        return webDriverThreadLocal.get().getBrowserName().toLowerCase();
    }

    public static String getExecutionUrl() {
        return webDriverThreadLocal.get().getExecutionUrl();
    }

    public static boolean isDriverExists() {
        return webDriverThreadLocal.get() != null;
    }

    public static void killAllDriverInstances() {
        for (WebDriver driver : webDriverStorage) {
            try {
                driver.quit();
                webDriverThreadLocal.remove();
            } catch (WebDriverException e) {
                LOGGER.warn("Unable to close driver instance, perhaps it might be closed already \n" + e.getMessage());
            }
        }
        webDriverStorage.clear();
    }

    public static void destroyDriver() {
        WebDriver webDriver = webDriverThreadLocal.get().getWebDriver();
        boolean isRemote = webDriverThreadLocal.get().isRemote();
        if (webDriver == null) {
            LOGGER.warn("WebDriver instance is null.");
        }
        try {
            if (webDriver != null) {
                if (isRemote) {
                    String webDriverSessionId = ((RemoteWebDriver) webDriver).getSessionId().toString();
                    LOGGER.info("Closing Driver with Session Id : {} ", webDriverSessionId);
                }
                webDriver.manage().deleteAllCookies();
                webDriver.quit();
            }
        } catch (WebDriverException e) {
            LOGGER.error("Error occurred during closing WebDriver : " + e);
        } finally {
            webDriverStorage.remove(webDriver);
            webDriverThreadLocal.remove();
            LOGGER.info("WebDriver is closed");
        }
    }

}
