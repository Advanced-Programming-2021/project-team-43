package view;

import controller.MainMenuController;
import controller.RegisterAndLoginController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.ChatRoom;
import model.UserModel;

import java.io.IOException;
import java.util.*;

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
    public TextField rivalUsernameTxt;
    public Label matchLbl;
    private int whichLbl;
    public MessageBox focusedMessageBox;
    private static int messageCounter = 0;
    public static Label focusedLbl;
    private static int messageX = 457;
    private static int messageY = 673;
    public static LobbyView lobbyView;
    public static String pinnedMessage;
    public static int chatRecord = 0;
    public static int nextRecord = 5;
    public static MediaPlayer note;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/lobbyPage.fxml")));
        lobbyStage = primaryStage;
        lobbyStage.setScene(new Scene(root));
        lobbyStage.show();
    }

    public void initialize() {
        messageVBox.setOnMouseEntered(mouseEvent -> {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("numberOfOnlineUser");
                RegisterAndLoginView.dataOutputStream.flush();
                headLbl.setText(RegisterAndLoginView.dataInputStream.readUTF());
            } catch (Exception ignored) {
            }
        });

    }

    public void updateOnline() {

    }


    public void sendMessage() throws IOException, ClassNotFoundException {
        Object obj;
        if (sendBtn.getText().equals("Send")) {
            if (!messageTxt.getText().isEmpty()) {
                String message = messageTxt.getText();
                new ChatRoom(UserModel.getUserByUsername(MainMenuController.username), message);
                RegisterAndLoginView.dataOutputStream.writeUTF("chat/" + MainMenuController.username + "/" + message);
                RegisterAndLoginView.dataOutputStream.flush();
                obj = RegisterAndLoginView.objectInputStream.readObject();
                String pin = RegisterAndLoginView.dataInputStream.readUTF();
                System.out.println(pin + "pinMessage");
                ChatRoom.setPinMessage(pin);
                ChatRoom.setObject((ArrayList<ChatRoom>)obj);
                updateChats();
                chatRecord++;
            }
        }
        else if (sendBtn.getText().equals("Edit")) {
            String[] split = focusedMessageBox.getMessageLbl().getText().split("\n");
            RegisterAndLoginView.dataOutputStream.writeUTF("edit/" + split[1] + "/" + focusedMessageBox.getSender().getNickname() + "/" + messageTxt.getText());
            RegisterAndLoginView.dataOutputStream.flush();
            focusedLbl.setText("(" + focusedMessageBox.getSender().getNickname() + ")\n" + messageTxt.getText());
            sendBtn.setText("Send");
            messageTxt.clear();
        }
        else {
            RegisterAndLoginView.dataOutputStream.writeUTF("reply/" + focusedMessageBox.getMessageLbl().getText());
            RegisterAndLoginView.dataOutputStream.flush();
            System.out.println(focusedMessageBox.getMessageLbl().getText());
            focusedMessageBox.getChatRoom().setReplyMessage(messageTxt.getText());
            focusedMessageBox.setReplyLbl(messageTxt.getText());
            sendBtn.setText("Send");
            messageTxt.clear();
        }
        if (chatRecord == nextRecord) {
            nextRecord *= 3;
            RegisterAndLoginView.dataOutputStream.writeUTF("chatCup/" + MainMenuController.token);
            RegisterAndLoginView.dataOutputStream.flush();
            chatCup();
        }
    }

    public void chatCup() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(480);
        anchorPane.setPrefHeight(300);
        anchorPane.setStyle("-fx-background-color: #2a0002");
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/images/cup3.jpg")).toExternalForm())));
        Label label = new Label("Congrats! You won the Chat Cup!");
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

    public void updateChats() {
        messageVBox.getChildren().clear();
        for (ChatRoom eachChat : ChatRoom.getAllChats()) {
            String message = eachChat.getMessage();
            MessageBox messageBox = new MessageBox(eachChat.getSender(), message, eachChat);
            messageVBox.getChildren().add(messageBox.getHBox());
            lobbyPane.getChildren().add(messageBox.getOptionVBox());
            setUpActions(messageBox);
            messageTxt.clear();
            allMassages.add(messageBox);
        }
        pinHeadLbl.setText(ChatRoom.getPinMessage());
    }

    public void clickOnDeleteLbl(MessageBox messageBox) {
        messageBox.getOptionVBox().getChildren().get(0).setOnMouseClicked(mouseEvent -> {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("delete/" + messageBox.getMessageLbl().getText() + "/" + messageBox.getSender().getNickname());
                RegisterAndLoginView.dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageVBox.getChildren().remove(messageBox.getHBox());
        });
    }

    public void clickOnEditLbl(MessageBox messageBox) {
        messageBox.getOptionVBox().getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            sendBtn.setText("Edit");
            focusedMessageBox = messageBox;
            focusedLbl = messageBox.getMessageLbl();
        });
    }

    public void clickOnReplyLbl(MessageBox messageBox) {
        messageBox.getOptionVBox().getChildren().get(2).setOnMouseClicked(mouseEvent -> {
            sendBtn.setText("Reply");
            focusedLbl = messageBox.getMessageLbl();
            focusedMessageBox = messageBox;
        });
    }

    public void clickOnPinLbl(MessageBox messageBox) {
        messageBox.getOptionVBox().getChildren().get(3).setOnMouseClicked(mouseEvent -> {
            String pinMessage = messageBox.getMessageLbl().getText();
            try {
                String[] split = pinMessage.split("\n");
                RegisterAndLoginView.dataOutputStream.writeUTF("pinMessage/" + split[1]);
                RegisterAndLoginView.dataOutputStream.flush();
                ChatRoom.setPinMessage(split[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pinHeadLbl.setText(ChatRoom.getPinMessage());
        });
    }

    public void clickOnAvatar(MessageBox messageBox) {
        messageBox.getAvatar().setOnMouseEntered(mouseEvent -> {
            userImage.setImage(messageBox.getAvatar().getImage());
            infoLbl.setText("Username: " + messageBox.getSender().getUsername() + "\nNickname: " + messageBox.getSender().getNickname() + "\nScore: " + messageBox.getSender().getUserScore());
            userPane.setVisible(true);
        });
        messageBox.getAvatar().setOnMouseExited(mouseEvent -> {
            userPane.setVisible(false);
        });
    }

    public void setUpActions(MessageBox messageBox) {
        clickOnDeleteLbl(messageBox);
        clickOnEditLbl(messageBox);
        clickOnReplyLbl(messageBox);
        clickOnPinLbl(messageBox);
        clickOnAvatar(messageBox);
    }

    public void findADuelist() throws Exception {
        if (oneRoundBtn.isSelected()) {
            messageLbl.setText("waiting...");
            cancelBtn.setVisible(true);
            messageLbl.setCursor(Cursor.WAIT);
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("findADuelist/" + MainMenuController.token + "/" + 1);
                RegisterAndLoginView.dataOutputStream.flush();
                RegisterAndLoginView.dataInputStream.readUTF();
            } catch (Exception ignored) {

            }

        }
        else if (threeRoundBtn.isSelected()) {

        }
        else {
            messageLbl.setText("Please choose a Round!");
        }
    }

    public void match() {
        String rivalUsername = rivalUsernameTxt.getText();
        Object obj;
        HashMap<String, String> x;
        System.out.println("before read obj");
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("match/" + MainMenuController.token + "/" + rivalUsername);
            RegisterAndLoginView.dataOutputStream.flush();
            System.out.println("befre data read");
            String result = RegisterAndLoginView.dataInputStream.readUTF();
            if (result.equals("success")) {
                System.out.println("before obj read");
                obj = RegisterAndLoginView.objectInputStream.readObject();
                UserModel.setObject((UserModel) obj);
                System.out.println(((UserModel) obj).getNickname());
            }
            matchLbl.setText(result);
        } catch (Exception e) {

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
////////////
    public void cancel() {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("cancelGame/" + MainMenuController.token);
            messageLbl.setText("Canceled Successfully!");
            messageLbl.setCursor(Cursor.DEFAULT);
        } catch (Exception ignored) {
        }

    }
///////////////////////

    public void goToInvitations() throws Exception {
        new InvitationView().start(lobbyStage);
    }

    public void back() throws Exception {
        new MainMenuView().start(lobbyStage);
    }

}
