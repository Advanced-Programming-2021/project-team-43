package view;
import controller.MainMenuController;
import controller.RegisterAndLoginController;
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


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenu.fxml")));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize() {
        KeyFrame duelEnd = new KeyFrame(new Duration(2000), new KeyValue(duelBtn.opacityProperty(), 0.0));
        KeyFrame duelStart = new KeyFrame(new Duration(4000), new KeyValue(duelBtn.opacityProperty(), 1.0));
        new Timeline(duelEnd, duelStart).play();
        KeyFrame proEnd = new KeyFrame(new Duration(2000), new KeyValue(profileBtn.opacityProperty(), 0.0));
        KeyFrame proStart = new KeyFrame(new Duration(4000), new KeyValue(profileBtn.opacityProperty(), 1.0));
        new Timeline(proEnd, proStart).play();
        KeyFrame shopEnd = new KeyFrame(new Duration(2000), new KeyValue(shopBtn.opacityProperty(), 0.0));
        KeyFrame shopStart = new KeyFrame(new Duration(4000), new KeyValue(shopBtn.opacityProperty(), 1.0));
        new Timeline(shopEnd, shopStart).play();
        KeyFrame deckEnd = new KeyFrame(new Duration(2000), new KeyValue(deckBtn.opacityProperty(), 0.0));
        KeyFrame deckStart = new KeyFrame(new Duration(4000), new KeyValue(deckBtn.opacityProperty(), 1.0));
        new Timeline(deckEnd, deckStart).play();
        KeyFrame scoreEnd = new KeyFrame(new Duration(2000), new KeyValue(scoreboardBtn.opacityProperty(), 0.0));
        KeyFrame scoreStart = new KeyFrame(new Duration(4000), new KeyValue(scoreboardBtn.opacityProperty(), 1.0));
        new Timeline(scoreEnd, scoreStart).play();
        KeyFrame cardEnd = new KeyFrame(new Duration(2000), new KeyValue(cardBtn.opacityProperty(), 0.0));
        KeyFrame cardStart = new KeyFrame(new Duration(4000), new KeyValue(cardBtn.opacityProperty(), 1.0));
        new Timeline(cardEnd, cardStart).play();
        KeyFrame logEnd = new KeyFrame(new Duration(2000), new KeyValue(logoutBtn.opacityProperty(), 0.0));
        KeyFrame logStart = new KeyFrame(new Duration(4000), new KeyValue(logoutBtn.opacityProperty(), 1.0));
        new Timeline(logEnd, logStart).play();
    }

    public void Profile() throws Exception {
        new ProfileView().start(stage);
    }

    public void Duel() throws Exception {
        new Duel().start(stage);
    }

    public void Deck() throws Exception {
        RegisterAndLoginController.updateUser(MainMenuController.token);
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

    public void LogOut() throws Exception {
        new RegisterAndLoginView().start(stage);
    }

}
