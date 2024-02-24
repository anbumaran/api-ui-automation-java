package com.asapp.common.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    private DateTimeUtil() {
        //Empty Constructor
    }

    /**
     * Get Date as String with default format "yyyy/MM/dd hh:mm:ss"
     *
     * @param date - Date
     * @return - Date in String format
     */
    public static String getDateAsString(Date date) {
        return getDateAsString(date, "yyyy/MM/dd hh:mm:ss");
    }

    /**
     * Get Date as String with given input Format
     *
     * @param date           - Date
     * @param dateTimeFormat - Date Time Format
     * @return - Data in String format
     */
    public static String getDateAsString(Date date, String dateTimeFormat) {
        DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
        return dateFormat.format(date);
    }

    /**
     * Get UTC Zone - Date in default format "YYYY/MM/dd" After Adding the given Days from Today
     *
     * @param numberOfDays - Number of Days to be added
     * @return - Date after adding input number of Days
     */
    public static String getDateAfterAddingDaysFromToday(long numberOfDays) {
        return getDateAfterAddingDaysFromToday(numberOfDays, ChronoUnit.DAYS, "yyyy/MM/dd");
    }

    /**
     * Get UTC Zone - Date in default format "YYYY/MM/dd" After Adding the given Days from Today
     *
     * @param numberOfMonths - Number of Months to be added
     * @return - Date after adding input number of Days
     */
    public static String getDateAfterAddingMonthsFromToday(long numberOfMonths) {
        return getDateAfterAddingDaysFromToday(numberOfMonths, ChronoUnit.MONTHS, "yyyy/MM/dd");
    }

    /**
     * Get UTC Zone - Date in the required Format and After Adding the given Days from Today
     *
     * @param numberOf       - Number of Days or Months or Years to be added
     * @param dateTimeFormat - Date Time format
     * @return - Date after adding input number of Days and in the required format
     */
    public static String getDateAfterAddingDaysFromToday(long numberOf, TemporalUnit unit, String dateTimeFormat) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
        localDateTime = localDateTime.plus(numberOf, unit);
        Instant instant = localDateTime.atZone(ZoneOffset.ofHours(0)).toInstant();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(dateTimeFormat).withLocale(Locale.UK).withZone(ZoneId.of("UTC"));
        return dateTimeFormatter.format(instant);
    }

    /**
     * Get Current Date and Time in the Specified Format
     *
     * @param dateTimeFormat - Date Time format
     * @return - Current Date as String
     */
    public static String getCurrentDateAndTime(String dateTimeFormat) {
        return getDateAfterAddingDaysFromToday(0, ChronoUnit.DAYS, dateTimeFormat);
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
