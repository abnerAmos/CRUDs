package com.test.proposal.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String stringToLocalDate(String date) {

        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate originalDate = LocalDate.parse(date, originalFormatter);

        return originalDate.format(formatter);
    }
}
