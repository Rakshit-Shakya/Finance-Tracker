package utils;

import models.Transaction;
import java.util.List;

public class BudgetHelper {
    private static double monthlyLimit = 0.0;
    private static double income = 0.0;

    public static void setIncome(double val) {
        income = val;
    }

    public static void setMonthlyLimit(double val) {
        monthlyLimit = val;
    }

    public static double getMonthlyLimit() {
        return monthlyLimit;
    }

    public static double getTotalSpent(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public static double getUsagePercentage(List<Transaction> transactions) {
        if (monthlyLimit == 0) return 0;
        return (getTotalSpent(transactions) / monthlyLimit) * 100;
    }

    public static boolean isLimitExceeded(List<Transaction> transactions) {
        return getTotalSpent(transactions) > monthlyLimit;
    }

    public static double getSavingsLeft(List<Transaction> transactions) {
        return Math.max(0, monthlyLimit - getTotalSpent(transactions));
    }
}
