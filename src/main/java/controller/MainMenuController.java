package controller;
import model.UserModel;
import view.RegisterAndLoginView;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("profile"+token+" change --nickname "+nickname);
            String input=RegisterAndLoginView.dataInputStream.readUTF();
            if(isSuccessful(input)) {
                UserModel.getUserByUsername(username).changeNickname(nickname);
            }
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String changePassword(String currentPassword, String newPassword) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("profile"+token+" change --password --new "+newPassword+" --current "+currentPassword);
            String input=RegisterAndLoginView.dataInputStream.readUTF();
            if(isSuccessful(input)) {
                UserModel.getUserByUsername(username).changePassword(newPassword);
            }
            return input ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isSuccessful(String string){
        Pattern pattern=Pattern.compile("success");
        System.out.println(string);
        Matcher matcher=pattern.matcher(string);
        return matcher.find();
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