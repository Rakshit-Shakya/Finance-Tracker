package ai;

import models.Transaction;
import utils.TransactionService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GamificationEngine {

    public static int calculateSaveStreak() {
        List<Transaction> txns = TransactionService.getAllTransactions();
        Set<String> spendDays = txns.stream()
                .filter(t -> t.getAmount() > 100)
                .map(Transaction::getDate)
                .collect(Collectors.toSet());

        LocalDate today = LocalDate.now();
        int streak = 0;

        for (int i = 0; i < 30; i++) {
            LocalDate d = today.minusDays(i);
            if (spendDays.contains(d.toString())) break;
            streak++;
        }

        return streak;
    }

    public static int getFinancialHealthScore() {
        int streak = calculateSaveStreak();
        List<Transaction> txns = TransactionService.getAllTransactions();
        double total = txns.stream().mapToDouble(Transaction::getAmount).sum();
        double shopping = txns.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase("Shopping"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double adherence = total < 20000 ? 60 : 40;
        double score = adherence + Math.min(streak, 10) * 2 + (shopping < total * 0.2 ? 20 : 10);
        return (int) score;
    }
}
