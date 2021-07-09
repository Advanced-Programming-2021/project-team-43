package controller;
import model.UserModel;
import java.util.ArrayList;



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

    public static ArrayList<String> showScoreboard() {
        String[] keysUsers;
        String temp;
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
        ArrayList<String> arrayList = new ArrayList<>();
        for (String keysUser : keysUsers) {
            if (keysUser.equals("AI")) continue;
            arrayList.add(keysUser);
        }
        return arrayList;
    }

}