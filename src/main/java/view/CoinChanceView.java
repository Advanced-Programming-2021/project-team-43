package view;

import controller.PickFirstPlayer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CoinChanceView extends Application {
    private static Stage stage;
    public static String winnerUsername;
    public static String ply1 ;
    public static String ply2 ;///in dota bayad az main menu por beshan
    @FXML
    Label label;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pickFirstPlayerCoin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        winnerUsername= PickFirstPlayer.chanceCoin(ply1,ply2);
        label.setText(winnerUsername+" is winner!");
        label.setVisible(true);
        //go to game mat
    }
}
