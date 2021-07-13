package controller;

import model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Run {
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;
    public static Object objectSend;
    public static Object receivedObject;
    public static ArrayList<String> waitingPlayer = new ArrayList<>();




    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(7700);
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
                            if (RegisterAndLoginController.allOnlineUsers.containsKey(input)) {
                                getUserByToken(input);
                                continue;
                            }
                            if (output.equals("==")) {
                                break;
                            }
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

    public static String onlineToken;
    public static String rivalToken;

    private static String process(String input) throws IOException, ClassNotFoundException {

        if (input.startsWith("R")) {
            return RegisterAndLoginController.run(input);
        }
        if (input.startsWith("D")) {
            return DeckController.run(input);
        }
        if (input.startsWith("GameMat")) {
            String[] in = input.split(":");
            onlineToken = in[1];
            rivalToken = in[3];

            ArrayList<Object> objects = (ArrayList<Object>) objectInputStream.readObject();
            setObject(objects);
            GameMatController.onlineUser = userByToken(onlineToken);
            GameMatController.rivalUser = userByToken(rivalToken);
            String returnValue = GameMatController.commandController(in[2]);

            objectSend = getObjects();
            objectOutputStream.writeObject(objectSend);
            dataOutputStream.flush();
            return returnValue;
        }
        if (input.equals("Scoreboard")) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        objectOutputStream.writeObject(MainMenuController.showScoreboard());
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }, 1000);
        }
        if (input.startsWith("findADuelist")) {
            String[] split = input.split("/");
            FindADuelist.addWaitingPlayer(split[1], Integer.parseInt(split[2]));
            FindADuelist.run(split[1], Integer.parseInt(split[2]));
            System.out.println(FindADuelist.matchedPlayer.get(split[1]) + "rovalalalalalla");
            return FindADuelist.matchedPlayer.get(split[1]);
        }
        if (input.equals("PlayWithAI")) {

        }
        if (input.startsWith("cancelGame")) {
            String[] split = input.split("/");
            FindADuelist.removeWaitingPlayer(split[1]);
        }
        if (input.startsWith("chat")) {
            String[] split = input.split("/");
            UserModel userModel = null;
            for (Map.Entry<String, String> eachUser : RegisterAndLoginController.allOnlineUsers.entrySet()) {
                if (eachUser.getKey().equals(split[1]))
                    userModel = UserModel.getUserByUsername(eachUser.getValue());
            }
          //  new ChatRoom(userModel, split[2]);
        }
        if (input.equals("lobby")) {
            updateMessenger();
        }

        return "==";
    }


    public static UserModel getUserModelByToken(String token) {
        return UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
    }

//    public static String findADuelist(String onlineToken, int roundNumber) {
//        if (waitingPlayerToken.isEmpty() || (waitingPlayerToken.size() == 1 && waitingPlayerToken.containsKey(onlineToken))) {
//            return null;
//        }
//        else {
//            int size = waitingPlayerToken.size();
//            Random random = new Random();
//            String result;
//            while (true) {
//                int counter = 0;
//                int randNum = random.nextInt(size);
//                for (Map.Entry<String, Integer> eachPlayer : waitingPlayerToken.entrySet()) {
//                    if (counter == randNum) {
//                        if (!eachPlayer.getKey().equals(onlineToken) && roundNumber == eachPlayer.getValue()) {
//                            result = eachPlayer.getKey();
//                            System.out.println(result);
//                            waitingPlayerToken.remove(result);
//                            waitingPlayerToken.remove(onlineToken);
//                            return result;
//                        }
//                    }
//                    counter++;
//                }
//            }
//        }
//    }

    public static void updateMessenger() {
        new Thread(() -> {







        }).start();
    }











    public static void getUserByToken(String token) {
        try {
            UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
            objectOutputStream.writeObject(userModel);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String userByToken(String token) {
        return RegisterAndLoginController.allOnlineUsers.get(token);//onlineUser/rivalUser
    }

    private static void setObject(ArrayList<Object> objects) {
        GameMatModel.setObject(RegisterAndLoginController.allOnlineUsers.get(onlineToken), (GameMatModel) objects.get(0));
        HandCardZone.setObject(RegisterAndLoginController.allOnlineUsers.get(onlineToken), (HandCardZone) objects.get(1));
        MonsterZoneCard.setObject(RegisterAndLoginController.allOnlineUsers.get(onlineToken), (MonsterZoneCard) objects.get(2));
        Player.setObject(RegisterAndLoginController.allOnlineUsers.get(onlineToken), (Player) objects.get(3));
        SpellTrapZoneCard.setObject(RegisterAndLoginController.allOnlineUsers.get(onlineToken), (SpellTrapZoneCard) objects.get(4));
        UserModel.setObject((UserModel) objects.get(5));

        GameMatModel.setObject(userByToken(rivalToken), (GameMatModel) objects.get(6));
        HandCardZone.setObject(userByToken(rivalToken), (HandCardZone) objects.get(7));
        MonsterZoneCard.setObject(userByToken(rivalToken), (MonsterZoneCard) objects.get(8));
        Player.setObject(userByToken(rivalToken), (Player) objects.get(9));
        SpellTrapZoneCard.setObject(userByToken(rivalToken), (SpellTrapZoneCard) objects.get(10));
        UserModel.setObject((UserModel) objects.get(11));
    }

    private static ArrayList<Object> getObjects() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(GameMatModel.getGameMatByNickname(RegisterAndLoginController.allOnlineUsers.get(onlineToken)));
        objects.add(HandCardZone.getHandCardZoneByName(RegisterAndLoginController.allOnlineUsers.get(onlineToken)));
        objects.add(MonsterZoneCard.getMonsterZoneCardByName(RegisterAndLoginController.allOnlineUsers.get(onlineToken)));
        objects.add(Player.getPlayerByName(RegisterAndLoginController.allOnlineUsers.get(onlineToken)));
        objects.add(SpellTrapZoneCard.getSpellTrapZoneCardByName(RegisterAndLoginController.allOnlineUsers.get(onlineToken)));
        objects.add(UserModel.getUserByNickname(RegisterAndLoginController.allOnlineUsers.get(onlineToken)));

        objects.add(GameMatModel.getGameMatByNickname(userByToken(rivalToken)));
        objects.add(HandCardZone.getHandCardZoneByName(userByToken(rivalToken)));
        objects.add(MonsterZoneCard.getMonsterZoneCardByName(userByToken(rivalToken)));
        objects.add(Player.getPlayerByName(userByToken(rivalToken)));
        objects.add(SpellTrapZoneCard.getSpellTrapZoneCardByName(userByToken(rivalToken)));
        objects.add(UserModel.getUserByNickname(userByToken(rivalToken)));
        return objects;

    }
}
