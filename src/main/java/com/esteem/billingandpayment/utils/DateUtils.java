package com.esteem.billingandpayment.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = 1 - (date.getDay() == 0 ? 7 : date.getDay());
        c.add(Calendar.DAY_OF_WEEK, days);
        return c.getTime();
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = 7 - (date.getDay() == 0 ? 7 : date.getDay());
        c.add(Calendar.DAY_OF_WEEK, days);
        return c.getTime();
    }

    public static Date getFirstDayOfTheMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = 1 - date.getDate();
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    public static Date getLastDayOfTheMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH) - date.getDate();
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    public static Date convertDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = format.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dt;
    }

    public static Date getPreviousDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}