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

public class CoinChanceView extends Application {
    private static Stage stage;
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
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pickFirstPlayerCoin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        //go to game mat
    }

    public void no(MouseEvent mouseEvent) {
        if (winnerUsername.equals(ply1)) {
            winnerUsername = ply2;
        }
        if (winnerUsername.equals(ply2)) {
            winnerUsername = ply1;
        }
        //go to game mat
    }
}
