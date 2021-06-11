package main.java.view;
import main.java.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Duel extends Application {
    public Button AIBtt;
    public TextField playerId;
    public Button duelBtt;
    public Stage stage;
    public Label text;
    public TextField number;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/duel.fxml")));
        stage.setScene(new Scene(root));
        this.stage = stage;
        stage.show();
    }

    public void duel() throws Exception {
        if (playerId.getText().isEmpty()) {
            text.setText("pls enter player username");
        } else {
            if (number.getText().isEmpty()) {
                text.setText("pls enter round number");
            } else {
                Pattern pattern = Pattern.compile("(\\d+)");
                Matcher matcher = pattern.matcher(number.getText());
                if (matcher.find()) {
                    text.setText(MainMenuController.duelMenu(playerId.getText(), Integer.parseInt(matcher.group(1))));
                    if (text.getText().equals("abbas boo azar")) {
                        new PickFirstPlayerView().start(stage);
                    }
                } else {
                    text.setText("invalid round number");
                }
            }
        }

    }
}