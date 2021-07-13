package controller;

import model.Player;
import model.UserModel;
import view.MainMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainMenuController {

    public static String username;
    public static String username2;
    private static Matcher matcher;


    public static String findMatcher(String command) {


        if (getMatcher(command, "^M(.+?)menu \\s*exit$").find() || getMatcher(command, "^m \\s*ex$").find()) {
            RegisterAndLoginController.allOnlineUsers.remove(matcher.group(1));
            return "continue";
        }
        if (getMatcher(command, "^M(.+?)user \\s*logout$").find()) {

            RegisterAndLoginController.allOnlineUsers.remove(matcher.group(1));
            return ("user logged out successfully!");
        }
        if ((matcher = getMatcher(command, "^M(.+?)increase --money (\\d+)$")).find() || (matcher = getMatcher(command, "^increase\\s* -m\\s* (\\d+)$")).find()) {
            increaseMoney(Integer.parseInt(matcher.group(2)), matcher.group(1));
            return "continue";
        }
        return null;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void increaseMoney(int money, String token) {
        UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        userModel.changeUserCoin(money);
    }


//    public static int duelRun() {
//        while (true) {
//            String command = MainMenuView.getCommand();
//            command = command.trim();
//            int breaker = duelFindMatcher(command);
//            if (breaker == 0) {
//                break;
//            }
//        }
//        return 0;
//    }

//    public static int duelFindMatcher(String command) {
//        if ((matcher = getMatcher(command, "^duel\\s+--new\\s+--second-player\\s+(.+?)\\s+--rounds\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^duel\\s+-n\\s+-s-p\\s+(.+?)\\s+-r\\s+(\\d+)$")).find()) {
//            duelMenu(matcher.group(1), Integer.parseInt(matcher.group(2)));
//            return 1;
//        }
//
//
//        if ((matcher = getMatcher(command, "^duel\\s+--new\\s+--ai\\s+--rounds\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^duel\\s+-n\\s+-ai\\s+-r\\s+(\\d+)$")).find()) {
//            duelMenu("AI", Integer.parseInt(matcher.group(1)));
//            return 1;
//        }
//
//        MainMenuView.showInput("invalid command");
//        return 1;
//}

//    public static void duelMenu(String playerName, int roundNumber) {
//        if (UserModel.isRepeatedUsername(playerName)) {
//            UserModel user1 = UserModel.getUserByUsername(MainMenuController.username);
//            UserModel user2 = UserModel.getUserByUsername(playerName);
//            if (!user1.getActiveDeck().equals("")) {
//                if (!user2.getActiveDeck().equals("")) {
//                    if (user1.userAllDecks.get(user1.getActiveDeck()).validOrInvalid().equals("valid")) {
//                        if (user2.userAllDecks.get(user2.getActiveDeck()).validOrInvalid().equals("valid")) {
//                            if (roundNumber == 1 || roundNumber == 3) {
//                                username2 = playerName;
//                                String firstPlayer;
//                                String secondPlayer;
//                                if (playerName.equals("AI")) {
//                                    firstPlayer = username;
//                                    secondPlayer = "AI";
//                                } else {
//                                    firstPlayer = PickFirstPlayer.chose(MainMenuController.username, playerName);
//                                    if (firstPlayer.equals(playerName))
//                                        secondPlayer = MainMenuController.username;
//                                    else
//                                        secondPlayer = playerName;
//                                }
//                                new Player(UserModel.getUserByUsername(firstPlayer).getNickname(), UserModel.getUserByUsername(firstPlayer).userAllDecks.get(UserModel.getUserByUsername(firstPlayer).getActiveDeck()), true, roundNumber);
//                                new Player(UserModel.getUserByUsername(secondPlayer).getNickname(), UserModel.getUserByUsername(secondPlayer).userAllDecks.get(UserModel.getUserByUsername(secondPlayer).getActiveDeck()), false, roundNumber);
//                                GameMatController.run(UserModel.getUserByUsername(firstPlayer).getNickname(), UserModel.getUserByUsername(secondPlayer).getNickname());
//                            } else {
//                                MainMenuView.showInput("number of rounds is not supported");
//                            }
//                        } else {
//                            MainMenuView.showInput(user2.getUsername() + "’s deck is invalid");
//                        }
//                    } else {
//                        MainMenuView.showInput(user1.getUsername() + "’s deck is invalid");
//                    }
//                } else {
//                    MainMenuView.showInput(user2.getUsername() + " has no active deck");
//                }
//            } else {
//                MainMenuView.showInput(user1.getUsername() + " has no active deck");
//            }
//        } else {
//            MainMenuView.showInput("there is no player with this username");
//        }
//    }


    public static String profile(String command) {


        if ((matcher = getMatcher(command, "^profile(.+?) change --nickname (.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s* change\\s* -n (.+?)$")).find()) {
            return changeNickname(matcher.group(2), matcher.group(1));
        }

        if ((matcher = getMatcher(command, "^profile(.+?) change --password --new (.+?)  --current (.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-new\\s+(.+?)\\s+-p\\s+-c\\s+(.+?)$")).find()) {
            return changePassword(matcher.group(1), matcher.group(3), matcher.group(2));
        }

        if ((matcher = getMatcher(command, "^profile(.+?) change image (\\d*)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-new\\s+(.+?)\\s+-p\\s+-c\\s+(.+?)$")).find()) {
            UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(matcher.group(1))).setImageUrl("/images/profile/char" + matcher.group(2) + ".jpg");
            return "continue";
        }

        return null;
    }



    public static String changeNickname(String matcher, String token) {
        if (UserModel.isRepeatedNickname(matcher)) {
            return ("user with nickname " + matcher + " already exists");
        } else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changeNickname(matcher);
            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
            return ("nickname changed successfully!");
        }
    }

//    public static int scoreboardRun() {
//        while (true) {
//            String command = MainMenuView.getCommand();
//            command = command.trim();
//            int breaker = scoreboard(command);
//            if (breaker == 0) {
//                break;
//            }
//        }
//        return 0;
//    }
//
//    public static int scoreboard(String command) {
//        if (getMatcher(command, "^scoreboard \\s*show$").find() || getMatcher(command, "^s\\s+s$").find()) {
//            showScoreboard();
//            return 1;
//        }
//        if (getMatcher(command, "^menu\\s* exit$").find() || getMatcher(command, "^m\\s* ex$").find()) {
//            return 0;
//        }
//        if ((matcher = getMatcher(command, "^menu\\s* enter\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^m\\s* en\\s* (.+?)$")).find()) {
//            if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard")) {
//                MainMenuView.showInput("menu navigation is not possible");
//            } else {
//                MainMenuView.showInput("invalid command");
//            }
//            return 1;
//        }
//        if (getMatcher(command, "^menu\\s* show-current$").find() || getMatcher(command, "^m\\s* s-c$").find()) {
//            MainMenuView.showInput("Score board Menu");
//            return 1;
//        }
//        MainMenuView.showInput("invalid command");
//        return 1;
//    }

//    public static void showScoreboard() {
//        String[] keysUsers;
//        String temp;
//        keysUsers = UserModel.allUsernames.toArray(new String[0]);
//        for (int x = 0; x < keysUsers.length; x++) {
//            for (int y = x + 1; y < keysUsers.length; y++) {
//                if (UserModel.getUserByUsername(keysUsers[x]).getUserScore() < UserModel.getUserByUsername(keysUsers[y]).getUserScore()) {
//                    temp = keysUsers[y];
//                    keysUsers[y] = keysUsers[x];
//                    keysUsers[x] = temp;
//                }
//                if (UserModel.getUserByUsername(keysUsers[x]).getUserScore() == UserModel.getUserByUsername(keysUsers[y]).getUserScore()) {
//                    if (keysUsers[x].compareToIgnoreCase(keysUsers[y]) > 0) {
//                        temp = keysUsers[x];
//                        keysUsers[x] = keysUsers[y];
//                        keysUsers[y] = temp;
//                    }
//                }
//            }
//        }
//        int rank = 1;
//        for (int i = 0; i < keysUsers.length; i++) {
//            MainMenuView.showInput((rank) + "- " + UserModel.getUserByUsername(keysUsers[i]).getNickname() + ": " + UserModel.getUserByUsername(keysUsers[i]).getUserScore());
//            if (i < keysUsers.length - 1 && UserModel.getUserByUsername(keysUsers[i]).getUserScore() != UserModel.getUserByUsername(keysUsers[i + 1]).getUserScore())
//                rank++;
//        }
//    }

    public static String changePassword(String currentPassword, String newPassword, String token) {

        if (!UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token)).getPassword().equals(currentPassword))
            return ("current password is invalid");
        else if (currentPassword.equals(newPassword))
            return ("please enter a new password");
        else {
            UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
            user.changePassword(newPassword);
            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
            return ("password changed successfully!");
        }
    }

}