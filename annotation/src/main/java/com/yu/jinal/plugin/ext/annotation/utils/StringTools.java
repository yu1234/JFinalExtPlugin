package com.yu.jinal.plugin.ext.annotation.utils;

/**
 * Created by igreentree on 2017/7/20 0020.
 */
public class StringTools {

    /**
     * 判断字符串是否为空
     */
    public static boolean isBlank(final String str) {
        return (str == null) || (str.trim().length() <= 0);
    }

    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串数组是否为空
     */
    public static boolean isNotBlank(final String... str) {
        if (str != null && str.length > 0) {
            boolean flag = true;
            for (String string : str) {
                if (null == string) {
                    flag = false;
                } else {
                    flag = true;
                    break;
                }
            }
            return flag;
        }
        return false;
    }

    public static boolean isBlank(final String... str) {
        return !isNotBlank(str);
    }



}
