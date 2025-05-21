package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import models.Transaction;
import utils.TransactionService;

import java.util.*;
import java.util.stream.Collectors;

public class ChartController {

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML
    public void initialize() {
        List<Transaction> transactions = TransactionService.getAllTransactions();

        // Pie Chart by Category
        Map<String, Double> categoryTotals = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getCategory,
                    Collectors.summingDouble(Transaction::getAmount)));

        pieChart.getData().clear();
        categoryTotals.forEach((category, total) ->
            pieChart.getData().add(new PieChart.Data(category, total))
        );

        // Bar Chart by Date
        Map<String, Double> dateTotals = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getDate,
                    TreeMap::new, Collectors.summingDouble(Transaction::getAmount)));

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        dateTotals.forEach((date, total) ->
            barSeries.getData().add(new XYChart.Data<>(date, total))
        );

        barChart.getData().clear();
        barChart.getData().add(barSeries);
    }
}
