package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TVView extends Application {

    private static Stage tvStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/tvPage.fxml")));
        tvStage = primaryStage;
        tvStage.setScene(new Scene(root));
        tvStage.show();
    }

    public void back() throws Exception {
        new MainMenuView().start(tvStage);
    }

}