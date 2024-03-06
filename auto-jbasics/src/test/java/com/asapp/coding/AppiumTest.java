
package com.asapp.coding;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumTest {

    @Test
    public void appiumTest() {

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .withLogFile(new File("logs/appium.log"))
                .withTimeout(Duration.ofSeconds(60)));

        service.start();

        System.out.println("Service URL :" + service.getUrl());


        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("emulator34");
        options.setApp(".//ApiDemos-debug.apk");

        URL url;
        try {
            url = new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        AndroidDriver androidDriver = new AndroidDriver(url, options);
        androidDriver.quit();

        service.stop();

    }


}