package edu.itechart.contactlist.util;

import org.apache.commons.lang3.StringUtils;

public class Validator {
    private static final String DIGITS = "0123456789";
    private static final String ALPHABET_RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String ALPHABET_EN = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPACE = " ";
    private static final String HYPHEN = "-";

    public static boolean isNumber(String value) {
        return containsChars(value, DIGITS);
    }

    public static boolean containsLettersHyphen(String value) {
        return containsChars(value, ALPHABET_EN + ALPHABET_RU + HYPHEN);
    }

    public static boolean containsLettersHyphenSpace(String value) {
        return containsChars(value, ALPHABET_EN + ALPHABET_RU + HYPHEN + SPACE);
    }

    public static boolean containsLettersHyphenSpaceDigits(String value) {
        return containsChars(value, ALPHABET_EN + ALPHABET_RU + HYPHEN + SPACE + DIGITS);
    }

    public static boolean checkMaxLength(String value, int max) {
        return StringUtils.length(value) < max;
    }

    public static boolean isGender(String value) {
        return StringUtils.containsAny(value,  "m", "f");
    }

    public static boolean isEmail(String value) {
        int atSymbolIndex = value.lastIndexOf('@');
        return atSymbolIndex > 0 && atSymbolIndex < (value.length() - 2);
    }

    public static boolean isWebsite(String value) {
        int dotSymbolIndex = value.lastIndexOf('.');
        return dotSymbolIndex > 0 && dotSymbolIndex < (value.length() - 2);
    }

    private static boolean containsChars(String value, String chars) {
        return StringUtils.containsOnly(StringUtils.lowerCase(value), chars);
    }
}
