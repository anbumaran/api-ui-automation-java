package com.asapp.ui.scenario;

import com.asapp.common.model.ServiceObject;
import com.asapp.ui.BaseTestBlazeShopping;
import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import static io.opentelemetry.semconv.trace.attributes.SemanticAttributes.DbCassandraConsistencyLevelValues.TWO;

@ExtendWith(MockitoExtension.class)
public class BlazeShoppingPhonesX1 extends BaseTestBlazeShopping {

    @Mock
    ServiceObject serviceObject;

    private static WebDriver driver;
    private static final String TEST_NAME = "Blaze Shopping Phones X1";
    private static final String MODULE_NAME = "UI";


    @BeforeEach
    public void initializeDriver() {
        driver = initializerDriver(MODULE_NAME, TEST_NAME);
    }

    @ParameterizedRepeatedIfExceptionsTest(repeatedName = TWO,
            name = "Test - " + TEST_NAME + " in - " + MODULE_NAME + " Module - Positive scenario  {0}")
    @ValueSource(ints = {1, 2})
    @Tag("int")
    @Tag("live")
    public void testBlazeShopping(int testInput) {

        testBlazeShopping(serviceObject, testInput, TEST_NAME, MODULE_NAME, driver);

    }

}
