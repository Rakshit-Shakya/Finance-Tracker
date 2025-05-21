package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import utils.DatabaseUtil;

public class MainApp extends Application {  
    private boolean isDarkMode = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseUtil.initializeDatabase();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // ✅ Safe theme toggle lookup
        Platform.runLater(() -> {
            ToggleButton toggleButton = (ToggleButton) scene.lookup("#themeToggle");
            if (toggleButton != null) {
                toggleButton.setOnAction(e -> {
                    scene.getStylesheets().clear();
                    String theme = isDarkMode ? "light-theme.css" : "dark-theme.css";
                    scene.getStylesheets().add(getClass().getResource("/styles/" + theme).toExternalForm());
                    isDarkMode = !isDarkMode;
                });
            } else {
                System.err.println("⚠️ Theme toggle button not found (fx:id=\"themeToggle\").");
            }
        });

        primaryStage.setTitle("Finance Tracker - Personal Finance App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
