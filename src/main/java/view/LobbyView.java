package view;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class LobbyView extends Application {

    public AnchorPane lobbyPane;
    public Label findDuelistLbl;
    public RadioButton oneRoundBtn;
    public RadioButton threeRoundBtn;
    public Label duelWithAILbl;
    public Label messageLbl;
    public Button cancelBtn;
    public Label errorLbl;
    public Button sendBtn;
    public TextField rivalNameTxt;
    public VBox vBox;
    public Label invitationLbl;
    public Button acceptBtn;
    public Button refuseBtn;
    private HBox focusedHBox;
    private int whichInvitation;
    public static Stage lobbyStage;
    private boolean isCanceled;
    private static String split1 = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/lobbyPage.fxml")));
        lobbyStage = primaryStage;
        lobbyStage.setScene(new Scene(root));
        lobbyStage.show();
    }

    public void initialize() {
        ///showInvitations();
    }

    public void findADuelist() {
        if (oneRoundBtn.isSelected()) {
            messageLbl.setText("waiting...");
            cancelBtn.setVisible(true);
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("findADuelist/" + MainMenuController.token + "/" + 1);
                RegisterAndLoginView.dataOutputStream.flush();
                String answer = RegisterAndLoginView.dataInputStream.readUTF();

                if (answer.equals("failed")) {
                    System.out.println("failed  ss");
                    while (!isCanceled) {
                        RegisterAndLoginView.dataOutputStream.writeUTF("isFind:" + MainMenuController.token + ":" + 1);
                        RegisterAndLoginView.dataOutputStream.flush();
                        String[] answer2 = RegisterAndLoginView.dataInputStream.readUTF().split(":");


                        if (answer2[0].equals("true")) {
                            RegisterAndLoginView.dataOutputStream.writeUTF("pickPlayer:" + MainMenuController.token + ":" + answer2[1] + ":" + 1);
                            RegisterAndLoginView.dataOutputStream.flush();
                            ArrayList<Object> answer3 = (ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject();
                            GameMatController.onlineToken = (String) answer3.get(0);
                            GameMatController.rivalToken = (String) answer3.get(1);
                            Player playerOne = (Player) answer3.get(2);
                            Player playerTwo = (Player) answer3.get(3);
                            UserModel userOne = (UserModel) answer3.get(4);
                            UserModel userTwo = (UserModel) answer3.get(5);
                            ///

                            if (userTwo.getNickname().equals(MainMenuController.username)) {
                                MainMenuController.username2 = userOne.getNickname();

                            } else
                                MainMenuController.username2 = userTwo.getNickname();
                            //
                            UserModel.setObject(userOne);
                            UserModel.setObject(userTwo);
                            Player.setObject(userOne.getNickname(), playerOne);
                            Player.setObject(userTwo.getNickname(), playerTwo);
                            GameMatController.onlineUser = userOne.getNickname();
                            GameMatController.rivalUser = userTwo.getNickname();
                            GameMatModel.playerGameMat = (HashMap<String, GameMatModel>) answer3.get(6);
                            HandCardZone.allHandCards = (Map<String, List<HandCardZone>>) answer3.get(7);
                            MonsterZoneCard.allMonsterCards = (Map<String, Map<Integer, MonsterZoneCard>>) answer3.get(8);
                            SpellTrapZoneCard.allSpellTrapCards = (Map<String, Map<Integer, SpellTrapZoneCard>>) answer3.get(9);

                            /////////
                            ArrayList<Object> newArray = new ArrayList<>();
                            for (int i = 10; i < 22; i++) {
                                newArray.add(answer3.get(i));
                            }
                            GameMatController.setObjects(newArray);
                            /////

                            new GameMatView().start(lobbyStage);
                            break;
                        }

                    }
                } else if (answer.startsWith("invitation")) {
                    String[] split = answer.split(":");

                    invitationLbl.setText(split[2] + " has requested to play with you, do you want too?");
                    split1 = split[1];

                } else {
                    System.out.println("success");

                    RegisterAndLoginView.dataOutputStream.writeUTF("pickPlayer:" + MainMenuController.token + ":" + answer + ":" + 1);
                    RegisterAndLoginView.dataOutputStream.flush();
                    ArrayList<Object> answer3 = (ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject();
                    GameMatController.onlineToken = (String) answer3.get(0);
                    GameMatController.rivalToken = (String) answer3.get(1);
                    Player playerOne = (Player) answer3.get(2);
                    Player playerTwo = (Player) answer3.get(3);
                    UserModel userOne = (UserModel) answer3.get(4);
                    UserModel userTwo = (UserModel) answer3.get(5);
                    ///
                    if (userTwo.getNickname().equals(MainMenuController.username)) {
                        MainMenuController.username2 = userOne.getNickname();

                    } else
                        MainMenuController.username2 = userTwo.getNickname();
                    //
                    UserModel.setObject(userOne);
                    UserModel.setObject(userTwo);
                    Player.setObject(userOne.getNickname(), playerOne);
                    Player.setObject(userTwo.getNickname(), playerTwo);
                    GameMatController.onlineUser = userOne.getNickname();
                    GameMatController.rivalUser = userTwo.getNickname();
                    GameMatModel.playerGameMat = (HashMap<String, GameMatModel>) answer3.get(6);
                    HandCardZone.allHandCards = (Map<String, List<HandCardZone>>) answer3.get(7);
                    MonsterZoneCard.allMonsterCards = (Map<String, Map<Integer, MonsterZoneCard>>) answer3.get(8);
                    SpellTrapZoneCard.allSpellTrapCards = (Map<String, Map<Integer, SpellTrapZoneCard>>) answer3.get(9);
                    /////////
                    ArrayList<Object> newArray = new ArrayList<>();
                    for (int i = 10; i < 22; i++) {
                        newArray.add(answer3.get(i));
                    }
                    GameMatController.setObjects(newArray);
                    /////
                    new GameMatView().start(lobbyStage);
                }
            } catch (Exception ignored) {
            }


        } else if (threeRoundBtn.isSelected()) {
/////\
            messageLbl.setText("waiting...");
            cancelBtn.setVisible(true);
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("findADuelist/" + MainMenuController.token + "/" + 3);
                RegisterAndLoginView.dataOutputStream.flush();
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                if (answer.equals("failed")) {
                    System.out.println("failed  ss");
                    while (!isCanceled) {
                        RegisterAndLoginView.dataOutputStream.writeUTF("isFind:" + MainMenuController.token + ":" + 3);
                        RegisterAndLoginView.dataOutputStream.flush();
                        String[] answer2 = RegisterAndLoginView.dataInputStream.readUTF().split(":");
                        cancelBtn.setVisible(true);
                        if (answer2[0].equals("true")) {
                            RegisterAndLoginView.dataOutputStream.writeUTF("pickPlayer:" + MainMenuController.token + ":" + answer2[1] + ":" + 3);
                            RegisterAndLoginView.dataOutputStream.flush();
                            ArrayList<Object> answer3 = (ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject();
                            GameMatController.onlineToken = (String) answer3.get(0);
                            GameMatController.rivalToken = (String) answer3.get(1);
                            Player playerOne = (Player) answer3.get(2);
                            Player playerTwo = (Player) answer3.get(3);
                            UserModel userOne = (UserModel) answer3.get(4);
                            UserModel userTwo = (UserModel) answer3.get(5);
                            ///
                            if (userTwo.getNickname().equals(MainMenuController.username)) {
                                MainMenuController.username2 = userOne.getNickname();

                            } else
                                MainMenuController.username2 = userTwo.getNickname();
                            //
                            UserModel.setObject(userOne);
                            UserModel.setObject(userTwo);
                            Player.setObject(userOne.getNickname(), playerOne);
                            Player.setObject(userTwo.getNickname(), playerTwo);
                            GameMatController.onlineUser = userOne.getNickname();
                            GameMatController.rivalUser = userTwo.getNickname();
                            GameMatModel.playerGameMat = (HashMap<String, GameMatModel>) answer3.get(6);
                            HandCardZone.allHandCards = (Map<String, List<HandCardZone>>) answer3.get(7);
                            MonsterZoneCard.allMonsterCards = (Map<String, Map<Integer, MonsterZoneCard>>) answer3.get(8);
                            SpellTrapZoneCard.allSpellTrapCards = (Map<String, Map<Integer, SpellTrapZoneCard>>) answer3.get(9);

                            /////////
                            ArrayList<Object> newArray = new ArrayList<>();
                            for (int i = 10; i < 22; i++) {
                                newArray.add(answer3.get(i));
                            }
                            GameMatController.setObjects(newArray);
                            /////

                            new GameMatView().start(lobbyStage);
                            break;
                        }

                    }
                } else if (answer.startsWith("invitation")) {
                    String[] split = answer.split(":");
                    invitationLbl.setText(split[2] + " has requested to play with you, do you want too?");
                    split1 = split[1];

                } else {
                    System.out.println("success");

                    RegisterAndLoginView.dataOutputStream.writeUTF("pickPlayer:" + MainMenuController.token + ":" + answer + ":" + 3);
                    RegisterAndLoginView.dataOutputStream.flush();
                    ArrayList<Object> answer3 = (ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject();
                    GameMatController.onlineToken = (String) answer3.get(0);
                    GameMatController.rivalToken = (String) answer3.get(1);
                    Player playerOne = (Player) answer3.get(2);
                    Player playerTwo = (Player) answer3.get(3);
                    UserModel userOne = (UserModel) answer3.get(4);
                    UserModel userTwo = (UserModel) answer3.get(5);
                    ///
                    if (userTwo.getNickname().equals(MainMenuController.username)) {
                        MainMenuController.username2 = userOne.getNickname();

                    } else
                        MainMenuController.username2 = userTwo.getNickname();
                    //
                    UserModel.setObject(userOne);
                    UserModel.setObject(userTwo);
                    Player.setObject(userOne.getNickname(), playerOne);
                    Player.setObject(userTwo.getNickname(), playerTwo);
                    GameMatController.onlineUser = userOne.getNickname();
                    GameMatController.rivalUser = userTwo.getNickname();
                    GameMatModel.playerGameMat = (HashMap<String, GameMatModel>) answer3.get(6);
                    HandCardZone.allHandCards = (Map<String, List<HandCardZone>>) answer3.get(7);
                    MonsterZoneCard.allMonsterCards = (Map<String, Map<Integer, MonsterZoneCard>>) answer3.get(8);
                    SpellTrapZoneCard.allSpellTrapCards = (Map<String, Map<Integer, SpellTrapZoneCard>>) answer3.get(9);
                    /////////
                    ArrayList<Object> newArray = new ArrayList<>();
                    for (int i = 10; i < 22; i++) {
                        newArray.add(answer3.get(i));
                    }
                    GameMatController.setObjects(newArray);
                    /////
                    new GameMatView().start(lobbyStage);
                }
            } catch (Exception ignored) {
            }


            ////
        } else {
            messageLbl.setText("Please choose a Round!");
        }
    }

    public void playWithAI() {
        if (oneRoundBtn.isSelected()) {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("PlayWithAI");
            } catch (Exception ignored) {
            }

        } else if (threeRoundBtn.isSelected()) {

        } else {
            messageLbl.setText("Please choose a Round!");
        }
    }

    public void cancel() {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("cancelGame/" + MainMenuController.token);
            messageLbl.setText("Canceled Successfully!");
            // messageLbl.setCursor(Cursor.DEFAULT);
            isCanceled = true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        isCanceled = false;
    }

//    public void showInvitations() {
//        HashMap<Integer, String> myInvitations = null;
//        try {
//            RegisterAndLoginView.dataOutputStream.writeUTF("myInvitations/" + MainMenuController.username);
//            RegisterAndLoginView.dataOutputStream.flush();
//            myInvitations = (HashMap<Integer, String>) RegisterAndLoginView.objectInputStream.readObject();
//        } catch (Exception ignored) {
//        }
//        if (myInvitations != null) {
//            for (Map.Entry<Integer, String> eachOne : myInvitations.entrySet()) {
//                Label label = new Label("Hi! " + MainMenuController.username + ", I dare to challenge you!\nDuel with me to see who the King is!\n" + eachOne.getValue() + ":)");
//                Button accept = new Button("Accept");
//                Button reject = new Button("Reject");
//                accept.setFocusTraversable(false);
//                reject.setFocusTraversable(false);
//                HBox hBox = new HBox();
//                hBox.setSpacing(5);
//                hBox.getChildren().addAll(label, accept, reject);
//                focusedHBox = hBox;
//                whichInvitation = eachOne.getKey();
//                vBox.getChildren().add(hBox);
//                buttonActions(accept, reject);
//            }
//        }
//    }

    public void buttonActions(Button accept, Button reject) {
        accept.setOnMouseClicked(mouseEvent -> {
            vBox.getChildren().remove(focusedHBox);
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("acceptInvitation/" + MainMenuController.username + "/" + whichInvitation);
                RegisterAndLoginView.dataOutputStream.flush();
            } catch (Exception ignored) {
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
                RegisterAndLoginView.dataOutputStream.writeUTF("sendInvitation:" + MainMenuController.token + ":" + rivalNameTxt.getText());
                RegisterAndLoginView.dataOutputStream.flush();
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                while (true) {
                    RegisterAndLoginView.dataOutputStream.writeUTF("isAccepted:" + MainMenuController.token + ":" + rivalNameTxt.getText());
                    RegisterAndLoginView.dataOutputStream.flush();
                    String result = RegisterAndLoginView.dataInputStream.readUTF();
                    if ((result.startsWith("refused"))) {
                        new MainMenuView().start(lobbyStage);
                        break;
                    }
                    if (result.startsWith("true")) {
                        String[] splitResult = result.split(":");
                        split1 = splitResult[1];
                        set();
                        break;
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void set() throws Exception {
        RegisterAndLoginView.dataOutputStream.writeUTF("pickPlayer:" + MainMenuController.token + ":" + split1 + ":" + 1);
        RegisterAndLoginView.dataOutputStream.flush();
        ArrayList<Object> answer3 = (ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject();
        GameMatController.onlineToken = (String) answer3.get(0);
        GameMatController.rivalToken = (String) answer3.get(1);
        Player playerOne = (Player) answer3.get(2);
        Player playerTwo = (Player) answer3.get(3);
        UserModel userOne = (UserModel) answer3.get(4);
        UserModel userTwo = (UserModel) answer3.get(5);
        ///
        if (userTwo.getNickname().equals(MainMenuController.username)) {
            MainMenuController.username2 = userOne.getNickname();

        } else
            MainMenuController.username2 = userTwo.getNickname();
        //
        UserModel.setObject(userOne);
        UserModel.setObject(userTwo);
        Player.setObject(userOne.getNickname(), playerOne);
        Player.setObject(userTwo.getNickname(), playerTwo);
        GameMatController.onlineUser = userOne.getNickname();
        GameMatController.rivalUser = userTwo.getNickname();
        GameMatModel.playerGameMat = (HashMap<String, GameMatModel>) answer3.get(6);
        HandCardZone.allHandCards = (Map<String, List<HandCardZone>>) answer3.get(7);
        MonsterZoneCard.allMonsterCards = (Map<String, Map<Integer, MonsterZoneCard>>) answer3.get(8);
        SpellTrapZoneCard.allSpellTrapCards = (Map<String, Map<Integer, SpellTrapZoneCard>>) answer3.get(9);
        /////////
        ArrayList<Object> newArray = new ArrayList<>();
        for (int i = 10; i < 22; i++) {
            newArray.add(answer3.get(i));
        }
        GameMatController.setObjects(newArray);
        /////
        new GameMatView().start(lobbyStage);
    }

    public void refresh() {
        //showInvitations();
    }

    public void back() throws Exception {
        new MainMenuView().start(lobbyStage);
    }

    public void accept(MouseEvent mouseEvent) throws Exception {
        RegisterAndLoginView.dataOutputStream.writeUTF("acceptInvitation:" + MainMenuController.token + ":" + split1);
        RegisterAndLoginView.dataOutputStream.flush();
        String ss = RegisterAndLoginView.dataInputStream.readUTF();
        /////////////////////////


        RegisterAndLoginView.dataOutputStream.writeUTF("pickPlayer:" + MainMenuController.token + ":" + split1 + ":" + 1);
        RegisterAndLoginView.dataOutputStream.flush();
        ArrayList<Object> answer3 = (ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject();
        GameMatController.onlineToken = (String) answer3.get(0);
        GameMatController.rivalToken = (String) answer3.get(1);
        Player playerOne = (Player) answer3.get(2);
        Player playerTwo = (Player) answer3.get(3);
        UserModel userOne = (UserModel) answer3.get(4);
        UserModel userTwo = (UserModel) answer3.get(5);
        ///
        if (userTwo.getNickname().equals(MainMenuController.username)) {
            MainMenuController.username2 = userOne.getNickname();

        } else
            MainMenuController.username2 = userTwo.getNickname();
        //
        UserModel.setObject(userOne);
        UserModel.setObject(userTwo);
        Player.setObject(userOne.getNickname(), playerOne);
        Player.setObject(userTwo.getNickname(), playerTwo);
        GameMatController.onlineUser = userOne.getNickname();
        GameMatController.rivalUser = userTwo.getNickname();
        GameMatModel.playerGameMat = (HashMap<String, GameMatModel>) answer3.get(6);
        HandCardZone.allHandCards = (Map<String, List<HandCardZone>>) answer3.get(7);
        MonsterZoneCard.allMonsterCards = (Map<String, Map<Integer, MonsterZoneCard>>) answer3.get(8);
        SpellTrapZoneCard.allSpellTrapCards = (Map<String, Map<Integer, SpellTrapZoneCard>>) answer3.get(9);
        /////////
        ArrayList<Object> newArray = new ArrayList<>();
        for (int i = 10; i < 22; i++) {
            newArray.add(answer3.get(i));
        }
        GameMatController.setObjects(newArray);
        /////
        new GameMatView().start(lobbyStage);
    }



    public void refuse(MouseEvent mouseEvent) throws Exception {
        RegisterAndLoginView.dataOutputStream.writeUTF("refuseInvitation:" + MainMenuController.token + ":" + split1);
        RegisterAndLoginView.dataOutputStream.flush();
        String ss = RegisterAndLoginView.dataInputStream.readUTF();
        new MainMenuView().start(lobbyStage);
    }
}