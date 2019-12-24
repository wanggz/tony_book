package com.yudao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class DateUtils {

    /**
     * 年月日时分秒  20169231043
     */
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    public static Date toDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

}
