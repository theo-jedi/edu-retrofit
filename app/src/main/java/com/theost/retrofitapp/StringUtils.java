package com.theost.retrofitapp;

public class StringUtils {

    public static String getLastWord(String text) {
        int index = text.lastIndexOf(" ");
        return (index != -1) ? text.substring(index) : text;
    }

    public static String trimLastWord(String text) {
        int index = text.lastIndexOf(" ");
        return (index != -1) ? text.substring(0, index) : "";
    }

}
