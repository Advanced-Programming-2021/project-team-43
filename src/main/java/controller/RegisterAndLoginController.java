package controller;

import view.RegisterAndLoginView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterAndLoginController {

    public static String registerInGame(String username, String nickname, String password, String imageUrl) {
        if (username.isEmpty())
            return "Please fill the username field!";
        if (nickname.isEmpty())
            return "Please fill the nickname field!";
        if (password.isEmpty())
            return "Please fill the password filed!";

        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("R user create --username " + username + " --nickname " + nickname + " --password " + password + " --imageURL " + imageUrl);
            return RegisterAndLoginView.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String loginInGame(String username, String password) {
        if (username.isEmpty())
            return "Please fill the username field!";
        if (password.isEmpty())
            return "Please fill the password filed!";

        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("R user login --username " + username + " --password " + password);
            String output = RegisterAndLoginView.dataInputStream.readUTF();
            System.out.println("Output:" + output);
            Pattern pattern = Pattern.compile("user logged in successfully!(.+)");
            Matcher matcher = pattern.matcher(output);
            if (matcher.find()) {
                MainMenuController.token = matcher.group(1);
                MainMenuController.username = username;
                System.out.println("128");
                return "User logged in successfully!";
            }
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}