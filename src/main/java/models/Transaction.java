package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaction {
    private int id;
    private LocalDate date;
    private String description;
    private double amount;
    private String category;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Transaction(int id, String date, String description, double amount, String category) {
        this.id = id;
        setDate(date);  // Use setter to validate and set LocalDate
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    // Getters and setters
    public int getId() { return id; }

    // Return date as String in "YYYY-MM-DD" format
    public String getDate() {
        return date.format(DATE_FORMATTER);
    }

    // Set date from String, validate format and value
    public void setDate(String dateStr) {
        try {
            this.date = LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
}
