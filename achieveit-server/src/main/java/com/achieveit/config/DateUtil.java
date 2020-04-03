package com.achieveit.config;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Timestamp String2Timestamp(String str, String input_format) throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat(input_format);
        Date date = format.parse(str);
        //日期转时间戳（毫秒）
        long time=date.getTime();
        return new Timestamp(time);
    }
}