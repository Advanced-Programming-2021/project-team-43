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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Objects;


public class Pause extends Application {

    private static Stage pauseStage;
    @FXML
    Slider volume;
    @Override
    public void start(Stage sage) throws Exception {
        Stage stage = new Stage();
        pauseStage = stage;
        stage.initModality(Modality.APPLICATION_MODAL);//emphsize
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pause.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.DARKBLUE);
        stage.setTitle("setting");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        Media media = new Media(Objects.requireNonNull(this.getClass().getResource("/sounds/18.mp3")).toExternalForm());
        MediaPlayer note = new MediaPlayer(media);
        note.setCycleCount(-1);
        note.setAutoPlay(true);
        volume.setValue(RegisterAndLoginView.note.getVolume()*100);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                RegisterAndLoginView.note.setVolume(volume.getValue()/100);
            }
        });
    }

    public void exit() {
        System.exit(0);
    }

    public void resume() {
        pauseStage.close();
    }

    public void restart() {
        try {
            new MainMenuView().start(GameMatView.gameMatStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mute() {
        RegisterAndLoginView.note.stop();
    }

}