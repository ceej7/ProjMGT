package com.achieveit.config;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Timestamp String2Timestamp(String str, String input_format) throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat(input_format);
        Date date = format.parse(str);
        //日期转时间戳（毫秒）
        long time=date.getTime();
        return new Timestamp(time);
    }

    public static Timestamp fromInputUTC2Timestamp(String str) throws ParseException{
        String[] startString= str.split("T");
        Timestamp startdate = DateUtil.String2Timestamp(startString[0]+" "+startString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
        if(startString[1].split("\\.")[1].equals("000Z")){
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(startdate.getTime());
            calendar.add(Calendar.HOUR,8);
            return new Timestamp(calendar.getTimeInMillis());
        }
        return startdate;
    }
}