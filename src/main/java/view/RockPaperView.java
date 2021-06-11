package view;

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
import model.UserModel;

public class RockPaperView extends Application {
    @FXML
    private Label showTurn;
    @FXML
    private Label showTurn1;
    @FXML
    private Label showResult;
    private static Stage stage;
    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pickFirstPlayer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static String firstChoice;
    public static String secondChoice;

    @FXML
    public void initialize() {
        showTurn.setText((ply1) + " select one");
        showTurn1.setText((ply2) + " select one");
    }


    public void stone(MouseEvent mouseEvent) {
        firstChoice = "rock";
        result();
    }

    public void paper(MouseEvent mouseEvent) {
        firstChoice = "paper";
        result();
    }

    public void sci(MouseEvent mouseEvent) {
        firstChoice = "scissors";
        result();
    }

    public void sci1(MouseEvent mouseEvent) {
        secondChoice = "scissors";
        result();
    }

    public void paper1(MouseEvent mouseEvent) {
        secondChoice = "paper";
        result();
    }

    public void stone1(MouseEvent mouseEvent) {
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
                    new RockPaperView().start(stage);
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
        //go to game mat
    }

    public void no(MouseEvent mouseEvent) {
        if (winnerPlayer.equals(ply1)) {
            winnerPlayer = ply2;
        }
        if (winnerPlayer.equals(ply2)) {
            winnerPlayer = ply1;
        }
        //go to game mat
    }
}
