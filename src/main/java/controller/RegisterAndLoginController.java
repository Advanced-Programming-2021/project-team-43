package controller;

import model.UserModel;
import view.*;


import java.util.regex.*;

public class RegisterAndLoginController {


    public static void findMatcher() {
        if (JSON.readUserInfo()==null){
            JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
        }
        else {
            UserModel.allUsersInfo=JSON.readUserInfo();
            UserModel.allUsernames=JSON.readUsernames();
            UserModel.allUsersNicknames=JSON.readUserNicknames();
        }

        if (JSON.exportCad()!=null)
        {
            UserModel.importedCards =JSON.exportCad();
        }


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
                registerInGame(matcher.group(1), matcher.group(2), matcher.group(3));
                continue;
            }


            pattern = Pattern.compile("^user create --username (.+?) --password (.+?) --nickname (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(1), matcher.group(3), matcher.group(2));
                continue;
            }


            pattern = Pattern.compile("^user create --nickname (.+?) --username (.+?) --password (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(2), matcher.group(1), matcher.group(3));
                continue;
            }


            pattern = Pattern.compile("^user create --nickname (.+?) --password (.+?)--usaername (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(2), matcher.group(3), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^user create --password (.+?) --username (.+?) --nickname (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(3), matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^user create --password (.+?) --nickname (.+?) --username (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                registerInGame(matcher.group(3), matcher.group(2), matcher.group(1));
                continue;
            }

            pattern = Pattern.compile("^menu enter (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel")||matcher.group(1).equals("Import/Export") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
                    RegisterAndLoginView.showInput("menu navigation is not possible");
                } else {
                    RegisterAndLoginView.showInput("invalid command");
                }
                continue;
            }

            pattern = Pattern.compile("menu exit");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                break;
            }


            pattern = Pattern.compile("menu show-current");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                RegisterAndLoginView.showInput("Login Menu");
                continue;
            }


            RegisterAndLoginView.showInput("invalid command");


        }
    }


    private static void registerInGame(String username, String nickname, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            RegisterAndLoginView.showInput("user with username " + username + " already exists");

            return;
        }
        if (UserModel.isRepeatedNickname(nickname)) {
            RegisterAndLoginView.showInput("user with nickname " + nickname + " already exists");

            return;
        }
        UserModel.allUsersInfo.put(username, new UserModel(username, password, nickname));
        UserModel.allUsernames.add(username);
        UserModel.allUsersNicknames.add(nickname);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
        RegisterAndLoginView.showInput("user created successfully!");

    }


    private static void loginInGame(String username, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                RegisterAndLoginView.showInput("user logged in successfully!");
                MainMenuController.username = username;
                MainMenuController.findMatcher();
            } else {
                RegisterAndLoginView.showInput("Username and password didn’t match!11");


            }
        } else {
            RegisterAndLoginView.showInput("Username and password didn’t match!");

        }

    }
}
