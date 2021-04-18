package controller;

import model.UserModel;
import view.*;

import java.util.regex.*;

public class RegisterAndLoginController {


    public static void findMatcher() {
        while (true) {
            String command;
            command = RegisterAndLoginView.getCommand();
            Pattern pattern = Pattern.compile("^user login --username (.+?) --password (.+?)$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                loginInGame(matcher.group(1), matcher.group(2));
                continue;
            }


            pattern = Pattern.compile("^user login --password (.+?) --username (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                loginInGame(matcher.group(2), matcher.group(1));
                continue;

            }


            pattern = Pattern.compile("^user create --username (.+?) --nickname (.+?) --password (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(1), matcher.group(2), matcher.group(3));continue;
            }


            pattern = Pattern.compile("^user create --username (.+?) --password (.+?) --nickname (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(1), matcher.group(3), matcher.group(2));continue;
            }


            pattern = Pattern.compile("^user create --nickname (.+?) --username (.+?) --password (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(2), matcher.group(1), matcher.group(3));continue;
            }


            pattern = Pattern.compile("^user create --nickname (.+?) --password (.+?) --username (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(2), matcher.group(3), matcher.group(1));continue;
            }


            pattern = Pattern.compile("^user create --password (.+?) --username (.+?) --nickname (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(3), matcher.group(1), matcher.group(2));continue;
            }
            pattern = Pattern.compile("^user create --password (.+?) --nickname (.+?) --username (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(3), matcher.group(2), matcher.group(1));continue;
            }

            pattern = Pattern.compile("^menu enter .+?$");
            matcher = pattern.matcher(command);
            if (matcher.find()){
                RegisterAndLoginView.showInput("please login first");
                continue;
            }

            RegisterAndLoginView.showInput("invalid command");


        }
    }


    private static void registerInGame(String username, String nickname, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            RegisterAndLoginView.showInput("user with username " + username + " already exists");
            findMatcher();
            return;
        }
        if (UserModel.isRepeatedNickname(nickname)) {
            RegisterAndLoginView.showInput("user with nickname " + nickname + " already exists");
            findMatcher();
            return;
        }
        UserModel.allUsersInfo.put(username, new UserModel(username, password, nickname));
        findMatcher();
    }


    private static void loginInGame(String username, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                RegisterAndLoginView.showInput("user logged in successfully!");
                MainMenuController.findMatcher(username);
            } else {
                RegisterAndLoginView.showInput("Username and password didn’t match!");


            }
        } else {
            RegisterAndLoginView.showInput("Username and password didn’t match!");
            findMatcher();
        }

    }
}