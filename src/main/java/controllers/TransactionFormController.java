package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Transaction;
import utils.TransactionService;

public class TransactionFormController {
    @FXML private TextField txtDate;
    @FXML private TextField txtDescription;
    @FXML private TextField txtAmount;
    @FXML private TextField txtCategory;

    private Runnable onSaveCallback;

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    public void saveTransaction() {
        try {
            String date = txtDate.getText();
            String desc = txtDescription.getText();
            double amt = Double.parseDouble(txtAmount.getText());
            String cat = txtCategory.getText().isEmpty() ? "Misc" : txtCategory.getText();

            if (desc.isEmpty() || date.isEmpty()) {
                showAlert("Fields can't be empty!");
                return;
            }

            Transaction t = new Transaction(0, date, desc, amt, cat);
            TransactionService.addTransaction(t);

            if (onSaveCallback != null) {
                onSaveCallback.run();  // 🔁 reloads dashboard before closing form
            }

            ((Stage) txtDate.getScene().getWindow()).close();
        } catch (Exception e) {
            showAlert("Invalid input. Please check values.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
