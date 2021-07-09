import controller.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
       run();

    }
    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1277);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            String output=null;
                            if (input.startsWith("R")){  output =RegisterAndLoginController.run(input);}

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

}
