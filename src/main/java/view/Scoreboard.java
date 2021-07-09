package view;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.UserModel;
import java.util.ArrayList;
import java.util.Objects;



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
        ArrayList<Label> list = new ArrayList<>();
        ArrayList<String> users = MainMenuController.showScoreboard();
        int k = 1;
        int x = 10;
        int y = 0;
        for (int i = 0; i < users.size(); i++) {
            if (i == 19)
                break;
            Label label;
            if (MainMenuController.username.equals(users.get(i))) {
                label = new Label(((i + 1) + "- " + UserModel.getUserByUsername(users.get(i)).getNickname() + ": " + UserModel.getUserByUsername(users.get(i)).getUserScore()));
                label.setTextFill(Color.rgb(128, 128, 0));
            } else {
                if (i != 0 && UserModel.getUserByUsername(users.get(i)).getUserScore() == UserModel.getUserByUsername(users.get(i - 1)).getUserScore())
                    k--;
                label = new Label(((k) + "- " + UserModel.getUserByUsername(users.get(i)).getNickname() + ": " + UserModel.getUserByUsername(users.get(i)).getUserScore()));
                label.setTextFill(Color.rgb(102, 51, 0));
            } list.add(label);
            list.get(i).setLayoutY(y);
            list.get(i).setLayoutX(x);
            list.get(i).setFont(new Font("Bodoni MT",20));
            int finalI = i;
            int finalX = x;
            int finalY = y;
            final ImageView[] imageView = new ImageView[1];
            list.get(i).setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    imageView[0] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByUsername(users.get(finalI)).getImageUrl())).toExternalForm()));
                    imageView[0].setFitWidth(50);
                    imageView[0].setFitHeight(50);
                    imageView[0].setLayoutX(finalX + 140);
                    imageView[0].setLayoutY(finalY);
                    scorePane.getChildren().add(imageView[0]);
                }
            });
            list.get(i).setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    scorePane.getChildren().remove(imageView[0]);
                }
            });
            y += 40;
            if (i == 9) {
                x += 210;
                y = 0;
            }
            scorePane.getChildren().add(list.get(i));
            k++;
        }
    }

    public void Back() throws Exception {
        new MainMenuView().start(stage);
    }

}
