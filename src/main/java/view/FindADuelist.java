package view;

import controller.GameMatController;
import controller.MainMenuController;
import model.UserModel;

import javax.xml.transform.Source;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FindADuelist {

    private static boolean isDone;
    private static String rivalToken;
    private static String onlineToken;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static int run(int roundNumber) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("findADuelist/" + MainMenuController.token + "/" + roundNumber);
            RegisterAndLoginView.dataOutputStream.flush();
            RegisterAndLoginView.dataInputStream.readUTF();
        } catch (Exception ignored) {

        }
        return 1;
    }

}
