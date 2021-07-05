package view;

import controller.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;


import java.util.Objects;

public class CoinChanceView extends Application {

    private static Stage coinStage;
    public static String winnerUsername;
    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;
    @FXML
    Label label;
    @FXML
    Label label1;
    @FXML
    Button btnY;
    @FXML
    Button btnN;
    public AnchorPane pane;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/pickFirstPlayerCoin.fxml")));
        coinStage = stage;
        coinStage.setScene(new Scene(root));
        coinStage.show();
    }

    @FXML
    public void initialize() {
        winnerUsername = PickFirstPlayer.chanceCoin(ply1, ply2);
        label.setText(winnerUsername + " is winner!");
        label.setVisible(true);
        label1.setText(winnerUsername + " do you want play first ?");
        btnN.setVisible(true);
        btnY.setVisible(true);

        KeyFrame flashEnd = new KeyFrame(new Duration(2000), new KeyValue(
                btnN.opacityProperty(), 0.0));
        KeyFrame flashStart = new KeyFrame(new Duration(4000), new KeyValue(
                btnN.opacityProperty(), 1.0));
        new Timeline(flashEnd, flashStart).play();
        KeyFrame flashEnd2 = new KeyFrame(new Duration(2000), new KeyValue(
                btnY.opacityProperty(), 0.0));
        KeyFrame flashStart2 = new KeyFrame(new Duration(4000), new KeyValue(
                btnY.opacityProperty(), 1.0));
        new Timeline(flashEnd2, flashStart2).play();
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/fxml/coin.jpg")).toExternalForm()));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        pane.getChildren().add(imageView);
        RotateTransition rotator = createRotator(imageView);
        rotator.play();

    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);

        return rotator;
    }

    public void yes(MouseEvent mouseEvent) {
        if (winnerUsername.equals(ply1)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        if (winnerUsername.equals(ply2)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        try {
            GameMatController.gameMatView = new GameMatView();
//            TrapEffect.gameMatView = GameMatController.gameMatView;
//            Player.gameMatView = GameMatController.gameMatView;
//            HandCardZone.gameMatView = GameMatController.gameMatView;
//            GameMatModel.gameMatView = GameMatController.gameMatView;
//            SpellEffect.gameMatView = GameMatController.gameMatView;
//            MonsterEffect.gameMatView = GameMatController.gameMatView;
            GameMatController.gameMatView.start(coinStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void no(MouseEvent mouseEvent) {
        if (winnerUsername.equals(ply1)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        if (winnerUsername.equals(ply2)) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();
            new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
            new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
        }
        try {
            GameMatController.gameMatView = new GameMatView();
//            TrapEffect.gameMatView = GameMatController.gameMatView;
//            Player.gameMatView = GameMatController.gameMatView;
//            HandCardZone.gameMatView = GameMatController.gameMatView;
//            GameMatModel.gameMatView = GameMatController.gameMatView;
//            SpellEffect.gameMatView = GameMatController.gameMatView;
//            MonsterEffect.gameMatView = GameMatController.gameMatView;
            GameMatController.gameMatView.start(coinStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
