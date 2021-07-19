package controller;
import model.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.zone.ZoneOffsetTransition;
import java.util.*;


public class Run {

    public static Object objectSend;
    public static String onlineToken;
    public static String rivalToken;
    private static final HashMap<String, Boolean> tokensInLine = new HashMap<>();
    private static final HashMap<String, String> pairTokens = new HashMap<>();
    private static final HashMap<String, Boolean> pickPlayer = new HashMap<>();


    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1111);
            while (true) {
                Socket socket = serverSocket.accept();
                startNewThread(serverSocket, socket);
            }
        } catch (Exception e) {
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
        String result = "";
        if (input.equals("logout")) {
            System.out.println("jjjjjjjjjjjjjjjjj");
            String[] split = input.split("/");
            RegisterAndLoginController.allOnlineUsers.remove(split[1]);
            return "==";
        }
        if (input.startsWith("M")){
            return MainMenuController.findMatcher(input);
        }
        if (RegisterAndLoginController.allOnlineUsers.containsKey(input)) {
            getUserByToken(input, objectOutputStream);
            return "continue";
        }
        if (input.startsWith("profile")) {
            return MainMenuController.profile(input);
        }
        if (input.startsWith("R")) {
            return RegisterAndLoginController.run(input);
        }
        if (input.startsWith("D")) {
            return DeckController.run(input);
        }
        result = processAchievementsRequests(input, objectOutputStream);
        if (!result.equals(""))
            return result;
        result = processInvitationsRequest(input, objectOutputStream);
        if (!result.equals(""))
            return result;
        if (input.equals("Scoreboard")) {
            objectOutputStream.writeObject(MainMenuController.showScoreboard());
            objectOutputStream.flush();
            return "continue";
        }
        if (input.startsWith("S")){
            return ShopController.run(input);
        }
        result = processChatroomRequests(input, objectOutputStream);
        if (!result.equals(""))
            return result;
        result = processFindDuelistRequests(input, objectOutputStream);
        if (!result.equals(""))
            return result;
        if (input.startsWith("GameMat")) {
            String[] in = input.split(":");
            System.out.println(in[1]+" nn");
            System.out.println(in[3]+" ss");
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
        if (input.startsWith("cancel")) {
            String[] split = input.split("/");
            tokensInLine.remove(split[1]);
            return "continue";
        }
        return "==";
    }

    private static String find(String myToken) {
        tokensInLine.put(myToken, false);
        String[] tokens = tokensInLine.keySet().toArray(new String[0]);
        for (String token : tokens) {
            if (!tokensInLine.get(token) && !token.equals(myToken)) {
                tokensInLine.put(token, true);
                tokensInLine.put(myToken, true);
                pairTokens.put(myToken, token);
                pickPlayer.put(myToken, false);
                pickPlayer.put(token, false);
                return token;
            }
        }
        return "failed";
    }

    private static synchronized String processFindDuelistRequests(String input, ObjectOutputStream objectOutputStream) throws IOException {
        if (input.startsWith("findADuelist")) {
            String[] split = input.split("/");
            return find(split[1]);
        }
        if (input.startsWith("pickPlayer")) {
            String[] give = input.split(":");
            //
            onlineToken = give[1];
            rivalToken = give[2];
            //
            ArrayList<Object> players = new ArrayList<>();
            System.out.println(give[1] + "]]]" + give[2]);
            if (!pickPlayer.get(give[1]) && !pickPlayer.get(give[2])) {
                for (Map.Entry<String, String> x : pairTokens.entrySet()) {
                    players.add(x.getKey());
                    players.add(x.getValue());
                    String playerOneNickname = UserModel.getUserByUsername(userByToken(x.getKey())).getNickname();
                    String playerTwoNickname = UserModel.getUserByUsername(userByToken(x.getValue())).getNickname();
                    Player playerOne;
                    Player playerTwo;
                    System.out.println(playerOneNickname + "one");
                    System.out.println(playerTwoNickname + "two");
                    if (Player.getPlayerByName(playerOneNickname) == null)
                        playerOne = new Player(playerOneNickname, UserModel.getUserByUsername(userByToken(x.getKey())).userAllDecks.get(UserModel.getUserByUsername(userByToken(x.getKey())).getActiveDeck()), true, Integer.parseInt(give[3]));
                    else
                        playerOne = Player.getPlayerByName(playerOneNickname);
                    if (Player.getPlayerByName(playerTwoNickname) == null)
                        playerTwo = new Player(playerTwoNickname, UserModel.getUserByUsername(userByToken(x.getValue())).userAllDecks.get(UserModel.getUserByUsername(userByToken(x.getValue())).getActiveDeck()), false, Integer.parseInt(give[3]));
                    else
                        playerTwo = Player.getPlayerByName(playerTwoNickname);
                    players.add(playerOne);
                    players.add(playerTwo);
                    players.add(UserModel.getUserByUsername(userByToken(x.getKey())));
                    players.add(UserModel.getUserByUsername(userByToken(x.getValue())));
                    players.add(GameMatModel.playerGameMat);
                    players.add(HandCardZone.allHandCards);
                    players.add(MonsterZoneCard.allMonsterCards);
                    players.add(SpellTrapZoneCard.allSpellTrapCards);

                    players.addAll(getObjects());
                }
                objectOutputStream.writeUnshared(players);
                objectOutputStream.flush();
            }
            return "continue";
        }
        if (input.startsWith("isFind")) {
            String[] split = input.split(":");
            if (tokensInLine.get(split[1])) {
                if (pairTokens.get(split[1]) != null) {

                    return "true:" + pairTokens.get(split[1]);
                } else {
                    String[] keys = pairTokens.keySet().toArray(new String[0]);
                    for (int i = 0; i < keys.length; i++) {
                        if (pairTokens.get(Arrays.toString(keys).substring(1, Arrays.toString(keys).length() - 1)).equals(split[1])) {

                            return "true:" + Arrays.toString(keys).substring(1, Arrays.toString(keys).length() - 1);
                        }
                    }
                }

            } else {
                return "false";
            }
        }
        return "";
    }

    private static String processInvitationsRequest(String input, ObjectOutputStream objectOutputStream) throws IOException {
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
        return "";
    }

    private static String processAchievementsRequests(String input, ObjectOutputStream objectOutputStream) throws IOException {
        if (input.startsWith("chatCup")) {
            String[] split = input.split("/");
            UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(split[1]));
            userModel.addAchievement("chatCup");
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
        return "";
    }

    private static String processChatroomRequests(String input, ObjectOutputStream objectOutputStream) throws IOException {
        if (input.startsWith("getPinMessage")) {
            return ChatRoom.getPinMessage();
        }
        if (input.startsWith("chatRecord")) {
            String[] split = input.split("/");
            UserModel.getUserByUsername(userByToken(split[2])).setChatRecords(Integer.parseInt(split[1]));
            return "continue";
        }
        if (input.startsWith("chat")) {
            ChatController.newChat(input, objectOutputStream);
            return "continue";
        }
        if (input.startsWith("pinMessage")) {
            String[] split = input.split("/");
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
        if (input.startsWith("reply")) {
            String[] split = input.split("\n");
            String[] name = split[0].split("-");
            String[] message = split[1].split("/");
            ChatRoom.getChat(UserModel.getUserByUsername(name[1]), message[0]).setReplyMessage(message[1]);
            return "continue";
        }
        if (input.equals("numberOfOnlineUser")) {
            return RegisterAndLoginController.allOnlineUsers.size() + "/" + ChatRoom.getPinMessage();
        }
        return "";
    }

    public static String getTokenByUsername(String username) {
        for (Map.Entry<String, String> eachUser : RegisterAndLoginController.allOnlineUsers.entrySet())
            if (eachUser.getValue().equals(username))
                return eachUser.getKey();
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
        System.out.println(userByToken(onlineToken)+" ,,");
        System.out.println(onlineToken);
        System.out.println(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname());
        GameMatModel.setObject(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname(), (GameMatModel) objects.get(0));
        HandCardZone.setObject(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname(), (List<HandCardZone>) objects.get(1));
        MonsterZoneCard.setObject(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname(), (Map<Integer, MonsterZoneCard>) objects.get(2));
        Player.setObject(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname(), (Player) objects.get(3));
        SpellTrapZoneCard.setObject(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname(), (Map<Integer, SpellTrapZoneCard>) objects.get(4));
        UserModel.setObject((UserModel) objects.get(5));

        GameMatModel.setObject(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname(), (GameMatModel) objects.get(6));
        HandCardZone.setObject(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname(), (List<HandCardZone>) objects.get(7));
        MonsterZoneCard.setObject(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname(), (Map<Integer, MonsterZoneCard>) objects.get(8));
        Player.setObject(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname(), (Player) objects.get(9));
        SpellTrapZoneCard.setObject(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname(), (Map<Integer, SpellTrapZoneCard>) objects.get(10));
        UserModel.setObject((UserModel) objects.get(11));
    }

    private static ArrayList<Object> getObjects() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(GameMatModel.getGameMatByNickname(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname()));
        objects.add(HandCardZone.getAllHandCardZoneByName(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname()));
        objects.add(MonsterZoneCard.getAllMonstersByPlayerName(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname()));
        objects.add(Player.getPlayerByName(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname()));
        objects.add(SpellTrapZoneCard.getAllSpellTrapByPlayerName(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname()));
        objects.add(UserModel.getUserByNickname(UserModel.getUserByUsername(userByToken(onlineToken)).getNickname()));

        objects.add(GameMatModel.getGameMatByNickname(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname()));
        objects.add(HandCardZone.getAllHandCardZoneByName(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname()));
        objects.add(MonsterZoneCard.getAllMonstersByPlayerName(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname()));
        objects.add(Player.getPlayerByName(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname()));
        objects.add(SpellTrapZoneCard.getAllSpellTrapByPlayerName(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname()));
        objects.add(UserModel.getUserByNickname(UserModel.getUserByUsername(userByToken(rivalToken)).getNickname()));
        return objects;
    }

}
