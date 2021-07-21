package view;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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
import java.util.ArrayList;
import java.util.Objects;


public class ChatRoomView extends Application {

    public Label headLbl;
    public Label pinHeadLbl;
    public ImageView userImage;
    public Label infoLbl;
    public AnchorPane userPane;
    public ScrollPane scrollPane;
    public TextField messageTxt;
    public VBox messageVBox;
    public Button sendBtn;
    public AnchorPane chatRoomPane;
    private static Stage chatRoomStage;
    public MessageBox focusedMessageBox;
    public static Label focusedLbl;
    public static String pinnedMessage;
    public static int chatRecord;
    public static int nextRecord;
    public static MediaPlayer note;
    public static ArrayList<MessageBox> allMassages = new ArrayList<>();
    public static ChatRoomView chatRoomView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/chatroomPage.fxml")));
        chatRoomStage = primaryStage;
        chatRoomStage.setScene(new Scene(root));
        chatRoomStage.show();
    }

    public void initialize() {
        messageVBox.setOnMouseEntered(mouseEvent -> {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("numberOfOnlineUser");
                RegisterAndLoginView.dataOutputStream.flush();
                headLbl.setText( RegisterAndLoginView.dataInputStream.readUTF());
            } catch (Exception ignored) {
            }
        });
        chatRoomPane.setOnMouseClicked(mouseEvent -> {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("pinAndChatRecord/" + MainMenuController.token);
                RegisterAndLoginView.dataOutputStream.flush();
                String result = RegisterAndLoginView.dataInputStream.readUTF();
                String[] split = result.split("/");
                if (result.startsWith("!!")) {
                    ChatRoom.setPinMessage("");
                }
                else {
                    ChatRoom.setPinMessage(split[0]);
                }
                chatRecord = Integer.parseInt(split[1]);
                pinHeadLbl.setText(ChatRoom.getPinMessage());
            } catch (Exception ignored) {
            }
        });
        if (chatRecord != 0)
            nextRecord = chatRecord * 3;
        else
            nextRecord = 5;
    }

    public void sendMessage() throws IOException, ClassNotFoundException {
        Object obj;
        if (sendBtn.getText().equals("Send")) {
            if (!messageTxt.getText().isEmpty()) {
                String message = messageTxt.getText();
                new ChatRoom(UserModel.getUserByUsername(MainMenuController.username), message);
                RegisterAndLoginView.dataOutputStream.writeUTF("chat/" + MainMenuController.token + "/" + message + "/" + chatRecord);
                RegisterAndLoginView.dataOutputStream.flush();
                ChatRoom.setObject((ArrayList<ChatRoom>)RegisterAndLoginView.objectInputStream.readObject());
                updateChats();
                chatRecord++;
            }
        }
        else if (sendBtn.getText().equals("Edit")) {
            String[] split = focusedMessageBox.getMessageLbl().getText().split("\n");
            RegisterAndLoginView.dataOutputStream.writeUTF("edit/" + split[1] + "/" + focusedMessageBox.getSender().getNickname() + "/" + messageTxt.getText());
            RegisterAndLoginView.dataOutputStream.flush();
            focusedLbl.setText("-" + focusedMessageBox.getSender().getNickname() + "-\n" + messageTxt.getText());
            sendBtn.setText("Send");
            messageTxt.clear();
        }
        else {
            String[] message = focusedMessageBox.getMessageLbl().getText().split("\n");
            String[] split = message[0].split("-");
            RegisterAndLoginView.dataOutputStream.writeUTF("reply/" + message[1] + "/" + messageTxt.getText() + "/" + split[1]);
            RegisterAndLoginView.dataOutputStream.flush();
            focusedMessageBox.getChatRoom().setReplyMessage(messageTxt.getText());
            focusedMessageBox.setReplyLbl(messageTxt.getText());
            sendBtn.setText("Send");
            messageTxt.clear();
        }
        if (chatRecord == nextRecord) {
            nextRecord *= 3;
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
        int counter = 1;
        for (ChatRoom eachChat : ChatRoom.getAllChats()) {
            if (counter == 16)
                break;
            String message = eachChat.getMessage();
            MessageBox messageBox = new MessageBox(eachChat.getSender(), message, eachChat);
            messageVBox.getChildren().add(messageBox.getHBox());
            chatRoomPane.getChildren().add(messageBox.getOptionVBox());
            setUpActions(messageBox);
            messageTxt.clear();
            allMassages.add(messageBox);
            counter++;
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

    public void back() throws Exception {
        new MainMenuView().start(chatRoomStage);
    }

}