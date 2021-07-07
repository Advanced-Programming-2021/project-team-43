package view;

import controller.GameMatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class GameOverView extends Application {

    private static Stage gameOverStage;
    public Label resultLbl;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/gameOver.fxml")));
        gameOverStage = primaryStage;
        gameOverStage.setHeight(790);
        Scene scene = new Scene(root);
        gameOverStage.setTitle("Game Over");
        gameOverStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        gameOverStage.setResizable(false);
        gameOverStage.setScene(scene);
        gameOverStage.initModality(Modality.APPLICATION_MODAL);
        gameOverStage.show();
        gameOverStage.setOnHidden(e -> {
            try {
                new MainMenuView().start(gameOverStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void initialize() {
        System.out.println(GameMatController.message);
        resultLbl.setText(GameMatController.message);
    }
}
