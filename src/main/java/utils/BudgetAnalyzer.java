package utils;

import models.Transaction;

import java.time.LocalDate;
import java.util.List;

public class BudgetAnalyzer {

    public static double calculateMonthlySpending(List<Transaction> transactions, int month, int year) {
        return transactions.stream()
                .filter(t -> {
                    LocalDate date = DateUtil.parseFromDB(t.getDate());
                    return date != null && date.getMonthValue() == month && date.getYear() == year;
                })
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static int calculateFinancialHealthScore(List<Transaction> transactions, double monthlyIncome) {
        double totalSpent = calculateMonthlySpending(transactions, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        if (monthlyIncome <= 0) return 0;

        double savingRatio = (monthlyIncome - totalSpent) / monthlyIncome;
        if (savingRatio >= 0.3) return 90;
        else if (savingRatio >= 0.1) return 75;
        else if (savingRatio >= 0.05) return 60;
        else return 40;
    }

    public static String generateTip(List<Transaction> transactions, double monthlyIncome) {
        double food = sumByCategory(transactions, "Food");
        double entertainment = sumByCategory(transactions, "Entertainment");

        if (entertainment > 0.3 * monthlyIncome) {
            return "Try reducing your entertainment spending to boost savings.";
        } else if (food > 0.4 * monthlyIncome) {
            return "Consider meal planning to reduce food expenses.";
        } else {
            return "Great job! Your spending looks healthy.";
        }
    }

    private static double sumByCategory(List<Transaction> transactions, String category) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
