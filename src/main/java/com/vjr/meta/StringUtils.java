package com.vjr.meta;

public class StringUtils {

    public static boolean isBlank(String source) {
        return null == source || source.equals("");
    }
    
    public static boolean isNotBlank(String source) {
        return !isBlank(source);
    }
}

