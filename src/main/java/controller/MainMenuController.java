package main.java.controller;

import main.java.model.UserModel;
import main.java.view.MainMenuView;
import main.java.view.RegisterAndLoginView;

import java.lang.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuController {

    public static String username;

    public static void findMatcher() {
        while (true) {
            String command;
            command = MainMenuView.getCommand();

            Pattern pattern = Pattern.compile("^menu enter (.+?)$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel")) {

                } else if (matcher.group(1).equals("deck")) {
                    DeckController.findMatcher();
                    break;
                } else if (matcher.group(1).equals("scoreboard")) {
                    scoreboard();
                    break;

                } else if (matcher.group(1).equals("profile")) {
                    profile();

                } else if (matcher.group(1).equals("shop")) {
                    //ShopController.findMatcher();
                    break;
                } else {
                    MainMenuView.showInput("invalid command");
                    continue;
                }
            }
            pattern = Pattern.compile("^menu show-current$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                RegisterAndLoginView.showInput("Main Menu");
            }

        }
    }

    private static void profile() {
        while (true) {
            String command = MainMenuView.getCommand();
            Pattern pattern = Pattern.compile("^menu exit$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                findMatcher();
                break;
            }
            pattern = Pattern.compile("^menu enter (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
                    MainMenuView.showInput("menu navigation is not possible");
                } else {
                    MainMenuView.showInput("invalid command");
                }
                continue;
            }


            pattern = Pattern.compile("^profile change --nickname (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changeNickname(matcher);
                continue;
            }


            pattern = Pattern.compile("^profile change --password --current (.+?) --new (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changePassword(matcher.group(1), matcher.group(2));
                continue;
            }

            pattern = Pattern.compile("^profile change --current (.+?) --password --new (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changePassword(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^profile change --current (.+?) --new (.+?) --password$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changePassword(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^profile change --password --new (.+?) --current (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changePassword(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^profile change --new (.+?) --password --current (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changePassword(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^profile change --new (.+?) --current (.+?) --password$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                changePassword(matcher.group(2), matcher.group(1));
                continue;
            }


            MainMenuView.showInput("invalid command");


        }

    }

    private static void changeNickname(Matcher matcher) {
        if (UserModel.isRepeatedNickname(matcher.group(1))) {
            MainMenuView.showInput("user with nickname " + matcher.group(1) + " already exists");
        } else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changeNickname(matcher.group(1));
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
        }
    }


    private static void scoreboard() {
        while (true) {
            String command = MainMenuView.getCommand();
            Pattern pattern = Pattern.compile("^scoreboard show$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                showScoreboard();
                continue;
            }

            pattern = Pattern.compile("^menu exit$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                findMatcher();
                break;
            }
            pattern = Pattern.compile("^menu enter (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
                    MainMenuView.showInput("menu navigation is not possible");
                } else {
                    MainMenuView.showInput("invalid command");
                }
                continue;
            }
            MainMenuView.showInput("invalid command");
        }
    }


    private static void showScoreboard() {
        String[] keysUsers;
        String temp = "";
        keysUsers = UserModel.allUsernames.toArray(new String[0]);
        for (int x = 0; x < keysUsers.length; x++) {
            for (int y = x + 1; y < keysUsers.length; y++) {
                if (UserModel.getUserByUsername(keysUsers[x]).getUserScore() < UserModel.getUserByUsername(keysUsers[y]).getUserScore()) {
                    temp = keysUsers[y];
                    keysUsers[y] = keysUsers[x];
                    keysUsers[x] = temp;
                }

                if (UserModel.getUserByUsername(keysUsers[x]).getUserScore() == UserModel.getUserByUsername(keysUsers[y]).getUserScore()) {
                    if (keysUsers[x].compareToIgnoreCase(keysUsers[y]) > 0) {
                        temp = keysUsers[x];
                        keysUsers[x] = keysUsers[y];
                        keysUsers[y] = temp;
                    }
                }
            }
        }

        for (int i = 0; i < keysUsers.length; i++) {
            MainMenuView.showInput((i + 1) + "_" + keysUsers[i] + ": " + UserModel.getUserByUsername(keysUsers[i]).getUserScore());
        }
    }



    private static void changePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(newPassword)){
            MainMenuView.showInput("please enter a new password");
            return;
        }


        if (UserModel.getUserByUsername(MainMenuController.username).getPassword().equals(currentPassword)){
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changePassword(newPassword);
            UserModel.allUsersInfo.replace(MainMenuController.username,user);
            MainMenuView.showInput("password changed successfully!");
        }
        else {
            MainMenuView.showInput("current password is invalid");
        }

    }


//    public final String chooseFirstPlayer(String rivalName)
//    {
//
//    }
//    private void logout()
//    {
//    }


}
