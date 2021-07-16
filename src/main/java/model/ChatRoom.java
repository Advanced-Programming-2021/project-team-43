package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoom implements Serializable {

    private static final long serialVersionUID = -8870642557353150985L;
    private final UserModel sender;
    private String message;
    private static String pinMessage;
    private static ArrayList<ChatRoom> allChats = new ArrayList<>();


    public ChatRoom(UserModel userModel, String message) {
        this.sender = userModel;
        this.message = message;
        pinMessage = "";
        allChats.add(this);
    }

    public String getMessage() {
        return message;
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

    public static String getPinMessage() {
        return pinMessage;
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

}
