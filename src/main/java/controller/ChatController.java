package controller;
import model.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;


public class ChatController {


    public static ArrayList<ChatRoom> getRecentChats() {
        return null;
    }


    public static void newChat(String input, ObjectOutputStream objectOutputStream) throws IOException {
        String[] split = input.split("/");
        System.out.println(input);
        new ChatRoom(UserModel.getUserByUsername(split[1]), split[2]);
        List<ChatRoom> allChats = ChatRoom.getAllChats();
        objectOutputStream.writeUnshared(allChats);
        objectOutputStream.flush();
    }

    public static void deleteChat(String input) {
        String[] split = input.split("/");
        String[] message = split[1].split("\n");
        ChatRoom.getChat(UserModel.getUserByNickname(split[2]), message[1]).deleteChat();
    }


    public static void editChat(String input) {
        System.out.println(input);
        String[] split = input.split("/");
        ChatRoom.getChat(UserModel.getUserByUsername(split[2]), split[1]).editChat(split[3]);
    }

}