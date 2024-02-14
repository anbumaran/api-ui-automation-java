package com.asapp.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeUtil {

    private DateTimeUtil() {
        //Empty Constructor
    }

    public static String getCurrentDataTimeAsString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static String getReadableTime(double timeInSeconds) {
        int hours = (int) (timeInSeconds / 3600);
        double remainder = (timeInSeconds - hours * 3600) / 60;
        DecimalFormat decimalFormatMts = new DecimalFormat("00");
        int minutes = (int) remainder;
        String mts = decimalFormatMts.format(minutes);
        DecimalFormat decimalFormatSec = new DecimalFormat("00.0");
        String seconds = decimalFormatSec.format((remainder - minutes) * 60);
        return hours + ":" + mts + ":" + seconds;
    }

    public static double convertToEpoch(String timestamp) {
        LocalDateTime localDateTime = LocalDateTime.parse(timestamp);
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return (double) instant.toEpochMilli() / 1000;
    }

}
