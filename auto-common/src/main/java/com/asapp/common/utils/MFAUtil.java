package com.asapp.common.utils;

import org.jboss.aerogear.security.otp.Totp;

public class MFAUtil {

    private MFAUtil() {
        //Empty Constructor
    }

    public static String getTimeBasedOTP(String key) {
        return new Totp(key).now();
    }

}