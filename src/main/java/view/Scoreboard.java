package view;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        showScoreboard();
        refreshAutomatically();
    }

    public void showScoreboard() {
        ArrayList<UserModel> scoreboard = MainMenuController.showScoreboard();
        for (UserModel userModel : scoreboard) {
            UserModel.setObject(userModel);
        }
        HashMap<String, String> allOnlineUsers = null;
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("allOnlineUsers");
            RegisterAndLoginView.dataOutputStream.flush();
            allOnlineUsers = (HashMap<String, String>) RegisterAndLoginView.objectInputStream.readObject();
        } catch (Exception ignored) {
        }
        int x = 10;
        int y = 0;
        int counter = 1;
        for (int i = 0; i < scoreboard.size(); i++) {
            Label label;
            label = new Label((counter + "- " + scoreboard.get(i).getUsername() + ": " + scoreboard.get(i).getUserScore()));
            if (MainMenuController.username.equals(scoreboard.get(i).getUsername())) {
                label.setTextFill(Color.rgb(128, 128, 0));
            }
            else {
                label.setTextFill(Color.rgb(102, 51, 0));
            }
            label.setLayoutY(y);
            label.setLayoutX(x);
            label.setFont(new Font("Bodoni MT", 20));
            if (allOnlineUsers.containsValue(scoreboard.get(i).getUsername())) {
                label.setUnderline(true);
            }
            final ImageView[] imageView = new ImageView[1];
            int finalX = x;
            int finalY = y;
            int finalI = i;
            label.setOnMouseEntered(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    imageView[0] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(scoreboard.get(finalI).getImageUrl())).toExternalForm()));
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
            scorePane.getChildren().add(label);
            if (counter == 20)
                break;
            if (i + 1 < scoreboard.size() && (scoreboard.get(i).getUserScore() != scoreboard.get(i + 1).getUserScore()))
                counter++;
        }
    }



    public void refreshPage() {
        showScoreboard();
    }

    public void refreshAutomatically() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> showScoreboard());
            }
        }, 0, 2000);
    }

    public void back() throws Exception {
        new MainMenuView().start(stage);
    }

}