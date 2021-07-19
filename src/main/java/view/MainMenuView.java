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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserModel;
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
    public Button tvBtn;
    public static MediaPlayer note;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenu.fxml")));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.show();
        if (UserModel.getUserByUsername(MainMenuController.username).getSequentialWin() == 8) {
            sequentialWinCup();
            RegisterAndLoginView.dataOutputStream.writeUTF("winCup/" + MainMenuController.username);
            RegisterAndLoginView.dataOutputStream.flush();
            UserModel.getUserByUsername(MainMenuController.username).resetSequentialWin();
        }
        if (UserModel.getUserByUsername(MainMenuController.username).getSequentialLost() == 10) {
            sequentialLostCup();
            RegisterAndLoginView.dataOutputStream.writeUTF("lostCup/" + MainMenuController.username);
            RegisterAndLoginView.dataOutputStream.flush();
            UserModel.getUserByUsername(MainMenuController.username).resetSequentialLost();
        }
    }

    public void sequentialWinCup() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(480);
        anchorPane.setPrefHeight(300);
        anchorPane.setStyle("-fx-background-color: #2a0002");
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/images/cup1.jpg")).toExternalForm())));
        Label label = new Label("Congrats! You won the Sequential Win Cup!");
        label.setFont(new Font("Bodoni MT", 20));
        label.setTextFill(Color.rgb(255, 229, 0));
        label.setLayoutX(110);
        label.setLayoutY(270);
        imageView.setLayoutX(110);
        imageView.setLayoutY(10);
        anchorPane.getChildren().addAll(imageView, label);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        stage.setResizable(false);
        stage.setTitle("Achievement");
        stage.setScene(new Scene(anchorPane));
        Media media = new Media(Objects.requireNonNull(this.getClass().getResource("/sounds/bonus.wav")).toExternalForm());
        note = new MediaPlayer(media);
        note.setAutoPlay(true);
        stage.show();
    }

    public void sequentialLostCup() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(480);
        anchorPane.setPrefHeight(300);
        anchorPane.setStyle("-fx-background-color: #2a0002");
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/images/cup5.jpg")).toExternalForm())));
        Label label = new Label("Congrats! You won the Sequential Lost Cup!");
        label.setFont(new Font("Bodoni MT", 20));
        label.setTextFill(Color.rgb(255, 229, 0));
        label.setLayoutX(110);
        label.setLayoutY(270);
        imageView.setLayoutX(110);
        imageView.setLayoutY(10);
        anchorPane.getChildren().addAll(imageView, label);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        stage.setResizable(false);
        stage.setTitle("Achievement");
        stage.setScene(new Scene(anchorPane));
        Media media = new Media(Objects.requireNonNull(this.getClass().getResource("/sounds/bonus.wav")).toExternalForm());
        note = new MediaPlayer(media);
        note.setAutoPlay(true);
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
        //setFrame(tvBtn);
        setFrame(logoutBtn);
    }

    public void setFrame(Button button) {
      //  KeyFrame duelEnd = new KeyFrame(new Duration(2000), new KeyValue(button.opacityProperty(), 0.0));
        KeyFrame duelStart = new KeyFrame(new Duration(4000), new KeyValue(button.opacityProperty(), 1.0));
    //    new Timeline(duelEnd, duelStart).play();
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

    public void lobby() throws Exception {
        (LobbyView.lobbyView = new LobbyView()).start(stage);
    }

    public void TV() throws Exception {
        new TVView().start(stage);
    }

    public void LogOut() throws Exception {
        new RegisterAndLoginView().start(stage);
    }

}