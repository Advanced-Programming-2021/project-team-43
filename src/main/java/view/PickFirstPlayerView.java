package main.java.view;
import main.java.controller.PickFirstPlayer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;


public class PickFirstPlayerView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Random random = new Random();
        int chance = random.nextInt(50);
        if (chance % 2 == 0) {
            try {
                new CoinChanceView().start(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new RockPaperView().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}





