package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter DB_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatForDisplay(LocalDate date) {
        return date.format(DISPLAY_FORMATTER);
    }

    public static String formatForDB(LocalDate date) {
        return date.format(DB_FORMATTER);
    }

    public static LocalDate parseFromDB(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DB_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid DB date: " + dateStr);
            return null;
        }
    }

    public static LocalDate parseFromDisplay(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DISPLAY_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid display date: " + dateStr);
            return null;
        }
    }

    public static String todayAsDBString() {
        return formatForDB(LocalDate.now());
    }

    public static LocalDate today() {
        return LocalDate.now();
    }
}
