package com.asapp;

import java.util.HashMap;
import java.util.Map;

public class TestConstants {

    public static final String TEST_DATA = "testData";
    public static final String REQUEST_FILE_PATH = "request/%s/%s/%s.json";
    public static final String RESPONSE_FILE_PATH = "response/%s/%s/%s.json";
    public static final String END_POINT_BASE_UI_LIVE = "http://localhost:3000";
    public static final String END_POINT_BASE_UI_INT = "http://localhost:3000";
    public static final String EVN_PROPERTY = "testautomation.env";
    public static final int DEFAULT_PORT_NUMBER = 8090;
    public static final int DEFAULT_PORT_NUMBER_ASAPP = 5000;
    public static final String DEV_PROFILE = "dev";
    public static final String END_POINT_BASE = "http://localhost:%s";
    public static final int MAX_RETRY = 2;
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
    private static final String BLAZE_HOME = "https://www.demoblaze.com/";
    private static final String BLAZE_URLS = BLAZE_HOME+"%s.html";
    public final static Map<String, String> BLAZE_PAGES;
    static {
        BLAZE_PAGES = new HashMap<>();
        BLAZE_PAGES.put(HOME, BLAZE_HOME);
        BLAZE_PAGES.put(CART, String.format(BLAZE_URLS, CART));
    }






}
