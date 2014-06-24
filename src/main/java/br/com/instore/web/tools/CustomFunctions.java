package br.com.instore.web.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomFunctions {
    public static String dateFormat(Date date , String format) {
        if( null != date ) {
            return new SimpleDateFormat(format).format(date);
        }
        return "";
    }
}
