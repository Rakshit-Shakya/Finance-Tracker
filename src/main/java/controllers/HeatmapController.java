package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Transaction;
import utils.TransactionService;

import java.time.LocalDate;
import java.util.*;

public class HeatmapController {

    @FXML private GridPane calendarGrid;

    @FXML
    public void initialize() {
        Map<String, Double> expenseByDate = new HashMap<>();
        for (Transaction t : TransactionService.getAllTransactions()) {
            expenseByDate.put(t.getDate(),
                    expenseByDate.getOrDefault(t.getDate(), 0.0) + t.getAmount());
        }

        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);

        for (int i = 0; i < 31; i++) {
            LocalDate day = firstDay.plusDays(i);
            if (day.getMonthValue() != today.getMonthValue()) break;

            String key = day.toString();
            double amount = expenseByDate.getOrDefault(key, 0.0);

            Rectangle rect = new Rectangle(50, 50);
            rect.setArcWidth(12);
            rect.setArcHeight(12);
            rect.setFill(getColor(amount));
            rect.setStroke(Color.web("#333333"));
            rect.setStrokeWidth(1);

            Label dayLabel = new Label(String.valueOf(day.getDayOfMonth()));
            dayLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 13px;");

            VBox box = new VBox(dayLabel, rect);
            box.setSpacing(5);
            box.setStyle("-fx-alignment: center;");
            calendarGrid.add(box, i % 7, i / 7);
        }
    }

    private Color getColor(double amount) {
        if (amount == 0) return Color.web("#3a3a3a");
        if (amount < 100) return Color.web("#4CAF50");     // Green
        if (amount < 300) return Color.web("#FFC107");     // Amber
        if (amount < 700) return Color.web("#FF9800");     // Orange
        return Color.web("#F44336");                       // Red
    }
}
