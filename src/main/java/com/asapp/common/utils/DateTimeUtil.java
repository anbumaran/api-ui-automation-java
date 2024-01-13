package com.asapp.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    private DateTimeUtil() {
        //Empty Constructor
    }

    public static String getCurrentDataTimeAsString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static String getReadableTime(long timeInMilliSeconds) {
        double input = (double) timeInMilliSeconds / 1000;
        int hours = (int) (input / 3600);
        double remainder = (input - hours * 3600) / 60;
        DecimalFormat decimalFormatMts = new DecimalFormat("00");
        int minutes = (int) remainder;
        String mts = decimalFormatMts.format(minutes);
        DecimalFormat decimalFormatSec = new DecimalFormat("00.000");
        String seconds = decimalFormatSec.format((remainder - minutes) * 60);
        return hours + ":" + mts + ":" + seconds;
    }

}
