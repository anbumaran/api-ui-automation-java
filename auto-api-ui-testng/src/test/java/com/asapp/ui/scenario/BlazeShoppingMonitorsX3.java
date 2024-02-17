package com.asapp.ui.scenario;

import com.asapp.TestConstants;
import com.asapp.common.extentreport.ExtentReportsManager;
import com.asapp.common.listener.Retry;
import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestBlazeShopping;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.asapp.TestConstants.INT;
import static com.asapp.TestConstants.LIVE;
import static com.asapp.TestConstants.TWO;

@Test(testName = "BlazeShoppingMonitorsX3")
public class BlazeShoppingMonitorsX3 extends BaseTestBlazeShopping {

    ServiceObject serviceObject = new ServiceObject();

    private static WebDriver driver;
    private static final String TEST_NAME = "Blaze Shopping Monitors X3";
    private static final String MODULE_NAME = "UI";
    private static int index = 0;


    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        ExtentReportsManager.startExtentTest(MODULE_NAME + " - " + TEST_NAME + " - " + ++index);
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @Test(groups = {INT, LIVE}, dataProvider = TWO, dataProviderClass = TestConstants.class,
            retryAnalyzer = Retry.class)
    public void testBlazeShopping(int testInput) {

        testBlazeShopping(serviceObject, testInput, TEST_NAME, MODULE_NAME, driver);

    }

}
