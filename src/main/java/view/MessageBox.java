package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    private int id;
    private static int idCounter = 0;
    public static ArrayList<MessageBox> allMassages = new ArrayList<>();


    public MessageBox(UserModel sender, String message) {
        id = idCounter++;
        this.sender = sender;
        avatar = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(sender.getImageUrl())).toExternalForm()));
        setMessageLblStyle(message);
        hBox = new HBox(avatar, messageLbl);
        hBox.setSpacing(5);
        optionVBox = new VBox();
        optionVBox.setAlignment(Pos.CENTER);
        optionVBox.setVisible(false);
        optionVBox.setOnMouseExited(mouseEvent -> optionVBox.setVisible(false));
        optionVBox.setOnMouseEntered(mouseEvent -> optionVBox.setVisible(true));
        createLabels();
        setImageSize(30, 30);
        clickOnImage();
        setUpActions();
        optionVBox.getChildren().addAll(deleteLbl, editLbl, replyLbl, pinLbl);
    }

    public Label getMessageLbl() {
        return messageLbl;
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
        messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color: #66ffff");
        messageLbl.setPrefWidth(300);
        messageLbl.setPrefHeight(35);
        messageLbl.setWrapText(true);
        messageLbl.setOnMouseClicked(mouseEvent -> clickOnMessage());
        messageLbl.setOnMouseExited(mouseEvent -> removeOptionBox());
    }

    public void setLabelStyle(Label lbl) {
        lbl.setTextFill(Color.WHITE);
        lbl.setFont(new Font("Bodoni MT", 16));
    }

    public VBox getOptionVBox() {
        return optionVBox;
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

    public ImageView getAvatar() {
        return avatar;
    }

    public UserModel getSender() {
        return sender;
    }

    public void clickOnImage() {
        avatar.setOnMouseClicked(mouseEvent -> {

        });
    }

    public void deleteMessage() {
        allMassages.remove(this);
        LobbyView.allMassages.remove(id);
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

    public HBox getHBox() {
        return hBox;
    }

    public void removeOptionBox() {
        optionVBox.setVisible(false);
    }


    public static MessageBox getMessageBoxByIndex(int index) {
        return allMassages.get(index);
    }
}
