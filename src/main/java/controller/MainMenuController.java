package controller;
import model.UserModel;
import view.RegisterAndLoginView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainMenuController {

    public static String token;
    public static String username;
    public static String username2;
    public static int roundNumber1;


    public static String duelMenu(String playerName, int roundNumber) {
        if (UserModel.isRepeatedUsername(playerName)) {
            UserModel user1 = UserModel.getUserByUsername(username);
            UserModel user2 = UserModel.getUserByUsername(playerName);
            if (!user1.getActiveDeck().equals("")) {
                if (!user2.getActiveDeck().equals("")) {
                    if (user1.userAllDecks.get(user1.getActiveDeck()).validOrInvalid().equals("valid")) {
                        if (user2.userAllDecks.get(user2.getActiveDeck()).validOrInvalid().equals("valid")) {
                            if (roundNumber == 1 || roundNumber == 3) {
                                username2 = playerName;
                                roundNumber1 = roundNumber;
                                return "00";
                            } else {
                                return ("number of rounds is not supported");
                            }
                        } else {
                            return (user2.getUsername() + "’s deck is invalid");
                        }
                    } else {
                        return (user1.getUsername() + "’s deck is invalid");
                    }
                } else {
                    return (user2.getUsername() + " has no active deck");
                }
            } else {
                return (user1.getUsername() + " has no active deck");
            }
        } else {
            return ("there is no player with this username");
        }
    }

    public static String changeNickname(String nickname) {
        if (UserModel.isRepeatedNickname(nickname))
            return "user with nickname " + nickname + " already exists";
        else {
            UserModel.getUserByUsername(username).changeNickname(nickname);
            return "nickname changed successfully!";
        }
    }

    public static String changePassword(String currentPassword, String newPassword) {
        if (!UserModel.getUserByUsername(username).getPassword().equals(currentPassword))
            return "current password is invalid";
        else if (currentPassword.equals(newPassword))
            return "please enter a new password";
        else {
            UserModel.getUserByUsername(username).changePassword(newPassword);
            return "password changed successfully!";
        }
    }

    public static ArrayList<UserModel> showScoreboard() {
        ArrayList<UserModel> scoreboard = new ArrayList<>();
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("Scoreboard");
            scoreboard = (ArrayList<UserModel>) RegisterAndLoginView.objectInputStream.readObject();
        } catch (Exception ignored) {
        }
        return scoreboard;
    }


}