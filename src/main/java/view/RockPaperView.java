package view;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GameMatModel;
import model.HandCardZone;
import model.Player;
import model.UserModel;

import java.io.IOException;
import java.util.Objects;


public class RockPaperView extends Application {

    @FXML
    private Label showTurn;
    @FXML
    private Label showTurn1;
    @FXML
    private Label showResult;
    @FXML
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private Label ask;
    public static String winnerPlayer;
    private static Stage rockStage;
    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;
    public static String firstChoice;
    public static String secondChoice;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pickFirstPlayer.fxml")));
        rockStage = stage;
        rockStage.setScene(new Scene(root));
        rockStage.show();
    }

    @FXML
    public void initialize() {
        showTurn.setText((ply1) + " select one");
        showTurn1.setText((ply2) + " select one");
    }

    public void stone() {
        firstChoice = "rock";
        result();
    }

    public void paper() {
        firstChoice = "paper";
        result();
    }

    public void sci() {
        firstChoice = "scissors";
        result();
    }

    public void sci1() {
        secondChoice = "scissors";
        result();
    }

    public void paper1() {
        secondChoice = "paper";
        result();
    }

    public void stone1() {
        secondChoice = "rock";
        result();
    }

    public void result() {
        if (secondChoice == null) {
            showTurn1.setText(ply2 + " you did not selected,select again");
        }
        if (firstChoice == null) {
            showTurn.setText(ply1 + " you did not selected,select again");
        }
        if (secondChoice != null && firstChoice != null) {
            String[] answer = new String[5];
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("rock:" + MainMenuController.token + ":" + ply1 + ":" + ply2 + ":" + firstChoice + ":" + secondChoice);
                RegisterAndLoginView.dataOutputStream.flush();
                answer = RegisterAndLoginView.dataInputStream.readUTF().split("@");
            } catch (IOException e) {
                e.printStackTrace();
            }
            winnerPlayer = answer[0];
            showResult.setText(answer[1]);
            if (answer[1].equals("The game equalised")) {
                try {
                    firstChoice = null;
                    secondChoice = null;
                    new RockPaperView().start(rockStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ask.setText(winnerPlayer + " do you want play first ?");
                ask.setVisible(true);
                yes.setVisible(true);
                no.setVisible(true);
            }
        }
    }

    public void yes() {
        if (winnerPlayer.equals(ply1)) {
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
        if (winnerPlayer.equals(ply2)) {
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
            Objects.requireNonNullElseGet(GameMatController.gameMatView, () -> GameMatController.gameMatView = new GameMatView()).start(rockStage);
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
        if (winnerPlayer.equals(ply1)) {
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
        if (winnerPlayer.equals(ply2)) {
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
            Objects.requireNonNullElseGet(GameMatController.gameMatView, () -> GameMatController.gameMatView = new GameMatView()).start(rockStage);
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

