package view;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InvitationView extends Application {

    public VBox vBox;
    private static Stage invitationStage;
    public Button sendBtn;
    public TextField rivalNameTxt;
    public Label messageLbl;
    private HBox focusedHBox;
    private int whichInvitation;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/invitationPage.fxml")));
        invitationStage = primaryStage;
        invitationStage.setScene(new Scene(root));
        invitationStage.show();
    }

    public void initialize() {
        showInvitations();
    }

    public void showInvitations() {
        HashMap<Integer, String> myInvitations = null;
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("myInvitations/" + MainMenuController.username);
            RegisterAndLoginView.dataOutputStream.flush();
            System.out.println("[[[[[[[[[[[[");
            myInvitations = (HashMap<Integer, String>) RegisterAndLoginView.objectInputStream.readObject();
            System.out.println(myInvitations.size());
        } catch (Exception ignored) {
        }
        if (myInvitations != null) {
            for (Map.Entry<Integer, String> eachOne : myInvitations.entrySet()) {
                Label label = new Label("Hi! " + MainMenuController.username + ", I dare to challenge you!\nDuel with me to see who the King is!\n" + eachOne.getValue() + ":)");
                Button accept = new Button("Accept");
                Button reject = new Button("Reject");
                accept.setFocusTraversable(false);
                reject.setFocusTraversable(false);
                HBox hBox = new HBox();
                hBox.setSpacing(5);
                hBox.getChildren().addAll(label, accept, reject);
                focusedHBox = hBox;
                whichInvitation = eachOne.getKey();
                vBox.getChildren().add(hBox);
                buttonActions(accept ,reject);
            }
        }

    }

    public void buttonActions(Button accept, Button reject) {
        accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vBox.getChildren().remove(focusedHBox);
                try {
                    RegisterAndLoginView.dataOutputStream.writeUTF("acceptInvitation/" + MainMenuController.username + "/" + whichInvitation);
                    RegisterAndLoginView.dataOutputStream.flush();
                } catch (Exception ignored) {
                }
            }
        });
        reject.setOnMouseClicked(mouseEvent -> {
            vBox.getChildren().remove(focusedHBox);
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("rejectInvitation/" + MainMenuController.username + "/" + whichInvitation);
                RegisterAndLoginView.dataOutputStream.flush();
            } catch (Exception ignored) {
            }
        });
    }

    public void send() {
        if (!rivalNameTxt.getText().equals("")) {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("sendInvitation/" + MainMenuController.username + "/" + rivalNameTxt.getText());
                RegisterAndLoginView.dataOutputStream.flush();
                String result = RegisterAndLoginView.dataInputStream.readUTF();
                System.out.println(result);
                messageLbl.setText(result);
            } catch (Exception ignored) {
            }
        }
    }


    public void back() throws Exception {
        new LobbyView().start(invitationStage);
    }

}
