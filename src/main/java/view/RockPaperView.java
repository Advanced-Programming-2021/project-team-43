package view;

import controller.GameMatController;
import controller.MainMenuController;
import controller.PickFirstPlayer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Player;
import model.UserModel;

import java.util.Objects;


public class RockPaperView extends Application {

    @FXML
    private Label showTurn;
    @FXML
    private Label showTurn1;
    @FXML
    private Label showResult;
    private static Stage rockStage;
    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pickFirstPlayer.fxml")));
        rockStage = stage;
        rockStage.setScene(new Scene(root));
        rockStage.show();
    }

    public static String firstChoice;
    public static String secondChoice;

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
            winnerPlayer = PickFirstPlayer.rockPaperScissors(ply1, ply2, firstChoice, secondChoice);
            showResult.setText(PickFirstPlayer.result);

            if (PickFirstPlayer.result.equals("The game equalised")) {
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

    @FXML
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private Label ask;
    public static String winnerPlayer;


    public void yes(MouseEvent mouseEvent) {
        if (winnerPlayer.equals(ply1)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        if (winnerPlayer.equals(ply2)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        try {
            (GameMatController.gameMatView = new GameMatView()).start(rockStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void no() {
        if (winnerPlayer.equals(ply1)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        if (winnerPlayer.equals(ply2)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();

            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        try {
            (GameMatController.gameMatView = new GameMatView()).start(rockStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

