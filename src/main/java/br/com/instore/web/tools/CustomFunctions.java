package br.com.instore.web.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class CustomFunctions {
    public static String dateFormat(Date date , String format) {
        if( null != date ) {
            return new SimpleDateFormat(format).format(date);
        } else if( null == date ) {
            return new SimpleDateFormat(format).format( new Date());
        }
        return "";
    }
    public static String dateCurrent(String format) {
        return new SimpleDateFormat(format).format( new Date());
    }
    public static String leftPad(String value , int size, String value2) {
        return StringUtils.leftPad(value, size, value2);
    }
}
