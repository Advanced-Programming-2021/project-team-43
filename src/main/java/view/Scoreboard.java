package view;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.UserModel;

import java.util.*;


public class Scoreboard extends Application {

    public Button back;
    public static Stage stage;
    public AnchorPane scorePane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/scoreboard.fxml")));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize() {
        ArrayList<UserModel> scoreboard = MainMenuController.showScoreboard();
        ArrayList<Label> allLabels = new ArrayList<>();
        int k = 1;
        int x = 10;
        int y = 0;
        int counter = 1;
        for (UserModel eachUser : scoreboard) {
            Label label;
            label = new Label((counter + "- " + eachUser.getUsername() + ": " + eachUser.getUserScore()));
            if (MainMenuController.username.equals(eachUser.getUsername())) {
                label.setTextFill(Color.rgb(128, 128, 0));
            }
            else {
                label.setTextFill(Color.rgb(102, 51, 0));
            }
            label.setLayoutY(y);
            label.setLayoutX(x);
            label.setFont(new Font("Bodoni MT", 20));
            if (eachUser.getIsOnline()) {
                DropShadow ds = new DropShadow();
                ds.setOffsetY(10.0f);
                ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
                label.setEffect(ds);
                label.setFont(Font.font(("Bodoni MT"), FontWeight.BOLD, 20));
            }
            final ImageView[] imageView = new ImageView[1];
            int finalI = counter;
            int finalX = x;
            int finalY = y;
            label.setOnMouseEntered(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    imageView[0] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(eachUser.getImageUrl())).toExternalForm()));
                    imageView[0].setFitWidth(50);
                    imageView[0].setFitHeight(50);
                    imageView[0].setLayoutX(finalX + 140);
                    imageView[0].setLayoutY(finalY);
                    scorePane.getChildren().add(imageView[0]);
                }
            });
            label.setOnMouseExited(mouseEvent -> scorePane.getChildren().remove(imageView[0]));
            y += 40;
            if (counter == 9) {
                x += 210;
                y = 0;
            }
            allLabels.add(label);
            scorePane.getChildren().add(label);
            if (counter == 20)
                break;
            counter++;
        }
    }

    public void Back() throws Exception {
        new MainMenuView().start(stage);
    }

}
