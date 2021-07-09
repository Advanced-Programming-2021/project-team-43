package controller;

import controller.DeckController;
import controller.RegisterAndLoginController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Run {
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1277);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            String output;
                            output = process(input);
                            if (output.equals("==")) {
                                break;
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

    private static String process(String input) {
        if (input.startsWith("R")) {
            return RegisterAndLoginController.run(input);
        }
        if (input.startsWith("D")) {
            return DeckController.run(input);
        }
        return "==";
    }
}
