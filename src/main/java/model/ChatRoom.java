package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoom implements Serializable{

    private static final long serialVersionUID = -8870642557353150985L;
    private UserModel sender;
    private String message;
    private int id;
    private static int idCounter = 0;
    private static String pinMessage;
    private static ArrayList<ChatRoom> allChats = new ArrayList<>();
    private static HashMap<Integer, ChatRoom> chats = new HashMap<>();



    public ChatRoom(UserModel userModel, String message) {
        this.sender = userModel;
        this.message = message;
        this.id = ++idCounter;
        chats.put(id, this);
        allChats.add(this);
    }

    public void deleteChat() {
        allChats.remove(this);
    }

    public void editChat(String newMessage) {
        message = newMessage;
    }

    public static void setPinMessage(String message) {
        pinMessage = message;
    }

    public static ChatRoom getChat(UserModel userModel, String message) {
        for (ChatRoom allChat : allChats)
            if (allChat.message.equals(message) && allChat.sender.getNickname().equals(userModel.getNickname()))
                return allChat;
        return null;
    }


    public static ArrayList<ChatRoom> getAllChats() {
        return allChats;
    }

    public static ChatRoom getLastChat() {
        return allChats.get(allChats.size() - 1);
    }


    public static HashMap<Integer, ChatRoom> getChats() {
        return chats;
    }

}
