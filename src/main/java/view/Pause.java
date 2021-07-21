package view;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Objects;


public class Pause extends Application {

    private static Stage pauseStage;
    private static Stage realStage;
    public Button muteBtn;
    @FXML
    Slider volume;


    @Override
    public void start(Stage stg) throws Exception {
        realStage = stg;
        pauseStage = new Stage();
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pause.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.DARKBLUE);
        pauseStage.setTitle("Pause");
        pauseStage.setScene(scene);
        pauseStage.setResizable(false);
        pauseStage.show();
    }

    @FXML
    public void initialize() {
        if (RegisterAndLoginView.isMusicOn)
            muteBtn.setText("Mute");
        else
            muteBtn.setText("UnMute");
        volume.setValue(RegisterAndLoginView.note.getVolume()*100);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                RegisterAndLoginView.note.setVolume(volume.getValue()/100);
            }
        });
    }

    public void exit() throws Exception {
        pauseStage.close();
        new MainMenuView().start(realStage);
    }

    public void resume() {
        pauseStage.close();
    }

    public void mute() {
        if (muteBtn.getText().equals("Mute")) {
            muteBtn.setText("UnMute");
            RegisterAndLoginView.note.stop();
        }
        else {
            muteBtn.setText("Mute");
            RegisterAndLoginView.note.play();
        }
    }

}