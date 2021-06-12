package view;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class Pause extends Application {
    private static Stage stage;
    @FXML
    Slider volume;
    @Override
    public void start(Stage sage) throws Exception {
        Stage stage = new Stage();
        this.stage = stage;
        stage.initModality(Modality.APPLICATION_MODAL);//emphsize
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pause.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.DARKBLUE);
        stage.setTitle("setting");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        volume.setValue(RegisterAndLoginView.backgroundMusic.getVolume()*100);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
               RegisterAndLoginView.backgroundMusic.setVolume(volume.getValue()/100);
            }
        });
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void resume(MouseEvent mouseEvent) throws Exception {
        this.stage.close();

    }

    public void restart(MouseEvent mouseEvent) {
        try {
            new MainMenuView().start(GameMatView.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mute(MouseEvent mouseEvent) {
        RegisterAndLoginView.backgroundMusic.stop();
    }


}
