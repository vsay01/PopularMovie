package com.udacity.popularmovie.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formattedDateFromString(String inputDate){

        Date parsed;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat("MMM, dd yyyy", java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.d(DateUtils.class.getName(), "ParseException - dateFormat");
        }

        return outputDate;
    }
}