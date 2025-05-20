package ai;

import models.Transaction;
import utils.TransactionService;

import java.time.LocalDate;
import java.util.*;

public class SuggestionEngine {

    public static List<String> getSuggestions() {
        List<String> tips = new ArrayList<>();
        Map<String, Double> categoryTotals = new HashMap<>();
        List<Transaction> txns = TransactionService.getAllTransactions();

        String currentMonth = LocalDate.now().toString().substring(0, 7);
        double total = 0;

        for (Transaction t : txns) {
            if (t.getDate().startsWith(currentMonth)) {
                categoryTotals.put(t.getCategory(),
                        categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
                total += t.getAmount();
            }
        }

        for (String cat : categoryTotals.keySet()) {
            double pct = categoryTotals.get(cat) / total * 100;
            if (cat.equalsIgnoreCase("Food") && pct > 30)
                tips.add("You're spending a lot on food. Consider cooking at home.");
            if (cat.equalsIgnoreCase("Shopping") && pct > 25)
                tips.add("High shopping detected. Limit impulse buys.");
            if (cat.equalsIgnoreCase("Transport") && pct > 20)
                tips.add("Transport expenses are high. Consider alternatives.");
        }

        if (tips.isEmpty()) tips.add("Great job! Your spending is well-balanced.");
        return tips;
    }
}
