package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;
import models.Transaction;
import utils.TransactionService;

import java.time.LocalDate;
import java.util.*;

public class SummaryController {

    @FXML private PieChart pieChart;

    @FXML
    public void initialize() {
        Map<String, Double> categorySums = new HashMap<>();
        List<Transaction> txns = TransactionService.getAllTransactions();

        String currentMonth = LocalDate.now().toString().substring(0, 7);
        for (Transaction t : txns) {
            if (t.getDate().startsWith(currentMonth)) {
                categorySums.put(t.getCategory(),
                        categorySums.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        for (Map.Entry<String, Double> entry : categorySums.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        FadeTransition fade = new FadeTransition(Duration.millis(1000), pieChart);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
