

package com.asapp.coding;

import org.apache.commons.io.FileUtils;
import org.jboss.aerogear.security.otp.Totp;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.print.PrintOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

public class Selenium4Feat {

    @Test
    public void testGeoLocation() {

        EdgeDriver driver = new EdgeDriver();

        Map<String, Object> coordinates = Map.of(
                "latitude", 40.730610,
                "longitude", -73.935242,
                "accuracy", 1
        );

        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
        driver.navigate().refresh();
        driver.get("https://www.gps-coordinates.net/my-location");
        //driver.navigate().to("https://oldnavy.gap.com/stores");

    }

    @Test
    public void testTimeZone() {

        EdgeDriver driver = new EdgeDriver();
        Map<String, Object> timezone = Map.of("timezoneId", "Asia/Singapore");
        driver.executeCdpCommand("Emulation.setTimezoneOverride", timezone);
        driver.navigate().refresh();
        driver.get("https://whatismytimezone.com/");

    }

    @Test
    public void testPrint() {

        WebDriver driver = new EdgeDriver();

        driver.get("https://www.selenium.dev/");
        driver.manage().window().maximize();

        PrintsPage printsPage = (PrintsPage) driver;
        PrintOptions printOptions = new PrintOptions();
        printOptions.setPageRanges("1-2");
        Pdf pdf = printsPage.print(printOptions);

        try {
            FileUtils.copyFile(OutputType.FILE.convertFromBase64Png(pdf.getContent()), new File("./build/outputs/myPdf.pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver.close();

    }

    @Test
    public void getKeys() {

        String otpKeyStr = "anbud"; // <- this 2FA secret key.

        String twoFactorCode = new Totp(otpKeyStr).now();

        System.out.print("OTP : " + twoFactorCode);

    }

    @Test
    public void testScrollRight() {
        WebDriver driver = new EdgeDriver();
        String url = Objects.requireNonNull(getClass().getClassLoader().getResource("right.html")).toString();
        driver.get(url);
        driver.manage().window().maximize();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement webElement = driver.findElement(By.cssSelector("input.scroll-textbox"));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
        //javascriptExecutor.executeScript("window.scrollBy(3500, 0)");
        webElement.sendKeys("Test My UI");
    }


    @Test
    public void testAsyncAPIAwait() {

        await().atMost(30, TimeUnit.SECONDS)
                .pollInterval(2, TimeUnit.SECONDS)
                .until(() ->
                        given()
                                .when()
                                .get("/check-status-endpoint")
                                .then()
                                .extract()
                                .path("status").equals("completed")
                );

    }


    @Test
    public void createTestFile() {
        try {
            File file = new File("./build/outputs/testFile.txt");
            FileWriter writer = new FileWriter(file);
            writer.write("Hello, World!");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTestFile() {
        File file = new File("./build/outputs/");
        try {
            FileUtils.cleanDirectory(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}