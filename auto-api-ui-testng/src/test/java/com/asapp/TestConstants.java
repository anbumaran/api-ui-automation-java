package com.asapp;

import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Map;

public class TestConstants {

    public static final String TEST_DATA = "testData";
    public static final String REQUEST_FILE_PATH = "request/%s/%s/%s.json";
    public static final String RESPONSE_FILE_PATH = "response/%s/%s/%s.json";
    public static final String END_POINT_BASE_API_LIVE = "http://localhost:5000";
    public static final String END_POINT_BASE_API_INT = "http://localhost:5000";
    public static final String END_POINT_BASE_UI_LIVE = "http://localhost:3000";
    public static final String END_POINT_BASE_UI_INT = "http://localhost:3000";

    public static final String END_POINT_BASE_API_LIVE_DOCKER = "http://host.docker.internal:5000";
    public static final String END_POINT_BASE_API_INT_DOCKER = "http://host.docker.internal:5000";
    public static final String END_POINT_BASE_UI_LIVE_DOCKER = "http://host.docker.internal:3000";
    public static final String END_POINT_BASE_UI_INT_DOCKER = "http://host.docker.internal:3000";
    public static final String HOME = "index";
    public static final String CART = "cart";
    public static final String LIVE_PROFILE = "live";
    public static final String INT_PROFILE = "int";
    public static final String END_POINT_FILE = "endpoint.json";
    public static final String USER_NAME = "username";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_NOT_EXIST = "\"Product \\\"{}\\\" does not exist.\"";
    public static final String USER_NOT_LOGGED_IN = "\"User must be logged-in to perform this action\"";
    public static final String LOGIN_SUCCESS = "\"Login succeeded.\"";
    public static final String LOGOUT_SUCCESS = "\"Logout succeeded.\"";
    public static final String LOGIN_FAIL = "\"Invalid username/password combo.\"";
    public static final String EMPTY_CART = "OH NO YOUR CART IS EMPTY";
    public static final int MAX_RETRY = 2;
    public static final String INT = "int";
    public static final String LIVE = "live";
    public static final String ONE = "One";
    public static final String TWO = "Two";
    public static final String THREE = "Three";
    public static final String FOUR = "Four";
    public static final String VALID = "Valid";
    public static final String INVALID = "Invalid";
    public static final String POST = "Post";
    public static final String GET = "Get";
    private static final String BLAZE_HOME = "https://www.demoblaze.com/";
    private static final String BLAZE_URLS = BLAZE_HOME + "%s.html";
    public final static Map<String, String> BLAZE_PAGES;

    static {
        BLAZE_PAGES = new HashMap<>();
        BLAZE_PAGES.put(HOME, BLAZE_HOME);
        BLAZE_PAGES.put(CART, String.format(BLAZE_URLS, CART));
    }

    @DataProvider(name = ONE)
    public static Object[][] one() {
        return new Object[][]{{1}};
    }

    @DataProvider(name = TWO)
    public static Object[][] two() {
        return new Object[][]{{1}, {2}};
    }

    @DataProvider(name = THREE)
    public static Object[][] three() {
        return new Object[][]{{1}, {2}, {3}};
    }

    @DataProvider(name = FOUR)
    public static Object[][] four() {
        return new Object[][]{{1}, {2}, {3}, {4}};
    }

}
