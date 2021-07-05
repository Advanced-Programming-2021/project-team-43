package view;

import controller.GameMatController;
import controller.MainMenuController;
import controller.PickFirstPlayer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Player;
import model.UserModel;

import java.io.IOException;
import java.util.Random;

public class PickFirstPlayerView extends Application {
    
    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Random random = new Random();
        int chance = random.nextInt(50);
        if (ply1.equals("AI")) {
            GameMatController.onlineUser= UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser= UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
            (GameMatController.gameMatView = new GameMatView()).start(primaryStage);

        } else if (ply2.equals("AI")) {
            GameMatController.onlineUser= UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser= UserModel.getUserByUsername(ply2).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
            (GameMatController.gameMatView = new GameMatView()).start(primaryStage);
        } else {
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

}





