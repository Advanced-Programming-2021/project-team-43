package view;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;



public class MainMenuView extends Application {

    private static Stage stage;
    public Button duelBtn;
    public Button profileBtn;
    public Button shopBtn;
    public Button deckBtn;
    public Button scoreboardBtn;
    public Button cardBtn;
    public Button logoutBtn;
    public Button lobbyBtn;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenu.fxml")));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize() {
        setFrame(duelBtn);
        setFrame(profileBtn);
        setFrame(shopBtn);
        setFrame(deckBtn);
        setFrame(scoreboardBtn);
        setFrame(cardBtn);
        setFrame(lobbyBtn);
        setFrame(logoutBtn);
    }

    public void setFrame(Button button) {
        KeyFrame duelEnd = new KeyFrame(new Duration(2000), new KeyValue(button.opacityProperty(), 0.0));
        KeyFrame duelStart = new KeyFrame(new Duration(4000), new KeyValue(button.opacityProperty(), 1.0));
        new Timeline(duelEnd, duelStart).play();
    }


    public void Profile() throws Exception {
        new ProfileView().start(stage);
    }

    public void Duel() throws Exception {
        new Duel().start(stage);
    }

    public void Deck() throws Exception {
        new DeckView().start(stage);
    }

    public void Scoreboard() throws Exception {
        new Scoreboard().start(stage);
    }

    public void Shop() throws Exception {
        new ShopView().start(stage);
    }

    public void card() throws Exception {
        new StartClass().start(stage);
    }

    public void lobby() throws Exception {
        (LobbyView.lobbyView = new LobbyView()).start(stage);
    }

    public void LogOut() throws Exception {
        new RegisterAndLoginView().start(stage);
    }
}
