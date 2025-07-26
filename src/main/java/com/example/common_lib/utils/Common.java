package com.example.common_lib.utils;

import com.example.common_lib.error.ConstraintException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.utils  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:01 PM
 */

public class Common {
    public static final String LOAIHINHCODE = "LOAIHINHDONVI";
    public static final String PHANCAPCODE = "PHANCAPDONVI";
    public static final String LINHVUCCODE = "LINHVUCHOATDONG";
    public static final String APPCODE = "APP";
    public static final String GIOITINHCODE = "GIOITINH";
    public static final String DANTOCCODE = "DANTOC";
    public static final String TONGIAOCODE = "TONGIAO";
    public static final String TRINHDOHOCVANCODE = "TRINHDOHOCVAN";
    public static final Set<String> PASSWORD_BLACKLIST = new HashSet<>();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@$!%*?&";
    private static final String ALL_CHARACTERS = UPPER + LOWER + DIGITS + SPECIAL;

    public static final String CONSTANT_PATH_SCHEMAS = "__mdpstorage/schemas";

    static {
        Collections.addAll(PASSWORD_BLACKLIST, "vnpt", "012", "123", "234", "345", "456", "567", "678", "789", "890");
    }

    public static void validatePassword(String password) throws ConstraintException {
        if (password != null && PASSWORD_BLACKLIST.stream().anyMatch(password::contains)) {
            throw new ConstraintException("Vui lòng không sử dụng các mật khẩu dễ đoán!");
        }
    }

    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        return shuffleString(password.toString(), random);
    }

    private static String shuffleString(String input, SecureRandom random) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        return new String(array);
    }

    public static long parseDDMMYYYYToLong(String date) throws NumberFormatException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static void main(String[] args) {
        String password = generateRandomPassword(12);
        System.out.println("Generated Password: " + password);
    }
}
