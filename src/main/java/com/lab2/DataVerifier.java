package com.lab2;

import java.time.LocalDate;

public class DataVerifier {
    public static boolean isCorrectString(String s) {
        return s.matches("[A-Za-zА-Яа-я., ]+") && s.length() < 50;
    }

    public static boolean isCorrectInteger(String s) {
        return s.matches("[0-9]+") && s.length() < 9;
    }

    public static boolean isCorrectDates(LocalDate date1, LocalDate date2) {
        return date2.isAfter(date1) || date2.isEqual(date1);
    }
}
