package com.asapp.common.validations;

import com.asapp.common.utils.DateTimeUtil;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

import static com.asapp.common.utils.StringUtil.printObject;

public class Assertions extends org.assertj.core.api.Assertions {

    private static final Logger LOGGER = LogManager.getLogger(Assertions.class);
    private static final List<Integer> API_SUCCESS_CODE = List.of(200, 201);


    public void assertEqual(Object actualObject, Object expectedObject) {

        LOGGER.info("Actual Object : {}", printObject(actualObject));
        LOGGER.info("Expected Object : {}", printObject(expectedObject));

        assertThat(actualObject).isEqualTo(expectedObject);
        LOGGER.info("Actual Object matches Expected Object");

    }

    public void assertNotEqual(Object actualObject, Object expectedObject) {

        LOGGER.info("Actual Object : {}", printObject(actualObject));
        LOGGER.info("Expected Object : {}", printObject(expectedObject));

        assertThat(actualObject).isNotEqualTo(expectedObject);
        LOGGER.info("Actual Object Not matches Expected Object");

    }

    public void assertNotEmpty(Object object) {
        String objectString = object.toString();
        assertThat(objectString).isNotEmpty();
        LOGGER.info("Actual - Object matches Expected - Non Empty Object - {}", objectString);
    }

    public void assertStatusCodeSuccess(Response response) {
        LOGGER.info("Actual - Response Code - {}", response.getStatusCode());
        assertThat(API_SUCCESS_CODE.contains(response.getStatusCode())).isTrue();
        LOGGER.info("Actual - Response Code matches Expected - Success Response Code");
    }

    public void assertStatusCodeFail(Response response) {
        LOGGER.info("Actual - Response Code - {}", response.getStatusCode());
        assertThat(API_SUCCESS_CODE.contains(response.getStatusCode())).isFalse();
        LOGGER.info("Actual - Response Code - matches Expected - Fail Response Code");
    }

    public void assertEmptyBody(Response response) {
        assertThat(response.getBody().asString()).isEmpty();
        LOGGER.info("Actual - Response Body matches Expected - Empty Response Body");
    }

    public void assertEmptyCurlyBracesBody(Response response) {
        assertThat(response.getBody().asString()).isEqualTo("{}");
        LOGGER.info("Actual - Response Body matches Expected - Empty Curly Braces");
    }

    public void assertEmptySquareBracesBody(Response response) {
        assertThat(response.getBody().asString()).isEqualTo("[]");
        LOGGER.info("Actual - Response Body matches Expected - Empty Square Braces");
    }

    public void assertAbsValueIsGreaterThanZero(double input) {
        LOGGER.info("Actual Input - {}", input);
        double absInputValue = Math.abs(input);
        assertThat(absInputValue).isGreaterThan(0.0);
        LOGGER.info("Absolute Value of Input - {} is Greater Than Zero", absInputValue);
    }

    public void assertDateTimeWithinAllowedLag(Date date, long allowedLagInMinutes) {
        long allowedTimeLagEpoch = date.getTime() - (allowedLagInMinutes * 60 * 1000);
        long inputEpoch = date.getTime();
        assertGreaterThan(inputEpoch, allowedTimeLagEpoch);
        LOGGER.info("Input Date and Time - {} is within allowed Lag - {} minutes",
                DateTimeUtil.getDateAsString(date), allowedLagInMinutes);
    }

    public void assertGreaterThan(long higherValue, long lowerValue) {
        assertThat(higherValue).isGreaterThan(lowerValue);
        LOGGER.info("Input Higher Value - {} is Greater Than Input Lower Value - {}",
                higherValue, lowerValue);
    }

    public void assertGreaterThan(double higherValue, double lowerValue) {
        assertThat(higherValue).isGreaterThan(lowerValue);
        LOGGER.info("Input Higher Value - {} is Greater Than Input Lower Value - {}",
                higherValue, lowerValue);
    }

    public void assertGreaterOrEqualTo(long higherValue, long lowerValue) {
        assertThat(higherValue).isGreaterThanOrEqualTo(lowerValue);
        LOGGER.info("Input Higher Value - {} is Greater Than Or Equal to Input Lower Value - {}",
                higherValue, lowerValue);
    }

    public void assertGreaterOrEqualTo(double higherValue, double lowerValue) {
        assertThat(higherValue).isGreaterThanOrEqualTo(lowerValue);
        LOGGER.info("Input Higher Value - {} is Greater Than Or Equal to Input Lower Value - {}",
                higherValue, lowerValue);
    }

    public void assertStringStartsWith(String input, String startsWith) {
        assertThat(input.startsWith(startsWith)).isTrue();
        LOGGER.info("Input String - {} matches the Expected - Starts with String - {}", input, startsWith);
    }

    public void assertStringLength(String input, int expectedLength) {
        assertThat(input.length()).isEqualTo(expectedLength);
        LOGGER.info("Input String - {} matches the Expected - String Length - {}", input, expectedLength);
    }

    public void assertSubStringCountAndNonEmpty(String input, String splitter, int expectedNoOfSubStrings) {
        String[] strings = input.split(splitter);
        assertThat(strings.length).isEqualTo(expectedNoOfSubStrings);
        for (String string : strings) {
            assertNotEmpty(string);
        }
        LOGGER.info("Input String - {} matches the Expected - Number of Sub Strings - {} for the Splitter - '{}' and they are Not Empty", input, expectedNoOfSubStrings, splitter);
    }

    public void assertUUID(String input) {
        assertSubStringCountAndNonEmpty(input, "-", 5);
    }

    public void assertSubStringEqual(String input, String splitter, int index, String expectedSubString) {
        String[] strings = input.split(splitter);
        assertEqual(strings[index], expectedSubString);
        LOGGER.info("Input String - {} matches the Expected - Sub Strings - {} at the index - {} for the Splitter - '{}' ",
                input, expectedSubString, index, splitter);
    }

    public static boolean isPojo(Object object) {
        boolean pojo;
        try {
            pojo = !(object instanceof String) && !object.getClass().isArray() && !object.getClass().isPrimitive();
        } catch (Exception e) {
            pojo = false;
        }
        return pojo;
    }

}
