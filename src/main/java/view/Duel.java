package view;
import controller.GameMatController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Duel extends Application {

    public Button AIBtt;
    public TextField playerId;
    public Button duelBtt;
    public static Stage duelStage;
    public Label text;
    public TextField number;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/duel.fxml")));
        stage.setScene(new Scene(root));
        duelStage = stage;
        stage.show();
    }

    public void duel() throws Exception {
        RegisterAndLoginView.dataOutputStream.writeUTF("duel/" + MainMenuController.username + "/" + MainMenuController.token);
        RegisterAndLoginView.dataOutputStream.flush();
        RegisterAndLoginView.dataInputStream.readUTF();
        GameMatController.rivalToken = RegisterAndLoginView.dataInputStream.readUTF();
        GameMatController.onlineToken = MainMenuController.token;
        Object obj = RegisterAndLoginView.objectInputStream.readObject();
        UserModel.setObject((UserModel) obj);
        System.out.println(GameMatController.rivalToken + "rivaltoken");
        System.out.println(GameMatController.onlineToken + "onlinetoken");
        new GameMatView().start(duelStage);
    }

    public void AIDuel() throws Exception {
        if (number.getText().isEmpty()) {
            text.setText("pls enter round number");
        } else {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(number.getText());
            if (matcher.find()) {
                text.setText(MainMenuController.duelMenu("AI", Integer.parseInt(matcher.group(1))));
                if (text.getText().equals("00")) {
                    new PickFirstPlayerView().start(duelStage);
                }
            } else {
                text.setText("invalid round number");
            }
        }
    }


    public void pressBackBtn() throws Exception {
        new MainMenuView().start(duelStage);
    }

}