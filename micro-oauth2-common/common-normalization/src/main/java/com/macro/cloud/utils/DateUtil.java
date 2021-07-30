

package com.macro.cloud.utils;


import com.macro.cloud.common.DatePattern;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @
 */
@Slf4j
public class DateUtil {

    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.YYYY_MM_DD);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String getDateTimeString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.YYYY_MM_DD_HH_MM_SS);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static boolean inToday(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        String subDate = format.substring(0, 10);
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date parseBeginTime = null;
        Date parseEndTime = null;
        try {
            parseBeginTime = dateFormat.parse(beginTime);
            parseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        if (date.before(parseEndTime) && date.after(parseBeginTime)) {
            return true;
        }
        return false;
    }


}
