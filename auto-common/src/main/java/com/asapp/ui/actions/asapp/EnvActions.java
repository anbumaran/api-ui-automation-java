package com.asapp.ui.actions.asapp;

import static com.asapp.common.Constants.PASSWORD;
import static com.asapp.common.Constants.USERNAME;

public class EnvActions {

    public static String getEnvUsername(String env) {
        return System.getProperty(USERNAME + "." + env.toLowerCase());
    }

    public static String getEnvPassword(String env) {
        return System.getProperty(PASSWORD + "." + env.toLowerCase());
    }

}
