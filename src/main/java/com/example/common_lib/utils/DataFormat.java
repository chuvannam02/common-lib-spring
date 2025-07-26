package com.example.common_lib.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.utils  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:00 PM
 */

public class DataFormat {

    public static String lower(String data) {
        return (data != null) ? data.toLowerCase() : null;
    }

    public static String trim(String data) {
        return (data != null) ? data.trim() : null;
    }

    public static List<Integer> toList(Integer[] data) {
        return data.length == 0 ? null : Arrays.asList(data);
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static String convertEmptyToNull(String str) {
        return isNullOrEmpty(str) ? null : str;
    }

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return (list == null || list.isEmpty());
    }

    public static <T> List<T> convertEmptyListToNull(List<T> list) {
        return isNullOrEmpty(list) ? null : list;
    }

    public static String convertNullToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    public static String safeGet(String value) {
        return value != null ? value : "";
    }

    public static String escapeLike(String input) {
        if (input == null) return null;
        return input
                .toLowerCase()
                .replace("\\", "\\\\")
                .replace("_", "\\_")
                .replace("%", "\\%");
    }
}
