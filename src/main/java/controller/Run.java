package controller;

import model.UserModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Run {
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;

    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1277);
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
                            if (input.startsWith("R")) {
                                output = RegisterAndLoginController.run(input);

                            }
                            if (input.startsWith("D")) {
                                output = DeckController.run(input);
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

    public static void getUserByToken(String token) {
        try {
            UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));

            objectOutputStream.writeObject(userModel);

            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
