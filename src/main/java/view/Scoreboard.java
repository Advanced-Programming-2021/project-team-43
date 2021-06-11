package main.java.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.controller.MainMenuController;
import main.java.model.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class Scoreboard extends Application {

    public VBox vBox;
    public Button back;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/scoreboard.fxml")));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize() {
        ArrayList<Label> list = new ArrayList<>();
        ArrayList<String> users = MainMenuController.showScoreboard();
        for (int i = 0; i < users.size(); i++) {
            if (MainMenuController.username.equals(users.get(i))) {
                Label label = new Label(((i + 1) + "_" + UserModel.getUserByUsername(users.get(i)).getNickname() + ": " + UserModel.getUserByUsername(users.get(i)).getUserScore()));
                label.setTextFill(Color.rgb(0, 26, 255));
                label.setFont(new Font(20));
                list.add(label);
                continue;
            }
            list.add(new Label(((i + 1) + "_" + UserModel.getUserByUsername(users.get(i)).getNickname() + ": " + UserModel.getUserByUsername(users.get(i)).getUserScore())));
            if (i == 19) {
                break;
            }
        }
        vBox.getChildren().addAll(list);
    }

    public void Back() throws Exception {
        new MainMenuView().start(stage);
    }


}
