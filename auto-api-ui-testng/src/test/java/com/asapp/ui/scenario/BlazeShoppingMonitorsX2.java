package com.asapp.ui.scenario;

import com.asapp.TestConstants;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestBlazeShopping;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.TWO;

@Test(testName = "BlazeShoppingMonitorsX2")
public class BlazeShoppingMonitorsX2 extends BaseTestBlazeShopping {

    ServiceObject serviceObject = new ServiceObject();

    private static WebDriver driver;
    private static final String TEST_NAME = "Blaze Shopping Monitors X2";
    private static final String MODULE_NAME = "UI";


    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @Test(groups = {INT, LIVE}, dataProvider = TWO, dataProviderClass = TestConstants.class,
            retryAnalyzer = Retry.class)
    public void testBlazeShopping(int testInput) {

        testBlazeShopping(serviceObject, testInput, TEST_NAME, MODULE_NAME, driver);

    }

}
