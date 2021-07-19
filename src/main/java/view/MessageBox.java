package view;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.ChatRoom;
import model.UserModel;
import java.util.ArrayList;
import java.util.Objects;


public class MessageBox {

    private HBox hBox;
    private Label messageLbl;
    private final ImageView avatar;
    private UserModel sender;
    private VBox optionVBox;
    private Label deleteLbl;
    private Label editLbl;
    private Label replyLbl;
    private Label pinLbl;
    private Label replyMessage;
    private ChatRoom chatRoom;
    public static ArrayList<MessageBox> allMassages = new ArrayList<>();


    public MessageBox(UserModel sender, String message, ChatRoom chatRoom) {
        this.sender = sender;
        this.chatRoom = chatRoom;
        avatar = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(sender.getImageUrl())).toExternalForm()));
        setImageSize(30, 30);
        setMessageLblStyle(message);
        createLabels();
        setUpActions();
        setUpReplyLbl();
        setUpOptionVBox();
        hBox = new HBox(avatar, messageLbl);
        hBox.setSpacing(5);
        hBox.getChildren().add(replyMessage);
    }

    public Label getMessageLbl() {
        return messageLbl;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setReplyLbl(String message) {
        replyMessage.setText(message);
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public VBox getOptionVBox() {
        return optionVBox;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public HBox getHBox() {
        return hBox;
    }

    public void deleteMessage() {
        allMassages.remove(this);
        LobbyView.lobbyView.messageVBox.getChildren().remove(hBox);
    }

    public void editMessage() {
        // messageLbl.setText(newMessage);
    }

    public void reply() {

    }

    public void pin() {
        LobbyView.pinnedMessage = messageLbl.getText();
    }

    public void removeOptionBox() {
        optionVBox.setVisible(false);
    }

    public void createLabels() {
        deleteLbl = new Label("Delete");
        editLbl = new Label("Edit");
        replyLbl = new Label("Reply");
        pinLbl = new Label("Pin");
        setLabelStyle(deleteLbl);
        setLabelStyle(replyLbl);
        setLabelStyle(pinLbl);
        setLabelStyle(editLbl);
    }

    public void setUpOptionVBox() {
        optionVBox = new VBox();
        optionVBox.setAlignment(Pos.CENTER);
        optionVBox.setVisible(false);
        optionVBox.setOnMouseExited(mouseEvent -> optionVBox.setVisible(false));
        optionVBox.setOnMouseEntered(mouseEvent -> optionVBox.setVisible(true));
        optionVBox.getChildren().addAll(deleteLbl, editLbl, replyLbl, pinLbl);
    }

    public void setUpReplyLbl() {
        if (ChatRoom.getReplyMessageBySenderAndMessage(sender, messageLbl.getText()) != null)
            replyMessage = new Label(ChatRoom.getReplyMessageBySenderAndMessage(sender, messageLbl.getText()));
        else
            replyMessage = new Label("");
        replyMessage.setVisible(false);
        replyMessage.setPrefWidth(300);
        replyMessage.setPrefHeight(35);
        replyMessage.setWrapText(true);
        replyMessage.setStyle("-fx-background-color: #008080");
        replyMessage.setTextFill(Color.WHITE);
    }

    public void setImageSize(int width, int height) {
        avatar.setFitWidth(width);
        avatar.setFitHeight(height);
    }

    public void setUpActions() {
        deleteLbl.setOnMouseClicked(mouseEvent -> deleteMessage());
        editLbl.setOnMouseClicked(mouseEvent -> editMessage());
        replyLbl.setOnMouseClicked(mouseEvent -> reply());
        pinLbl.setOnMouseClicked(mouseEvent -> pin());
    }

    public void setMessageLblStyle(String message) {
        messageLbl = new Label("(" + sender.getNickname() + ")\n" + message);
        messageLbl.setStyle("-fx-background-color: #66ffff");
        messageLbl.setPrefWidth(500);
        messageLbl.setPrefHeight(35);
        messageLbl.setWrapText(true);
        messageLbl.setOnMouseClicked(mouseEvent -> {
            clickOnMessage();
            replyMessage.setVisible(false);
        });
        messageLbl.setOnMouseExited(mouseEvent -> {
            removeOptionBox();
            replyMessage.setVisible(false);
        });
        messageLbl.setOnMouseEntered(mouseEvent -> {
            replyMessage.setVisible(!replyMessage.getText().equals(""));
        });
    }

    public void setLabelStyle(Label lbl) {
        lbl.setTextFill(Color.WHITE);
        lbl.setFont(new Font("Bodoni MT", 16));
    }

    public void clickOnMessage() {
        LobbyView.focusedLbl = messageLbl;
        optionVBox.setPrefWidth(53);
        optionVBox.setPrefHeight(101);
        optionVBox.setStyle("-fx-background-color: #003333");
        optionVBox.setVisible(true);
        optionVBox.setLayoutY(hBox.getLayoutY() + 30);
        optionVBox.setLayoutX(hBox.getLayoutX() + 30);
    }

    public static MessageBox getMessageBoxByIndex(int index) {
        return allMassages.get(index);
    }

}