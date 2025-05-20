package ai;

import models.Transaction;
import utils.TransactionService;

import java.util.*;

public class ForecastEngine {

    public static Map<String, Double> predictNextMonthSpending() {
        List<Transaction> txns = TransactionService.getAllTransactions();
        Map<String, List<Double>> categoryHistory = new HashMap<>();

        for (Transaction t : txns) {
            String month = t.getDate().substring(0, 7);
            String key = t.getCategory() + "_" + month;
            categoryHistory.putIfAbsent(key, new ArrayList<>());
            categoryHistory.get(key).add(t.getAmount());
        }

        Map<String, List<Double>> categoryMonthlyTotals = new HashMap<>();
        for (String key : categoryHistory.keySet()) {
            String category = key.split("_")[0];
            double total = categoryHistory.get(key).stream().mapToDouble(Double::doubleValue).sum();
            categoryMonthlyTotals.putIfAbsent(category, new ArrayList<>());
            categoryMonthlyTotals.get(category).add(total);
        }

        Map<String, Double> forecast = new HashMap<>();
        for (String category : categoryMonthlyTotals.keySet()) {
            List<Double> values = categoryMonthlyTotals.get(category);
            if (values.size() >= 2) {
                double delta = values.get(values.size() - 1) - values.get(values.size() - 2);
                forecast.put(category, values.get(values.size() - 1) + delta);
            } else {
                forecast.put(category, values.get(0));
            }
        }

        return forecast;
    }
}
