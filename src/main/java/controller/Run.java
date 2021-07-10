package controller;

import model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Run {
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;
    public static Object objectSend;
    public static Object receivedObject;


    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1277);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());

                        objectInputStream = new ObjectInputStream(socket.getInputStream());
                        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                        while (true) {
                            String input = dataInputStream.readUTF();
                            String output;
                            output = process(input);
                            if (output.equals("==")) {
                                break;
                            }
                            objectOutputStream.writeObject(objectSend);
                            dataOutputStream.writeUTF(output);
                            dataOutputStream.flush();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String process(String input) throws IOException, ClassNotFoundException {

        if (input.startsWith("R")) {
            return RegisterAndLoginController.run(input);
        }
        if (input.startsWith("D")) {
            return DeckController.run(input);
        }
        if (input.startsWith("GameMat")) {
            ArrayList<Object> objects = (ArrayList<Object>) objectInputStream.readObject();
            GameMatModel.setObject(MainMenuController.username, (GameMatModel) objects.get(0));
            HandCardZone.setObject(MainMenuController.username, (HandCardZone) objects.get(1));
            MonsterZoneCard.setObject(MainMenuController.username, (MonsterZoneCard) objects.get(2));
            Player.setObject(MainMenuController.username, (Player) objects.get(3));
            SpellTrapZoneCard.setObject(MainMenuController.username, (SpellTrapZoneCard) objects.get(4));
            UserModel.setObject((UserModel) objects.get(5));


            String[] in = input.split(":");
            HashMap<String, String> getByToken = RegisterAndLoginController.allOnlineUsers;
            MainMenuController.username = getByToken.get(in[1]);
            String returnValue = GameMatController.commandController(in[2]);
            objectSend = getObjects();
            return returnValue;

        }


        if (input.startsWith("PickFirstPlayer")) {
            return PickFirstPlayer.chose(null, null);
        }
        if (input.startsWith("CoinChance")) {
            return PickFirstPlayer.chanceCoin(PickFirstPlayer.ply1, PickFirstPlayer.ply2);
        }
        if (input.startsWith("onlineUser")) {
            String[] in = input.split(":");
            GameMatController.onlineUser = in[2];
            GameMatController.rivalUser = in[3];
            return "success";
        }
        if (input.startsWith("rock")) {
            String[] in = input.split(":");
            return PickFirstPlayer.rockPaperScissors(in[2], in[3], in[4], in[5]);

        }
        return "==";
    }

    private static ArrayList<Object> getObjects() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(GameMatModel.getGameMatByNickname(MainMenuController.username));
        objects.add(HandCardZone.getHandCardZoneByName(MainMenuController.username));
        objects.add(MonsterZoneCard.getMonsterZoneCardByName(MainMenuController.username));
        objects.add(Player.getPlayerByName(MainMenuController.username));
        objects.add(SpellTrapZoneCard.getSpellTrapZoneCardByName(MainMenuController.username));
        objects.add(UserModel.getUserByNickname(MainMenuController.username));
        return objects;

    }
}
