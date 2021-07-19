package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ChatRoom implements Serializable {

    private final UserModel sender;
    private String message;
    private String replyMessage;
    private static String pinMessage;
    private static final ArrayList<ChatRoom> allChats = new ArrayList<>();
    private static final long serialVersionUID = -8870642557353150985L;


    public ChatRoom(UserModel userModel, String message) {
        this.sender = userModel;
        this.message = "(" + userModel.getNickname() + ")\n" + message;
        this.replyMessage = "";
        this.replyMessage = "";
        allChats.add(this);
    }

    public UserModel getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public void deleteMessage() {
        allChats.remove(this);
    }

    public static ChatRoom getChat(UserModel userModel, String message) {
        for (ChatRoom allChat : allChats)
            if (allChat.message.equals(message) && allChat.sender.getNickname().equals(userModel.getNickname()))
                return allChat;
        return null;
    }

    public static List<ChatRoom> getAllChats() {
        return allChats;
    }

    public static String getReplyMessageBySenderAndMessage(UserModel sender, String message) {
        for (ChatRoom eachChat : allChats) {
            if (eachChat.getSender().getNickname().equals(sender.getNickname()) && eachChat.getMessage().equals(message))
                return eachChat.getReplyMessage();
        }
        return null;
    }

    public static void setObject(List<ChatRoom> newChats){
        allChats.clear();
        allChats.addAll(newChats);
    }

    public static void setPinMessage(String message) {
        pinMessage = message;
    }

    public static String getPinMessage() {
        return pinMessage;
    }

    public static int getNumberOfChatBySender(String username) {
        int counter = 0;
        for (ChatRoom eachChat : allChats)
            if (eachChat.getSender().getUsername().equals(username))
                counter++;
        return counter;
    }

}