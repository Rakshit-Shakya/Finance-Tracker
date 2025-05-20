package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class BudgetTipsController {

    @FXML private TextArea tipsArea;

    @FXML
    public void initialize() {
        tipsArea.setText("""
                💡 Budgeting Tips:
                • Track all your expenses.
                • Use the 50/30/20 rule.
                • Eliminate unnecessary subscriptions.
                • Cook at home to save.
                • Review spending monthly.
                """);
    }
}
