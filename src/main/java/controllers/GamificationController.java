package controllers;

import ai.GamificationEngine;
import ai.SuggestionEngine;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

public class GamificationController {

    @FXML private Label lblStreak;
    @FXML private Label lblHealthScore;
    @FXML private ListView<String> tipList;

    @FXML
    public void initialize() {
        int streak = GamificationEngine.calculateSaveStreak();
        int score = GamificationEngine.getFinancialHealthScore();

        lblStreak.setText(streak + " days 🔥");
        lblHealthScore.setText(score + "/100 💰");

        tipList.getItems().addAll(SuggestionEngine.getSuggestions());

        FadeTransition fade = new FadeTransition(Duration.millis(800), tipList);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
