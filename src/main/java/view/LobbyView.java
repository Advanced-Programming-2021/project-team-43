package view;

import controller.MainMenuController;
import controller.RegisterAndLoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

    public void sendMessage() throws IOException, ClassNotFoundException {
        Object obj;
        if (sendBtn.getText().equals("Send")) {
            if (!messageTxt.getText().isEmpty()) {
                String message = messageTxt.getText();
                new ChatRoom(UserModel.getUserByUsername(MainMenuController.username), message);
                RegisterAndLoginView.dataOutputStream.writeUTF("chat/" + MainMenuController.username + "/" + message);
                RegisterAndLoginView.dataOutputStream.flush();
                RegisterAndLoginView.dataInputStream.readUTF();
                obj = RegisterAndLoginView.objectInputStream.readObject();
                ChatRoom.setObject((ArrayList<ChatRoom>)obj);
                updateChats();
            }
        }
        else {
            focusedLbl.setText(messageTxt.getText());
            sendBtn.setText("Send");
            messageTxt.clear();
        }
    }

    public void updateChats() {
        messageVBox.getChildren().clear();
        for (ChatRoom eachChat : ChatRoom.getAllChats()) {
            String message = eachChat.getMessage();
            MessageBox messageBox = new MessageBox(eachChat.getSender(), message);
            messageVBox.getChildren().add(messageBox.getHBox());
            lobbyPane.getChildren().add(messageBox.getOptionVBox());
            setUpActions(messageBox);
            messageTxt.clear();
            allMassages.add(messageBox);
        }
    }

    public void setUpActions(MessageBox messageBox) {
        //delete
        messageBox.getOptionVBox().getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    RegisterAndLoginView.dataOutputStream.writeUTF("delete/" + messageBox.getMessageLbl().getText());
                    RegisterAndLoginView.dataOutputStream.flush();
                    RegisterAndLoginView.objectOutputStream.writeUnshared(messageBox.getSender());
                    RegisterAndLoginView.objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messageVBox.getChildren().remove(messageBox.getHBox());
            }
        });
        //edit
        messageBox.getOptionVBox().getChildren().get(1).setOnMouseClicked(mouseEvent -> {
//            RegisterAndLoginView.dataOutputStream.writeUTF("edit/" + messageBox.getMessageLbl().getText());
//            RegisterAndLoginView.dataOutputStream.flush();
            sendBtn.setText("Edit");
            focusedLbl = messageBox.getMessageLbl();
        });

        //reply




        //pin
        messageBox.getOptionVBox().getChildren().get(3).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String pinMessage = messageBox.getMessageLbl().getText();
                try {
                    RegisterAndLoginView.dataOutputStream.writeUTF("pinMessage/" + pinMessage);
                    RegisterAndLoginView.dataOutputStream.flush();
                    ChatRoom.setPinMessage(RegisterAndLoginView.dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pinHeadLbl.setText(pinMessage);
            }
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
    public void back() throws Exception {
        new MainMenuView().start(lobbyStage);
    }

}
