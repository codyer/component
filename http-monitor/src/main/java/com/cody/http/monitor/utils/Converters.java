package com.cody.http.monitor.utils;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by xu.yi. on 2019/4/5.
 * Converters
 */
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}