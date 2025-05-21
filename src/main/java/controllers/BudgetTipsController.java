package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import models.Transaction;
import utils.TransactionService;

import java.time.LocalDate;
import java.util.List;

public class BudgetTipsController {

    @FXML private TextArea tipsArea;

    @FXML
    public void initialize() {
        double monthlyBudget = DashboardController.globalMonthlyBudget;

        if (monthlyBudget <= 0) {
            tipsArea.setText("⚠️ Monthly budget is not set. Please enter it in the dashboard to get personalized tips.");
            return;
        }

        List<Transaction> allTransactions = TransactionService.getAllTransactions();
        double currentMonthTotal = allTransactions.stream()
                .filter(t -> LocalDate.parse(t.getDate()).getMonthValue() == LocalDate.now().getMonthValue())
                .mapToDouble(Transaction::getAmount)
                .sum();

        double percentUsed = (currentMonthTotal / monthlyBudget) * 100;

        String tips;
        if (percentUsed <= 30) {
            tips = """
                    💡 Budget Tips – Low Usage:
                    • Great start! You've used less than 30% of your budget.
                    • Continue this momentum — consider saving or investing the surplus.
                    • Avoid impulse spending to stay consistent.
                    """;
        } else if (percentUsed <= 85) {
            tips = """
                    💡 Budget Tips – Moderate Usage:
                    • You’ve used around half to most of your budget.
                    • Monitor weekly spending closely.
                    • Set daily limits to avoid sudden overspending.
                    • Prepare for upcoming bills or surprises.
                    """;
        } else {
            tips = """
                    ⚠️ Budget Tips – High Usage Alert:
                    • You've crossed 85% of your monthly budget!
                    • Identify and pause non-essential spending immediately.
                    • Reassess categories like dining out, shopping, etc.
                    • Set a challenge to stay within the limit next month.
                    """;
        }

        tipsArea.setText(tips);
    }
}
