package view;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class LobbyView extends Application {
    public Button sendBtn;
    public TextField messageTxt;
    public VBox messageVBox;
    public static ArrayList<MessageBox> allMassages = new ArrayList<>();
    public static Stage lobbyStage;
    public AnchorPane lobbyPane;
    public ScrollPane scrollPane;
    public Label findDuelistLbl;
    public RadioButton oneRoundBtn;
    public RadioButton threeRoundBtn;
    public Label headLbl;
    public Label pinHeadLbl;
    public ImageView userImage;
    public Label infoLbl;
    public AnchorPane userPane;
    public Label duelWithAILbl;
    public Label messageLbl;
    public Button cancelBtn;
    private int whichLbl;
    private static int messageCounter = 0;
    public static Label focusedLbl;
    private static int messageX = 457;
    private static int messageY = 673;
    public static LobbyView lobbyView;
    public static String pinnedMessage;



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/lobbyPage.fxml")));
        lobbyStage = primaryStage;
        lobbyStage.setScene(new Scene(root));
        lobbyStage.show();
    }

    public void initialize() {
//        scrollPane.setOnMouseClicked(mouseEvent -> deleteAllOptionBox());
//        lobbyPane.setOnMouseClicked(mouseEvent -> deleteAllOptionBox());
    }

    public void sendMessage() {
        if (sendBtn.getText().equals("Send")) {
            if (!messageTxt.getText().isEmpty()) {
                MessageBox messageBox = new MessageBox(UserModel.getUserByUsername(MainMenuController.username), messageTxt.getText());
                messageVBox.getChildren().add(messageBox.getHBox());
                lobbyPane.getChildren().add(messageBox.getOptionVBox());
                setUpActions(messageBox);
                messageTxt.clear();
                allMassages.add(messageBox);
            }
        }
        else {
            focusedLbl.setText(messageTxt.getText());
            sendBtn.setText("Send");
            messageTxt.clear();
        }
    }

    public void setUpActions(MessageBox messageBox) {
        messageBox.getOptionVBox().getChildren().get(3).setOnMouseClicked(mouseEvent -> pinHeadLbl.setText(messageBox.getMessageLbl().getText()));
        messageBox.getOptionVBox().getChildren().get(0).setOnMouseClicked(mouseEvent -> messageVBox.getChildren().remove(messageBox.getHBox()));
        messageBox.getOptionVBox().getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            sendBtn.setText("Edit");
            focusedLbl = messageBox.getMessageLbl();
        });
        messageBox.getAvatar().setOnMouseEntered(mouseEvent -> {
            userImage.setImage(messageBox.getAvatar().getImage());
            infoLbl.setText("Username: " + messageBox.getSender().getUsername() + "\nNickname: " + messageBox.getSender().getNickname() + "\nScore: " + messageBox.getSender().getUserScore());
            userPane.setVisible(true);
        });
        messageBox.getAvatar().setOnMouseExited(mouseEvent -> {
            userPane.setVisible(false);
        });
    }

    public void findADuelist() throws Exception {
        if (oneRoundBtn.isSelected()) {
            messageLbl.setText("waiting...");
            cancelBtn.setVisible(true);
            messageLbl.setCursor(Cursor.WAIT);
//            RegisterAndLoginView.dataOutputStream.writeUTF("findADuelist/" + MainMenuController.token + "/" + 1);
            FindADuelist.run(1);
//            if (FindADuelist.run(1) == 1) {
//                messageLbl.setText("Successful!");
//                //new GameMatView().start(lobbyStage);
//            }
        //    new PickFirstPlayerView().start(lobbyStage);

        }
        else if (threeRoundBtn.isSelected()) {

        }
        else {
            messageLbl.setText("Please choose a Round!");
        }
    }

    public void playWithAI() {
        if (oneRoundBtn.isSelected()) {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("PlayWithAI");
            } catch (Exception ignored) {
            }

        }
        else if (threeRoundBtn.isSelected()) {

        }
        else {
            messageLbl.setText("Please choose a Round!");
        }
    }


    public void cancel() {
        try {
            FindADuelist.setDone(true);
            RegisterAndLoginView.dataOutputStream.writeUTF("cancelGame/" + MainMenuController.token);
            messageLbl.setText("Canceled Successfully!");
            messageLbl.setCursor(Cursor.DEFAULT);
        } catch (Exception ignored) {
        }

    }

    public void back() throws Exception {
        new MainMenuView().start(lobbyStage);
    }
}
