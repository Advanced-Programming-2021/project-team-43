package controller;

import model.UserModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Run {
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;

    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(127);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                        objectInputStream=new ObjectInputStream(socket.getInputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            String output = null;
                            if (RegisterAndLoginController.allOnlineUsers.containsKey(input)) {
                                getUserByToken(input);
                                continue;
                            }
                            Pattern pattern =Pattern.compile("^1010(.+?)$");
                            Matcher matcher = pattern.matcher(input);
                            if (matcher.find()){
                                System.out.println("find"+matcher.group(1));
                                getAllUserDeck(matcher.group(1));
                                continue;
                            }
                            if (input.startsWith("R")) {
                                output = RegisterAndLoginController.run(input);

                            }
                            if (input.startsWith("D")) {
                                output = DeckController.run(input);
                            }
                            if (input.startsWith("M")){
                                output = MainMenuController.findMatcher(input);
                            }
                            if (input.startsWith("profile")){
                                output=MainMenuController.profile(input);
                            }
                            if (output.equals("continue")){
                                continue;
                            }
                            dataOutputStream.writeUTF(output);

                            dataOutputStream.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getUserByToken(String token) throws IOException {

            UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
            System.out.println(userModel);
            objectOutputStream.writeObject(userModel);
            objectOutputStream.flush();

    }
    public static void getAllUserDeck(String token) throws IOException {

        UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        objectOutputStream.writeObject(userModel.userAllDecks);
        objectOutputStream.flush();
    }

}
