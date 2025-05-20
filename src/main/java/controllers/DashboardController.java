package controllers;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import models.Transaction;
import utils.TransactionService;
import utils.CSVExportUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DashboardController {

    // Table & Columns
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;

    // Buttons
    @FXML private Button addTransactionButton, deleteTransactionButton, exportButton,
                         heatmapButton, summaryButton, gamifyButton, chartsButton, tipsButton;

    // Dashboard labels
    @FXML private Label totalSpentLabel;
    @FXML private Label totalIncomeLabel;
    @FXML private Label savingsLabel;

    // Data state
    private double monthlyIncome = 0;
    private double monthlyBudget = 0;
    private double totalSpent = 0;

    @FXML
    public void initialize() {
        // Animate Table
        FadeTransition fade = new FadeTransition(Duration.millis(800), transactionTable);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        // Table Bindings
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Load data
        loadData();

        // Prompt for budget info
        promptUserBudgetInfo();

        // Button Actions
        if (addTransactionButton != null)
            addTransactionButton.setOnAction(e -> openWindow("/views/transaction_form.fxml", "Add Transaction"));

        if (deleteTransactionButton != null)
            deleteTransactionButton.setOnAction(e -> deleteSelected());

        if (exportButton != null)
            exportButton.setOnAction(e -> exportDataToCSV());

        if (heatmapButton != null)
            heatmapButton.setOnAction(e -> openWindow("/views/heatmap.fxml", "Spending Heatmap"));

        if (summaryButton != null)
            summaryButton.setOnAction(e -> openWindow("/views/summary.fxml", "Monthly Summary"));

        if (gamifyButton != null)
            gamifyButton.setOnAction(e -> openWindow("/views/gamification.fxml", "Gamification & Tips"));

        if (chartsButton != null)
            chartsButton.setOnAction(e -> openWindow("/views/chart_view.fxml", "Expense Charts"));

        if (tipsButton != null)
            tipsButton.setOnAction(e -> openWindow("/views/budget_tips.fxml", "Budgeting Tips"));
    }

    private void loadData() {
        transactionTable.setItems(FXCollections.observableArrayList(TransactionService.getAllTransactions()));
        updateDashboardValues();
    }

    private void deleteSelected() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TransactionService.deleteTransaction(selected.getId());
            loadData();
        }
    }

    private void exportDataToCSV() {
        List<String[]> rows = transactionTable.getItems().stream()
                .map(txn -> new String[]{
                        txn.getDate(),
                        txn.getCategory(),
                        txn.getDescription(),
                        String.valueOf(txn.getAmount())
                })
                .collect(Collectors.toList());

        CSVExportUtil.exportToCSV("transactions.csv", rows);
        showAlert("✅ Exported to transactions.csv");
    }

    private void openWindow(String resourcePath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent view = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(view));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("❌ Failed to open: " + resourcePath);
        }
    }

    private void promptUserBudgetInfo() {
        TextInputDialog incomeDialog = new TextInputDialog();
        incomeDialog.setHeaderText("Enter your monthly income:");
        incomeDialog.setTitle("Monthly Income");
        incomeDialog.setContentText("Income:");
        Optional<String> incomeInput = incomeDialog.showAndWait();
        incomeInput.ifPresent(input -> {
            try {
                monthlyIncome = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                showAlert("⚠️ Invalid input. Monthly income set to 0.");
                monthlyIncome = 0;
            }
        });

        TextInputDialog budgetDialog = new TextInputDialog();
        budgetDialog.setHeaderText("Enter your monthly budget:");
        budgetDialog.setTitle("Monthly Budget");
        budgetDialog.setContentText("Budget:");
        Optional<String> budgetInput = budgetDialog.showAndWait();
        budgetInput.ifPresent(input -> {
            try {
                monthlyBudget = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                showAlert("⚠️ Invalid input. Monthly budget set to 0.");
                monthlyBudget = 0;
            }
        });

        updateDashboardValues();
    }

    private void updateDashboardValues() {
        totalSpent = transactionTable.getItems().stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        double savings = monthlyBudget - totalSpent;

        totalSpentLabel.setText("₹" + String.format("%.2f", totalSpent));
        totalIncomeLabel.setText("₹" + String.format("%.2f", monthlyIncome));
        savingsLabel.setText("₹" + String.format("%.2f", savings));

        if (monthlyBudget > 0 && totalSpent > monthlyBudget) {
            showAlert("⚠️ Budget Exceeded! You’ve spent more than your monthly budget.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Finance Tracker");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
