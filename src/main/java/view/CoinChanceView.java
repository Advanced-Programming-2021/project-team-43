package main.java.view;
import main.java.controller.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.model.*;
import java.util.Objects;



public class CoinChanceView extends Application {

    private static Stage coinStage;
    public static String winnerUsername;
    public static String ply1= MainMenuController.username;
    public static String ply2=MainMenuController.username2;
    @FXML
    Label label;
    @FXML
    Label label1;
    @FXML
    Button btnY;
    @FXML
    Button btnN;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pickFirstPlayerCoin.fxml")));
        coinStage = stage;
        coinStage.setScene(new Scene(root));
        coinStage.show();
    }

    @FXML
    public void initialize() {
        winnerUsername = PickFirstPlayer.chanceCoin(ply1, ply2);
        label.setText(winnerUsername + " is winner!");
        label.setVisible(true);
        label1.setText(winnerUsername + " do you want play first ?");
        btnN.setVisible(true);
        btnY.setVisible(true);
    }

    public void yes(MouseEvent mouseEvent) {
        if (winnerUsername.equals(ply1)) {
            GameMatController.onlineUser= UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser= UserModel.getUserByUsername(ply2).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        if (winnerUsername.equals(ply2)) {
            GameMatController.onlineUser= UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser= UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        try {
            (GameMatController.gameMatView = new GameMatView()).start(coinStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void no(MouseEvent mouseEvent) {
        if (winnerUsername.equals(ply1)) {
            GameMatController.onlineUser= UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser= UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        if (winnerUsername.equals(ply2)) {
            GameMatController.onlineUser= UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser= UserModel.getUserByUsername(ply2).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        try {
            (GameMatController.gameMatView = new GameMatView()).start(coinStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
