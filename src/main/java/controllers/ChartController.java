package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import models.Transaction;
import utils.TransactionService;

import java.util.*;

public class ChartController {

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML
    public void initialize() {
        loadPieChart();
        loadBarChart();
    }

    private void loadPieChart() {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : TransactionService.getAllTransactions()) {
            categoryTotals.put(t.getCategory(),
                    categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void loadBarChart() {
        Map<String, Double> dailyTotals = new TreeMap<>();
        for (Transaction t : TransactionService.getAllTransactions()) {
            dailyTotals.put(t.getDate(),
                    dailyTotals.getOrDefault(t.getDate(), 0.0) + t.getAmount());
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Double> entry : dailyTotals.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
    }
}
