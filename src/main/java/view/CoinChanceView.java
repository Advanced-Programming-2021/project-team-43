package view;

import controller.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;


import java.io.IOException;
import java.util.Objects;


public class CoinChanceView extends Application {

    private static Stage coinStage;
    public static String winnerUsername;
    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;
    public ImageView coinImg;
    @FXML
    Label label;
    @FXML
    Label label1;
    @FXML
    Button btnY;
    @FXML
    Button btnN;
    public AnchorPane pane;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pickFirstPlayerCoin.fxml")));
        coinStage = stage;
        coinStage.setScene(new Scene(root));
        coinStage.show();
    }

    @FXML
    public void initialize() {
        KeyFrame flashEnd = new KeyFrame(new Duration(2000), new KeyValue(btnN.opacityProperty(), 0.0));
        KeyFrame flashStart = new KeyFrame(new Duration(4000), new KeyValue(btnN.opacityProperty(), 1.0));
        new Timeline(flashEnd, flashStart).play();
        KeyFrame flashEnd2 = new KeyFrame(new Duration(2000), new KeyValue(btnY.opacityProperty(), 0.0));
        KeyFrame flashStart2 = new KeyFrame(new Duration(4000), new KeyValue(btnY.opacityProperty(), 1.0));
        new Timeline(flashEnd2, flashStart2).play();
        RotateTransition rotator = createRotator(coinImg);
        rotator.play();
        rotator.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                label.setText(winnerUsername + " is winner!");
                label.setVisible(true);
                label1.setText(winnerUsername + " do you want play first ?");
                btnN.setVisible(true);
                btnY.setVisible(true);
            }
        });
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(5);
        return rotator;
    }

    public void yes() {
        if (winnerUsername.equals(ply1)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();
            if (Player.getPlayerByName(GameMatController.onlineUser) == null) {
                new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
                new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
                GameMatController.round = MainMenuController.roundNumber1;
            } else {
                Player.getPlayerByName(UserModel.getUserByUsername(ply1).getNickname()).startNewGame(UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true);
                Player.getPlayerByName(UserModel.getUserByUsername(ply2).getNickname()).startNewGame(UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false);
            }
        }
        if (winnerUsername.equals(ply2)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            if (Player.getPlayerByName(GameMatController.onlineUser) == null) {
                new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
                new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
                GameMatController.round = MainMenuController.roundNumber1;
            } else {
                Player.getPlayerByName(UserModel.getUserByUsername(ply2).getNickname()).startNewGame(UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true);
                Player.getPlayerByName(UserModel.getUserByUsername(ply1).getNickname()).startNewGame(UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false);
                GameMatController.round = Player.getPlayerByName(UserModel.getUserByUsername(ply2).getNickname()).getNumberOfRound();
            }
        }
        try {
            Objects.requireNonNullElseGet(GameMatController.gameMatView, () -> GameMatController.gameMatView = new GameMatView()).start(coinStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("onlineUser:" + MainMenuController.token+":"+GameMatController.onlineUser+":"+GameMatController.rivalUser);
            RegisterAndLoginView.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void no() {
        if (winnerUsername.equals(ply1)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            if (Player.getPlayerByName(GameMatController.onlineUser) == null) {
                new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
                new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
                GameMatController.round = MainMenuController.roundNumber1;
            } else {
                Player.getPlayerByName(UserModel.getUserByUsername(ply2).getNickname()).startNewGame(UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true);
                Player.getPlayerByName(UserModel.getUserByUsername(ply1).getNickname()).startNewGame(UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false);
            }
        }
        if (winnerUsername.equals(ply2)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();
            if (Player.getPlayerByName(GameMatController.onlineUser) == null) {
                new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
                new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
                GameMatController.round = MainMenuController.roundNumber1;
            } else {
                Player.getPlayerByName(UserModel.getUserByUsername(ply1).getNickname()).startNewGame(UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true);
                Player.getPlayerByName(UserModel.getUserByUsername(ply2).getNickname()).startNewGame(UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false);
                GameMatController.round = Player.getPlayerByName(UserModel.getUserByUsername(ply2).getNickname()).getNumberOfRound();
            }
        }
        try {
            Objects.requireNonNullElseGet(GameMatController.gameMatView, () -> GameMatController.gameMatView = new GameMatView()).start(coinStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("onlineUser:" + MainMenuController.token+":"+GameMatController.onlineUser+":"+GameMatController.rivalUser);
            RegisterAndLoginView.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}