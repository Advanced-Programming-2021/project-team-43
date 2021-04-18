package controller;

import model.UserModel;
import view.*;

import java.util.regex.*;

public class RegisterAndLoginController {


    private void enterMenu(String menu) {

    }


    public void findMatcher() {
        while (true){
        String command;
        command = RegisterAndLoginView.getCommand();
        Pattern pattern = Pattern.compile("^user login --username (.+?) --password (.+?)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            loginInGame(matcher.group(1), matcher.group(2));
        }


        Pattern pattern2 = Pattern.compile("^user login --password (.+?) --username (.+?)$");
        Matcher matcher2 = pattern2.matcher(command);
        if (matcher2.find()) {
            loginInGame(matcher.group(2), matcher.group(1));
        }




    }}


    private void registerInGame(String username, String nickname, String password) {
        if(UserModel.isRepeatedUsername(username)){
            RegisterAndLoginView.showInput("user with username "+username+" already exists");
            findMatcher();
            return;
        }
        if(UserModel.isRepeatedNickname(nickname)){
            RegisterAndLoginView.showInput("user with nickname "+nickname+" already exists");
            findMatcher();
            return;
        }
        UserModel.allUsersInfo.put(username,new UserModel(username,password,nickname));
        findMatcher();
    }


    private void loginInGame(String username, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                RegisterAndLoginView.showInput("user logged in successfully!");
                //go too main menu
            } else {
                RegisterAndLoginView.showInput("Username and password didn’t match!");


            }
        } else {
            RegisterAndLoginView.showInput("Username and password didn’t match!");
            findMatcher();
        }

    }
}
