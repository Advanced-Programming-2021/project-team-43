package controller;
import model.*;



public class RegisterAndLoginController {

    public static String registerInGame(String username, String nickname, String password, String imageUrl) {
        if (username.isEmpty())
            return "Please fill the username field!";
        if (nickname.isEmpty())
            return "Please fill the nickname field!";
        if (password.isEmpty())
            return "Please fill the password filed!";
        if (UserModel.isRepeatedUsername(username))
            return "User with username " + username + " already exists";
        if (UserModel.isRepeatedNickname(nickname))
            return "User with nickname " + nickname + " already exists";
        else {
            new UserModel(username, password, nickname, imageUrl);
            return "User created successfully!";
        }
    }

    public static String loginInGame(String username, String password) {
        if (username.isEmpty())
            return "Please fill the username field!";
        if (password.isEmpty())
            return "Please fill the password filed!";
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                MainMenuController.username = username;
                return "User logged in successfully!";
            }
            else
                return "Username and password didn’t match!";
        }
        else
            return "Username and password didn’t match!";
    }

}