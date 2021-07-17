package controller;

import model.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Run {

    public static Object objectSend;
    public static String onlineToken;
    public static String rivalToken;

    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1227);
            while (true) {
                Socket socket = serverSocket.accept();
                startNewThread(serverSocket, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startNewThread(ServerSocket serverSocket, Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                getInputAndProcess(dataInputStream, dataOutputStream, objectInputStream, objectOutputStream);
                dataInputStream.close();
                objectInputStream.close();
                socket.close();
                serverSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        while (true) {
            String input = dataInputStream.readUTF();
            String result = process(input, objectInputStream, objectOutputStream);
            if (result.equals("=="))
                break;
            else if (result.equals("continue"))
                continue;
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        }
    }

    private static String process(String input, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        if (RegisterAndLoginController.allOnlineUsers.containsKey(input)) {
            getUserByToken(input, objectOutputStream);
            return "continue";
        }
        if (input.startsWith("profile")){
            return MainMenuController.profile(input);
        }
        if (input.startsWith("R")) {
            return RegisterAndLoginController.run(input);
        }
        if (input.startsWith("D")) {
            return DeckController.run(input);
        }
        if (input.startsWith("chatCup")) {
            String[] split = input.split("/");
            UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(split[1]));
            userModel.addAchievement("chatCup");
            System.out.println(userModel.getMyAchievements().get("chatCup"));
            return "continue";
        }
        if (input.startsWith("winCup")) {
            String[] split = input.split("/");
            UserModel.getUserByUsername(split[1]).addAchievement("sequentialWin");
            UserModel.getUserByUsername(split[1]).resetSequentialWin();
            return "continue";
        }
        if (input.startsWith("lostCup")) {
            String[] split = input.split("/");
            UserModel.getUserByUsername(split[1]).addAchievement("sequentialLost");
            UserModel.getUserByUsername(split[1]).resetSequentialLost();
            return "continue";
        }
        if (input.startsWith("achievement")) {
            String[] split = input.split("/");
            objectOutputStream.writeUnshared(UserModel.getUserByUsername(split[1]).getMyAchievements());
            objectOutputStream.flush();
            return "continue";
        }
        if (input.startsWith("myInvitations")) {
            String[] split = input.split("/");
            objectOutputStream.writeUnshared(UserModel.getUserByUsername(split[1]).getMyInvitations());
            objectOutputStream.flush();
            return "continue";
        }
        if (input.startsWith("sendInvitation")) {
            String[] split = input.split("/");
            if (UserModel.getUserByUsername(split[2]) == null)
                return "Wrong Username!";
            else {
                UserModel.getUserByUsername(split[2]).addInvitation(split[1]);
                return "Send Successfully!";
            }
        }
        if (input.startsWith("rejectInvitation")) {
            String[] split = input.split("/");
            UserModel.getUserByUsername(split[1]).removeInvitation(Integer.parseInt(split[2]));
            return "continue";
        }
        if (input.startsWith("acceptInvitation")) {
            String[] split = input.split("/");
           // UserModel.getUserByUsername(split[1]).removeInvitation(Integer.parseInt(split[2]));
            return "continue";
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
            objectOutputStream.flush();
            return returnValue;
        }
        if (input.equals("Scoreboard")) {
            objectOutputStream.writeObject(MainMenuController.showScoreboard());
            objectOutputStream.flush();
            return "continue";
        }
        if (input.startsWith("findADuelist")) {
            String[] split = input.split("/");
            ArrayList<Object> result;
            if (split[2].equals("1")) {
                FindADuelist.oneRoundPlayer.add(split[1]);
                FindADuelist.oneRoundPlayerTwo.add(split[1]);
                System.out.println(FindADuelist.oneRoundPlayer.size() + "first");
                System.out.println(FindADuelist.oneRoundPlayerTwo.size() + "sec");
//                FindADuelist.addToList(split[1]);
//                FindADuelist.searchForOneRound(split[1]);
            }
            else {
                FindADuelist.threeRoundPlayer.add(split[1]);
                result = FindADuelist.searchForThreeRound(split[1]);
            }

            return "continue";
        }
        if (input.startsWith("match")) {
//            String[] split = input.split("/");
//            UserModel rivalUser = UserModel.getUserByUsername(split[2]);
//            System.out.println(rivalUser.getNickname());
//            System.out.println(FindADuelist.oneRoundPlayerTwo.size());
//            System.out.println(FindADuelist.oneRoundPlayer.size());
//            if (FindADuelist.oneRoundPlayer.contains(rivalUser.getOwnToken())) {
//                System.out.println(rivalUser.getNickname() + "first");
//                rivalUser.setRivalToken(split[1]);
//                objectOutputStream.writeObject(rivalUser);
//                objectOutputStream.flush();
//                FindADuelist.oneRoundPlayer.remove(rivalUser.getOwnToken());
//                FindADuelist.oneRoundPlayer.remove(split[1]);
//                return "success";
//            }
//            else if (FindADuelist.oneRoundPlayerTwo.contains(rivalUser.getOwnToken())) {
//                System.out.println(rivalUser.getNickname() + "sec");
//                rivalUser.setRivalToken(split[1]);
//                objectOutputStream.writeObject(rivalUser);
//                objectOutputStream.flush();
//                FindADuelist.oneRoundPlayerTwo.remove(rivalUser.getRivalToken());
//                FindADuelist.oneRoundPlayerTwo.remove(split[1]);
//                return "success";
//            }
//            else {
//                return "fail";
//            }
        }
        if (input.equals("PlayWithAI")) {

        }
        if (input.startsWith("cancelGame")) {
            String[] split = input.split("/");
        //    FindADuelist.waitingPlayerToken.remove(split[1]);
        }
        if (input.startsWith("chat")) {
            ChatController.newChat(input, objectOutputStream);
            return ChatRoom.getPinMessage();
        }
        if (input.startsWith("pinMessage")) {
            String[] split = input.split("/");
            System.out.println(split[1]);
            ChatRoom.setPinMessage(split[1]);
            return "continue";
        }
        if (input.startsWith("delete")) {
            ChatController.deleteChat(input);
            return "continue";
        }
        if (input.startsWith("edit")) {
            ChatController.editChat(input);
            return "continue";
        }
        if (input.startsWith("allOnlineUsers")) {
            objectOutputStream.writeUnshared(RegisterAndLoginController.allOnlineUsers);
            objectOutputStream.flush();
            return "continue";
        }
        if (input.startsWith("duel")) {
            String[] split = input.split("/");
            System.out.println(split[1]);
            if (split[1].equals("q")) {
                objectOutputStream.writeObject(UserModel.getUserByUsername("w"));
                objectOutputStream.flush();
                return getTokenByUsername("w");
            }
            else {
                objectOutputStream.writeObject(UserModel.getUserByUsername("q"));
                objectOutputStream.flush();
                return getTokenByUsername("q");
            }
        }
        if (input.equals("numberOfOnlineUser")) {
            System.out.println(RegisterAndLoginController.allOnlineUsers.size());
            return String.valueOf(RegisterAndLoginController.allOnlineUsers.size());
        }
        return "==";
    }

    public static String getTokenByUsername(String username) {
        for (Map.Entry<String, String> eachUser : RegisterAndLoginController.allOnlineUsers.entrySet()) {
            if (eachUser.getValue().equals(username)) {
                return eachUser.getKey();
            }
        }
        return null;
    }

    public static void getUserByToken(String token, ObjectOutputStream objectOutputStream) {
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
