package com.zhiyu.pm_modbus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * 根据正则条件返回字符串
     */
    public static String match(String string, String pattern) {
        if (null != string) {
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(string);
            if (matcher.find())
                return matcher.group();
        }
        return null;
    }
}
