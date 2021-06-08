package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class CoinChanceView extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;

    }
    public void coin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pickFirstPlayerCoin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
